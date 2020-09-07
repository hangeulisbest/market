package wj.baedal.market.repository.store;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import wj.baedal.market.controller.dto.category.CategoryResponseDto;
import wj.baedal.market.controller.dto.menu.MenuResponseDto;
import wj.baedal.market.controller.dto.store.StoreResponseDto;
import wj.baedal.market.controller.dto.store.StoreSearchResponseDto;
import wj.baedal.market.entity.category.QCategory;
import wj.baedal.market.entity.categorystore.CategoryStore;
import wj.baedal.market.entity.categorystore.QCategoryStore;
import wj.baedal.market.entity.menu.Menu;
import wj.baedal.market.entity.menu.QMenu;
import wj.baedal.market.entity.store.QStore;
import wj.baedal.market.entity.store.Store;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static wj.baedal.market.entity.category.QCategory.category;
import static wj.baedal.market.entity.categorystore.QCategoryStore.categoryStore;
import static wj.baedal.market.entity.menu.QMenu.menu;
import static wj.baedal.market.entity.store.QStore.store;

@Repository
public class StoreSearchRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StoreSearchRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<StoreSearchResponseDto> searchStoreQueryDSL(StoreSearchCondition searchCondition){

        /**
         * categoryStore를 기준으로 category는 다대일 이며,
         * store 역시 다대일이다.
         * 따라서 fetch join으로 다 가져올 수 있다.
         *
         * where 문으로 가게 이름과 카테고리 이름에 관한 검색조건을 적용한다.
         *
         * */
        List<CategoryStore> searhCategoryStoreList = queryFactory.selectFrom(categoryStore)
                .leftJoin(categoryStore.category, category).fetchJoin()
                .leftJoin(categoryStore.store, store).fetchJoin()
                .where(storeNameEq(searchCondition.getStoreName())
                        ,categoryNameEq(searchCondition.getCategoryName()))
                .fetch();


        /**
         *  menu를 기준으로 store는 다대일이므로
         *  모든 메뉴와 함께 가게의 정보도 함께 가져올 수 있다.
         *
         *  where 문으로 가게이름에 관한 검색조건을 적용한다.
         * */
        List<Menu> searhMenuList = queryFactory.selectFrom(menu)
                .leftJoin(menu.store, store).fetchJoin()
                .where(storeNameEq(searchCondition.getStoreName()))
                .fetch();


        /**
         *  중복되지 않는 Store만 모으기 위해,
         *  categoryStore 에서 스트림을 형성하여 Store를 Set에 담아둔다.
         * */
        Set<Store> storeList = searhCategoryStoreList.stream()
                .map(o -> o.getStore())
                .collect(Collectors.toSet());


        return storeList.stream().map(
                o->StoreSearchResponseDto.builder()
                .categoryList(
                        /**
                         *  가게 하나당 여러개의 카테고리를 가질 수 있다.
                         *  가게 하나에 해당하는 모든 category를 DTO로 변환하는 작업
                         * */
                        searhCategoryStoreList.stream().filter(x->x.getStore().getId()==o.getId()).map(
                                x->CategoryResponseDto.builder()
                                        .id(x.getCategory().getId())
                                        .name(x.getCategory().getName())
                                        .parent((x.getCategory().getParent()==null)?null:x.getCategory().getParent().getName())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .menuList(
                        /**
                         *  가게 하나당 여러개의 메뉴를 가질 수 있다.
                         *  가게 하나에 해당하는 모든 메뉴들을 DTO로 변환하는 작업
                         *
                         * */
                        searhMenuList.stream().filter(x->x.getStore().getId()==o.getId()).map(
                                x->MenuResponseDto.builder()
                                        .id(x.getId())
                                        .name(x.getName())
                                        .price(x.getPrice())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .id(o.getId())
                .name(o.getName())
                .city(o.getAddress().getCity())
                .street(o.getAddress().getStreet())
                .zipcode(o.getAddress().getZipcode())
                .build()
        ).collect(Collectors.toList());

    }

    public BooleanExpression storeNameEq(String storeName){
        return StringUtils.hasText(storeName)? store.name.eq(storeName) : null;
    }

    public BooleanExpression categoryNameEq(String categoryName){
        return StringUtils.hasText(categoryName) ? category.name.eq(categoryName) : null;
    }
}

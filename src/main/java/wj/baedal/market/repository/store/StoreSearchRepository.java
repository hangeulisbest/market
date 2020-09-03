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
        List<CategoryStore> searhCategoryStoreList = queryFactory.selectFrom(categoryStore)
                .leftJoin(categoryStore.category, category).fetchJoin()
                .leftJoin(categoryStore.store, store).fetchJoin()
                .where(storeNameEq(searchCondition.getStoreName())
                        ,categoryNameEq(searchCondition.getCategoryName()))
                .fetch();

        List<Menu> searhMenuList = queryFactory.selectFrom(menu)
                .leftJoin(menu.store, store).fetchJoin()
                .where(storeNameEq(searchCondition.getStoreName()))
                .fetch();

        Set<Store> storeList = searhCategoryStoreList.stream()
                .map(o -> o.getStore())
                .collect(Collectors.toSet());


        return storeList.stream().map(
                o->StoreSearchResponseDto.builder()
                .categoryList(
                        searhCategoryStoreList.stream().filter(x->x.getStore().getId()==o.getId()).map(
                                x->CategoryResponseDto.builder()
                                        .id(x.getCategory().getId())
                                        .name(x.getCategory().getName())
                                        .parent((x.getCategory().getParent()==null)?null:x.getCategory().getParent().getName())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .menuList(
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

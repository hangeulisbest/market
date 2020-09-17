package wj.baedal.market.service.storeservice;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wj.baedal.market.controller.dto.category.CategoryListResponseDto;
import wj.baedal.market.controller.dto.category.CategoryResponseDto;
import wj.baedal.market.controller.dto.menu.MenuListResponseDto;
import wj.baedal.market.controller.dto.menu.MenuResponseDto;
import wj.baedal.market.controller.dto.store.*;
import wj.baedal.market.entity.Address;
import wj.baedal.market.entity.category.Category;
import wj.baedal.market.entity.category.CategoryRepository;
import wj.baedal.market.entity.categorystore.CategoryStore;
import wj.baedal.market.entity.categorystore.CategoryStoreRepository;
import wj.baedal.market.entity.menu.Menu;
import wj.baedal.market.entity.menu.MenuRepository;
import wj.baedal.market.entity.ordermenu.OrderMenu;
import wj.baedal.market.entity.ordermenu.OrderMenuRepository;
import wj.baedal.market.entity.store.Store;
import wj.baedal.market.entity.store.StoreRepository;
import wj.baedal.market.repository.store.StoreSearchCondition;
import wj.baedal.market.repository.store.StoreSearchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryStoreRepository categoryStoreRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final MenuRepository menuRepository;
    private final StoreSearchRepository storeSearchRepository;

    @Transactional
    public Long save(StoreSaveRequestDto requestDto){

        if(requestDto.getCategory().equals("") || requestDto.getCategory()==null){
            throw new IllegalArgumentException("카테고리는 필수 입니다.");
        }

        /** Store 를 static 생성 메서드로 생성
         *  새로운 주소(도시,거리,우편번호) 와 가게의 이름을 넘겨주면 됨.
         * */
        Store store = Store.createStore(
                new Address(requestDto.getCity(),
                        requestDto.getStreet(),
                        requestDto.getZipcode()),
                requestDto.getName()
        );

        /**
         *  카테고리가 존재하지 않을 수 있음!
         * */
        Optional<Category> category = categoryRepository.findByName(requestDto.getCategory());

        /** 가게 저장*/
        storeRepository.save(store);

        /**
         *  카테고리가 존재한다면 기존의 카테고리와 가게를 연결함.
         * */
        if(category.isPresent()){
            CategoryStore.createCategoryStore(category.get(),store);
        }else{
            /**
             *  카테고리가 존재하지 않는다면 새로운 카테고리를 생성하여 가게를 연결
             * */
            Category newCategory = Category.createCategory(requestDto.getCategory());
            categoryRepository.save(newCategory);
            CategoryStore.createCategoryStore(newCategory,store);
        }
        return store.getId();
    }

    /**
     *  가게 정보를 수정하는 함수.
     *  모든 메뉴 정보와 카테고리를 삭제한뒤 새로 갈아 끼움
     * */
    @Transactional
    public Long updateCategoeyAndMenu(Long id,StoreUpdateRequestDto requestDto){
        /** 가게 존재 검사*/
        Store store = storeRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 가게가 없습니다.")
        );

        /** 변경하고자 하는 카테고리가 존재해야하므로 존재하는 검사*/
        Category category = categoryRepository.findByName(requestDto.getCategoryName()).orElseThrow(
                () -> new IllegalArgumentException("해당 카테고리가 없습니다.")
        );

        /** 카테고리와 가게의 연관관계정보를 가지고 있는 CategoryStore를 삭제
         *  실제 카테고리는 삭제되지 않지만 카테고리와의 연관관계 정보가 사라진다.
         * */
        List<CategoryStore> categoryStoreByStore = categoryStoreRepository.findCategoryStoreByStore(store);
        categoryStoreByStore.stream().forEach(o->
                categoryStoreRepository.delete(o)
        );



        /** 해당 가게이름을 가진 모든 메뉴를 가져와서 삭제 */
        List<Menu> menuList = menuRepository.findByStore(store);
        List<OrderMenu> ordermenus = orderMenuRepository.findAll();
        for(OrderMenu orderMenu : ordermenus){
           if(menuList.contains(orderMenu.getMenu())){
               throw new IllegalArgumentException("주문된 메뉴가 포함된 가게는 수정할 수 없습니다.");
           }
        }
        menuList.stream().forEach(o->
                menuRepository.delete(o)
        );


        /** 메뉴리스트를 추가 */
        List<Menu> menuListCollect = requestDto.getMenuList().stream().map(
                o -> Menu.createMenu(o.getName(), o.getPrice(), store)
        ).collect(Collectors.toList());

        /** 카테고리와 가게의 연관관계 설정
         *  만약에 카테고리의 부모가 있다면 자동으로 설정함.
         * */
        CategoryStore.createCategoryStore(category,store);

        return store.getId();
    }


    /**
     *  검색 조건을 통해 Store를 검색할 수 있음
     *  StoreApiController 참고.
     *
     *  동적쿼리를 처리하기 위해 따로 repository를 생성함.
     * */
    @Transactional(readOnly = true)
    public StoreSearchListResponseDto findALLQueryDSL(StoreSearchCondition searchCondition){
        List<StoreSearchResponseDto> result = storeSearchRepository.searchStoreQueryDSL(searchCondition);
        return StoreSearchListResponseDto.builder()
                .data(result)
                .count(result.size())
                .build();
    }



    @Transactional(readOnly = true)
    public StoreSearchResponseDto findById2(Long id){
        return storeSearchRepository.findById2(id);
    }

    public Page<StoreSummaryResponseDto> searchStoreV5(Pageable pageable, StoreSearchCondition searchCondition) {
        return storeSearchRepository.searchStoreV5(pageable,searchCondition);
    }

    /**
     *  사용 X
     * */

    @Transactional(readOnly = true)
    public Page<StoreSearchResponseDto> searchStore(Pageable pageable, StoreSearchCondition condition) {
        return storeSearchRepository.searchStore(pageable,condition);
    }

    /**
     * 사용 X
     *
     * findALlQueryDSL을 사용할 것
     *
     * */
    @Transactional(readOnly = true)
    public StoreResponseDto findById(Long id){
        Store store = storeRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당하는 가게가 없습니다.")
        );
        // 메뉴를 DTO로 만들기
        List<Menu> menuList = menuRepository.findByStore(store);
        List<MenuResponseDto> menuListCollect = menuList.stream().map(
                o -> new MenuResponseDto(o.getId(),o.getName(), o.getPrice())
        ).collect(Collectors.toList());

        // 카테고리를 DTO로 만들기
        List<CategoryStore> categoryStoreList = categoryStoreRepository.findCategoryStoreByStore(store);
        List<CategoryResponseDto> categoryListCollect = categoryStoreList.stream().map(
                o -> new CategoryResponseDto(o.getCategory().getId()
                        , o.getCategory().getName()
                        , (o.getCategory().getParent() == null) ? null : o.getCategory().getParent().getName())
        ).collect(Collectors.toList());

        return StoreResponseDto.builder()
                .name(store.getName())
                .city(store.getAddress().getCity())
                .street(store.getAddress().getStreet())
                .zipcode(store.getAddress().getZipcode())
                .menuList(
                        MenuListResponseDto.builder()
                        .count(menuListCollect.size())
                        .data(menuListCollect)
                        .build()
                )
                .categoryList(
                        CategoryListResponseDto.builder()
                        .count(categoryListCollect.size())
                        .data(categoryListCollect)
                        .build()
                )
                .build();

    }

    /**
     * 사용 X
     *
     * findALlQueryDSL을 사용할 것
     *
     * */
    @Transactional(readOnly = true)
    public StoreSummaryListResponseDto findAll(){
        List<Store> storeList = storeRepository.findAll();
        List<StoreSummaryResponseDto> collect = storeList.stream().map(o -> new StoreSummaryResponseDto(
                o.getId(),
                o.getName(),
                o.getAddress().getCity(),
                o.getAddress().getStreet(),
                o.getAddress().getZipcode()
        )).collect(Collectors.toList());

        return StoreSummaryListResponseDto.builder()
                .count(collect.size())
                .data(collect)
                .build();
    }



    /**
     * 사용 X
     *
     * findALlQueryDSL을 사용할 것
     *
     * */
    @Transactional(readOnly = true)
    public StoreListResponseDto findAll2(){
        List<Store> storeList = storeRepository.findAll();
        List<StoreResponseDto<Object, Object>> collect = storeList.stream().map(o -> StoreResponseDto.builder()
                .name(o.getName())
                .city(o.getAddress().getCity())
                .street(o.getAddress().getStreet())
                .zipcode(o.getAddress().getZipcode())
                .menuList(
                        MenuListResponseDto.builder()
                                .count(menuRepository.findByStore(o).stream().map(
                                        x -> new MenuResponseDto(x.getId(),x.getName(), x.getPrice())
                                ).collect(Collectors.toList()).size())
                                .data(menuRepository.findByStore(o).stream().map(
                                        x -> new MenuResponseDto(x.getId(),x.getName(), x.getPrice())
                                ).collect(Collectors.toList()))
                                .build()
                )
                .categoryList(
                        CategoryListResponseDto.builder()
                                .count(categoryStoreRepository.findCategoryStoreByStore(o).stream().map(
                                        x -> new CategoryResponseDto(x.getCategory().getId()
                                                , x.getCategory().getName()
                                                , (x.getCategory().getParent() == null) ? null : x.getCategory().getParent().getName())
                                ).collect(Collectors.toList()).size())
                                .data(categoryStoreRepository.findCategoryStoreByStore(o).stream().map(
                                        x -> new CategoryResponseDto(x.getCategory().getId()
                                                , x.getCategory().getName()
                                                , (x.getCategory().getParent() == null) ? null : x.getCategory().getParent().getName())
                                ).collect(Collectors.toList()))
                                .build()
                ).build()
        ).collect(Collectors.toList());

        return StoreListResponseDto.builder()
                .count(collect.size())
                .data(collect)
                .build();

    }

}

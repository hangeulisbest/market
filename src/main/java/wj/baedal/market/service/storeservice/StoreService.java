package wj.baedal.market.service.storeservice;


import lombok.RequiredArgsConstructor;
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
import wj.baedal.market.entity.store.Store;
import wj.baedal.market.entity.store.StoreRepository;
import wj.baedal.market.repository.store.StoreSearchCondition;
import wj.baedal.market.repository.store.StoreSearchRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryStoreRepository categoryStoreRepository;
    private final MenuRepository menuRepository;
    private final StoreSearchRepository storeSearchRepository;

    @Transactional
    public Long save(StoreSaveRequestDto requestDto){
        Store store = Store.createStore(
                new Address(requestDto.getCity(),
                        requestDto.getStreet(),
                        requestDto.getZipcode()),
                requestDto.getName()
        );
        storeRepository.save(store);
        return store.getId();
    }

    @Transactional
    public Long updateCategoeyAndMenu(Long id,StoreUpdateRequestDto requestDto){
        Store store = storeRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 가게가 없습니다.")
        );

        Category category = categoryRepository.findByName(requestDto.getCategoryName()).orElseThrow(
                () -> new IllegalArgumentException("해당 카테고리가 없습니다.")
        );
        // 카테고리 삭제

        List<CategoryStore> categoryStoreByStore = categoryStoreRepository.findCategoryStoreByStore(store);
        categoryStoreByStore.stream().forEach(o->
                categoryStoreRepository.delete(o)
        );

        //메뉴삭제


        List<Menu> menuList = menuRepository.findByStore(store);
        menuList.stream().forEach(o->
                menuRepository.delete(o)
        );


        // 메뉴 추가
        List<Menu> menuListCollect = requestDto.getMenuList().stream().map(
                o -> Menu.createMenu(o.getName(), o.getPrice(), store)
        ).collect(Collectors.toList());

        // 카테고리 추가
        CategoryStore.createCategoryStore(category,store);

        return store.getId();
    }


    /**
     * 사용 x!
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
     * 조회용 레포지토리를 따로 작성하여 쿼리를 개선할 예정! 사용 X!
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


    @Transactional(readOnly = true)
    public List<StoreSearchResponseDto> findALLQueryDSL(StoreSearchCondition searchCondition){
        return storeSearchRepository.searchStoreQueryDSL(searchCondition);
    }
}

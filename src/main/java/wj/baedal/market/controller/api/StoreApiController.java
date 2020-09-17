package wj.baedal.market.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import wj.baedal.market.controller.dto.store.*;
import wj.baedal.market.repository.store.StoreSearchCondition;
import wj.baedal.market.service.storeservice.StoreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreApiController {

    private final StoreService storeService;

    /**
     * 가게 정보 저장
     * Request
     * {
     *      "name" : String,
     *      "city" : String,
     *      "street": String,
     *      "zipcode" : String,
     *      "category" : String,
     * }
     *
     * Response
     * Long(Store's Id)
     * */
    @PostMapping("/api/v1/stores")
    public Long save(@RequestBody StoreSaveRequestDto requestDto){
        return storeService.save(requestDto);
    }


    /**
     * 가게 정보 수정
     *
     * 카테고리와 메뉴정보를 수정할 수 있습니다.
     * 기존에 가지고 있던 카테고리와의 연관관계와 메뉴정보는 모두 삭제 됩니다.
     *
     * Request
     * {
     *     "categoryName" : String,
     *     "menuList" : [
     *          {
     *             "name" : String,
     *             "price" : int
     *          },
     *          ...
     *     ]
     * }
     *
     * Response
     * Long ( store's id )
     * */
    @PutMapping("/api/v1/stores/{id}")
    public Long updateCategoryAndMenuList(
            @PathVariable Long id,
            @RequestBody StoreUpdateRequestDto requestDto
    ){
        return storeService.updateCategoeyAndMenu(id,requestDto);
    }



    /**
     * 가게 검색
     * - 가게 이름 및 카테고리로 가게들을 검색할 수 있음.
     * - 가게 이름으로 검색하면 가게이름은 유일하기 때문에 하나의 결과만 나올것임.
     *
     * Request
     * {
     *     "storeName" : String,
     *     "categoryName" : String
     * }
     *
     *
     * Response
     * {
     *     "count" : int,
     *     "data" : [
     *         {
     *              "id" : Long,
     *              "name" : String,
     *              "city" : String,
     *              "street" : String,
     *              "zipcode" : String,
     *          }
     *     ]
     *
     * }
     *
     * */
    @PostMapping("/api/v3/stores")
    public StoreSearchListResponseDto findAllQueryDSL(@RequestBody StoreSearchCondition searchCondition){
        return storeService.findALLQueryDSL(searchCondition);
    }


    /**
     * Request & Response 는 /api/b3/stores 와 같음
     *
     * v3 에서 paging을 추가함
     * */

    @PostMapping("/api/v4/stores")
    public Page<StoreSearchResponseDto> searchStore(@PageableDefault(sort = {"name"},value = 5)Pageable pageable,@RequestBody StoreSearchCondition condition){
        return storeService.searchStore(pageable,condition);
    }

    @PostMapping("/api/v5/stores")
    public Page<StoreSummaryResponseDto> searchStoreV5(@PageableDefault(sort = {"name"},value = 5)Pageable pageable
    ,@RequestBody StoreSearchCondition searchCondition){
        return storeService.searchStoreV5(pageable,searchCondition);
    }


    /**
     * findAllQueryDSL 사용 권장
     * */
    @GetMapping("/api/v1/stores/{id}")
    public StoreResponseDto findById(@PathVariable Long id){
        return storeService.findById(id);
    }

    /**
     * findAllQueryDSL 사용 권장
     * */
    @GetMapping("/api/v1/stores")
    public StoreSummaryListResponseDto findAll(){
        return storeService.findAll();
    }

    /**
     * findAllQueryDSL 사용 권장
     * */
    @GetMapping("/api/v2/stores")
    public StoreListResponseDto findAll2(){
        return storeService.findAll2();
    }
}

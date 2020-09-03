package wj.baedal.market.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wj.baedal.market.controller.dto.store.*;
import wj.baedal.market.repository.store.StoreSearchCondition;
import wj.baedal.market.service.storeservice.StoreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreApiController {

    private final StoreService storeService;

    @PostMapping("/api/v1/stores")
    public Long save(@RequestBody StoreSaveRequestDto requestDto){
        return storeService.save(requestDto);
    }


    @PutMapping("/api/v1/stores/{id}")
    public Long updateCategoryAndMenuList(
            @PathVariable Long id,
            @RequestBody StoreUpdateRequestDto requestDto
    ){
        return storeService.updateCategoeyAndMenu(id,requestDto);
    }


    @GetMapping("/api/v1/stores/{id}")
    public StoreResponseDto findById(@PathVariable Long id){
        return storeService.findById(id);
    }

    @GetMapping("/api/v1/stores")
    public StoreSummaryListResponseDto findAll(){
        return storeService.findAll();
    }

    @GetMapping("/api/v2/stores")
    public StoreListResponseDto findAll2(){
        return storeService.findAll2();
    }


    @GetMapping("/api/v3/stores")
    public StoreSearchListResponseDto findAllQueryDSL(@RequestBody StoreSearchCondition searchCondition){
        List<StoreSearchResponseDto> result = storeService.findALLQueryDSL(searchCondition);
        return StoreSearchListResponseDto.builder()
                .data(result)
                .count(result.size())
                .build();
    }
}

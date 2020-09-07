package wj.baedal.market.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wj.baedal.market.controller.dto.category.CategoryListResponseDto;
import wj.baedal.market.controller.dto.category.CategorySaveRequestDto;
import wj.baedal.market.controller.dto.category.CategoryUpdateRequestDto;
import wj.baedal.market.service.categoryservice.CategoryService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@RestController
public class CategoryApiController {

    private final CategoryService categoryService;

    /**
     * 카테고리 저장
     *
     * Request
     *{
     *     "name": String,
     *     "parent" : String(null 가능,단 parent가 존재하지 않을 경우 에러반환)
     *}
     *
     * Response
     * Long ( 저장된 Category의 id)
     * */

    @PostMapping("/api/v1/categories")
    public Long save(@Valid @RequestBody CategorySaveRequestDto requestDto){
        return categoryService.save(requestDto);
    }

    /**
     * 모든 카테고리 조회
     *
     * Request
     * none
     *
     * Response
     * CategoryListResponseDto
     * {
     *     "count" : int,
     *     "data" : [
     *              {
     *                "id" : Long,
     *                "name" : String,
     *                "parent" : String (null 가능)
     *              },
     *              ...
     *     ]
     * }
     * */
    @GetMapping("/api/v1/categories")
    public CategoryListResponseDto findAll(){
        return categoryService.findAll();
    }

    /**
     * 카테고리 수정
     *
     * Request
     * {
     *     "name" : String
     * }
     *
     * Response
     * Long (업데이트된 카테고리의 아이디)
     * */
    @PutMapping("/api/v1/categories/{id}")
    public Long updateCategoryName(@PathVariable Long id, @RequestBody CategoryUpdateRequestDto requestDto){
        return categoryService.update(id,requestDto);
    }

    /**
     * 카테고리 삭제
     * */
    @DeleteMapping("/api/v1/categories/{id}")
    public Long delete(@PathVariable Long id){
        categoryService.delete(id);
        return id;
    }

}

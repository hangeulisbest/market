package wj.baedal.market.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wj.baedal.market.controller.dto.category.CategoryListResponseDto;
import wj.baedal.market.controller.dto.category.CategorySaveRequestDto;
import wj.baedal.market.service.categoryservice.CategoryService;

import javax.validation.Valid;
import java.util.List;
@RequiredArgsConstructor
@RestController
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping("/api/v1/categories")
    public Long save(@Valid @RequestBody CategorySaveRequestDto requestDto){
        return categoryService.save(requestDto);
    }

    @GetMapping("/api/v1/categories")
    public CategoryListResponseDto findAll(){
        return categoryService.findAll();
    }

    @DeleteMapping("/api/v1/categories/{id}")
    public Long delete(@PathVariable Long id){
        categoryService.delete(id);
        return id;
    }

}

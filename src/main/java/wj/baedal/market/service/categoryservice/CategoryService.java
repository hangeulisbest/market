package wj.baedal.market.service.categoryservice;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wj.baedal.market.controller.dto.category.CategoryListResponseDto;
import wj.baedal.market.controller.dto.category.CategoryResponseDto;
import wj.baedal.market.controller.dto.category.CategorySaveRequestDto;
import wj.baedal.market.entity.category.Category;
import wj.baedal.market.entity.category.CategoryRepository;
import wj.baedal.market.entity.categorystore.CategoryStore;
import wj.baedal.market.entity.categorystore.CategoryStoreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryStoreRepository categoryStoreRepository;

    @Transactional
    public Long save(CategorySaveRequestDto requestDto){
        Category category = Category.createCategory(requestDto.getName());
        if(requestDto.getParent()!=null) {
            Category parent = categoryRepository.findByName(requestDto.getParent()).get();
            category.setParent(parent);
        }
        categoryRepository.save(category);
        return category.getId();
    }

    @Transactional(readOnly = true)
    public CategoryListResponseDto findAll(){
        List<Category> all = categoryRepository.findAll();
        List<CategoryResponseDto> collect = all.stream().map(
                o -> new CategoryResponseDto(o.getId()
                        ,o.getName()
                        ,(o.getParent()==null)?null:o.getParent().getName()
        )).collect(Collectors.toList());
        return CategoryListResponseDto.builder()
                .count(collect.size())
                .data(collect)
                .build();
    }

    @Transactional
    public void delete(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 카테고리가 없습니다.")
        );


        List<CategoryStore> categoryStoreByCategory = categoryStoreRepository.findCategoryStoreByCategory(category);
        if(categoryStoreByCategory.size()>0){
            throw new IllegalStateException("카테고리를 참조하는 가게가 있습니다");
        }

        categoryRepository.delete(category);

    }
}

package wj.baedal.market.service.categoryservice;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wj.baedal.market.controller.dto.category.CategoryListResponseDto;
import wj.baedal.market.controller.dto.category.CategoryResponseDto;
import wj.baedal.market.controller.dto.category.CategorySaveRequestDto;
import wj.baedal.market.controller.dto.category.CategoryUpdateRequestDto;
import wj.baedal.market.entity.category.Category;
import wj.baedal.market.entity.category.CategoryRepository;
import wj.baedal.market.entity.categorystore.CategoryStore;
import wj.baedal.market.entity.categorystore.CategoryStoreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryStoreRepository categoryStoreRepository;

    @Transactional
    public Long save(CategorySaveRequestDto requestDto){
        /** 카테고리를 먼저 생성 */
        Category category = Category.createCategory(requestDto.getName());

        /**만약 request의 parent가 존재하지 않는다면 에러를 반환*/
        if(requestDto.getParent()!=null) {
            Category parent = categoryRepository.findByName(requestDto.getName()).orElseThrow(() ->
                    new IllegalArgumentException("parent의 이름을 가진 카테고리가 존재하지 않습니다.")
            );
            /** request에서 보낸 parent를 생성한 category의 부모로 설정 */
            category.setParent(parent);
        }
        categoryRepository.save(category);
        return category.getId();
    }

    @Transactional(readOnly = true)
    public CategoryListResponseDto findAll(){
        // 모든 카테고리를 검색
        List<Category> all = categoryRepository.findAll();

        /**
         * List<Category> -> List<CategoryResponseDto> 로 변경
         * 단, parent가 null인경우 getParent().getName()은 NullPointerException 에러나므로
         * parent가 null인 경우를 체크해 줘야한다.
        */
        List<CategoryResponseDto> collect = all.stream().map(
                o -> new CategoryResponseDto(o.getId()
                        ,o.getName()
                        ,(o.getParent()==null)?null:o.getParent().getName()
        )).collect(Collectors.toList());

        /**
         * List<CategoryResponseDto> -> CategoryListResponseDto
         * */
        return CategoryListResponseDto.builder()
                .count(collect.size())
                .data(collect)
                .build();
    }

    @Transactional
    public void delete(Long id){
        /** 삭제하고자 하는 카테고리가 있는 지 검사*/
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 카테고리가 없습니다.")
        );


        /** 만약 카테고리를 참조하고 있는 가게가 있는 경우
         *  무결성 제약조건에 의해 카테고리 삭제를 할 수 없음.
         *  따라서 참조하고 있는 가게가 없어야 한다.
         * */

        List<CategoryStore> categoryStoreByCategory = categoryStoreRepository.findCategoryStoreByCategory(category);
        if(categoryStoreByCategory.size()>0){
            throw new IllegalStateException("카테고리를 참조하는 가게가 있습니다");
        }

        categoryRepository.delete(category);

    }


    @Transactional
    public Long update(Long id, CategoryUpdateRequestDto requestDto) {
        /** 업데이트 하고자 하는 카테고리의 이름이 이미 존재하는 지 검사.
         *  카테고리 이름의 제약조건이 Unique 이므로 이름으로 조회하면 하나의 Optional객체가 반환된다.
         * */
        Optional<Category> findCategory = categoryRepository.findByName(requestDto.getName());

        /** 이름이 이미 존재하기 때문에 에러를 반환*/
        if(findCategory.isPresent()){
            throw new IllegalArgumentException("해당하는 카테고리의 이름이 이미 존재합니다.");
        }

        /** 변경하려는 카테고리의 아이디가 존재하는 지 검사*/
        Category category = categoryRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("변경하려는 카테고리가 존재하지 않습니다.")
        );

        /** 카테고리 객체의 메서드로 이름을 변경함*/
        category.changeName(requestDto.getName());
        return category.getId();
    }
}

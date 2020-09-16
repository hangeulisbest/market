package wj.baedal.market.controller.dto.store;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.controller.dto.category.CategoryResponseDto;
import wj.baedal.market.controller.dto.menu.MenuResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class StoreSearchResponseDto {

    private Long id;
    private String name;

    private String city;
    private String street;
    private String zipcode;

    private List<MenuResponseDto> menuList;
    private List<CategoryResponseDto> categoryList;

    @Builder
    @QueryProjection
    public StoreSearchResponseDto(Long id, String name, String city, String street, String zipcode, List<MenuResponseDto> menuList, List<CategoryResponseDto> categoryList) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.menuList = menuList;
        this.categoryList = categoryList;
    }
}

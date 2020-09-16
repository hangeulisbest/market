package wj.baedal.market.controller.dto.store;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.controller.dto.menu.MenuListResponseDto;
import wj.baedal.market.controller.dto.menu.MenuRequestDto;
import wj.baedal.market.controller.dto.menu.MenuResponseDto;

import java.util.List;
@NoArgsConstructor
@Getter
public class StoreUpdateRequestDto {
    private String categoryName;
    private List<MenuRequestDto> menuList;

    @Builder
    public StoreUpdateRequestDto(String categoryName, List<MenuRequestDto> menuList) {
        this.categoryName = categoryName;
        this.menuList = menuList;
    }
}


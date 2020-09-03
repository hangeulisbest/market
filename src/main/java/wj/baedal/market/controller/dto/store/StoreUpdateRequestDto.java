package wj.baedal.market.controller.dto.store;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.controller.dto.menu.MenuListResponseDto;
import wj.baedal.market.controller.dto.menu.MenuResponseDto;

import java.util.List;
@NoArgsConstructor
@Getter
public class StoreUpdateRequestDto {
    private String categoryName;
    private List<MenuResponseDto> menuList;

    @Builder
    public StoreUpdateRequestDto(String categoryName, List<MenuResponseDto> menuList) {
        this.categoryName = categoryName;
        this.menuList = menuList;
    }
}


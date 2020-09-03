package wj.baedal.market.controller.dto.ordermenu;

import lombok.Builder;
import lombok.Data;

@Data
public class OrderMenuResponseDto {
    private Long menuId;
    private int count;

    @Builder
    public OrderMenuResponseDto(Long menuId, int count) {
        this.menuId = menuId;
        this.count = count;
    }
}

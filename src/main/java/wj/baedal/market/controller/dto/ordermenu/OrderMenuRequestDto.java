package wj.baedal.market.controller.dto.ordermenu;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderMenuRequestDto {

    private Long menuId;
    private int count;

    @Builder
    public OrderMenuRequestDto(Long menuId, int count) {
        this.menuId = menuId;
        this.count = count;
    }
}

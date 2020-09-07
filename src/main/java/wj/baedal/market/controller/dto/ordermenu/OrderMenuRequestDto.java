package wj.baedal.market.controller.dto.ordermenu;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderMenuRequestDto {

    // 메뉴의 아이디
    private Long menuId;

    // 몇개의 메뉴를 주문했는지
    private int count;

    @Builder
    public OrderMenuRequestDto(Long menuId, int count) {
        this.menuId = menuId;
        this.count = count;
    }
}

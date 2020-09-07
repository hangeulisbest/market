package wj.baedal.market.controller.dto.order;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.controller.dto.ordermenu.OrderMenuRequestDto;

import java.util.List;

@NoArgsConstructor
@Getter
public class OrderSaveRequestDto {

    // 주문 저장하려는 유저의 아이디
    private Long userId;

    // 배달 받을 주소
    private String deliveryCity;
    private String deliveryStreet;
    private String deliveryZipcode;

    // 어떤 메뉴는 얼마나 시켰는지에 관한 리스트
    private List<OrderMenuRequestDto> orderMenuList;


    @Builder
    public OrderSaveRequestDto(Long userId, String deliveryCity, String deliveryStreet, String deliveryZipcode, List<OrderMenuRequestDto> orderMenuList) {
        this.userId = userId;
        this.deliveryCity = deliveryCity;
        this.deliveryStreet = deliveryStreet;
        this.deliveryZipcode = deliveryZipcode;
        this.orderMenuList = orderMenuList;
    }
}

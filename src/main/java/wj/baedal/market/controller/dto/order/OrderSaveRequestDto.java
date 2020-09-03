package wj.baedal.market.controller.dto.order;


import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.controller.dto.ordermenu.OrderMenuRequestDto;

import java.util.List;

@NoArgsConstructor
@Getter
public class OrderSaveRequestDto {

    private Long userId;

    private String deliveryCity;
    private String deliveryStreet;
    private String deliveryZipcode;

    private List<OrderMenuRequestDto> orderMenuList;

    public OrderSaveRequestDto(Long userId, String deliveryCity, String deliveryStreet, String deliveryZipcode, List<OrderMenuRequestDto> orderMenuList) {
        this.userId = userId;
        this.deliveryCity = deliveryCity;
        this.deliveryStreet = deliveryStreet;
        this.deliveryZipcode = deliveryZipcode;
        this.orderMenuList = orderMenuList;
    }
}

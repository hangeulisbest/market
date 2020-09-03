package wj.baedal.market.controller.dto.order;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wj.baedal.market.controller.dto.ordermenu.OrderMenuResponseDto;
import wj.baedal.market.entity.OrderStatus;

import java.time.LocalDateTime;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDto<T> {

    //주문 정보
    private Long orderId;

    // 유저 정보
    private Long userId;
    private String username;

    // 시간
    private LocalDateTime orderDate;


    // 주문메뉴 목록
    private List<OrderMenuResponseDto> orderMenuList;


    // 배달 위치
    private String city;
    private String street;
    private String zipcode;

    //주문 상태
    private OrderStatus orderStatus;


    @Builder
    public OrderResponseDto(Long orderId,Long userId, String username, LocalDateTime orderDate, List<OrderMenuResponseDto> orderMenuList, String city, String street, String zipcode, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.username = username;
        this.orderDate = orderDate;
        this.orderMenuList = orderMenuList;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.orderStatus = orderStatus;
    }
}

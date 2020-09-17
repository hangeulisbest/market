package wj.baedal.market.controller.dto.order;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import wj.baedal.market.entity.DeliveryStatus;
import wj.baedal.market.entity.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderSummaryResponseDto {
    // 주문 아아디
    private Long orderId;

    // 주문한 유저의 아이디
    private Long userId;
    private String userName;

    // 주문한 시각
    private LocalDateTime orderDate;

    // 배달 상태
    private DeliveryStatus deliveryStatus;

    // 주문 상태
    private OrderStatus orderStatus;

    @Builder
    @QueryProjection
    public OrderSummaryResponseDto(Long orderId, Long userId, String userName, LocalDateTime orderDate, DeliveryStatus deliveryStatus, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.orderDate = orderDate;
        this.deliveryStatus = deliveryStatus;
        this.orderStatus = orderStatus;
    }
}

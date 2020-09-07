package wj.baedal.market.repository.order;

import lombok.Data;
import wj.baedal.market.entity.OrderStatus;


/**
 * 검색 조건은 유저의 아이디와 주문상태로 검색
 *
 * */
@Data
public class OrderSearchCondition {
    private Long userId;
    private OrderStatus orderStatus;
}

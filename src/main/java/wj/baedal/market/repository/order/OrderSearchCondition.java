package wj.baedal.market.repository.order;

import lombok.Data;
import wj.baedal.market.entity.OrderStatus;

@Data
public class OrderSearchCondition {
    private Long userId;
    private OrderStatus orderStatus;
}

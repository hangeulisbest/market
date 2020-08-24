package wj.baedal.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wj.baedal.market.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}

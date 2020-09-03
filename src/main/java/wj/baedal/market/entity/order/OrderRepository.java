package wj.baedal.market.entity.order;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Override
    @EntityGraph(attributePaths = {"orderMenuList"})
    Optional<Order> findById(Long aLong);

    @EntityGraph(attributePaths = {"user","orderMenuList","delivery"})
    @Query("select o from Order o order by o.orderDate desc")
    List<Order> findAllDesc();
}

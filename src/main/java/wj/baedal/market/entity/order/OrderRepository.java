package wj.baedal.market.entity.order;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wj.baedal.market.entity.user.User;

import java.util.Optional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    /**
     *  OrderSearchRepository 로 직접 쿼리로 만듬.
     *  아래의 findById 와 findAllDesc 는 deprecated !!!
     * */

    @Override
    @EntityGraph(attributePaths = {"orderMenuList"})
    Optional<Order> findById(Long aLong);

    @EntityGraph(attributePaths = {"user","orderMenuList","delivery"})
    @Query("select o from Order o order by o.orderDate desc")
    List<Order> findAllDesc();

    List<Order> findByUser(User user);
}

package wj.baedal.market.entity.ordermenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wj.baedal.market.entity.menu.Menu;

import java.util.List;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu,Long> {

}

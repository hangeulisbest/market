package wj.baedal.market.entity.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wj.baedal.market.entity.store.Store;

import java.util.List;
import java.util.Optional;


@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findByName(String name);

    Optional<Menu> findByNameAndStore(String name, Store store);

    List<Menu> findByPriceGreaterThanEqual(int price);

    List<Menu> findByStore(Store store);
}

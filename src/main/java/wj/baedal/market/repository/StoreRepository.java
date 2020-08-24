package wj.baedal.market.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wj.baedal.market.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
}

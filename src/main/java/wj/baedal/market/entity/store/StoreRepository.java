package wj.baedal.market.entity.store;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {


    // 가게의 이름은 유일하다
    @EntityGraph(attributePaths = {"menuList"})
    Optional<Store> findByName(String name);

    @Override
    @EntityGraph(attributePaths = {"categoryStoreList"})
    List<Store> findAll();
}

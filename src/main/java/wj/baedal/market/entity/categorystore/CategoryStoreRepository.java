package wj.baedal.market.entity.categorystore;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wj.baedal.market.entity.category.Category;
import wj.baedal.market.entity.store.Store;


import java.util.List;

@Repository
public interface CategoryStoreRepository extends JpaRepository<CategoryStore,Long> {


    /** Store로 검색하면 주로 category를 많이 조회하므로 category도 같이 조인함 */
    @EntityGraph(attributePaths = {"category"})
    List<CategoryStore> findCategoryStoreByStore(Store store);

    /** category로 검색하면 주로 store를 많이 조회하므로 store도 같이 조인함 */
    @EntityGraph(attributePaths = {"store"})
    List<CategoryStore> findCategoryStoreByCategory(Category category);
}

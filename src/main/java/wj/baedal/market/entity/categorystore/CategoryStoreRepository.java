package wj.baedal.market.entity.categorystore;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wj.baedal.market.entity.category.Category;
import wj.baedal.market.entity.store.Store;


import java.util.List;

@Repository
public interface CategoryStoreRepository extends JpaRepository<CategoryStore,Long> {


    @EntityGraph(attributePaths = {"category"})
    List<CategoryStore> findCategoryStoreByStore(Store store);

    @EntityGraph(attributePaths = {"store"})
    List<CategoryStore> findCategoryStoreByCategory(Category category);
}

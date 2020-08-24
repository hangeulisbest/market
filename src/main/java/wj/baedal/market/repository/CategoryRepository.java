package wj.baedal.market.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wj.baedal.market.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}

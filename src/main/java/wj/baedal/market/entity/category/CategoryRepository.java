package wj.baedal.market.entity.category;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    /** 이름은 유일하기 때문에 Optional로 받는다 */
    Optional<Category> findByName(String name);
}

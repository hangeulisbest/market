package wj.baedal.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wj.baedal.market.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}

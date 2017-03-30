
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

}

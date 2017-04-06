
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

	@Query("select l from Like l where l.author.id=?1 and l.receiver.id=?2")
	Like getLikeOfChorbi(int idAuthor, int idReceiver);

	@Query("select l from Like l where l.author.id = ?1 and l.receiver.id = ?2")
	Like findLikeFromChorbies(int chorbiAuthor, int chorbiReceiver);
}

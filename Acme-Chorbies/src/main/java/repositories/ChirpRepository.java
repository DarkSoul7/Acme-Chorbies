
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	@Query("select c from Chirp c where c.sender.id = ?1 and c.original = true")
	public Collection<Chirp> findAllSentByActor(int actorId);

	@Query("select c from Chirp c where c.receiver.id = ?1 and c.original = false")
	public Collection<Chirp> findAllReceivedByActor(int actorId);

}

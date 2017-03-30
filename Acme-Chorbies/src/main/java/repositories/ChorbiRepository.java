
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("select c from Chorbi c where c.userAccount.id = ?1")
	Chorbi findByUserAccountId(int id);

	@Query("select c from Chorbi c where c.userAccount.username = ?1")
	Chorbi findByUserName(String username);

	@Query("select c from Chorbi c where c.id <> ?1")
	public Collection<Chorbi> findAllExceptPrincipal(int principalId);

}

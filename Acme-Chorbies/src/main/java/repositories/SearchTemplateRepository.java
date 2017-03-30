
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.SearchTemplate;

@Repository
public interface SearchTemplateRepository extends JpaRepository<SearchTemplate, Integer> {

	@Query("")
	public Collection<Chorbi> findChorbies();
}

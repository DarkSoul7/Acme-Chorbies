
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.Genre;
import domain.Relationship;
import domain.SearchTemplate;

@Repository
public interface SearchTemplateRepository extends JpaRepository<SearchTemplate, Integer> {

	@Query("select c from Chorbi c where c.relationship = ?1 and c.genre = ?2 and (c.coordinates.country = ?3 and c.coordinates.state = ?4 and c.coordinates.province = ?5 and c.coordinates.city = ?6)")
	public Collection<Chorbi> findChorbies(Relationship relationShip, Genre genre, String country, String state, String province, String city);
}

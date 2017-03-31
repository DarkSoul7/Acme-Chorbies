
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

	//Dashboard C

	//C1
	@Query("select count(c),c.coordinates.city from Chorbi c group by c.coordinates.country")
	public Collection<Object[]> getChorbiesPerCountry();

	@Query("select count(c),c.coordinates.city from Chorbi c group by c.coordinates.city")
	public Collection<Object[]> getChorbiesPerCity();

	//C2 

	//a)
	@Query("select min(datediff(CURRENT_DATE,c.birthDate)) from Chorbi c")
	public Integer minChorbiesAge();

	@Query("select max(datediff(CURRENT_DATE,c.birthDate)) from Chorbi c")
	public Integer maxChorbiesAge();

	//c)
	@Query("select sum((datediff(CURRENT_DATE,c.birthDate)/365))/(select count(c2) from Chorbi c2) from Chorbi c")
	public Integer avgChorbiesAge();

	//C3
	@Query("select count(c)*1.0/(select count(c2) from Chorbi c2) from Chorbi c where c.creditCard = null")
	public Double ratioChorbiesWithoutCreditCard();

	//C4

	//a
	@Query("select count(s)*1.0/(select count(s2) from SearchTemplate s2)  from SearchTemplate s where s.relationship = 0")
	public Double ratioChorbiesLookingForActivities();

	//b
	@Query("select count(s)*1.0/(select count(s2) from SearchTemplate s2)  from SearchTemplate s where s.relationship = 1")
	public Double ratioChorbiesLookingForFriends();

	//C
	@Query("select count(s)*1.0/(select count(s2) from SearchTemplate s2)  from SearchTemplate s where s.relationship = 2")
	public Double ratioChorbiesLookingForLove();
}

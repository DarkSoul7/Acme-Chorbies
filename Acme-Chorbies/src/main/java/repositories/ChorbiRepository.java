
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
	//TODO Form object para mapear los Object[]
	@Query("select count(c),c.coordinates.country from Chorbi c group by c.coordinates.country")
	public Collection<Object[]> getChorbiesPerCountry();
	
	@Query("select count(c),c.coordinates.city from Chorbi c group by c.coordinates.city")
	public Collection<Object[]> getChorbiesPerCity();
	
	//C2
	
	//a)
	@Query("select min(datediff(CURRENT_DATE,c.birthDate)/365) from Chorbi c")
	public Integer minChorbiesAge();
	
	@Query("select max(datediff(CURRENT_DATE,c.birthDate)/365) from Chorbi c")
	public Integer maxChorbiesAge();
	
	//c)
	@Query("select sum((datediff(CURRENT_DATE,c.birthDate)/365))/(select count(c2) from Chorbi c2) from Chorbi c")
	public Integer avgChorbiesAge();
	
	//C3
	@Query("select count(c)*1.0/(select count(c2) from Chorbi c2) from Chorbi c where c.creditCard = null")
	public Double ratioChorbiesWithoutCreditCard();
	
	//C4
	//TODO revisar esto
	//a
	@Query("select count(c)*1.0/(select count(c2) from Chorbi c2)  from Chorbi c where c.relationship = 0")
	public Double ratioChorbiesLookingForActivities();
	
	//b
	@Query("select count(c)*1.0/(select count(c2) from Chorbi c2)  from Chorbi c where c.relationship = 1")
	public Double ratioChorbiesLookingForFriends();
	
	//C
	@Query("select count(c)*1.0/(select count(c2) from Chorbi c2)  from Chorbi c where c.relationship = 2")
	public Double ratioChorbiesLookingForLove();
	
	//B1
	@Query("select c from Chorbi c order by c.receivedLikes.size desc")
	public Collection<Chorbi> listChorbiesSortedByReceivedLikes();
	
	//B2
	
	//a
	@Query("select min(c.receivedLikes.size) from Chorbi c")
	public Collection<Chorbi> minReceivedLikesPerChorbi();
	
	//b
	@Query("select max(c.receivedLikes.size) from Chorbi c")
	public Collection<Chorbi> maxReceivedLikesPerChorbi();
	
	//c
	@Query("select avg(c.receivedLikes.size) from Chorbi c")
	public Collection<Chorbi> avgReceivedLikesPerChorbi();
	
	//a
	@Query("select min(c.authoredLikes.size) from Chorbi c")
	public Collection<Chorbi> minAuthoredLikesPerChorbi();
	
	//b
	@Query("select max(c.authoredLikes.size) from Chorbi c")
	public Collection<Chorbi> maxAuthoredLikesPerChorbi();
	
	//c
	@Query("select avg(c.authoredLikes.size) from Chorbi c")
	public Collection<Chorbi> avgAuthoredLikesPerChorbi();
	
	//A1
	
	//a
	@Query("select min(c.receivedChirps.size) from Chorbi c")
	public Collection<Chorbi> minReceivedChirpsPerChorbi();
	
	//b
	@Query("select max(c.receivedChirps.size) from Chorbi c")
	public Collection<Chorbi> maxReceivedChirpsPerChorbi();
	
	//c
	@Query("select avg(c.receivedChirps.size) from Chorbi c")
	public Collection<Chorbi> avgReceivedChirpsPerChorbi();
	
	//A2
	
	//a
	@Query("select min(c.sentChirps.size) from Chorbi c")
	public Collection<Chorbi> minSentChirpsPerChorbi();
	
	//b
	@Query("select max(c.sentChirps.size) from Chorbi c")
	public Collection<Chorbi> maxSentChirpsPerChorbi();
	
	//c
	@Query("select avg(c.sentChirps.size) from Chorbi c")
	public Collection<Chorbi> avgSentChirpsPerChorbi();
	
	//A4
	@Query("select c from Chorbi c where c.sentChirps.size = (select max(c2.sentChirps.size) from Chorbi c2)")
	public Collection<Chorbi> getChorbiMoreSentChirp();
	
	//A3
	@Query("select c from Chorbi c where c.receivedChirps.size = (select max(c2.receivedChirps.size) from Chorbi c2)")
	public Collection<Chorbi> getChorbiMoreGotChirp();
}

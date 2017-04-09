
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import forms.ChorbiListForm;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {
	
	@Query("select c from Chorbi c where c.userAccount.id = ?1")
	public Chorbi findByUserAccountId(int id);
	
	@Query("select c from Chorbi c where c.userAccount.username = ?1")
	public Chorbi findByUserName(String username);
	
	@Query("select 1 from Chorbi c where c.userAccount.username = ?1")
	public Integer checkUsername(String username);
	
	@Query("select new forms.ChorbiListForm(c, case when exists(select 1 from Like l where l.receiver.id = ?1 and l.author.id = ?2) then true else false end) from Chorbi c where c.id = ?1")
	public ChorbiListForm findOneWithLike(int chorbiId, int principalId);
	
	@Query("select c from Chorbi c where c.id <> ?1")
	public Collection<Chorbi> findAllExceptPrincipal(int principalId);
	
	@Query("select l.receiver from Like l where l.author.id = ?1")
	public Collection<Chorbi> findChorbisFromAuthor(int authorId);
	
	@Query("select l.author from Like l where l.receiver.id = ?1")
	public Collection<Chorbi> findChorbisFromReceiver(int receiverId);
	
	@Query("select new forms.ChorbiListForm(l.receiver, case when (exists(select l2.receiver.id from Like l2 where l2.receiver.id = l.receiver.id and l2.author.id = ?2)) then true else false end) from Like l where l.author.id = ?1")
	public Collection<ChorbiListForm> findChorbisFromAuthorWithLikes(int authorId, int principalId);
	
	@Query("select new forms.ChorbiListForm(l.author, case when (exists(select l2.receiver.id from Like l2 where l2.receiver.id = l.author.id and l2.author.id = ?2)) then true else false end) from Like l where l.receiver.id = ?1")
	public Collection<ChorbiListForm> findChorbisFromReceiverWithLikes(int receiverId, int principalId);
	
	@Query("select new forms.ChorbiListForm(c, case when (exists(select l.receiver.id from Like l where l.receiver.id = c.id and l.author.id = ?1)) then true else false end) from Chorbi c where c.id != ?1")
	public Collection<ChorbiListForm> findAllExceptPrincipalWithLikes(int principalId);
	
	//Dashboard C
	//C1
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
	public Integer minReceivedLikesPerChorbi();
	
	//b
	@Query("select max(c.receivedLikes.size) from Chorbi c")
	public Integer maxReceivedLikesPerChorbi();
	
	//c
	@Query("select avg(c.receivedLikes.size) from Chorbi c")
	public Double avgReceivedLikesPerChorbi();
	
	//a
	@Query("select min(c.authoredLikes.size) from Chorbi c")
	public Integer minAuthoredLikesPerChorbi();
	
	//b
	@Query("select max(c.authoredLikes.size) from Chorbi c")
	public Integer maxAuthoredLikesPerChorbi();
	
	//c
	@Query("select avg(c.authoredLikes.size) from Chorbi c")
	public Double avgAuthoredLikesPerChorbi();
	
	//A1
	
	//a
	@Query("select min(c.receivedChirps.size) from Chorbi c")
	public Integer minReceivedChirpsPerChorbi();
	
	//b
	@Query("select max(c.receivedChirps.size) from Chorbi c")
	public Integer maxReceivedChirpsPerChorbi();
	
	//c
	@Query("select avg(c.receivedChirps.size) from Chorbi c")
	public Double avgReceivedChirpsPerChorbi();
	
	//A2
	
	//a
	@Query("select min(c.sentChirps.size) from Chorbi c")
	public Integer minSentChirpsPerChorbi();
	
	//b
	@Query("select max(c.sentChirps.size) from Chorbi c")
	public Integer maxSentChirpsPerChorbi();
	
	//c
	@Query("select avg(c.sentChirps.size) from Chorbi c")
	public Double avgSentChirpsPerChorbi();
	
	//A4
	@Query("select c from Chorbi c where c.sentChirps.size = (select max(c2.sentChirps.size) from Chorbi c2)")
	public Collection<Chorbi> getChorbiMoreSentChirp();
	
	//A3
	@Query("select c from Chorbi c where c.receivedChirps.size = (select max(c2.receivedChirps.size) from Chorbi c2)")
	public Collection<Chorbi> getChorbiMoreGotChirp();
}


package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SearchTemplate extends DomainEntity {

	//Attributes
	private Relationship	relationship;
	private Integer			age;
	private Genre			genre;
	private Coordinates		coordinates;
	private String			keyword;
	private Date			cachedMoment;


	//Constructor
	public SearchTemplate() {
		super();
	}

	//Getter & setter

	public Relationship getRelationship() {
		return this.relationship;
	}

	public void setRelationship(final Relationship relationship) {
		this.relationship = relationship;
	}

	@Min(18)
	public Integer getAge() {
		return this.age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public Genre getGenre() {
		return this.genre;
	}

	public void setGenre(final Genre genre) {
		this.genre = genre;
	}

	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@SafeHtml
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Past
	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCachedMoment() {
		return this.cachedMoment;
	}

	public void setCachedMoment(final Date cachedMoment) {
		this.cachedMoment = cachedMoment;
	}


	//RelationShips
	private Collection<Chorbi>	listChorbi;
	private Chorbi				chorbi;


	@Valid
	@ManyToMany
	@JoinTable(name = "searchTemplate_chorbi", joinColumns = @JoinColumn(name = "searchtemplate_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "chorbi_id", referencedColumnName = "id"))
	public Collection<Chorbi> getListChorbi() {
		return this.listChorbi;
	}

	public void setListChorbi(final Collection<Chorbi> listChorbi) {
		this.listChorbi = listChorbi;
	}

	@OneToOne(optional = false)
	public Chorbi getChorbi() {
		return this.chorbi;
	}

	public void setChorbi(final Chorbi chorbi) {
		this.chorbi = chorbi;
	}

}

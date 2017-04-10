
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chorbi extends Actor {

	// Attributes
	private String			picture;
	private String			description;
	private Relationship	relationship;
	private Genre			genre;
	private Coordinates		coordinates;
	private Date			birthDate;
	private CreditCard		creditCard;

	//Aux attribute
	private Boolean			overAge;
	private Boolean			validCreditCard;


	// Constructor
	public Chorbi() {
		super();
	}

	//Getter & setter

	@NotBlank
	@URL
	@SafeHtml
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotBlank
	@SafeHtml
	@Size(min = 0, max = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	public Relationship getRelationship() {
		return this.relationship;
	}

	public void setRelationship(final Relationship relationship) {
		this.relationship = relationship;
	}

	@NotNull
	public Genre getGenre() {
		return this.genre;
	}

	public void setGenre(final Genre genre) {
		this.genre = genre;
	}

	@NotNull
	@Valid
	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Past
	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Transient
	@AssertTrue
	public Boolean getOverAge() {
		return this.overAge;
	}

	public void setOverAge(final Boolean overAge) {
		this.overAge = overAge;
	}

	@Transient
	@AssertTrue
	public Boolean getValidCreditCard() {
		return this.validCreditCard;
	}

	public void setValidCreditCard(final Boolean validCreditCard) {
		this.validCreditCard = validCreditCard;
	}


	//RelationShips

	private Collection<Like>	authoredLikes;
	private Collection<Like>	receivedLikes;
	private Collection<Chirp>	sentChirps;
	private Collection<Chirp>	receivedChirps;
	private SearchTemplate		searchTemplate;


	@Valid
	@OneToMany(mappedBy = "author")
	public Collection<Like> getAuthoredLikes() {
		return this.authoredLikes;
	}

	public void setAuthoredLikes(final Collection<Like> authoredLikes) {
		this.authoredLikes = authoredLikes;
	}

	@Valid
	@OneToMany(mappedBy = "receiver")
	public Collection<Like> getReceivedLikes() {
		return this.receivedLikes;
	}

	public void setReceivedLikes(final Collection<Like> receivedLikes) {
		this.receivedLikes = receivedLikes;
	}

	@Valid
	@OneToMany(mappedBy = "sender")
	public Collection<Chirp> getSentChirps() {
		return this.sentChirps;
	}

	public void setSentChirps(final Collection<Chirp> sentChirps) {
		this.sentChirps = sentChirps;
	}

	@Valid
	@OneToMany(mappedBy = "receiver")
	public Collection<Chirp> getReceivedChirps() {
		return this.receivedChirps;
	}

	public void setReceivedChirps(final Collection<Chirp> receivedChirps) {
		this.receivedChirps = receivedChirps;
	}

	@Valid
	@OneToOne(mappedBy = "chorbi", optional = true)
	public SearchTemplate getSearchTemplate() {
		return this.searchTemplate;
	}

	public void setSearchTemplate(final SearchTemplate searchTemplate) {
		this.searchTemplate = searchTemplate;
	}

}


package forms;

import java.util.Date;

import domain.Coordinates;
import domain.CreditCard;
import domain.Genre;
import domain.Relationship;

public class ChorbiForm {

	//Attributes
	private int				id;
	private String			username;
	private String			password;
	private String			repeatPassword;
	private boolean			acceptCondition;
	private String			name;
	private String			surname;
	private String			picture;
	private String			description;
	private Relationship	relationShip;
	private Genre			genre;
	private Coordinates		coordinates;
	private Date			birthDate;
	private CreditCard		creditCard;
	private String			phone;
	private String			email;


	//Constructor
	public ChorbiForm() {
		super();
	}

	//Getter & setter

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public boolean isAcceptCondition() {
		return this.acceptCondition;
	}

	public void setAcceptCondition(final boolean acceptCondition) {
		this.acceptCondition = acceptCondition;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Relationship getRelationShip() {
		return this.relationShip;
	}

	public void setRelationShip(final Relationship relationShip) {
		this.relationShip = relationShip;
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

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}

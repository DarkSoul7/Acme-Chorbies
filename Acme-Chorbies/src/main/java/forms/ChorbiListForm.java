
package forms;

import java.util.Date;

import domain.Chorbi;
import domain.Coordinates;
import domain.Genre;
import domain.Relationship;

public class ChorbiListForm {
	
	// Attributes
	private int				id;
	private String			name;
	private String			surname;
	private String			phone;
	private String			email;
	private String			picture;
	private String			description;
	private Relationship	relationship;
	private Genre			genre;
	private Coordinates		coordinates;
	private Date			birthDate;
	private Boolean			liked;
	
	
	// Constructor
	public ChorbiListForm() {
		super();
	}
	
	public ChorbiListForm(Chorbi chorbi, Boolean liked) {
		super();
		
		this.setId(chorbi.getId());
		this.name = chorbi.getName();
		this.surname = chorbi.getSurname();
		this.phone = chorbi.getPhone();
		this.email = chorbi.getEmail();
		this.birthDate = chorbi.getBirthDate();
		this.coordinates = chorbi.getCoordinates();
		this.genre = chorbi.getGenre();
		this.picture = chorbi.getPicture();
		this.relationship = chorbi.getRelationship();
		this.description = chorbi.getDescription();
		this.liked = liked;
	}
	
	//Getter & setter
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public Relationship getRelationship() {
		return this.relationship;
	}
	
	public void setRelationship(final Relationship relationship) {
		this.relationship = relationship;
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
	
	public Boolean getLiked() {
		return liked;
	}
	
	public void setLiked(Boolean liked) {
		this.liked = liked;
	}
	
}

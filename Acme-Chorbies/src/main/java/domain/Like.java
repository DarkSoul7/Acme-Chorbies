
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "\"Like\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"author_id", "receiver_id"
	})
})
public class Like extends DomainEntity {

	//Attributes
	private String	comment;
	private Date	moment;


	//Constructor
	public Like() {
		super();
	}

	//Getters and setters

	@SafeHtml
	@Size(min = 0, max = 200)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Past
	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	//RellationShips

	private Chorbi	author;
	private Chorbi	receiver;


	@Valid
	@ManyToOne(optional = false)
	public Chorbi getAuthor() {
		return this.author;
	}

	public void setAuthor(final Chorbi author) {
		this.author = author;
	}

	@Valid
	@ManyToOne(optional = false)
	public Chorbi getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Chorbi receiver) {
		this.receiver = receiver;
	}

}

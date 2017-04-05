
package forms;

import org.hibernate.validator.constraints.SafeHtml;

public class LikeForm {

	private String	comment;
	private int		idReceiver;


	@SafeHtml
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public int getIdReceiver() {
		return this.idReceiver;
	}

	public void setIdReceiver(final int idReceiver) {
		this.idReceiver = idReceiver;
	}

}

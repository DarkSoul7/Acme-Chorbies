
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LikeRepository;
import domain.Chorbi;
import domain.Like;
import forms.LikeForm;

@Service
@Transactional
public class LikeService {

	//Managed repository

	@Autowired
	private LikeRepository	likeRepository;

	//Supported services
	@Autowired
	private ChorbiService	chorbiService;


	//Constructor

	public LikeService() {
		super();
	}

	public LikeForm create() {
		final LikeForm result = new LikeForm();

		return result;
	}

	public Collection<Like> findAll() {
		final Collection<Like> result = this.likeRepository.findAll();

		return result;
	}

	public Like findOne(final int likeId) {
		final Like result = this.likeRepository.findOne(likeId);

		return result;

	}

	public Like save(final Like like) {
		Assert.notNull(like);

		//Check the target chorbi has not already a like from the sender
		final Like likeDone = this.findLikeFromChorbies(like.getAuthor(), like.getReceiver());
		Assert.isTrue(likeDone == null);

		final Like result = this.likeRepository.save(like);

		return result;
	}

	public void delete(final Like like) {
		Assert.notNull(like);

		//Checking the target has already a like from the sender
		final Like likeDone = this.findLikeFromChorbies(like.getAuthor(), like.getReceiver());
		Assert.isTrue(like.equals(likeDone));

		this.likeRepository.delete(like);
	}

	public void delete(final int likeId) {
		final Like like = this.likeRepository.findOne(likeId);

		this.delete(like);
	}


	//Other business methods

	//	@Autowired
	private Validator	validator;


	public Like reconstruct(final LikeForm likeForm, final BindingResult binding) {
		Assert.notNull(likeForm);
		final Like result;
		if (likeForm.getId() == 0)
			result = new Like();
		else
			result = this.findOne(likeForm.getId());
		result.setComment(likeForm.getComment());
		result.setMoment(new Date());
		result.setAuthor(this.chorbiService.findByPrincipal());
		result.setReceiver(this.chorbiService.findOne(likeForm.getIdReceiver()));

		return result;
	}

	public Like getLikeOfChorbi(final int idAuthor, final int idReceiver) {
		return this.likeRepository.getLikeOfChorbi(idAuthor, idReceiver);
	}

	public Like findLikeFromChorbies(final Chorbi sender, final Chorbi receiver) {
		return this.likeRepository.findLikeFromChorbies(sender.getId(), receiver.getId());
	}
}

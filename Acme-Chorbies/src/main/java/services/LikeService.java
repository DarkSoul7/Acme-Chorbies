
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Like;
import forms.LikeForm;
import repositories.LikeRepository;

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
		final Like result = this.likeRepository.save(like);

		return result;
	}

	public void delete(final Like like) {
		this.likeRepository.delete(like);
	}

	public void delete(final int likeId) {
		final Like like = this.likeRepository.findOne(likeId);

		this.delete(like);
	}


	//Other business methods

	private Validator validator;


	public Like reconstruct(final LikeForm likeForm, final BindingResult binding) {
		Assert.notNull(likeForm);
		final Like result = new Like();

		result.setComment(likeForm.getComment());
		result.setMoment(new Date());
		result.setAuthor(this.chorbiService.findByPrincipal());
		result.setReceiver(this.chorbiService.findOne(likeForm.getIdReceiver()));

		return result;
	}

}

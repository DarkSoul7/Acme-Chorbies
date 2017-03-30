
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LikeRepository;
import domain.Like;

@Service
@Transactional
public class LikeService {

	//Managed repository

	@Autowired
	private LikeRepository	likeRepository;


	//Supported services

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

}

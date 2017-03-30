
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	//Managed repository

	@Autowired
	private ActorRepository	actorRepository;


	//Supported services

	public ActorService() {
		super();
	}

	public Actor create() {
		return new Actor();
	}

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);

	}

	public void save(final Actor actor) {
		this.actorRepository.save(actor);
	}

	public void delete(final Actor actor) {
		this.actorRepository.delete(actor);
	}

	//Other business methods

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result, "Any actor with userAccountId=" + userAccount.getId() + "has be found");

		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor result;
		int userAccountId;

		userAccountId = userAccount.getId();
		result = this.actorRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Actor findByUserName(final String username) {
		Assert.notNull(username);
		Actor result;

		result = this.actorRepository.findByUserName(username);

		return result;
	}

}

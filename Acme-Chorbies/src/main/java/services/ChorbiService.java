
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;

@Service
@Transactional
public class ChorbiService {

	//Managed repository

	@Autowired
	private ChorbiRepository	chorbiRepository;


	//Supported services

	public ChorbiService() {
		super();
	}

	public Chorbi create() {
		final Chorbi result = new Chorbi();

		return result;
	}

	public Collection<Chorbi> findAll() {
		return this.chorbiRepository.findAll();
	}

	public Chorbi findOne(final int chorbiId) {
		return this.chorbiRepository.findOne(chorbiId);

	}

	public Chorbi save(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		Chorbi result;

		result = this.chorbiRepository.save(chorbi);

		return result;
	}

	public void delete(final Chorbi chorbi) {
		this.chorbiRepository.delete(chorbi);
	}

	//Other business methods

	public Chorbi findByPrincipal() {
		Chorbi result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result, "Any chorbi with userAccountId=" + userAccount.getId() + "has be found");

		return result;
	}

	public Chorbi findByUserAccount(final UserAccount userAccount) {
		Chorbi result;
		int userAccountId;

		userAccountId = userAccount.getId();
		result = this.chorbiRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Chorbi findByUserName(final String username) {
		Assert.notNull(username);
		Chorbi result;

		result = this.chorbiRepository.findByUserName(username);

		return result;
	}

}


package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Chorbi;

@Service
@Transactional
public class AdministratorService {

	//Managed repository

	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supported services

	@Autowired
	private ChorbiService			chorbiService;


	//Constructor
	public AdministratorService() {
		super();
	}

	public Administrator create() {
		return null;
	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);

	}

	public void save(final Administrator administrator) {
		this.administratorRepository.save(administrator);
	}

	public void delete(final Administrator administrator) {
		this.administratorRepository.delete(administrator);
	}

	//Other business methods

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result, "Any administrator with userAccountId=" + userAccount.getId() + "has be found");

		return result;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Administrator result;
		int userAccountId;

		userAccountId = userAccount.getId();
		result = this.administratorRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Administrator findByUserName(final String username) {
		Assert.notNull(username);
		Administrator result;

		result = this.administratorRepository.findByUserName(username);

		return result;
	}

	public void banChorbi(final Chorbi chorbi) {
		this.findByPrincipal();
		Assert.notNull(chorbi);
		Assert.isTrue(chorbi.getUserAccount().isEnabled() == true);
		chorbi.getUserAccount().setEnabled(false);
		this.chorbiService.save(chorbi);
	}

	public void unbanChorbi(final Chorbi chorbi) {
		this.findByPrincipal();
		Assert.notNull(chorbi);
		Assert.isTrue(chorbi.getUserAccount().isEnabled() == false);
		chorbi.getUserAccount().setEnabled(true);
		this.chorbiService.save(chorbi);
	}
}

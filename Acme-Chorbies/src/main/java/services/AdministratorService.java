
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

@Service
@Transactional
public class AdministratorService {

	//Managed repository

	@Autowired
	private AdministratorRepository	administratorRepository;


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

}

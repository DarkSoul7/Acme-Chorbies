
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Chorbi;
import domain.Like;
import domain.SearchTemplate;
import forms.ChorbiForm;

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

	public ChorbiForm create() {
		final ChorbiForm result = new ChorbiForm();

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
		Assert.notNull(chorbi);
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


	@Autowired
	private Validator	validator;


	public Chorbi reconstruct(final ChorbiForm chorbiForm, final BindingResult binding) {
		Assert.notNull(chorbiForm);
		final Chorbi result = new Chorbi();

		//Registering a new Chorbi
		if (chorbiForm.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(chorbiForm.getPassword(), null);

			if (chorbiForm.getPassword().equals(chorbiForm.getRepeatPassword()))
				throw new IllegalArgumentException("Passwords must be equals");

			final Authority authority = new Authority();
			final UserAccount userAccount = new UserAccount();

			//Configuring authority & userAccount
			authority.setAuthority("CHORBI");
			userAccount.addAuthority(authority);
			result.setUserAccount(userAccount);

			final Collection<Like> receivedLikes = new ArrayList<>();
			final Collection<Like> authoredLikes = new ArrayList<>();
			final Collection<Chirp> sentChirps = new ArrayList<>();
			final Collection<Chirp> receivedChirps = new ArrayList<>();
			final SearchTemplate searchTemplate = new SearchTemplate();

			result.setAuthoredLikes(authoredLikes);
			result.setReceivedLikes(receivedLikes);
			result.setReceivedChirps(receivedChirps);
			result.setSentChirps(sentChirps);
			result.setReceivedChirps(receivedChirps);
			result.setSearchTemplate(searchTemplate);

			final Collection<Chorbi> chorbies = new ArrayList<>();
			result.getSearchTemplate().setListChorbi(chorbies);

			result.getUserAccount().setUsername(chorbiForm.getUsername());
			result.getUserAccount().setPassword(hash);
		}

		//Registering or editing chorbi
		result.setPicture(chorbiForm.getPicture());
		result.setDescription(chorbiForm.getDescription());
		result.setRelationship(chorbiForm.getRelationShip());
		result.setGenre(chorbiForm.getGenre());
		result.setCoordinates(chorbiForm.getCoordinates());
		result.setBirthDate(chorbiForm.getBirthDate());
		result.setCreditCard(chorbiForm.getCreditCard());

		result.setName(chorbiForm.getName());
		result.setSurname(chorbiForm.getSurname());
		result.setPhone(chorbiForm.getPhone());
		result.setEmail(chorbiForm.getEmail());

		//Check chorbi is over age
		final LocalDate chorbiBirthDate = new LocalDate(result.getBirthDate());
		final LocalDate now = new LocalDate();
		final Years chorbiYears = Years.yearsBetween(chorbiBirthDate, now);
		if (chorbiYears.getYears() < 18)
			result.setOverAge(false);
		else
			result.setOverAge(true);

		this.validator.validate(result, binding);

		return result;
	}
}

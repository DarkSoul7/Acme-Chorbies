
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.joda.time.Days;
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
import domain.CreditCard;
import domain.Like;
import domain.SearchTemplate;
import forms.ChorbiForm;
import forms.ChorbiListForm;

@Service
@Transactional
public class ChorbiService {
	
	// Managed repository
	
	@Autowired
	private ChorbiRepository	chorbiRepository;
	
	
	// Supported services
	
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
	
	// Comprobar antes de guardar al actor que su tarjeta de crédito tenga una
	// fecha válida (del número se encargala anotación)
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
	
	// Other business methods
	
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
	
	public Collection<Chorbi> findAllExceptPrincipal() {
		Collection<Chorbi> result;
		Chorbi principal;
		
		principal = this.findByPrincipal();
		result = this.chorbiRepository.findAllExceptPrincipal(principal.getId());
		
		return result;
	}
	
	
	// @Autowired
	private Validator	validator;
	
	
	public Chorbi reconstruct(final ChorbiForm chorbiForm, final BindingResult binding) {
		Assert.notNull(chorbiForm);
		final Chorbi result = new Chorbi();
		
		// Registering a new Chorbi
		if (chorbiForm.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(chorbiForm.getPassword(), null);
			
			if (!chorbiForm.getPassword().equals(chorbiForm.getRepeatPassword()))
				throw new IllegalArgumentException("Passwords must be equals");
			
			final Authority authority = new Authority();
			final UserAccount userAccount = new UserAccount();
			
			// Configuring authority & userAccount
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
		
		// Registering or editing chorbi
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
		
		// Check chorbi is over age
		final int chorbiYears = this.getChorbiAge(result);
		if (chorbiYears < 18)
			result.setOverAge(false);
		else
			result.setOverAge(true);
		
		// Check creditCard if any
		if (result.getCreditCard() != null)
			result.setValidCreditCard(this.checkCreditCard(result.getCreditCard()));
		
		// this.validator.validate(result, binding);
		
		return result;
	}
	
	public ChorbiForm toFormObject(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		final ChorbiForm result = new ChorbiForm();
		result.setBirthDate(chorbi.getBirthDate());
		result.setCoordinates(chorbi.getCoordinates());
		result.setCreditCard(chorbi.getCreditCard());
		result.setDescription(chorbi.getDescription());
		result.setEmail(chorbi.getEmail());
		result.setGenre(chorbi.getGenre());
		result.setId(chorbi.getId());
		result.setName(chorbi.getName());
		result.setPhone(chorbi.getPhone());
		result.setPicture(chorbi.getPicture());
		result.setRelationShip(chorbi.getRelationship());
		result.setSurname(chorbi.getSurname());
		
		return result;
	}
	
	public int getChorbiAge(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		
		final LocalDate chorbiBirthDate = new LocalDate(chorbi.getBirthDate());
		final LocalDate now = new LocalDate();
		final Years chorbiYears = Years.yearsBetween(chorbiBirthDate, now);
		
		return chorbiYears.getYears();
	}
	
	public boolean checkCreditCard(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		boolean result = false;
		
		final boolean luhn = LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCard.getNumber());
		final LocalDate localDate = new LocalDate();
		final LocalDate expirationDate = new LocalDate(creditCard.getExpirationYear(), creditCard.getExpirationMonth(), 1);
		
		final int days = Days.daysBetween(localDate, expirationDate).getDays();
		
		if (luhn && days > 1)
			result = true;
		
		return result;
	}
	
	public Collection<Chorbi> findChorbiesLike(final int idChorbie) {
		return this.chorbiRepository.findChorbiesLike(idChorbie);
	}
	
	public Collection<ChorbiListForm> findAllExceptPrincipalWithLikes() {
		Collection<ChorbiListForm> result;
		Chorbi principal;
		
		principal = this.findByPrincipal();
		result = this.chorbiRepository.findAllExceptPrincipalWithLikes(principal.getId());
		
		return result;
	}
	
	// DashBoard
	
	public Collection<Object[]> getChorbiesPerCountry() {
		return this.chorbiRepository.getChorbiesPerCountry();
	}
	
	public Collection<Object[]> getChorbiesPerCity() {
		return this.chorbiRepository.getChorbiesPerCity();
	}
	
	public Integer minChorbiesAge() {
		return this.chorbiRepository.minChorbiesAge();
	}
	
	public Integer maxChorbiesAge() {
		return this.chorbiRepository.maxChorbiesAge();
	}
	
	public Integer avgChorbiesAge() {
		return this.chorbiRepository.avgChorbiesAge();
	}
	
	public Double ratioChorbiesWithoutCreditCard() {
		return this.chorbiRepository.ratioChorbiesWithoutCreditCard();
	}
	
	public Double ratioChorbiesLookingForActivities() {
		return this.chorbiRepository.ratioChorbiesLookingForActivities();
	}
	
	public Double ratioChorbiesLookingForFriends() {
		return this.chorbiRepository.ratioChorbiesLookingForFriends();
	}
	
	public Double ratioChorbiesLookingForLove() {
		return this.chorbiRepository.ratioChorbiesLookingForLove();
	}
	
	public Collection<Chorbi> listChorbiesSortedByReceivedLikes() {
		return this.chorbiRepository.listChorbiesSortedByReceivedLikes();
	}
	
	public Integer minReceivedLikesPerChorbi() {
		return this.chorbiRepository.minReceivedLikesPerChorbi();
	}
	
	public Integer maxReceivedLikesPerChorbi() {
		return this.chorbiRepository.maxReceivedLikesPerChorbi();
	}
	
	public Double avgReceivedLikesPerChorbi() {
		return this.chorbiRepository.avgReceivedLikesPerChorbi();
	}
	
	public Integer minAuthoredLikesPerChorbi() {
		return this.chorbiRepository.minAuthoredLikesPerChorbi();
	}
	
	public Integer maxAuthoredLikesPerChorbi() {
		return this.chorbiRepository.maxAuthoredLikesPerChorbi();
	}
	
	public Double avgAuthoredLikesPerChorbi() {
		return this.chorbiRepository.avgAuthoredLikesPerChorbi();
	}
	
	public Integer minReceivedChirpsPerChorbi() {
		return this.chorbiRepository.minReceivedChirpsPerChorbi();
	}
	
	public Integer maxReceivedChirpsPerChorbi() {
		return this.chorbiRepository.maxReceivedChirpsPerChorbi();
	}
	
	public Double avgReceivedChirpsPerChorbi() {
		return this.chorbiRepository.avgReceivedChirpsPerChorbi();
	}
	
	public Integer minSentChirpsPerChorbi() {
		return this.chorbiRepository.minSentChirpsPerChorbi();
	}
	
	public Integer maxSentChirpsPerChorbi() {
		return this.chorbiRepository.maxSentChirpsPerChorbi();
	}
	
	public Double avgSentChirpsPerChorbi() {
		return this.chorbiRepository.avgSentChirpsPerChorbi();
	}
	
	public Collection<Chorbi> getChorbiMoreSentChirp() {
		return this.chorbiRepository.getChorbiMoreSentChirp();
	}
	
	public Collection<Chorbi> getChorbiMoreGotChirp() {
		return this.chorbiRepository.getChorbiMoreGotChirp();
	}
}

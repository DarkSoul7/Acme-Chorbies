
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.directory.InvalidAttributeValueException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SearchTemplateRepository;
import domain.Chorbi;
import domain.Coordinates;
import domain.CreditCard;
import domain.Genre;
import domain.Relationship;
import domain.SearchTemplate;

@Service
@Transactional
public class SearchTemplateService {

	//Managed repository

	@Autowired
	private SearchTemplateRepository	searchTemplateRepository;

	//Supported services

	@Autowired
	private ChorbiService				chorbiService;

	@Autowired
	private ActorService				actorService;


	//Constructor

	public SearchTemplate create(final Integer age, final Coordinates coordinates, final Relationship relationship, final Genre genre, final String keyword) {
		SearchTemplate result;
		Chorbi principal;

		principal = this.chorbiService.findByPrincipal();
		result = new SearchTemplate();

		result.setAge(age);
		result.setCoordinates(coordinates);
		result.setGenre(genre);
		result.setRelationship(relationship);
		result.setKeyword(keyword);
		result.setCachedMoment(new Date());
		result.setChorbi(principal);

		return result;
	}

	public SearchTemplate create(final Chorbi chorbi) {
		SearchTemplate result;

		result = new SearchTemplate();
		result.setChorbi(chorbi);

		return result;
	}

	public SearchTemplateService() {
		super();
	}

	public Collection<SearchTemplate> findAll() {
		final Collection<SearchTemplate> result = this.searchTemplateRepository.findAll();

		return result;
	}

	public SearchTemplate findOne(final int lsearchTemplateId) {
		final SearchTemplate result = this.searchTemplateRepository.findOne(lsearchTemplateId);

		return result;

	}

	public SearchTemplate save(final SearchTemplate searchTemplate) {
		Assert.notNull(searchTemplate);
		final Chorbi principal = this.chorbiService.findByPrincipal();
		SearchTemplate result;
		Assert.isTrue(searchTemplate.getChorbi().getId() == principal.getId());
		searchTemplate.setCachedMoment(new Date(System.currentTimeMillis() - 1000));
		result = this.searchTemplateRepository.save(searchTemplate);

		return result;
	}

	public SearchTemplate saveOnChorbiRegistry(final SearchTemplate searchTemplate) {
		Assert.notNull(searchTemplate);
		SearchTemplate result;
		searchTemplate.setCachedMoment(new Date(System.currentTimeMillis() - 1000));
		result = this.searchTemplateRepository.save(searchTemplate);

		return result;
	}

	public void delete(final SearchTemplate lsearchTemplate) {
		this.searchTemplateRepository.delete(lsearchTemplate);
	}

	public void delete(final int lsearchTemplateId) {
		final SearchTemplate lsearchTemplate = this.searchTemplateRepository.findOne(lsearchTemplateId);

		this.delete(lsearchTemplate);
	}

	//Other business methods

	public void searchForChorbies(final SearchTemplate searchTemplate) {
		final Collection<Chorbi> result = new ArrayList<>();
		final Chorbi chorbi = this.chorbiService.findByPrincipal();

		//Check creditCard every search
		Assert.notNull(chorbi.getCreditCard());

		final Collection<Chorbi> chorbiesFound = this.searchTemplateRepository.findChorbies(searchTemplate.getRelationship(), searchTemplate.getGenre(), searchTemplate.getCoordinates().getCountry(), searchTemplate.getCoordinates().getState(),
			searchTemplate.getCoordinates().getProvince(), searchTemplate.getCoordinates().getCity());
		//Check age parameter
		for (final Chorbi found : chorbiesFound) {
			final int foundAge = this.chorbiService.getChorbiAge(found);
			if (foundAge - 5 <= searchTemplate.getAge() && searchTemplate.getAge() <= foundAge + 5)
				result.add(found);
		}
		searchTemplate.setListChorbi(result);
		searchTemplate.setCachedMoment(new Date(System.currentTimeMillis() - 1000));
	}

	public Collection<Chorbi> searchByPrincipal(final SearchTemplate searchTemplate) throws InvalidAttributeValueException {
		Chorbi principal;
		Collection<Chorbi> result;

		principal = this.chorbiService.findByPrincipal();

		this.checkCreditCardValidity(principal.getCreditCard());
		if (searchTemplate != null)
			result = this.findChorbiesBySearchTemplate(searchTemplate, principal.getId());
		else
			result = null;

		return result;

	}

	//Este método se debe usar sólo para pruebas. Para la aplicación se debe usar searchByPrincipal
	public Collection<Chorbi> search(final SearchTemplate searchTemplate, final int principalId) {
		Collection<Chorbi> result;

		if (searchTemplate != null)
			result = this.findChorbiesBySearchTemplate(searchTemplate, principalId);
		else
			result = new ArrayList<Chorbi>();

		return result;
	}

	//Un Chorbi nunca podrá encontrarse en los resultados de esta búsqueda
	private Collection<Chorbi> findChorbiesBySearchTemplate(final SearchTemplate searchTemplate, final int principalId) {
		Collection<Chorbi> result;
		TypedQuery<Chorbi> query;
		EntityManagerFactory entityManagerFactory;
		EntityManager entityManager;
		String queryString;
		String whereString = "";
		final String andLiteral = " AND";
		Map<String, Object> parameters;

		entityManagerFactory = Persistence.createEntityManagerFactory("Acme-Chorbies");
		entityManager = entityManagerFactory.createEntityManager();
		queryString = "SELECT c FROM Chorbi c WHERE c.id != :principalId";
		parameters = new HashMap<String, Object>();
		parameters.put("principalId", principalId);

		if (searchTemplate.getAge() != null) {
			whereString += " AND (((:age-5)<=(DATEDIFF(CURRENT_DATE,c.birthDate)/365) AND (:age+5)>=(DATEDIFF(CURRENT_DATE,c.birthDate)/365)))";
			parameters.put("age", searchTemplate.getAge());
		}

		if (searchTemplate.getCoordinates() != null) {
			if (StringUtils.isNotBlank(searchTemplate.getCoordinates().getCountry())) {
				whereString += andLiteral;
				whereString += " c.coordinates.country = :country";
				parameters.put("country", searchTemplate.getCoordinates().getCountry());
			}
			if (StringUtils.isNotBlank(searchTemplate.getCoordinates().getState())) {
				whereString += andLiteral;
				whereString += " c.coordinates.state = :state";
				parameters.put("state", searchTemplate.getCoordinates().getState());
			}
			if (StringUtils.isNotBlank(searchTemplate.getCoordinates().getProvince())) {
				whereString += andLiteral;
				whereString += " c.coordinates.province = :province";
				parameters.put("province", searchTemplate.getCoordinates().getProvince());
			}
			if (StringUtils.isNotBlank(searchTemplate.getCoordinates().getCity())) {
				whereString += andLiteral;
				whereString += " c.coordinates.city = :city";
				parameters.put("city", searchTemplate.getCoordinates().getCity());
			}
		}

		if (searchTemplate.getRelationship() != null) {
			whereString += andLiteral;
			whereString += " c.relationship = :relationship";
			if (searchTemplate.getRelationship().getName().equals("ACTIVITIES"))
				parameters.put("relationship", Relationship.ACTIVITIES);
			else if (searchTemplate.getRelationship().getName().equals("FRIENDSHIP"))
				parameters.put("relationship", Relationship.FRIENDSHIP);
			else if (searchTemplate.getRelationship().getName().equals("LOVE"))
				parameters.put("relationship", Relationship.LOVE);
		}

		if (searchTemplate.getGenre() != null) {
			whereString += andLiteral;
			whereString += " c.genre = :genre";
			if (searchTemplate.getGenre().getName().equals("WOMAN"))
				parameters.put("genre", Genre.WOMAN);
			else if (searchTemplate.getGenre().getName().equals("MAN"))
				parameters.put("genre", Genre.MAN);
		}

		if (StringUtils.isNotBlank(whereString))
			queryString += whereString;

		query = entityManager.createQuery(queryString, Chorbi.class);

		for (final Entry<String, Object> e : parameters.entrySet())
			query.setParameter(e.getKey(), e.getValue());

		result = query.getResultList();

		return result;
	}

	private void checkCreditCardValidity(final CreditCard creditCard) throws InvalidAttributeValueException {
		final GregorianCalendar currentMoment = new GregorianCalendar();
		final LocalDate localDate = new LocalDate();
		final LocalDate expirationDate = new LocalDate(creditCard.getExpirationYear(), creditCard.getExpirationMonth(), 1);

		final int days = Days.daysBetween(localDate, expirationDate).getDays();
		if (days < 1)
			throw new InvalidAttributeValueException("SearchTemplateService - checkCreditCardValidity: invalid credit card");
	}
}

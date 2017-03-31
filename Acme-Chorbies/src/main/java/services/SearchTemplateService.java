
package services;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SearchTemplateRepository;
import domain.Actor;
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
	
	public SearchTemplate create(Integer age, Coordinates coordinates, Relationship relationship, Genre genre, String keyword) {
		SearchTemplate result;
		Chorbi principal;
		
		principal = this.chorbiService.findByPrincipal();
		result = new SearchTemplate();
		
		result.setAge(age);
		result.setCoordinates(coordinates);
		result.setGenre(genre);
		result.setRelationship(relationship);
		result.setKeyword(keyword);
		result.setCaptionMoment(new Date());
		result.setChorbi(principal);
		
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
		final Actor actor = this.actorService.findByPrincipal();
		
		//Administrators can edit searchTemplates
		if (actor.getUserAccount().getAuthorities().iterator().next().equals("CHORBI")) {
			Assert.isTrue(searchTemplate.getChorbi().getId() == actor.getId());
			this.searchForChorbies(searchTemplate);
		}
		final SearchTemplate result = this.searchTemplateRepository.save(searchTemplate);
		
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
		searchTemplate.setCaptionMoment(new Date(System.currentTimeMillis() - 1000));
	}
	
	public Collection<Chorbi> searchByPrincipal() throws InvalidAttributeValueException {
		Chorbi principal;
		SearchTemplate searchTemplate;
		Collection<Chorbi> result;
		
		principal = this.chorbiService.findByPrincipal();
		searchTemplate = principal.getSearchTemplate();
		
		this.checkCreditCardValidity(principal.getCreditCard());
		if (searchTemplate != null) {
			result = this.findChorbiesBySearchTemplate(searchTemplate, principal.getId());
		} else {
			result = null;
		}
		
		return result;
		
	}
	
	//Este método se debe usar sólo para pruebas. Para la aplicación se debe usar searchByPrincipal
	public Collection<Chorbi> search(SearchTemplate searchTemplate, int principalId) {
		Collection<Chorbi> result;
		
		if (searchTemplate != null) {
			result = this.findChorbiesBySearchTemplate(searchTemplate, principalId);
		} else {
			result = new ArrayList<Chorbi>();
		}
		
		return result;
	}
	
	//Un Chorbi nunca podrá encontrarse en los resultados de esta búsqueda
	private Collection<Chorbi> findChorbiesBySearchTemplate(SearchTemplate searchTemplate, int principalId) {
		Collection<Chorbi> result;
		TypedQuery<Chorbi> query;
		EntityManagerFactory entityManagerFactory;
		EntityManager entityManager;
		String queryString;
		String whereString = "";
		String andLiteral = " AND";
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
				if (StringUtils.isNotBlank(whereString)) {
					whereString += andLiteral;
				}
				whereString += " c.coordinates.country = :country";
				parameters.put("country", searchTemplate.getCoordinates().getCountry());
			}
			if (StringUtils.isNotBlank(searchTemplate.getCoordinates().getState())) {
				if (StringUtils.isNotBlank(whereString)) {
					whereString += andLiteral;
				}
				whereString += " c.coordinates.state = :state";
				parameters.put("state", searchTemplate.getCoordinates().getState());
			}
			if (StringUtils.isNotBlank(searchTemplate.getCoordinates().getProvince())) {
				if (StringUtils.isNotBlank(whereString)) {
					whereString += andLiteral;
				}
				whereString += " c.coordinates.province = :province";
				parameters.put("province", searchTemplate.getCoordinates().getProvince());
			}
			if (StringUtils.isNotBlank(searchTemplate.getCoordinates().getCity())) {
				if (StringUtils.isNotBlank(whereString)) {
					whereString += andLiteral;
				}
				whereString += " c.coordinates.city = :city";
				parameters.put("city", searchTemplate.getCoordinates().getCity());
			}
		}
		
		if (searchTemplate.getRelationship() != null) {
			if (StringUtils.isNotBlank(whereString)) {
				whereString += andLiteral;
			}
			whereString += " c.relationship = :relationship";
			parameters.put("relationship", searchTemplate.getRelationship());
		}
		
		if (searchTemplate.getGenre() != null) {
			if (StringUtils.isNotBlank(whereString)) {
				whereString += andLiteral;
			}
			whereString += " c.genre = :genre";
			parameters.put("genre", searchTemplate.getGenre());
		}
		
		if (StringUtils.isNotBlank(whereString)) {
			queryString += whereString;
		}
		
		query = entityManager.createQuery(queryString, Chorbi.class);
		
		for (Entry<String, Object> e : parameters.entrySet()) {
			query.setParameter(e.getKey(), e.getValue());
		}
		
		result = query.getResultList();
		
		return result;
	}
	
	private void checkCreditCardValidity(CreditCard creditCard) throws InvalidAttributeValueException {
		GregorianCalendar currentMoment = new GregorianCalendar();
		if (currentMoment.get(Calendar.YEAR) > creditCard.getExpirationYear().intValue()) {
			throw new InvalidAttributeValueException("SearchTemplateService - checkCreditCardValidity: invalid year");
		} else if (currentMoment.get(Calendar.MONTH) + 1 > creditCard.getExpirationMonth().intValue()) {
			throw new InvalidAttributeValueException("SearchTemplateService - checkCreditCardValidity: invalid month");
		}
	}
}

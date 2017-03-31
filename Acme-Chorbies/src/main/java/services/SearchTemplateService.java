
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SearchTemplateRepository;
import domain.Actor;
import domain.Chorbi;
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
}

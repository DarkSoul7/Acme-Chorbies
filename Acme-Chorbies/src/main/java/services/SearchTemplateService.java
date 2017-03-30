
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SearchTemplateRepository;
import domain.SearchTemplate;

@Service
@Transactional
public class SearchTemplateService {

	//Managed repository

	@Autowired
	private SearchTemplateRepository	lsearchTemplateRepository;


	//Supported services

	//Constructor

	public SearchTemplateService() {
		super();
	}

	public Collection<SearchTemplate> findAll() {
		final Collection<SearchTemplate> result = this.lsearchTemplateRepository.findAll();

		return result;
	}

	public SearchTemplate findOne(final int lsearchTemplateId) {
		final SearchTemplate result = this.lsearchTemplateRepository.findOne(lsearchTemplateId);

		return result;

	}

	public SearchTemplate save(final SearchTemplate lsearchTemplate) {
		Assert.notNull(lsearchTemplate);
		final SearchTemplate result = this.lsearchTemplateRepository.save(lsearchTemplate);

		return result;
	}

	public void delete(final SearchTemplate lsearchTemplate) {
		this.lsearchTemplateRepository.delete(lsearchTemplate);
	}

	public void delete(final int lsearchTemplateId) {
		final SearchTemplate lsearchTemplate = this.lsearchTemplateRepository.findOne(lsearchTemplateId);

		this.delete(lsearchTemplate);
	}

	//Other business methods

}

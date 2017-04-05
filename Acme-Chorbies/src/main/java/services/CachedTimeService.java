
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CachedTimeRepository;
import domain.CachedTime;

@Transactional
@Service
public class CachedTimeService {

	//Managed repository
	@Autowired
	private CachedTimeRepository	cachedTimeRepository;

	//Related services

	@Autowired
	private AdministratorService	administratorService;


	//Constructor

	public CachedTimeService() {
		super();
	}

	//Simple CRUD methods

	public CachedTime findUnique() {
		return this.cachedTimeRepository.findUnique();

	}

	public void save(final CachedTime cachedTime) {
		this.administratorService.findByPrincipal();

		this.cachedTimeRepository.save(cachedTime);
	}
}

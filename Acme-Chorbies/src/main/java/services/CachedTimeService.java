
package services;

import java.sql.SQLException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CachedTimeRepository;
import utilities.internal.DatabaseUtil;
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
	
	public void flush() {
		this.cachedTimeRepository.flush();
	}
	
	public CachedTime findUnique() {
		return this.cachedTimeRepository.findUnique();
		
	}
	
	public void save(final CachedTime cachedTime) {
		this.administratorService.findByPrincipal();
		
		this.cachedTimeRepository.save(cachedTime);
	}
	
	//Other business methods
	public void updateEvent() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		DatabaseUtil databaseUtil;
		
		databaseUtil = new DatabaseUtil();
		databaseUtil.open(false);
		
		databaseUtil.executeNativeUpdate("ALTER EVENT erase_searchTemplateCache ON SCHEDULE EVERY 1 HOUR STARTS NOW()");
		
		databaseUtil.close();
	}
	
}

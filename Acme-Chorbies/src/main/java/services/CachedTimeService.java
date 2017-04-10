
package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		final String url = "jdbc:mysql://localhost:3306/Acme-Chorbies";
		final String user = "acme-manager";
		final String password = "ACME-M@n@ger-6874";
		final Connection connection = DriverManager.getConnection(url, user, password);

		final PreparedStatement prepared = connection.prepareStatement("ALTER EVENT erase_searchTemplateCache ON SCHEDULE EVERY 1 HOUR STARTS NOW()");
		prepared.executeUpdate();
		System.out.println("event updated");
	}

}

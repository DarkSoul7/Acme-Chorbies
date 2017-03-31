
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Coordinates;
import domain.Genre;
import domain.Relationship;
import domain.SearchTemplate;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SearchTemplateServiceTest extends AbstractTest {
	
	// System under test ------------------------------------------------------
	
	@Autowired
	private SearchTemplateService	searchTemplateService;
	
	// Other related services ------------------------------------------------
	
	@Autowired
	private ChorbiService			chorbiService;
	
	
	// Tests ------------------------------------------------------------------
	
	/***
	 * Apply for an offer or request.
	 * Testing cases:
	 * 1º Good search -> expected: a list with a chorbi
	 * 2º Good search -> expected: an empty list
	 * 3º Good search; I won't be able to find myself with my own search template -> expected: an empty list
	 * 4º Good search -> expected: a list with 4 chorbies
	 * 5º Bad search; A chorbi must be authenticated to search for other chorbies using a search template -> expected: IllegalArgumentException
	 * 6º Bad register; A customer must be authenticated to register an application for a request -> expected: IllegalArgumentException
	 */
	@Test
	public void searchTemplateDriver() {
		final Object testingData[][] = {
			//chorbi, age, country, state, province, city, relationship, genre, expected exception
			{
				"chorbi2", 32, "España", "Andalucía", "Sevilla", "Sevilla", Relationship.ACTIVITIES, Genre.MAN, 1, null
			},
			{
				"chorbi2", 32, "España", "Andalucía", "Sevilla", "Sevilla", Relationship.ACTIVITIES, Genre.WOMAN, 0, null
			},
			{
				"chorbi1", 32, "España", "Andalucía", "Sevilla", "Sevilla", Relationship.ACTIVITIES, Genre.MAN, 0, null
			},
			{
				"chorbi1", null, null, null, null, null, null, null, 3, null
			},
			{
				null, 32, "España", "Andalucía", "Sevilla", "Sevilla", Relationship.ACTIVITIES, Genre.MAN, 1, IllegalArgumentException.class
			}
		};
		
		for (int i = 0; i < testingData.length; i++)
			this.searchTemplateTemplate((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],
				(String) testingData[i][4], (String) testingData[i][5], (Relationship) testingData[i][6], (Genre) testingData[i][7], (Integer) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	
	protected void searchTemplateTemplate(String principal, Integer age, String country, String state, String province,
		String city, Relationship relationship, Genre genre, Integer resultSize, Class<?> expectedException) {
		
		Class<?> caught = null;
		
		try {
			this.authenticate(principal);
			
			Chorbi chorbi;
			SearchTemplate searchTemplate;
			Coordinates coordinates;
			Collection<Chorbi> result;
			
			chorbi = this.chorbiService.findByPrincipal();
			coordinates = new Coordinates();
			coordinates.setCity(city);
			coordinates.setCountry(country);
			coordinates.setProvince(province);
			coordinates.setState(state);
			
			searchTemplate = this.searchTemplateService.create(age, coordinates, relationship, genre, null);
			
			result = this.searchTemplateService.search(searchTemplate, chorbi.getId());
			
			Assert.isTrue(result.size() == resultSize);
			
			this.unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.unauthenticate();
		}
		
		this.checkExceptions(expectedException, caught);
		
	}
	
}

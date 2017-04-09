
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
	 * Browse result of search template.
	 * Testing cases:
	 * 1º Good search -> expected: a list with a chorbi
	 * 2º Good search -> expected: an empty list
	 * 3º Good search; I won't be able to find myself with my own search template -> expected: an empty list
	 * 4º Good search -> expected: a list with 4 chorbies
	 * 5º Bad search; A chorbi must be authenticated to search for other chorbies using a search template -> expected: IllegalArgumentException
	 */
	@Test
	public void searchTemplateDriver() {
		final Object testingData[][] = {
			//chorbi, age, country, state, province, city, relationship, genre, expected exception
			{
				"chorbi2", 32, "España", "Andalucía", "Sevilla", "Sevilla", Relationship.ACTIVITIES, Genre.MAN, 1, null
			}, {
				"chorbi2", 32, "España", "Andalucía", "Sevilla", "Sevilla", Relationship.ACTIVITIES, Genre.WOMAN, 0, null
			}, {
				"chorbi1", 32, "España", "Andalucía", "Sevilla", "Sevilla", Relationship.ACTIVITIES, Genre.MAN, 0, null
			}, {
				"chorbi1", null, null, null, null, null, null, null, 3, null
			}, {
				null, 32, "España", "Andalucía", "Sevilla", "Sevilla", Relationship.ACTIVITIES, Genre.MAN, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchTemplateTemplate((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Relationship) testingData[i][6],
				(Genre) testingData[i][7], (Integer) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	protected void searchTemplateTemplate(final String principal, final Integer age, final String country, final String state, final String province, final String city, final Relationship relationship, final Genre genre, final Integer resultSize,
		final Class<?> expectedException) {

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

	/***
	 * Change chorbi's searchTemplate
	 * Testing cases:
	 * 1º Good test -> searchTemplate changed
	 * 2º Bad test; an unauthenticated actor cannot change a searchTemplate -> expected: IllegalArgumentException
	 */

	@Test
	public void changeSearchDriver() {
		final Object testingData[][] = {
			{
				"chorbi1", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeSearchTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void changeSearchTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Chorbi chorbi = this.chorbiService.findByPrincipal();
			final SearchTemplate searchTemplate = chorbi.getSearchTemplate();

			//Editing search template
			searchTemplate.setAge(20);
			searchTemplate.setKeyword("test");
			this.searchTemplateService.save(searchTemplate);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

}

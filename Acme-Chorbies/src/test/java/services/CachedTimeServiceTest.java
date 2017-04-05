
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CachedTime;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CachedTimeServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CachedTimeService	cachedTimeService;


	// Other related services ------------------------------------------------

	// Tests ------------------------------------------------------------------

	/***
	 * Change
	 * Testing cases:
	 * 1º Good test -> expected: cached time changed
	 * 2º Bad test; an actor who is not an administrator cannot change the cache time -> expected: IllegalArgumentException
	 */

	@Test
	public void changeCacheTimeDriver() {

		final Object testingData[][] = {
			{
				"admin", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeCacheTimeTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void changeCacheTimeTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final CachedTime cachedTime = this.cachedTimeService.findUnique();
			cachedTime.setCachedHour(10);
			cachedTime.setCachedMinute(50);
			cachedTime.setCachedSecond(3);
			this.cachedTimeService.save(cachedTime);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}
}


package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ActorService	actorService;


	// Other related services ------------------------------------------------

	// Tests ------------------------------------------------------------------

	/***
	 * Login to the system with user's credentials
	 * Testing cases:
	 * 1º Good test -> expect: administrator logged into the system
	 * 2º Good test -> expect: chorbi logged into the system
	 * 3º Bad test -> expect: the credentials does not exist so no actor logged into the system
	 */

	@Test
	public void loginDriver() {
		final Object testingData[][] = {
			//principal, expected exception
			{
				"admin", null
			}, {
				"chorbi1", null
			}, {
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.loginTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void loginTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Assert.notNull(this.actorService.findByPrincipal());

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}
}

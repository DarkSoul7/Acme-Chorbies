
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
import domain.Banner;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BannerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private BannerService			bannerService;

	// Other related services ------------------------------------------------

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private AdministratorService	administratorService;


	// Tests ------------------------------------------------------------------

	/***
	 * Select a banner randomly
	 * Testing cases:
	 * 1º Good test (with banners in Data Base) -> expected: the banner resulting
	 * 2º Good test (without banners in Data Base) -> expected: no banner resulting
	 */

	@Test
	public void randomBannerDriver() {
		final Object testingData[][] = {
			{
				"case 1"
			}, {
				"case 2"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.randomBannerTemplate((String) testingData[i][0]);
	}

	protected void randomBannerTemplate(final String testingCase) {
		Banner result;

		if (testingCase.equals("case 1")) {
			result = this.bannerService.getRandomBanner();
			Assert.notNull(result);
		} else {
			this.authenticate("admin");
			final Collection<Banner> banners = this.bannerService.findAll();
			for (final Banner banner : banners)
				this.bannerService.delete(banner);
			this.unauthenticate();
			result = this.bannerService.getRandomBanner();
			Assert.isTrue(result == null);
		}
	}

	/***
	 * Create and save a banner
	 * Testing cases:
	 * 1º Good test -> expected: the banner resulting
	 * 2º Bad test; an unauthenticated actor cannot create or save banners-> expected: IllegalArgumentException
	 */

	@Test
	public void createAndSaveBannerDriver() {
		final Object testingData[][] = {
			//principal, expected exception
			{
				"admin", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createAndSaveBannerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void createAndSaveBannerTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Banner banner = this.bannerService.create();
			banner.setPicture("http://www.test.com");
			this.bannerService.save(banner);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit a banner
	 * Testing cases:
	 * 1º Good test -> expected: the banner edited
	 * 2º Bad test; an unauthenticated actor cannot edit banners-> expected: IllegalArgumentException
	 */

	@Test
	public void editABannerDriver() {
		final Object testingData[][] = {
			//principal, expected exception
			{
				"admin", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editABannerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void editABannerTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Banner banner = this.bannerService.findOne(58);
			banner.setPicture("http://www.testing.es");
			this.bannerService.save(banner);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete a banner
	 * Testing cases:
	 * 1º Good test -> expected: the banner deleted
	 * 2º Bad test; an unauthenticated actor cannot delete banners-> expected: IllegalArgumentException
	 */

	@Test
	public void deleteABannerDriver() {
		final Object testingData[][] = {
			//principal, expected exception
			{
				"admin", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteABannerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void deleteABannerTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Banner banner = this.bannerService.findOne(58);

			this.bannerService.delete(banner);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}
}

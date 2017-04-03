
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Brand;
import domain.Chorbi;
import domain.Coordinates;
import domain.CreditCard;
import domain.Genre;
import domain.Relationship;
import forms.ChorbiForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChorbiServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ChorbiService			chorbiService;

	// Other related services ------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	// Tests ------------------------------------------------------------------

	/***
	 * Register a chorbi
	 * Testing cases:
	 * 1º Good test -> expected: chorbi registered
	 * 2º Bad test; a chorbi cannot be under age -> expected: IllegalArgumentException
	 * 3º Bad test; the credit card must be valid, if any -> expected: IllegalArgumentException
	 */

	@Test
	public void registerChorbiDriver() {

		final Object testingData[][] = {
			{
				new DateTime(1989, 10, 10, 00, 00).toDate(), false, null
			}, {
				new DateTime(2010, 10, 10, 00, 00).toDate(), false, IllegalArgumentException.class
			}, {
				new DateTime(1989, 10, 10, 00, 00).toDate(), true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerChorbiTemplate((Date) testingData[i][0], (Boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void registerChorbiTemplate(final Date birthDate, final Boolean creditCard, final Class<?> expectedException) {
		Class<?> caught = null;
		try {
			Chorbi result = null;
			final Coordinates coordinates = new Coordinates();
			coordinates.setCity("test");
			coordinates.setCountry("Test");
			coordinates.setProvince("test");
			coordinates.setState("Testing");

			final ChorbiForm chorbiForm = this.chorbiService.create();
			chorbiForm.setAcceptCondition(true);
			chorbiForm.setBirthDate(birthDate);
			chorbiForm.setCoordinates(coordinates);
			chorbiForm.setDescription("test");
			chorbiForm.setEmail("test@testing.com");
			chorbiForm.setGenre(Genre.MAN);
			chorbiForm.setName("test");
			chorbiForm.setPassword("testing");
			chorbiForm.setPhone("+34 666666666");
			chorbiForm.setPicture("http://www.test.es");
			chorbiForm.setRelationShip(Relationship.ACTIVITIES);
			chorbiForm.setRepeatPassword("testing");
			chorbiForm.setSurname("test");
			chorbiForm.setUsername("test");

			if (creditCard) {
				final CreditCard creditCc = new CreditCard();
				creditCc.setBrandName(Brand.VISA);
				creditCc.setCvv(123);
				creditCc.setExpirationMonth(10);
				creditCc.setExpirationYear(2010);
				creditCc.setHolderName("test");
				creditCc.setNumber("123456789");

				chorbiForm.setCreditCard(creditCc);
			}

			result = this.chorbiService.reconstruct(chorbiForm, null);

			if (result.getOverAge() == false)
				throw new IllegalArgumentException();

			if (creditCard)
				if (result.getValidCreditCard() == false)
					throw new IllegalArgumentException();

			this.chorbiService.save(result);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Change chorbi's profile
	 * Testing cases:
	 * 1º Good test -> expected: profile changed
	 * 2º Bad test; a chorbi must be logged -> expected: IllegalArgumentException
	 */

	@Test
	public void changeChorbiProfileDriver() {

		final Object testingData[][] = {
			//principal, expected exception
			{
				"chorbi1", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeChorbiProfileTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void changeChorbiProfileTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Chorbi chorbi = this.chorbiService.findByPrincipal();
			final ChorbiForm chorbiForm = this.chorbiService.toFormObject(chorbi);

			//Editing profile
			chorbiForm.setDescription("New description");
			chorbiForm.setName("Other name");

			chorbi = this.chorbiService.reconstruct(chorbiForm, null);
			this.chorbiService.save(chorbi);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Ban a chorbi
	 * Testing cases:
	 * 1º Good test -> expected: chorbi banned
	 * 2º Bad test; an actor who is not an administrator cannot ban a chorbi -> expected: IllegalArgumentException
	 * 3º Bad test; a chorbi banned cannot be rebanned -> expected: IllegalArgumentException
	 */

	@Test
	public void banChorbiDriver() {

		final Object testingData[][] = {
			//principal, expected exception
			{
				"admin", null
			}, {
				null, IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.banChorbiTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void banChorbiTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Chorbi chorbi = this.chorbiService.findOne(39);
			this.administratorService.banChorbi(chorbi);
			Assert.isTrue(chorbi.getUserAccount().isEnabled() == false);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Unban a chorbi
	 * Testing cases:
	 * 1º Good test -> expected: chorbi unbanned
	 * 2º Bad test; an actor who is not an administrator cannot unban a chorbi -> expected: IllegalArgumentException
	 * 3º Bad test; a chorbi unbanned cannot be re-unbanned -> expected: IllegalArgumentException
	 */

	@Test
	public void unBanChorbiDriver() {

		final Object testingData[][] = {
			//principal, expected exception
			{
				"admin", null
			}, {
				null, IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.unBanChorbiTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void unBanChorbiTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Chorbi chorbi = this.chorbiService.findOne(42);
			this.administratorService.unbanChorbi(chorbi);
			Assert.isTrue(chorbi.getUserAccount().isEnabled() == true);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}
}

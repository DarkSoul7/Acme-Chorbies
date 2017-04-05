/* AdministratorController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Chorbi;
import services.ChorbiService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private ChorbiService chorbiService;

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/dashboardC", method = RequestMethod.GET)
	public ModelAndView dashboardC() {
		final ModelAndView result;

		Collection<Object[]> chorbiesPerCountry = chorbiService.getChorbiesPerCountry();
		Collection<Object[]> chorbiesPerCity = chorbiService.getChorbiesPerCity();

		Integer minChorbiesAge = chorbiService.minChorbiesAge();
		Integer maxChorbiesAge = chorbiService.maxChorbiesAge();
		Integer avgChorbiesAge = chorbiService.avgChorbiesAge();
		Object[] stadisticsChoribiesAge = { minChorbiesAge, maxChorbiesAge, avgChorbiesAge };

		Double ratioChorbiesWithoutCreditCard = chorbiService.ratioChorbiesWithoutCreditCard();

		Double ratioChorbiesLookingForActivities = chorbiService.ratioChorbiesLookingForActivities();
		Double ratioChorbiesLookingForFriends = chorbiService.ratioChorbiesLookingForFriends();
		Double ratioChorbiesLookingForLove = chorbiService.ratioChorbiesLookingForLove();
		
		Object[] ratioChorbiesLooking = { ratioChorbiesLookingForActivities, ratioChorbiesLookingForFriends, ratioChorbiesLookingForLove };

		result = new ModelAndView("administrator/dashboardC");
		result.addObject("chorbiesPerCountry", chorbiesPerCountry);
		result.addObject("chorbiesPerCity", chorbiesPerCity);
		result.addObject("stadisticsChoribiesAge", stadisticsChoribiesAge);
		result.addObject("ratioChorbiesWithoutCreditCard", ratioChorbiesWithoutCreditCard);
		result.addObject("ratioChorbiesLooking", ratioChorbiesLooking);
		return result;

	}

	@RequestMapping(value = "/dashboardB", method = RequestMethod.GET)
	public ModelAndView dashboardB() {
		final ModelAndView result;

		Collection<Chorbi> listChorbiesSortedByReceivedLikes = chorbiService.listChorbiesSortedByReceivedLikes();

		Integer minReceivedLikesPerChorbi = chorbiService.minReceivedLikesPerChorbi();
		Integer maxReceivedLikesPerChorbi = chorbiService.maxReceivedLikesPerChorbi();
		Double avgReceivedLikesPerChorbi = chorbiService.avgReceivedLikesPerChorbi();
		Object[] stadisticsReceivedLikesPerChorbi = { minReceivedLikesPerChorbi, maxReceivedLikesPerChorbi, avgReceivedLikesPerChorbi };
		
		Integer minAuthoredLikesPerChorbi = chorbiService.minAuthoredLikesPerChorbi();
		Integer maxAuthoredLikesPerChorbi = chorbiService.maxAuthoredLikesPerChorbi();
		Double avgAuthoredLikesPerChorbi = chorbiService.avgAuthoredLikesPerChorbi();
		Object[] stadisticsAuthoredLikesPerChorbi = { minAuthoredLikesPerChorbi, maxAuthoredLikesPerChorbi, avgAuthoredLikesPerChorbi };

		result = new ModelAndView("administrator/dashboardB");
		result.addObject("listChorbiesSortedByReceivedLikes", listChorbiesSortedByReceivedLikes);
		result.addObject("stadisticsReceivedLikesPerChorbi", stadisticsReceivedLikesPerChorbi);
		result.addObject("stadisticsAuthoredLikesPerChorbi", stadisticsAuthoredLikesPerChorbi);
		return result;

	}

	@RequestMapping(value = "/dashboardA", method = RequestMethod.GET)
	public ModelAndView dashboardA() {
		final ModelAndView result;

		Integer minReceivedChirpsPerChorbi = chorbiService.minReceivedChirpsPerChorbi();
		Integer maxReceivedChirpsPerChorbi = chorbiService.maxReceivedChirpsPerChorbi();
		Double avgReceivedChirpsPerChorbi = chorbiService.avgReceivedChirpsPerChorbi();
		Object[] stadisticsreceivedChirpsPerChorbi = { minReceivedChirpsPerChorbi, maxReceivedChirpsPerChorbi, avgReceivedChirpsPerChorbi };

		Integer minSentChirpsPerChorbi = chorbiService.minSentChirpsPerChorbi();
		Integer maxSentChirpsPerChorbi = chorbiService.maxSentChirpsPerChorbi();
		Double avgSentChirpsPerChorbi = chorbiService.avgSentChirpsPerChorbi();
		Object[] stadisticsSentChirpsPerChorbi = { minSentChirpsPerChorbi, maxSentChirpsPerChorbi, avgSentChirpsPerChorbi };

		Collection<Chorbi> chorbiMoreGotChirp = chorbiService.getChorbiMoreGotChirp();

		Collection<Chorbi> chorbiMoreSentChirp = chorbiService.getChorbiMoreSentChirp();

		result = new ModelAndView("administrator/dashboardA");
		result.addObject("stadisticsreceivedChirpsPerChorbi", stadisticsreceivedChirpsPerChorbi);
		result.addObject("stadisticsSentChirpsPerChorbi", stadisticsSentChirpsPerChorbi);
		result.addObject("chorbiMoreGotChirp", chorbiMoreGotChirp);
		result.addObject("chorbiMoreSentChirp", chorbiMoreSentChirp);
		return result;

	}

}
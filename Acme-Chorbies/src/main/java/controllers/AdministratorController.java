/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ChorbiService;
import domain.Chorbi;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {
	
	//Related services
	
	@Autowired
	private ChorbiService			chorbiService;
	
	@Autowired
	private AdministratorService	administratorService;
	
	
	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}
	
	//Ban a chorbi
	
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int chorbiId) {
		ModelAndView result;
		final Chorbi chorbi = this.chorbiService.findOne(chorbiId);
		this.administratorService.banChorbi(chorbi);
		
		result = new ModelAndView("redirect:/administrator/chorbi/list.do");
		
		return result;
	}
	
	//Unban a chorbi
	
	@RequestMapping(value = "/unBan", method = RequestMethod.GET)
	public ModelAndView unBan(@RequestParam final int chorbiId) {
		ModelAndView result;
		final Chorbi chorbi = this.chorbiService.findOne(chorbiId);
		this.administratorService.unbanChorbi(chorbi);
		
		result = new ModelAndView("redirect:/administrator/chorbi/list.do");
		
		return result;
	}
	
	//Dashboard C
	
	@RequestMapping(value = "/dashboardC", method = RequestMethod.GET)
	public ModelAndView dashboardC() {
		final ModelAndView result;
		
		final Collection<Object[]> chorbiesPerCountry = this.chorbiService.getChorbiesPerCountry();
		final Collection<Object[]> chorbiesPerCity = this.chorbiService.getChorbiesPerCity();
		
		final Integer minChorbiesAge = this.chorbiService.minChorbiesAge();
		final Integer maxChorbiesAge = this.chorbiService.maxChorbiesAge();
		final Integer avgChorbiesAge = this.chorbiService.avgChorbiesAge();
		final Object[] stadisticsChoribiesAge = {
			minChorbiesAge, maxChorbiesAge, avgChorbiesAge
		};
		
		final Double ratioChorbiesWithoutCreditCard = this.chorbiService.ratioChorbiesWithoutCreditCard();
		
		final Double ratioChorbiesLookingForActivities = this.chorbiService.ratioChorbiesLookingForActivities();
		final Double ratioChorbiesLookingForFriends = this.chorbiService.ratioChorbiesLookingForFriends();
		final Double ratioChorbiesLookingForLove = this.chorbiService.ratioChorbiesLookingForLove();
		
		final Object[] ratioChorbiesLooking = {
			ratioChorbiesLookingForActivities, ratioChorbiesLookingForFriends, ratioChorbiesLookingForLove
		};
		
		result = new ModelAndView("administrator/dashboardC");
		result.addObject("chorbiesPerCountry", chorbiesPerCountry);
		result.addObject("chorbiesPerCity", chorbiesPerCity);
		result.addObject("stadisticsChoribiesAge", stadisticsChoribiesAge);
		result.addObject("ratioChorbiesWithoutCreditCard", ratioChorbiesWithoutCreditCard);
		result.addObject("ratioChorbiesLooking", ratioChorbiesLooking);
		return result;
		
	}
	
	//DashBoad B
	
	@RequestMapping(value = "/dashboardB", method = RequestMethod.GET)
	public ModelAndView dashboardB() {
		final ModelAndView result;
		
		final Collection<Chorbi> listChorbiesSortedByReceivedLikes = this.chorbiService.listChorbiesSortedByReceivedLikes();
		
		final Integer minReceivedLikesPerChorbi = this.chorbiService.minReceivedLikesPerChorbi();
		final Integer maxReceivedLikesPerChorbi = this.chorbiService.maxReceivedLikesPerChorbi();
		final Double avgReceivedLikesPerChorbi = this.chorbiService.avgReceivedLikesPerChorbi();
		final Object[] stadisticsReceivedLikesPerChorbi = {
			minReceivedLikesPerChorbi, maxReceivedLikesPerChorbi, avgReceivedLikesPerChorbi
		};
		
		final Integer minAuthoredLikesPerChorbi = this.chorbiService.minAuthoredLikesPerChorbi();
		final Integer maxAuthoredLikesPerChorbi = this.chorbiService.maxAuthoredLikesPerChorbi();
		final Double avgAuthoredLikesPerChorbi = this.chorbiService.avgAuthoredLikesPerChorbi();
		final Object[] stadisticsAuthoredLikesPerChorbi = {
			minAuthoredLikesPerChorbi, maxAuthoredLikesPerChorbi, avgAuthoredLikesPerChorbi
		};
		
		result = new ModelAndView("administrator/dashboardB");
		result.addObject("listChorbiesSortedByReceivedLikes", listChorbiesSortedByReceivedLikes);
		result.addObject("stadisticsReceivedLikesPerChorbi", stadisticsReceivedLikesPerChorbi);
		result.addObject("stadisticsAuthoredLikesPerChorbi", stadisticsAuthoredLikesPerChorbi);
		return result;
		
	}
	
	//Dashboard A
	
	@RequestMapping(value = "/dashboardA", method = RequestMethod.GET)
	public ModelAndView dashboardA() {
		final ModelAndView result;
		
		final Integer minReceivedChirpsPerChorbi = this.chorbiService.minReceivedChirpsPerChorbi();
		final Integer maxReceivedChirpsPerChorbi = this.chorbiService.maxReceivedChirpsPerChorbi();
		final Double avgReceivedChirpsPerChorbi = this.chorbiService.avgReceivedChirpsPerChorbi();
		final Object[] stadisticsreceivedChirpsPerChorbi = {
			minReceivedChirpsPerChorbi, maxReceivedChirpsPerChorbi, avgReceivedChirpsPerChorbi
		};
		
		final Integer minSentChirpsPerChorbi = this.chorbiService.minSentChirpsPerChorbi();
		final Integer maxSentChirpsPerChorbi = this.chorbiService.maxSentChirpsPerChorbi();
		final Double avgSentChirpsPerChorbi = this.chorbiService.avgSentChirpsPerChorbi();
		final Object[] stadisticsSentChirpsPerChorbi = {
			minSentChirpsPerChorbi, maxSentChirpsPerChorbi, avgSentChirpsPerChorbi
		};
		
		final Collection<Chorbi> chorbiMoreGotChirp = this.chorbiService.getChorbiMoreGotChirp();
		
		final Collection<Chorbi> chorbiMoreSentChirp = this.chorbiService.getChorbiMoreSentChirp();
		
		result = new ModelAndView("administrator/dashboardA");
		result.addObject("stadisticsreceivedChirpsPerChorbi", stadisticsreceivedChirpsPerChorbi);
		result.addObject("stadisticsSentChirpsPerChorbi", stadisticsSentChirpsPerChorbi);
		result.addObject("chorbiMoreGotChirp", chorbiMoreGotChirp);
		result.addObject("chorbiMoreSentChirp", chorbiMoreSentChirp);
		return result;
		
	}
	
}

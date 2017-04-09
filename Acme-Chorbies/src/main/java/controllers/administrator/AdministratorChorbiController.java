/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import controllers.AbstractController;
import domain.Chorbi;

@Controller
@RequestMapping("/administrator/chorbi")
public class AdministratorChorbiController extends AbstractController {
	
	// Related services
	
	@Autowired
	private ChorbiService	chorbiService;
	
	
	// Constructors -----------------------------------------------------------
	
	public AdministratorChorbiController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		
		chorbies = this.chorbiService.findAll();
		
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("listForm", false);
		result.addObject("requestURI", "administrator/chorbi/list.do");
		
		return result;
	}
	
	@RequestMapping(value = "/showDetails", method = RequestMethod.GET)
	public ModelAndView showDetails(@RequestParam int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;
		Collection<Chorbi> chorbiesFromReceiver;
		Collection<Chorbi> chorbiesFromAuthor;
		
		chorbi = this.chorbiService.findOne(chorbiId);
		chorbiesFromReceiver = this.chorbiService.findChorbisFromReceiver(chorbiId);
		chorbiesFromAuthor = this.chorbiService.findChorbisFromAuthor(chorbiId);
		
		result = new ModelAndView("chorbi/showDetails");
		result.addObject("chorbi", chorbi);
		result.addObject("chorbiesFromReceiver", chorbiesFromReceiver);
		result.addObject("chorbiesFromAuthor", chorbiesFromAuthor);
		result.addObject("listForm", false);
		result.addObject("requestURI", "administrator/chorbi/showDetails.do");
		
		return result;
	}
	
	@RequestMapping(value = "/receivedLikesAuthors", method = RequestMethod.GET)
	public ModelAndView receivedLikesAuthors(@RequestParam int authorId) {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		
		chorbies = this.chorbiService.findChorbisFromReceiver(authorId);
		
		result = new ModelAndView("chorbi/receivedLikesAuthors");
		result.addObject("chorbies", chorbies);
		result.addObject("listForm", false);
		result.addObject("requestURI", "administrator/chorbi/receivedLikesAuthors.do");
		
		return result;
	}
	
	@RequestMapping(value = "/givenLikesReceivers", method = RequestMethod.GET)
	public ModelAndView givenLikesReceivers(@RequestParam int receiverId) {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		
		chorbies = this.chorbiService.findChorbisFromAuthor(receiverId);
		
		result = new ModelAndView("chorbi/givenLikesReceivers");
		result.addObject("chorbies", chorbies);
		result.addObject("listForm", false);
		result.addObject("requestURI", "administrator/chorbi/givenLikesReceivers.do");
		
		return result;
	}
	
}

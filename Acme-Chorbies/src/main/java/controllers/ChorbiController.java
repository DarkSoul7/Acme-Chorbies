/*
 * ProfileController.java
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

import services.ActorService;
import services.ChorbiService;
import domain.Actor;
import domain.Chorbi;
import forms.ChorbiListForm;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {
	
	// Related services
	
	@Autowired
	private ChorbiService	chorbiService;
	
	@Autowired
	private ActorService	actorService;
	
	
	// Constructors -----------------------------------------------------------
	
	public ChorbiController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<ChorbiListForm> chorbies = this.chorbiService.findAllExceptPrincipalWithLikes();
		
		final Actor actor = this.actorService.findByPrincipal();
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("listForm", true);
		result.addObject("RequestURI", "chorbi/list.do");
		result.addObject("idActor", actor.getId());
		
		return result;
	}
	
	@RequestMapping(value = "/showChorbi", method = RequestMethod.GET)
	public ModelAndView showChorbiLike(@RequestParam final int chorbiId) {
		ModelAndView result;
		final Collection<Chorbi> chorbies = this.chorbiService.findChorbiesLike(chorbiId);
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("listForm", false);
		result.addObject("RequestURI", "chorbi/list.do");
		
		return result;
	}
	
}

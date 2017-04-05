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

import domain.Chorbi;
import services.ChorbiService;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	// Related services

	@Autowired
	private ChorbiService chorbiService;


	// Constructors -----------------------------------------------------------

	public ChorbiController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Chorbi> chorbies = this.chorbiService.findAll();

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("RequestURI", "chorbi/list.do");

		return result;
	}

	@RequestMapping(value = "/showChorbiLike", method = RequestMethod.GET)
	public ModelAndView showChorbiLike(@RequestParam final int id) {
		ModelAndView result;
		final Chorbi chorbi = this.chorbiService.findOne(id);
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbi.getAuthoredLikes());
		result.addObject("RequestURI", "chorbi/list.do");

		return result;
	}

}

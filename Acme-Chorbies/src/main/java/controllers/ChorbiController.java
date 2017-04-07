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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Brand;
import domain.Chorbi;
import domain.Genre;
import domain.Relationship;
import forms.ChorbiForm;
import forms.ChorbiListForm;
import services.ActorService;
import services.ChorbiService;

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
		Collection<ChorbiListForm> chorbiesForm = null;
		Collection<Chorbi> chorbies = null;
		final Actor actor = this.actorService.findByPrincipal();
		if (actor.getUserAccount().getAuthorities().iterator().next().equals("CHORBI"))
			chorbiesForm = this.chorbiService.findAllExceptPrincipalWithLikes();
		else
			chorbies = this.chorbiService.findAll();

		result = new ModelAndView("chorbi/list");
		if (chorbies == null)
			result.addObject("chorbies", chorbiesForm);
		else
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

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final ChorbiForm chorbiForm = this.chorbiService.toFormObject(chorbi);

		result = this.editModelAndView(chorbiForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(@Valid final ChorbiForm chorbiForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		final Chorbi chorbi;

		if (binding.hasErrors())
			result = this.editModelAndView(chorbiForm);
		else
			try {
				chorbi = this.chorbiService.reconstruct(chorbiForm, binding);
				this.chorbiService.save(chorbi);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(chorbiForm, "chorbi.commit.error");
			}

		return result;
	}

	// Ancillary methods

	protected ModelAndView editModelAndView(final ChorbiForm chorbiForm) {
		final ModelAndView result = this.editModelAndView(chorbiForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final ChorbiForm chorbiForm, final String message) {
		ModelAndView result;
		final List<Genre> genres = Arrays.asList(Genre.values());
		final List<Relationship> relationships = Arrays.asList(Relationship.values());
		final List<Brand> brands = Arrays.asList(Brand.values());

		result = new ModelAndView("chorbi/edit");
		result.addObject("chorbiForm", chorbiForm);
		result.addObject("genres", genres);
		result.addObject("brands", brands);
		result.addObject("relationships", relationships);
		result.addObject("message", message);
		result.addObject("requestURI", "chorbi/edit.do");

		return result;
	}

}

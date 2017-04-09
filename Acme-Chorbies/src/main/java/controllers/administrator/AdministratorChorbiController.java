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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import controllers.AbstractController;
import domain.Brand;
import domain.Chorbi;
import domain.Genre;
import domain.Relationship;
import forms.ChorbiForm;

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
		result.addObject("requestURI", "chorbi/list.do");
		
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
		result.addObject("requestURI", "chorbi/showDetails.do");
		
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
		result.addObject("requestURI", "chorbi/receivedLikesAuthors.do");
		
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
		result.addObject("requestURI", "chorbi/givenLikesReceivers.do");
		
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
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		ChorbiForm chorbiForm = chorbiService.createForm();
		result = this.createEditModelAndView(chorbiForm);
		
		return result;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ChorbiForm chorbiForm, BindingResult binding) throws CheckDigitException {
		ModelAndView result = new ModelAndView();
		Chorbi chorbi;
		
		chorbi = this.chorbiService.reconstruct(chorbiForm, binding);
		this.chorbiService.checkRestrictions(chorbiForm, binding);
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(chorbiForm);
		} else {
			try {
				chorbiService.save(chorbi);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(chorbiForm, "chorbi.commit.error");
			}
		}
		
		return result;
	}
	
	// Ancillary methods
	
	protected ModelAndView createEditModelAndView(ChorbiForm chorbiForm) {
		ModelAndView result = this.createEditModelAndView(chorbiForm, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(ChorbiForm chorbiForm, String message) {
		ModelAndView result;
		
		result = new ModelAndView("chorbi/register");
		result.addObject("chorbiForm", chorbiForm);
		result.addObject("message", message);
		
		return result;
	}
	
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

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

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import domain.Brand;
import domain.Chorbi;
import domain.Genre;
import domain.Relationship;
import forms.ChorbiForm;
import forms.ChorbiListForm;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	// Related services

	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public ChorbiController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<ChorbiListForm> chorbies;

		chorbies = this.chorbiService.findAllExceptPrincipalWithLikes();

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("listForm", true);
		result.addObject("requestURI", "chorbi/list.do");

		return result;
	}

	@RequestMapping(value = "/showDetails", method = RequestMethod.GET)
	public ModelAndView showDetails(@RequestParam final int chorbiId) {
		ModelAndView result;
		ChorbiListForm chorbi;
		Collection<ChorbiListForm> chorbiesFromReceiver;
		Collection<ChorbiListForm> chorbiesFromAuthor;
		Chorbi principal;

		chorbi = this.chorbiService.findOneWithLike(chorbiId);
		chorbiesFromReceiver = this.chorbiService.findChorbisFromReceiverWithLikes(chorbiId);
		chorbiesFromAuthor = this.chorbiService.findChorbisFromAuthorWithLikes(chorbiId);
		principal = this.chorbiService.findByPrincipal();

		//Hiding email and phone
		this.chorbiService.applyPrivacityForm(chorbi);

		result = new ModelAndView("chorbi/showDetails");
		result.addObject("chorbi", chorbi);
		result.addObject("chorbiesFromReceiver", chorbiesFromReceiver);
		result.addObject("chorbiesFromAuthor", chorbiesFromAuthor);
		result.addObject("listForm", true);
		result.addObject("principalId", principal.getId());
		result.addObject("requestURI", "chorbi/showDetails.do");

		return result;
	}

	@RequestMapping(value = "/receivedLikesAuthors", method = RequestMethod.GET)
	public ModelAndView receivedLikesAuthors(@RequestParam final int authorId) {
		ModelAndView result;
		Collection<ChorbiListForm> chorbies;
		Chorbi principal;

		principal = this.chorbiService.findByPrincipal();
		chorbies = this.chorbiService.findChorbisFromReceiverWithLikes(authorId);

		result = new ModelAndView("chorbi/receivedLikesAuthors");
		result.addObject("chorbies", chorbies);
		result.addObject("listForm", true);
		result.addObject("requestURI", "chorbi/receivedLikesAuthors.do");
		result.addObject("principalId", principal.getId());

		return result;
	}

	@RequestMapping(value = "/givenLikesReceivers", method = RequestMethod.GET)
	public ModelAndView givenLikesReceivers(@RequestParam final int receiverId) {
		ModelAndView result;
		Collection<ChorbiListForm> chorbies;
		Chorbi principal;

		principal = this.chorbiService.findByPrincipal();
		chorbies = this.chorbiService.findChorbisFromAuthorWithLikes(receiverId);

		result = new ModelAndView("chorbi/givenLikesReceivers");
		result.addObject("chorbies", chorbies);
		result.addObject("listForm", true);
		result.addObject("requestURI", "chorbi/givenLikesReceivers.do");
		result.addObject("principalId", principal.getId());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false) final String successMessage) {
		ModelAndView result;
		Chorbi chorbi;
		ChorbiForm chorbiForm;

		chorbi = this.chorbiService.findByPrincipal();
		chorbiForm = this.chorbiService.toFormObject(chorbi);
		chorbiForm.setAcceptCondition(true);

		result = this.editModelAndView(chorbiForm);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(@Valid final ChorbiForm chorbiForm, final BindingResult binding) throws CheckDigitException {
		ModelAndView result = new ModelAndView();
		final Chorbi chorbi;

		chorbi = this.chorbiService.reconstruct(chorbiForm, binding);

		if (binding.hasErrors())
			result = this.editModelAndView(chorbiForm, "chorbi.creditCard.error");
		else
			try {
				this.chorbiService.save(chorbi);
				result = new ModelAndView("redirect:/chorbi/edit.do");
				result.addObject("successMessage", "chorbi.edit.success");
			} catch (final Throwable oops) {
				result = this.editModelAndView(chorbiForm, "chorbi.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final ChorbiForm chorbiForm = this.chorbiService.createForm();
		result = this.createModelAndView(chorbiForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChorbiForm chorbiForm, final BindingResult binding) throws CheckDigitException {
		ModelAndView result = new ModelAndView();
		Chorbi chorbi;

		chorbi = this.chorbiService.reconstruct(chorbiForm, binding);
		this.chorbiService.checkRestrictions(chorbiForm, binding);

		if (binding.hasErrors())
			result = this.createModelAndView(chorbiForm, "chorbi.creditCard.error");
		else
			try {
				this.chorbiService.save(chorbi);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				if (oops.getClass() == org.springframework.dao.DataIntegrityViolationException.class)
					result = this.createModelAndView(chorbiForm, "chorbi.commit.integrityError");
				else
					result = this.createModelAndView(chorbiForm, "chorbi.commit.error");
			}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createModelAndView(final ChorbiForm chorbiForm) {
		final ModelAndView result = this.createModelAndView(chorbiForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final ChorbiForm chorbiForm, final String message) {
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
		final String pictureUrl = chorbiForm.getPicture();

		result = new ModelAndView("chorbi/edit");
		result.addObject("chorbiForm", chorbiForm);
		result.addObject("pictureUrl", pictureUrl);
		result.addObject("genres", genres);
		result.addObject("brands", brands);
		result.addObject("relationships", relationships);
		result.addObject("message", message);
		result.addObject("requestURI", "chorbi/edit.do");

		return result;
	}

}

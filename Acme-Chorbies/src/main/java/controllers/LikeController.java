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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Like;
import forms.LikeForm;
import services.ChorbiService;
import services.LikeService;

@Controller
@RequestMapping("/like")
public class LikeController extends AbstractController {

	// Related services

	@Autowired
	private LikeService		likeService;

	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public LikeController() {
		super();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int idChorbi) {
		ModelAndView result;
		final LikeForm likeForm = new LikeForm();
		likeForm.setIdReceiver(idChorbi);

		result = this.createEditModelAndView(likeForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LikeForm likeForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Like like = new Like();

		like = this.likeService.reconstruct(likeForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(likeForm);
		else
			try {
				this.likeService.save(like);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(likeForm, "like.commit.error");
			}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final LikeForm likeForm) {
		final ModelAndView result = this.createEditModelAndView(likeForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final LikeForm likeForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("like/register");
		result.addObject("likeForm", likeForm);
		result.addObject("message", message);

		return result;
	}

}

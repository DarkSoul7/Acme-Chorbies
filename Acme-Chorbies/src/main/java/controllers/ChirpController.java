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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.ChorbiService;
import domain.Chirp;
import domain.Chorbi;
import forms.ChirpForm;

@Controller
@RequestMapping("/chirp")
public class ChirpController extends AbstractController {

	// Related services

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public ChirpController() {
		super();
	}

	// ShowChirp
	@RequestMapping(value = "/showChirp", method = RequestMethod.GET)
	public ModelAndView showChirp(@RequestParam(required = true) final int chirpId) {
		ModelAndView result;
		Chirp chirp;
		String errorMessage;

		try {
			chirp = this.chirpService.findOne(chirpId);

			//Hidding email & phone
			this.chirpService.applyPrivacity(chirp);

			errorMessage = null;
		} catch (final Throwable oops) {
			chirp = null;
			errorMessage = "chirp.nonAuthorized.error";
		}

		result = new ModelAndView("chirp/showChirp");
		result.addObject("chirp", chirp);
		result.addObject("message", errorMessage);
		result.addObject("requestURI", "chirp/showChirp.do");

		return result;
	}

	// SentChirps
	@RequestMapping(value = "/sentChirps", method = RequestMethod.GET)
	public ModelAndView sentChirps(@RequestParam(required = false) final String message) {
		ModelAndView result;
		final Collection<Chirp> chirps = this.chirpService.findAllSentByPrincipal();

		//Hiding email & phone
		this.chirpService.applyPrivacity(chirps);

		result = new ModelAndView("chirp/sentChirps");
		result.addObject("chirps", chirps);
		result.addObject("message", message);
		result.addObject("requestURI", "chirp/sentChirps.do");

		return result;
	}

	// ReceivedChirps
	@RequestMapping(value = "/receivedChirps", method = RequestMethod.GET)
	public ModelAndView receivedChirps(@RequestParam(required = false) final String message) {
		ModelAndView result;
		final Collection<Chirp> chirps = this.chirpService.findAllReceivedByPrincipal();

		result = new ModelAndView("chirp/receivedChirps");
		result.addObject("chirps", chirps);
		result.addObject("message", message);
		result.addObject("requestURI", "chirp/receivedChirps.do");

		return result;
	}

	// Create
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView send() {
		ModelAndView result;
		final ChirpForm chirpForm = this.chirpService.create();

		result = this.createEditModelAndView(chirpForm);

		return result;
	}

	// Reply
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam(required = true) final int chirpId) {
		ModelAndView result;
		Chirp chirp;
		ChirpForm chirpForm;
		String errorMessage;
		Boolean answerable;

		try {
			chirp = this.chirpService.findOne(chirpId);
			chirpForm = this.chirpService.toFormObject(chirp, true);
			chirpForm.setParentChirpId(chirp.getId());
			errorMessage = null;
			answerable = true;
		} catch (final Throwable oops) {
			chirpForm = this.chirpService.create();
			errorMessage = "chirp.nonAuthorizedReply.error";
			answerable = false;
		}

		result = this.createEditModelAndView(chirpForm, errorMessage);
		result.addObject("answerable", answerable);

		return result;
	}
	// Forward
	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam(required = true) final int chirpId) {
		ModelAndView result;
		Chirp chirp;
		ChirpForm chirpForm;
		String errorMessage;
		Boolean answerable;

		try {
			chirp = this.chirpService.findOne(chirpId);
			chirpForm = this.chirpService.toFormObject(chirp, false);
			chirpForm.setParentChirpId(chirp.getId());
			errorMessage = null;
			answerable = true;
		} catch (final Throwable oops) {
			chirpForm = this.chirpService.create();
			errorMessage = "chirp.nonAuthorizedForward.error";
			answerable = false;
		}

		result = this.createEditModelAndView(chirpForm, errorMessage);
		result.addObject("answerable", answerable);

		return result;
	}

	// Save
	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChirpForm chirpForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Chirp chirp;

		if (binding.hasErrors())
			result = this.createEditModelAndView(chirpForm);
		else
			try {
				chirp = this.chirpService.reconstruct(chirpForm);
				this.chirpService.save(chirp);
				result = new ModelAndView("redirect:/chirp/sentChirps.do");
			} catch (final IllegalArgumentException e) {
				result = this.createEditModelAndView(chirpForm, "chirp.attachments.error");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirpForm, "chirp.send.error");
			}

		return result;
	}

	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = true) final int chirpId, @RequestParam(required = true) final String url) {
		ModelAndView result = new ModelAndView();
		String errorChirp = null;

		try {
			this.chirpService.delete(chirpId);
		} catch (final Throwable oops) {
			errorChirp = "chirp.delete.error";
		}

		result = new ModelAndView("redirect:/" + url);
		result.addObject("chirp", errorChirp);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final ChirpForm chirpForm) {
		final ModelAndView result = this.createEditModelAndView(chirpForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ChirpForm chirpForm, final String message) {
		ModelAndView result;
		final Collection<Chorbi> chorbies = this.chorbiService.findAllExceptPrincipal();

		result = new ModelAndView("chirp/send");
		result.addObject("chirpForm", chirpForm);
		result.addObject("chorbies", chorbies);
		result.addObject("message", message);
		result.addObject("requestURI", "chirp/send.do");

		return result;
	}

}

/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BannerService;
import domain.Actor;
import domain.Banner;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	// Related services -------------------------------------------------------
	
	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private BannerService	bannerService;
	
	
	// Constructors -----------------------------------------------------------
	
	public WelcomeController() {
		super();
	}
	
	// Index ------------------------------------------------------------------		
	
	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		String fullName;
		Actor principal;
		Banner banner;
		
		if (LoginService.isAuthenticated()) {
			principal = actorService.findByPrincipal();
			fullName = principal.getName() + " " + principal.getSurname();
		} else {
			fullName = null;
		}
		
		banner = bannerService.getRandomBanner();
		
		result = new ModelAndView("welcome/index");
		result.addObject("fullName", fullName);
		result.addObject("banner", banner);
		
		return result;
	}
	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {
		
		ModelAndView result;
		
		result = new ModelAndView("welcome/cookies");
		result.addObject("backURI", "/welcome/index.do");
		
		return result;
	}
	
	@RequestMapping(value = "/conditions")
	public ModelAndView conditions() {
		ModelAndView result;
		
		result = new ModelAndView("welcome/conditions");
		result.addObject("backURI", "/welcome/index.do");
		
		return result;
	}
	
	@RequestMapping(value = "/eraseMe")
	public ModelAndView eraseMe() {
		ModelAndView result;
		
		result = new ModelAndView("welcome/eraseMe");
		result.addObject("backURI", "/welcome/index.do");
		
		return result;
	}
}

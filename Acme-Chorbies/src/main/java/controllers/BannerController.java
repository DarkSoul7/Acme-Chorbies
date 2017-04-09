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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import domain.Banner;

@Controller
@RequestMapping("/banner")
public class BannerController extends AbstractController {
	
	//Related services
	
	@Autowired
	private BannerService	bannerService;
	
	
	// Constructors -----------------------------------------------------------
	
	public BannerController() {
		super();
	}
	
	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		result = this.listModelAndView();
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Banner banner;
		
		banner = this.bannerService.create();
		result = this.createEditModelAndView(banner);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int bannerId) {
		ModelAndView result;
		Banner banner;
		
		try {
			banner = this.bannerService.findOne(bannerId);
			Assert.notNull(banner);
			
			result = this.createEditModelAndView(banner);
			result.addObject("successMessage", "banner.edit.success");
		} catch (Throwable e) {
			result = this.listModelAndView("banner.edit.error");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Banner banner, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors())
			result = this.createEditModelAndView(banner);
		else
			try {
				this.bannerService.save(banner);
				result = this.listModelAndView();
				result.addObject("successMessage", "banner.save.success");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(banner, "banner.save.error");
			}
		
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int bannerId) {
		ModelAndView result;
		Banner banner;
		
		try {
			banner = this.bannerService.findOne(bannerId);
			Assert.notNull(banner);
			this.bannerService.delete(banner);
			result = this.listModelAndView();
			result.addObject("successMessage", "banner.delete.success");
		} catch (Throwable e) {
			result = this.listModelAndView("banner.delete.error");
		}
		
		return result;
	}
	
	// Ancillary methods
	
	protected ModelAndView listModelAndView() {
		final ModelAndView result = this.listModelAndView(null);
		return result;
	}
	
	protected ModelAndView listModelAndView(String message) {
		ModelAndView result;
		Collection<Banner> banners;
		
		banners = this.bannerService.findAll();
		
		result = new ModelAndView("banner/list");
		result.addObject("banners", banners);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", "banner/list.do");
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Banner banner) {
		return this.createEditModelAndView(banner, null);
	}
	
	protected ModelAndView createEditModelAndView(Banner banner, String message) {
		ModelAndView result;
		
		result = new ModelAndView("banner/create");
		result.addObject("banner", banner);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", "banner/save.do");
		
		return result;
	}
	
}

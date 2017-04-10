
package controllers;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CachedTimeService;
import services.ChorbiService;
import services.SearchTemplateService;
import domain.Brand;
import domain.CachedTime;
import domain.Chorbi;
import domain.Genre;
import domain.Relationship;
import domain.SearchTemplate;

@Controller
@RequestMapping(value = "/searchTemplate")
public class SearchTemplateController extends AbstractController {
	
	//Related services
	
	@Autowired
	private SearchTemplateService	searchTemplateService;
	
	@Autowired
	private CachedTimeService		cachedTimeService;
	
	@Autowired
	private ChorbiService			chorbiService;
	
	
	//List
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final SearchTemplate searchTemplate = chorbi.getSearchTemplate();
		
		result = this.listSaveModelAndView(searchTemplate);
		return result;
	}
	
	//Save
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SearchTemplate searchTemplate, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.listSaveModelAndView(searchTemplate);
		else
			try {
				final Collection<Chorbi> chorbiesFound = this.searchTemplateService.searchByPrincipal(searchTemplate);
				searchTemplate.setListChorbi(chorbiesFound);
				this.searchTemplateService.save(searchTemplate);
				
				result = new ModelAndView("redirect:/searchTemplate/list.do");
				result.addObject("cachedChorbies", searchTemplate.getListChorbi());
			} catch (final Throwable oops) {
				result = this.listSaveModelAndView(searchTemplate, "searchTemplate.save.error");
			}
		return result;
	}
	
	//List cache time
	@RequestMapping(value = "/listCachedTime", method = RequestMethod.GET)
	public ModelAndView listCachedTime() {
		ModelAndView result;
		
		final CachedTime cachedTime = this.cachedTimeService.findUnique();
		
		result = new ModelAndView("searchTemplate/listCachedTime");
		result.addObject("cachedTime", cachedTime);
		result.addObject("RequestURI", "searchTemplate/listCachedTime.do");
		
		return result;
	}
	
	//List cache time
	@RequestMapping(value = "/editCachedTime", method = RequestMethod.GET)
	public ModelAndView editCachedTime() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		ModelAndView result;
		
		final CachedTime cachedTime = this.cachedTimeService.findUnique();
		
		result = this.createEditModelAndViewCachedTime(cachedTime);
		
		return result;
	}
	
	//Save cache time
	@RequestMapping(value = "/saveCachedTime", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCachedTime(@Valid final CachedTime cachedTime, final BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors())
			result = this.createEditModelAndViewCachedTime(cachedTime);
		else
			try {
				
				this.cachedTimeService.save(cachedTime);
				
				result = new ModelAndView("redirect:/searchTemplate/listCachedTime.do");
				
				//Updating event
				this.cachedTimeService.updateEvent();
				
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCachedTime(cachedTime, "searchTemplate.saveCachedTime.error");
			}
		return result;
	}
	
	//Ancillary methods
	
	protected ModelAndView listSaveModelAndView(final SearchTemplate searchTemplate) {
		return this.listSaveModelAndView(searchTemplate, null);
	}
	
	protected ModelAndView listSaveModelAndView(final SearchTemplate searchTemplate, final String message) {
		ModelAndView result;
		
		final Collection<Chorbi> cachedChorbies = searchTemplate.getListChorbi();
		
		final List<Genre> genres = Arrays.asList(Genre.values());
		final List<Relationship> relationships = Arrays.asList(Relationship.values());
		final List<Brand> brands = Arrays.asList(Brand.values());
		
		result = new ModelAndView("searchTemplate/list");
		result.addObject("searchTemplate", searchTemplate);
		result.addObject("cachedChorbies", cachedChorbies);
		result.addObject("genres", genres);
		result.addObject("relationships", relationships);
		result.addObject("brands", brands);
		result.addObject("errorMessage", message);
		result.addObject("RequestURI", "searchTemplate/save.do");
		
		return result;
	}
	
	protected ModelAndView createEditModelAndViewCachedTime(final CachedTime cachedTime) {
		return this.createEditModelAndViewCachedTime(cachedTime, null);
	}
	
	protected ModelAndView createEditModelAndViewCachedTime(final CachedTime cachedTime, final String message) {
		ModelAndView result;
		
		result = new ModelAndView("searchTemplate/editCachedTime");
		result.addObject("cachedTime", cachedTime);
		result.addObject("errorMessage", message);
		result.addObject("RequestURI", "searchTemplate/saveCachedTime.do");
		
		return result;
	}
}

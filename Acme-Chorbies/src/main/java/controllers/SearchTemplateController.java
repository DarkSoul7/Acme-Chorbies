
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CachedTimeService;
import services.ChorbiService;
import services.SearchTemplateService;
import domain.CachedTime;
import domain.Chorbi;
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
		final Collection<Chorbi> cachedChorbies = searchTemplate.getListChorbi();

		result = new ModelAndView("searchTemplate/list");
		result.addObject("searchTemplate", searchTemplate);
		result.addObject("cachedChorbies", cachedChorbies);
		result.addObject("RequestURI", "searchTemplate/list.do");

		return result;
	}

	//Save
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SearchTemplate searchTemplate, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(searchTemplate);
		else
			try {
				final Chorbi chorbi = this.chorbiService.findByPrincipal();
				Assert.isTrue(searchTemplate.getChorbi().equals(chorbi));

				this.searchTemplateService.searchForChorbies(searchTemplate);
				this.searchTemplateService.update(searchTemplate);

				result = new ModelAndView("redirect:/searchTemplate/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(searchTemplate, "searchTemplate.save.error");
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
	public ModelAndView editCachedTime() {
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

			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCachedTime(cachedTime, "searchTemplate.saveCachedTime.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final SearchTemplate searchTemplate) {
		return this.createEditModelAndView(searchTemplate, null);
	}

	protected ModelAndView createEditModelAndView(final SearchTemplate searchTemplate, final String message) {
		ModelAndView result;

		result = new ModelAndView("searchTemplate/list");
		result.addObject("searchTemplate", searchTemplate);
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

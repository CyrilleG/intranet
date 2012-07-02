package controllers;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import models.AppFilter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/filter")
public class FilterController {
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid AppFilter appFilter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appFilter);
            return "appfilters/create";
        }
        uiModel.asMap().clear();
        appFilter.persist();
        return "redirect:/appfilters/" + encodeUrlPathSegment(appFilter.getFilter().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new AppFilter());
        return "appfilters/create";
    }

	@RequestMapping(value = "/{idfilter}", produces = "text/html")
    public String show(@PathVariable("idfilter") Integer idfilter, Model uiModel) {
        uiModel.addAttribute("appfilter", AppFilter.findAppFilter(idfilter));
        uiModel.addAttribute("itemId", idfilter);
        return "appfilters/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("appfilters", AppFilter.findAppFilterEntries(firstResult, sizeNo));
            float nrOfPages = (float) AppFilter.countAppFilters() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("appfilters", AppFilter.findAllAppFilters());
        }
        return "appfilters/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AppFilter appFilter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appFilter);
            return "appfilters/update";
        }
        uiModel.asMap().clear();
        appFilter.merge();
        return "redirect:/appfilters/" + encodeUrlPathSegment(appFilter.getFilter().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{idfilter}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("idfilter") Integer idfilter, Model uiModel) {
        populateEditForm(uiModel, AppFilter.findAppFilter(idfilter));
        return "appfilters/update";
    }

	@RequestMapping(value = "/{idfilter}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("idfilter") Integer idfilter, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AppFilter appFilter = AppFilter.findAppFilter(idfilter);
        appFilter.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
 
        return "redirect:/appfilters";
    }

	void populateEditForm(Model uiModel, AppFilter appFilter) {
        uiModel.addAttribute("appFilter", appFilter);
        //uiModel.addAttribute("groupfilterses", GroupFilters.findAllGroupFilterses());
        //uiModel.addAttribute("userfilterses", UserFilters.findAllUserFilterses());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
	
}

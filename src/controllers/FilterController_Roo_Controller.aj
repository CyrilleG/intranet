// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package controllers;

import controllers.FilterController;
import intranet.Filter;
import intranet.GroupFilters;
import intranet.UserFilters;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect FilterController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String FilterController.create(@Valid Filter filter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, filter);
            return "filters/create";
        }
        uiModel.asMap().clear();
        filter.persist();
        return "redirect:/filters/" + encodeUrlPathSegment(filter.getIdfilter().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String FilterController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Filter());
        return "filters/create";
    }
    
    @RequestMapping(value = "/{idfilter}", produces = "text/html")
    public String FilterController.show(@PathVariable("idfilter") Integer idfilter, Model uiModel) {
        uiModel.addAttribute("filter", Filter.findFilter(idfilter));
        uiModel.addAttribute("itemId", idfilter);
        return "filters/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String FilterController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("filters", Filter.findFilterEntries(firstResult, sizeNo));
            float nrOfPages = (float) Filter.countFilters() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("filters", Filter.findAllFilters());
        }
        return "filters/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String FilterController.update(@Valid Filter filter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, filter);
            return "filters/update";
        }
        uiModel.asMap().clear();
        filter.merge();
        return "redirect:/filters/" + encodeUrlPathSegment(filter.getIdfilter().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{idfilter}", params = "form", produces = "text/html")
    public String FilterController.updateForm(@PathVariable("idfilter") Integer idfilter, Model uiModel) {
        populateEditForm(uiModel, Filter.findFilter(idfilter));
        return "filters/update";
    }
    
    @RequestMapping(value = "/{idfilter}", method = RequestMethod.DELETE, produces = "text/html")
    public String FilterController.delete(@PathVariable("idfilter") Integer idfilter, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Filter filter = Filter.findFilter(idfilter);
        filter.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/filters";
    }
    
    void FilterController.populateEditForm(Model uiModel, Filter filter) {
        uiModel.addAttribute("filter", filter);
        uiModel.addAttribute("groupfilterses", GroupFilters.findAllGroupFilterses());
        uiModel.addAttribute("userfilterses", UserFilters.findAllUserFilterses());
    }
    
    String FilterController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
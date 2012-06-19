// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package controllers;

import controllers.ActionRightsController;
import intranet.Action;
import intranet.ActionRights;
import intranet.Right;

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

privileged aspect ActionRightsController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String ActionRightsController.create(@Valid ActionRights actionRights, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, actionRights);
            return "actionrightses/create";
        }
        uiModel.asMap().clear();
        actionRights.persist();
        return "redirect:/actionrightses/" + encodeUrlPathSegment(actionRights.getIdactionRights().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ActionRightsController.createForm(Model uiModel) {
        populateEditForm(uiModel, new ActionRights());
        return "actionrightses/create";
    }
    
    @RequestMapping(value = "/{idactionRights}", produces = "text/html")
    public String ActionRightsController.show(@PathVariable("idactionRights") Integer idactionRights, Model uiModel) {
        uiModel.addAttribute("actionrights", ActionRights.findActionRights(idactionRights));
        uiModel.addAttribute("itemId", idactionRights);
        return "actionrightses/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String ActionRightsController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("actionrightses", ActionRights.findActionRightsEntries(firstResult, sizeNo));
            float nrOfPages = (float) ActionRights.countActionRightses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("actionrightses", ActionRights.findAllActionRightses());
        }
        return "actionrightses/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ActionRightsController.update(@Valid ActionRights actionRights, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, actionRights);
            return "actionrightses/update";
        }
        uiModel.asMap().clear();
        actionRights.merge();
        return "redirect:/actionrightses/" + encodeUrlPathSegment(actionRights.getIdactionRights().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{idactionRights}", params = "form", produces = "text/html")
    public String ActionRightsController.updateForm(@PathVariable("idactionRights") Integer idactionRights, Model uiModel) {
        populateEditForm(uiModel, ActionRights.findActionRights(idactionRights));
        return "actionrightses/update";
    }
    
    @RequestMapping(value = "/{idactionRights}", method = RequestMethod.DELETE, produces = "text/html")
    public String ActionRightsController.delete(@PathVariable("idactionRights") Integer idactionRights, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ActionRights actionRights = ActionRights.findActionRights(idactionRights);
        actionRights.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/actionrightses";
    }
    
    void ActionRightsController.populateEditForm(Model uiModel, ActionRights actionRights) {
        uiModel.addAttribute("actionRights", actionRights);
        uiModel.addAttribute("actions", Action.findAllActions());
        uiModel.addAttribute("rights", Right.findAllRights());
    }
    
    String ActionRightsController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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

// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package controllers;

import controllers.ActionGroupsController;
import intranet.Action;
import intranet.ActionGroups;
import intranet.Group;

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

privileged aspect ActionGroupsController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String ActionGroupsController.create(@Valid ActionGroups actionGroups, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, actionGroups);
            return "actiongroupses/create";
        }
        uiModel.asMap().clear();
        actionGroups.persist();
        return "redirect:/actiongroupses/" + encodeUrlPathSegment(actionGroups.getIdactionGroup().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ActionGroupsController.createForm(Model uiModel) {
        populateEditForm(uiModel, new ActionGroups());
        return "actiongroupses/create";
    }
    
    @RequestMapping(value = "/{idactionGroup}", produces = "text/html")
    public String ActionGroupsController.show(@PathVariable("idactionGroup") Integer idactionGroup, Model uiModel) {
        uiModel.addAttribute("actiongroups", ActionGroups.findActionGroups(idactionGroup));
        uiModel.addAttribute("itemId", idactionGroup);
        return "actiongroupses/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String ActionGroupsController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("actiongroupses", ActionGroups.findActionGroupsEntries(firstResult, sizeNo));
            float nrOfPages = (float) ActionGroups.countActionGroupses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("actiongroupses", ActionGroups.findAllActionGroupses());
        }
        return "actiongroupses/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ActionGroupsController.update(@Valid ActionGroups actionGroups, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, actionGroups);
            return "actiongroupses/update";
        }
        uiModel.asMap().clear();
        actionGroups.merge();
        return "redirect:/actiongroupses/" + encodeUrlPathSegment(actionGroups.getIdactionGroup().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{idactionGroup}", params = "form", produces = "text/html")
    public String ActionGroupsController.updateForm(@PathVariable("idactionGroup") Integer idactionGroup, Model uiModel) {
        populateEditForm(uiModel, ActionGroups.findActionGroups(idactionGroup));
        return "actiongroupses/update";
    }
    
    @RequestMapping(value = "/{idactionGroup}", method = RequestMethod.DELETE, produces = "text/html")
    public String ActionGroupsController.delete(@PathVariable("idactionGroup") Integer idactionGroup, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ActionGroups actionGroups = ActionGroups.findActionGroups(idactionGroup);
        actionGroups.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/actiongroupses";
    }
    
    void ActionGroupsController.populateEditForm(Model uiModel, ActionGroups actionGroups) {
        uiModel.addAttribute("actionGroups", actionGroups);
        uiModel.addAttribute("actions", Action.findAllActions());
        uiModel.addAttribute("groups", Group.findAllGroups());
    }
    
    String ActionGroupsController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet.controllers;

import intranet.ActionGroups;
import intranet.Group;
import intranet.GroupFilters;
import intranet.GroupRights;
import intranet.ModuleGroups;
import intranet.ModuleRights;
import intranet.Privacities;
import intranet.UserGroups;
import intranet.controllers.GroupController;
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

privileged aspect GroupController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String GroupController.create(@Valid Group group, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, group);
            return "groups/create";
        }
        uiModel.asMap().clear();
        group.persist();
        return "redirect:/groups/" + encodeUrlPathSegment(group.getIdgroup().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String GroupController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Group());
        return "groups/create";
    }
    
    @RequestMapping(value = "/{idgroup}", produces = "text/html")
    public String GroupController.show(@PathVariable("idgroup") Integer idgroup, Model uiModel) {
        uiModel.addAttribute("group", Group.findGroup(idgroup));
        uiModel.addAttribute("itemId", idgroup);
        return "groups/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String GroupController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("groups", Group.findGroupEntries(firstResult, sizeNo));
            float nrOfPages = (float) Group.countGroups() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("groups", Group.findAllGroups());
        }
        return "groups/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String GroupController.update(@Valid Group group, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, group);
            return "groups/update";
        }
        uiModel.asMap().clear();
        group.merge();
        return "redirect:/groups/" + encodeUrlPathSegment(group.getIdgroup().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{idgroup}", params = "form", produces = "text/html")
    public String GroupController.updateForm(@PathVariable("idgroup") Integer idgroup, Model uiModel) {
        populateEditForm(uiModel, Group.findGroup(idgroup));
        return "groups/update";
    }
    
    @RequestMapping(value = "/{idgroup}", method = RequestMethod.DELETE, produces = "text/html")
    public String GroupController.delete(@PathVariable("idgroup") Integer idgroup, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Group group = Group.findGroup(idgroup);
        group.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/groups";
    }
    
    void GroupController.populateEditForm(Model uiModel, Group group) {
        uiModel.addAttribute("group", group);
        uiModel.addAttribute("actiongroupses", ActionGroups.findAllActionGroupses());
        uiModel.addAttribute("groupfilterses", GroupFilters.findAllGroupFilterses());
        uiModel.addAttribute("grouprightses", GroupRights.findAllGroupRightses());
        uiModel.addAttribute("modulegroupses", ModuleGroups.findAllModuleGroupses());
        uiModel.addAttribute("modulerightses", ModuleRights.findAllModuleRightses());
        uiModel.addAttribute("privacitieses", Privacities.findAllPrivacitieses());
        uiModel.addAttribute("usergroupses", UserGroups.findAllUserGroupses());
    }
    
    String GroupController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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

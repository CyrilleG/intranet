// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package controllers;

import intranet.AppGroup;
import intranet.AppUser;
import intranet.UserGroups;
import controllers.UserGroupsController;
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

privileged aspect UserGroupsController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String UserGroupsController.create(@Valid UserGroups userGroups, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userGroups);
            return "usergroupses/create";
        }
        uiModel.asMap().clear();
        userGroups.persist();
        return "redirect:/usergroupses/" + encodeUrlPathSegment(userGroups.getIduserGroups().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String UserGroupsController.createForm(Model uiModel) {
        populateEditForm(uiModel, new UserGroups());
        return "usergroupses/create";
    }
    
    @RequestMapping(value = "/{iduserGroups}", produces = "text/html")
    public String UserGroupsController.show(@PathVariable("iduserGroups") Integer iduserGroups, Model uiModel) {
        uiModel.addAttribute("usergroups", UserGroups.findUserGroups(iduserGroups));
        uiModel.addAttribute("itemId", iduserGroups);
        return "usergroupses/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String UserGroupsController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("usergroupses", UserGroups.findUserGroupsEntries(firstResult, sizeNo));
            float nrOfPages = (float) UserGroups.countUserGroupses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("usergroupses", UserGroups.findAllUserGroupses());
        }
        return "usergroupses/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String UserGroupsController.update(@Valid UserGroups userGroups, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userGroups);
            return "usergroupses/update";
        }
        uiModel.asMap().clear();
        userGroups.merge();
        return "redirect:/usergroupses/" + encodeUrlPathSegment(userGroups.getIduserGroups().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{iduserGroups}", params = "form", produces = "text/html")
    public String UserGroupsController.updateForm(@PathVariable("iduserGroups") Integer iduserGroups, Model uiModel) {
        populateEditForm(uiModel, UserGroups.findUserGroups(iduserGroups));
        return "usergroupses/update";
    }
    
    @RequestMapping(value = "/{iduserGroups}", method = RequestMethod.DELETE, produces = "text/html")
    public String UserGroupsController.delete(@PathVariable("iduserGroups") Integer iduserGroups, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserGroups userGroups = UserGroups.findUserGroups(iduserGroups);
        userGroups.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/usergroupses";
    }
    
    void UserGroupsController.populateEditForm(Model uiModel, UserGroups userGroups) {
        uiModel.addAttribute("userGroups", userGroups);
        uiModel.addAttribute("appgroups", AppGroup.findAllAppGroups());
        uiModel.addAttribute("appusers", AppUser.findAllAppUsers());
    }
    
    String UserGroupsController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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

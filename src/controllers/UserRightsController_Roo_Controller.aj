// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package controllers;

import controllers.UserRightsController;
import intranet.Right;
import intranet.User;
import intranet.UserRights;

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

privileged aspect UserRightsController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String UserRightsController.create(@Valid UserRights userRights, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userRights);
            return "userrightses/create";
        }
        uiModel.asMap().clear();
        userRights.persist();
        return "redirect:/userrightses/" + encodeUrlPathSegment(userRights.getIduserRights().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String UserRightsController.createForm(Model uiModel) {
        populateEditForm(uiModel, new UserRights());
        return "userrightses/create";
    }
    
    @RequestMapping(value = "/{iduserRights}", produces = "text/html")
    public String UserRightsController.show(@PathVariable("iduserRights") Integer iduserRights, Model uiModel) {
        uiModel.addAttribute("userrights", UserRights.findUserRights(iduserRights));
        uiModel.addAttribute("itemId", iduserRights);
        return "userrightses/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String UserRightsController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("userrightses", UserRights.findUserRightsEntries(firstResult, sizeNo));
            float nrOfPages = (float) UserRights.countUserRightses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("userrightses", UserRights.findAllUserRightses());
        }
        return "userrightses/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String UserRightsController.update(@Valid UserRights userRights, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userRights);
            return "userrightses/update";
        }
        uiModel.asMap().clear();
        userRights.merge();
        return "redirect:/userrightses/" + encodeUrlPathSegment(userRights.getIduserRights().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{iduserRights}", params = "form", produces = "text/html")
    public String UserRightsController.updateForm(@PathVariable("iduserRights") Integer iduserRights, Model uiModel) {
        populateEditForm(uiModel, UserRights.findUserRights(iduserRights));
        return "userrightses/update";
    }
    
    @RequestMapping(value = "/{iduserRights}", method = RequestMethod.DELETE, produces = "text/html")
    public String UserRightsController.delete(@PathVariable("iduserRights") Integer iduserRights, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserRights userRights = UserRights.findUserRights(iduserRights);
        userRights.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/userrightses";
    }
    
    void UserRightsController.populateEditForm(Model uiModel, UserRights userRights) {
        uiModel.addAttribute("userRights", userRights);
        uiModel.addAttribute("rights", Right.findAllRights());
        uiModel.addAttribute("users", User.findAllUsers());
    }
    
    String UserRightsController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
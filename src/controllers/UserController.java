package controllers;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import models.AppSession;
import models.AppUser;
import models.UserInfo;

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
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/{iduser}", produces = "text/html")
    public String show(@PathVariable("iduser") Integer iduser, Model uiModel) {
        uiModel.addAttribute("appuser", AppUser.findAppUser(iduser));
        uiModel.addAttribute("itemId", iduser);
        return "appusers/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("appusers", AppUser.findAppUserEntries(firstResult, sizeNo));
            float nrOfPages = (float) AppUser.countAppUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("appusers", AppUser.findAllAppUsers());
        }
        return "appusers/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AppUser appUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appUser);
            return "appusers/update";
        }
        uiModel.asMap().clear();
        appUser.merge();
        return "redirect:/appusers/" + encodeUrlPathSegment(appUser.getUser().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{iduser}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("iduser") Integer iduser, Model uiModel) {
        populateEditForm(uiModel, AppUser.findAppUser(iduser));
        return "appusers/update";
    }
    
   
    
    void populateEditForm(Model uiModel, AppUser appUser) {
        uiModel.addAttribute("appUser", appUser);
        uiModel.addAttribute("appsessions", AppSession.findAllAppSessions());
        //uiModel.addAttribute("infoprivacitieses", InfoPrivacities.findAllInfoPrivacitieses());
        //uiModel.addAttribute("userdatas", UserData.findAllUserDatas());
        //uiModel.addAttribute("userfilterses", UserFilters.findAllUserFilterses());
        //uiModel.addAttribute("usergroupses", UserGroups.findAllUserGroupses());
        uiModel.addAttribute("userinfoes", UserInfo.findAllUserInfoes());
        //uiModel.addAttribute("userrightses", UserRights.findAllUserRightses());
    }
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid AppUser appUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appUser);
            return "appusers/create";
        }
        uiModel.asMap().clear();
        appUser.persist();
        return "redirect:/appusers/" + encodeUrlPathSegment(appUser.getUser().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{iduser}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("iduser") Integer iduser, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AppUser appUser = AppUser.findAppUser(iduser);
        appUser.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/appusers";
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new AppUser());
        return "appusers/create";
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

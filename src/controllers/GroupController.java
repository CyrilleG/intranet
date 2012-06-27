package controllers;

import intranet.AppGroup;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
@RequestMapping("/group")
public class GroupController {
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid AppGroup appGroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appGroup);
            return "appgroups/create";
        }
        uiModel.asMap().clear();
        appGroup.persist();
        return "redirect:/appgroups/" + encodeUrlPathSegment(appGroup.getGroup().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new AppGroup());
        return "appgroups/create";
    }

	@RequestMapping(value = "/{idgroup}", produces = "text/html")
    public String show(@PathVariable("idgroup") Integer idgroup, Model uiModel) {
        uiModel.addAttribute("appgroup", AppGroup.findAppGroup(idgroup));
        uiModel.addAttribute("itemId", idgroup);
        return "appgroups/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("appgroups", AppGroup.findAppGroupEntries(firstResult, sizeNo));
            float nrOfPages = (float) AppGroup.countAppGroups() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("appgroups", AppGroup.findAllAppGroups());
        }
        return "appgroups/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AppGroup appGroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appGroup);
            return "appgroups/update";
        }
        uiModel.asMap().clear();
        appGroup.merge();
        return "redirect:/appgroups/" + encodeUrlPathSegment(appGroup.getGroup().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{idgroup}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("idgroup") Integer idgroup, Model uiModel) {
        populateEditForm(uiModel, AppGroup.findAppGroup(idgroup));
        return "appgroups/update";
    }

	@RequestMapping(value = "/{idgroup}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("idgroup") Integer idgroup, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AppGroup appGroup = AppGroup.findAppGroup(idgroup);
        appGroup.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/appgroups";
    }

	void populateEditForm(Model uiModel, AppGroup appGroup) {
        uiModel.addAttribute("appGroup", appGroup);
        //uiModel.addAttribute("actiongroupses", ActionGroups.findAllActionGroupses());
        //uiModel.addAttribute("groupfilterses", GroupFilters.findAllGroupFilterses());
        //uiModel.addAttribute("grouprightses", GroupRights.findAllGroupRightses());
        //uiModel.addAttribute("infoprivacitieses", InfoPrivacities.findAllInfoPrivacitieses());
        //uiModel.addAttribute("modulegroupses", ModuleGroups.findAllModuleGroupses());
        //uiModel.addAttribute("modulerightses", ModuleRights.findAllModuleRightses());
        //uiModel.addAttribute("usergroupses", UserGroups.findAllUserGroupses());
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

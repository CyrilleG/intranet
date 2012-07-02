package controllers;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import models.AppRight;

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
@RequestMapping("/right")
public class RightController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid AppRight appRight, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appRight);
            return "apprights/create";
        }
        uiModel.asMap().clear();
        appRight.persist();
        return "redirect:/apprights/" + encodeUrlPathSegment(appRight.getRight().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new AppRight());
        return "apprights/create";
    }

	@RequestMapping(value = "/{idright}", produces = "text/html")
    public String show(@PathVariable("idright") Integer idright, Model uiModel) {
        uiModel.addAttribute("appright", AppRight.findAppRight(idright));
        uiModel.addAttribute("itemId", idright);
        return "apprights/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("apprights", AppRight.findAppRightEntries(firstResult, sizeNo));
            float nrOfPages = (float) AppRight.countAppRights() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("apprights", AppRight.findAllAppRights());
        }
        return "apprights/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AppRight appRight, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appRight);
            return "apprights/update";
        }
        uiModel.asMap().clear();
        appRight.merge();
        return "redirect:/apprights/" + encodeUrlPathSegment(appRight.getRight().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{idright}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("idright") Integer idright, Model uiModel) {
        populateEditForm(uiModel, AppRight.findAppRight(idright));
        return "apprights/update";
    }

	@RequestMapping(value = "/{idright}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("idright") Integer idright, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AppRight appRight = AppRight.findAppRight(idright);
        appRight.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/apprights";
    }

	void populateEditForm(Model uiModel, AppRight appRight) {
        uiModel.addAttribute("appRight", appRight);
        //uiModel.addAttribute("actionrightses", ActionRights.findAllActionRightses());
        //uiModel.addAttribute("grouprightses", GroupRights.findAllGroupRightses());
        //uiModel.addAttribute("modulerightses", ModuleRights.findAllModuleRightses());
        //uiModel.addAttribute("userrightses", UserRights.findAllUserRightses());
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

package controllers;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import models.AppModule;
import models.ModuleAction;

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
@RequestMapping("/module")
public class ModuleController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid AppModule appModule, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appModule);
            return "appmodules/create";
        }
        uiModel.asMap().clear();
        appModule.persist();
        return "redirect:/appmodules/" + encodeUrlPathSegment(appModule.getModule().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new AppModule());
        return "appmodules/create";
    }

	@RequestMapping(value = "/{idmodule}", produces = "text/html")
    public String show(@PathVariable("idmodule") Integer idmodule, Model uiModel) {
        uiModel.addAttribute("appmodule", AppModule.findAppModule(idmodule));
        uiModel.addAttribute("itemId", idmodule);
        return "appmodules/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("appmodules", AppModule.findAppModuleEntries(firstResult, sizeNo));
            float nrOfPages = (float) AppModule.countAppModules() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("appmodules", AppModule.findAllAppModules());
        }
        return "appmodules/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AppModule appModule, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appModule);
            return "appmodules/update";
        }
        uiModel.asMap().clear();
        appModule.merge();
        return "redirect:/appmodules/" + encodeUrlPathSegment(appModule.getModule().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{idmodule}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("idmodule") Integer idmodule, Model uiModel) {
        populateEditForm(uiModel, AppModule.findAppModule(idmodule));
        return "appmodules/update";
    }

	@RequestMapping(value = "/{idmodule}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("idmodule") Integer idmodule, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AppModule appModule = AppModule.findAppModule(idmodule);
        appModule.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/appmodules";
    }

	void populateEditForm(Model uiModel, AppModule appModule) {
        uiModel.addAttribute("appModule", appModule);
        uiModel.addAttribute("moduleactions", ModuleAction.findAllModuleActions());
        //uiModel.addAttribute("modulegroupses", ModuleGroups.findAllModuleGroupses());
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

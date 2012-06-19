// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package controllers;

import controllers.FieldsController;
import intranet.Fields;
import intranet.Objects;

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

privileged aspect FieldsController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String FieldsController.create(@Valid Fields fields, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, fields);
            return "fieldses/create";
        }
        uiModel.asMap().clear();
        fields.persist();
        return "redirect:/fieldses/" + encodeUrlPathSegment(fields.getIdfields().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String FieldsController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Fields());
        return "fieldses/create";
    }
    
    @RequestMapping(value = "/{idfields}", produces = "text/html")
    public String FieldsController.show(@PathVariable("idfields") Integer idfields, Model uiModel) {
        uiModel.addAttribute("fields", Fields.findFields(idfields));
        uiModel.addAttribute("itemId", idfields);
        return "fieldses/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String FieldsController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("fieldses", Fields.findFieldsEntries(firstResult, sizeNo));
            float nrOfPages = (float) Fields.countFieldses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("fieldses", Fields.findAllFieldses());
        }
        return "fieldses/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String FieldsController.update(@Valid Fields fields, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, fields);
            return "fieldses/update";
        }
        uiModel.asMap().clear();
        fields.merge();
        return "redirect:/fieldses/" + encodeUrlPathSegment(fields.getIdfields().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{idfields}", params = "form", produces = "text/html")
    public String FieldsController.updateForm(@PathVariable("idfields") Integer idfields, Model uiModel) {
        populateEditForm(uiModel, Fields.findFields(idfields));
        return "fieldses/update";
    }
    
    @RequestMapping(value = "/{idfields}", method = RequestMethod.DELETE, produces = "text/html")
    public String FieldsController.delete(@PathVariable("idfields") Integer idfields, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Fields fields = Fields.findFields(idfields);
        fields.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/fieldses";
    }
    
    void FieldsController.populateEditForm(Model uiModel, Fields fields) {
        uiModel.addAttribute("fields", fields);
        uiModel.addAttribute("objectses", Objects.findAllObjectses());
    }
    
    String FieldsController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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

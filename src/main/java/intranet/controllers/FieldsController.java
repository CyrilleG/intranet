package intranet.controllers;

import intranet.Fields;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/fieldses")
@Controller
@RooWebScaffold(path = "fieldses", formBackingObject = Fields.class)
public class FieldsController {
}

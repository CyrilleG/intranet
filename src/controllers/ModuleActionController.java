package controllers;

import intranet.ModuleAction;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/moduleactions")
@Controller
@RooWebScaffold(path = "moduleactions", formBackingObject = ModuleAction.class)
public class ModuleActionController {
}

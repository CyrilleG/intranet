package intranet.controllers;

import intranet.ModuleRights;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/modulerightses")
@Controller
@RooWebScaffold(path = "modulerightses", formBackingObject = ModuleRights.class)
public class ModuleRightsController {
}

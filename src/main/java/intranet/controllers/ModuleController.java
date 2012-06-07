package intranet.controllers;

import intranet.Module;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/modules")
@Controller
@RooWebScaffold(path = "modules", formBackingObject = Module.class)
public class ModuleController {
}

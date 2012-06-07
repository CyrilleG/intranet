package intranet.controllers;

import intranet.ModuleAccess;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/moduleaccesses")
@Controller
@RooWebScaffold(path = "moduleaccesses", formBackingObject = ModuleAccess.class)
public class ModuleAccessController {
}

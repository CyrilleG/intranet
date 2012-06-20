package controllers;

import intranet.AppModule;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/appmodules")
@Controller
@RooWebScaffold(path = "appmodules", formBackingObject = AppModule.class)
public class AppModuleController {
}

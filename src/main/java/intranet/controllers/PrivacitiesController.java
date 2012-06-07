package intranet.controllers;

import intranet.Privacities;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/privacitieses")
@Controller
@RooWebScaffold(path = "privacitieses", formBackingObject = Privacities.class)
public class PrivacitiesController {
}

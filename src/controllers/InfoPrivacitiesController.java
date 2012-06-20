package controllers;

import intranet.InfoPrivacities;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/infoprivacitieses")
@Controller
@RooWebScaffold(path = "infoprivacitieses", formBackingObject = InfoPrivacities.class)
public class InfoPrivacitiesController {
}

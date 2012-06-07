package intranet.controllers;

import intranet.Privacity;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/privacitys")
@Controller
@RooWebScaffold(path = "privacitys", formBackingObject = Privacity.class)
public class PrivacityController {
}

package controllers;

import intranet.AppRight;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/apprights")
@Controller
@RooWebScaffold(path = "apprights", formBackingObject = AppRight.class)
public class AppRightController {
}

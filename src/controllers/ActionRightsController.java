package controllers;

import intranet.ActionRights;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/actionrightses")
@Controller
@RooWebScaffold(path = "actionrightses", formBackingObject = ActionRights.class)
public class ActionRightsController {
}

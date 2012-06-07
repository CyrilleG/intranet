package intranet.controllers;

import intranet.Action;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/actions")
@Controller
@RooWebScaffold(path = "actions", formBackingObject = Action.class)
public class ActionController {
}

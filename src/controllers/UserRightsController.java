package controllers;

import intranet.UserRights;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/userrightses")
@Controller
@RooWebScaffold(path = "userrightses", formBackingObject = UserRights.class)
public class UserRightsController {
}

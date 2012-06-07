package intranet.controllers;

import intranet.Group;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/groups")
@Controller
@RooWebScaffold(path = "groups", formBackingObject = Group.class)
public class GroupController {
}

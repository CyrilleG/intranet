package controllers;

import intranet.UserGroups;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/usergroupses")
@Controller
@RooWebScaffold(path = "usergroupses", formBackingObject = UserGroups.class)
public class UserGroupsController {
}

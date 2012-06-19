package controllers;

import intranet.ActionGroups;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/actiongroupses")
@Controller
@RooWebScaffold(path = "actiongroupses", formBackingObject = ActionGroups.class)
public class ActionGroupsController {
}

package controllers;

import intranet.ModuleGroups;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/modulegroupses")
@Controller
@RooWebScaffold(path = "modulegroupses", formBackingObject = ModuleGroups.class)
public class ModuleGroupsController {
}

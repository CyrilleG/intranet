package controllers;

import intranet.GroupRights;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/grouprightses")
@Controller
@RooWebScaffold(path = "grouprightses", formBackingObject = GroupRights.class)
public class GroupRightsController {
}

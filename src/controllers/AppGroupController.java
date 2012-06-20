package controllers;

import intranet.AppGroup;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/appgroups")
@Controller
@RooWebScaffold(path = "appgroups", formBackingObject = AppGroup.class)
public class AppGroupController {
}

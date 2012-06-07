package intranet.controllers;

import intranet.GroupFilter;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/groupfilters")
@Controller
@RooWebScaffold(path = "groupfilters", formBackingObject = GroupFilter.class)
public class GroupFilterController {
}

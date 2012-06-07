package intranet.controllers;

import intranet.GroupFilters;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/groupfilterses")
@Controller
@RooWebScaffold(path = "groupfilterses", formBackingObject = GroupFilters.class)
public class GroupFiltersController {
}

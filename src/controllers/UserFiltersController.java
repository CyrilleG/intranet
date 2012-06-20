package controllers;

import intranet.UserFilters;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/userfilterses")
@Controller
@RooWebScaffold(path = "userfilterses", formBackingObject = UserFilters.class)
public class UserFiltersController {
}

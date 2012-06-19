package controllers;

import intranet.Filter;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/filters")
@Controller
@RooWebScaffold(path = "filters", formBackingObject = Filter.class)
public class FilterController {
}

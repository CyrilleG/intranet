package controllers;

import intranet.Objects;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/objectses")
@Controller
@RooWebScaffold(path = "objectses", formBackingObject = Objects.class)
public class ObjectsController {
}

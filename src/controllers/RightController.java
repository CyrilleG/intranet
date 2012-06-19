package controllers;

import intranet.Right;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/rights")
@Controller
@RooWebScaffold(path = "rights", formBackingObject = Right.class)
public class RightController {
}

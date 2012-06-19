package controllers;

import intranet.Session;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sessions")
@Controller
@RooWebScaffold(path = "sessions", formBackingObject = Session.class)
public class SessionController {
}

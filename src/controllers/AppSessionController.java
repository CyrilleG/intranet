package controllers;

import intranet.AppSession;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/appsessions")
@Controller
@RooWebScaffold(path = "appsessions", formBackingObject = AppSession.class)
public class AppSessionController {
}

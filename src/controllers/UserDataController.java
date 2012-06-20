package controllers;

import intranet.UserData;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/userdatas")
@Controller
@RooWebScaffold(path = "userdatas", formBackingObject = UserData.class)
public class UserDataController {
}

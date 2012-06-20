package controllers;

import intranet.DataField;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/datafields")
@Controller
@RooWebScaffold(path = "datafields", formBackingObject = DataField.class)
public class DataFieldController {
}

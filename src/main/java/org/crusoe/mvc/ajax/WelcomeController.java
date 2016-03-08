package org.crusoe.mvc.ajax;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/pfw")
public class WelcomeController {
	@RequestMapping(value = "default", method = RequestMethod.GET)
	public String getIndexForm(Model model) {


		return "default";
	}
}

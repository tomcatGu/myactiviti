package org.crusoe.mvc.ajax.cms;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cms")
public class CmsController {
	@RequestMapping(value = "index")
	public String index(ServletRequest request) {

		return "cms/index";
	}
}

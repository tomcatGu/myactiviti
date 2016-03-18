package org.crusoe.mvc.ajax.cmsManage;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cmsManage")
public class CmsManageController {
	@RequestMapping(value = "index")
	public String index(ServletRequest request) {

		return "cmsManage/index";
	}

	@RequestMapping(value = "channel/index")
	public String channelIndex(ServletRequest request) {

		return "cmsManage/channel/index";
	}
}

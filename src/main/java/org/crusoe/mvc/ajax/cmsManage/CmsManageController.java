package org.crusoe.mvc.ajax.cmsManage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;

import org.crusoe.dto.cms.ChannelDTO;
import org.crusoe.entity.cms.Channel;
import org.crusoe.service.cms.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cmsManage")
public class CmsManageController {

	@Autowired
	ChannelService channelService;

	@RequestMapping(value = "index")
	public String index(ServletRequest request) {

		return "cmsManage/index";
	}

	@RequestMapping(value = "channel/index")
	public String channelIndex(ServletRequest request) {

		return "cmsManage/channel/index";
	}

	@RequestMapping(value = "channel/data/{id}")
	public @ResponseBody ChannelDTO channelById(@PathVariable(value = "id") Long id) {

		List<Channel> crl = channelService.findById(id);
		Iterator iter = crl.iterator();
		while (iter.hasNext()) {
			Channel c = (Channel) iter.next();
			ChannelDTO cDTO = new ChannelDTO();
			cDTO.setId(c.getId());
			cDTO.setName(c.getTitle());

		}
		/// for test
		ChannelDTO cDTO = new ChannelDTO();
		cDTO.setId(-1L);
		cDTO.setName("根栏目");



		return cDTO;

	}

	@RequestMapping(value = "channel/data/{id}", method = RequestMethod.PUT)
	public @ResponseBody ChannelDTO add(@RequestBody ChannelDTO c) {
		//channelService.add(c);
		return c;
	}

}

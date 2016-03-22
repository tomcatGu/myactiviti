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
import org.springframework.web.bind.annotation.RequestMapping;
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

	@RequestMapping(value = "channel/data/root")
	public @ResponseBody List<ChannelDTO> channelRoot() {
		List<ChannelDTO> channelDTORootList = new ArrayList();
		List<Channel> crl=channelService.findRoot();
		Iterator iter=crl.iterator();
		while(iter.hasNext()){
			Channel c=(Channel) iter.next();
			ChannelDTO cDTO=new ChannelDTO();
			cDTO.setId(c.getId());
			cDTO.setName(c.getTitle());
			channelDTORootList.add(cDTO);
			
			
		}
		
		return channelDTORootList;

	}
}

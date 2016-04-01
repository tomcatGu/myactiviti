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

	@RequestMapping(value = "channel/data/{id}", method = RequestMethod.GET)
	public @ResponseBody ChannelDTO channelById(@PathVariable(value = "id") Long id) {

		Channel c = channelService.findById(id);

		ChannelDTO cDTO = new ChannelDTO();
		cDTO.setId(c.getId());
		cDTO.setName(c.getTitle());

		return cDTO;

	}

	@RequestMapping(value = "channel/data/", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody ChannelDTO add(@RequestBody ChannelDTO c) {
		Channel channel = new Channel();
		channel.setTitle(c.getName());
		channel.setState(c.getState());
		channel.setSequenceIndex(c.getSequenceIndex());
		Channel parent = (Channel) channelService.findById(c.getParent().getId());
		channel.setParent(parent);
		Channel savedChannel = channelService.save(channel);

		c.setId(savedChannel.getId());
		return c;
	}

	@RequestMapping(value = "channel/data/{id}", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody ChannelDTO update(@PathVariable(value = "id") Long id, @RequestBody ChannelDTO c) {
		Channel channel = channelService.findById(id);
		//channel.setId(c.getId());
		channel.setTitle(c.getName());
		channel.setSequenceIndex(c.getSequenceIndex());
		channel.setParent(channelService.findById(c.getParent().getId()));
		
		
		Channel savedChannel = channelService.save(channel);

		c.setId(savedChannel.getId());
		return c;
	}

}

package org.crusoe.service.cms;

import java.util.List;

import org.crusoe.entity.cms.Channel;
import org.crusoe.repository.jpa.cms.ChannelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {
	@Autowired
	ChannelDao channelDao;

	public List<Channel> findRoot() {
		// TODO Auto-generated method stub
		return channelDao.findById(-1L);
	}

}

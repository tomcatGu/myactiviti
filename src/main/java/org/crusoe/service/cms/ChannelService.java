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

	public Channel findRoot() {
		// TODO Auto-generated method stub
		return channelDao.findById(-1L);
	}

	public Channel findById(Long id) {
		// TODO Auto-generated method stub
		return channelDao.findById(id);
	}

	public Channel save(Channel channel) {
		// TODO Auto-generated method stub
		return channelDao.save(channel);
	}

	public Channel findByParentId(Long id) {
		// TODO Auto-generated method stub
		return channelDao.findByParent(id);
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

		channelDao.delete(id);

	}

}

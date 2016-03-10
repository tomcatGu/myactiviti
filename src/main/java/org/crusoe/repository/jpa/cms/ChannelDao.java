package org.crusoe.repository.jpa.cms;

import org.crusoe.entity.cms.Channel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChannelDao extends PagingAndSortingRepository<Channel, Long>, JpaSpecificationExecutor<Channel> {

}

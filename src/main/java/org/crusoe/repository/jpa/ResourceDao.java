package org.crusoe.repository.jpa;

import org.crusoe.entity.Resource;
import org.crusoe.entity.Role;
import org.crusoe.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ResourceDao extends PagingAndSortingRepository<Resource, Long>, JpaSpecificationExecutor<Resource>  {

	

}

package org.crusoe.repository.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.crusoe.entity.Organization;
import org.crusoe.entity.User;

public interface OrganizationDao extends PagingAndSortingRepository<Organization, Long>, JpaSpecificationExecutor<User> {

	Organization findByName(String name);

	
}

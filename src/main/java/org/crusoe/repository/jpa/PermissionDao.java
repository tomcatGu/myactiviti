package org.crusoe.repository.jpa;

import org.crusoe.entity.Permission;

import org.crusoe.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PermissionDao extends
		PagingAndSortingRepository<Permission, Long>,
		JpaSpecificationExecutor<Permission> {

}

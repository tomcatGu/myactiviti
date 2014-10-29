package org.crusoe.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.crusoe.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long>,
		JpaSpecificationExecutor<User> {

	User findByName(String name);

	User findByLoginName(String loginName);

	@Query("select u from User u where u.organization.id=?1")
	Page<User> findByOrganization(Long organizationId, Pageable pageable);
}

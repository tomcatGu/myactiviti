package org.crusoe.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.crusoe.entity.Organization;
import org.crusoe.entity.User;

public interface OrganizationDao extends
		PagingAndSortingRepository<Organization, Long>,
		JpaSpecificationExecutor<User> {

	Organization findByName(String name);

	@Query("select o from Organization o where o.parent.id=?1")
	List<Organization> findChildrenByParent(Long id);

	@Query("select o from Organization o where o.parent is null")
	List<Organization> findRoot();

}

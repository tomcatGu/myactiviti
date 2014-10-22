package org.crusoe.service;

import java.util.List;

import org.crusoe.entity.Organization;
import org.crusoe.repository.jpa.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("organizationService")
@Transactional
public class OrganizationService {
	@Autowired
	private OrganizationDao organizationDao;

	public List<Organization> findChildrenByParent(Long id) {
		return organizationDao.findChildrenByParent(id);

	}

	public List<Organization> findRoot() {
		return organizationDao.findRoot();

	}
}

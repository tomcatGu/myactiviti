package org.crusoe.service;

import org.crusoe.repository.jpa.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("organizationService")
@Transactional
public class OrganizationService {
	@Autowired
	private OrganizationDao organizationDao;
}

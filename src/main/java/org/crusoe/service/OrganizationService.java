package org.crusoe.service;

import java.util.Iterator;
import java.util.List;

import org.crusoe.entity.Organization;
import org.crusoe.entity.User;
import org.crusoe.repository.jpa.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	public Organization create(Organization o) {
		// TODO Auto-generated method stub
		return organizationDao.save(o);
	}

	public Organization findById(Long id) {
		// TODO Auto-generated method stub
		return organizationDao.findOne(id);
	}

	public Organization update(Organization o) {
		// TODO Auto-generated method stub
		return organizationDao.save(o);
	}

	public void deleteById(Long i) {
		// TODO Auto-generated method stub
		deleteAllChildren(i);
	}

	private void deleteAllChildren(Long parentId) {

		List<Organization> children = organizationDao
				.findChildrenByParent(parentId);
		Iterator<Organization> iter = children.iterator();
		while (iter.hasNext()) {
			Organization o = iter.next();
			deleteAllChildren(o.getId());
		}
		organizationDao.delete(parentId);

	}

	public Organization findbyName(String organizationName) {
		// TODO Auto-generated method stub
		// Organization o=new Organization();
		return organizationDao.findByName(organizationName);
	}
}

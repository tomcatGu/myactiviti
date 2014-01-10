package org.crusoe.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.crusoe.dto.RoleDTO;
import org.crusoe.entity.Resource;

import org.crusoe.repository.jpa.ResourceDao;
import org.crusoe.repository.jpa.RoleDao;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("resourceService")
@Transactional
public class ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private RoleDao roleDao;

	@Transactional
	public void addResource(Resource resource) {

		resourceDao.save(resource);

	}

	@Transactional
	public Page<Resource> getResources(Pageable page) {

		return resourceDao.findAll(page);
		// return resources;
	}

	@Transactional
	public Resource load(long id) {
		// TODO Auto-generated method stub
		return resourceDao.findOne(id);
	}

	@Transactional
	public void remove(long id) {
		// TODO Auto-generated method stub

		resourceDao.delete(Long.valueOf(id));
	}

	@Transactional
	public void update(Resource resource) {
		// TODO Auto-generated method stub
		resourceDao.save(resource);
	}
}

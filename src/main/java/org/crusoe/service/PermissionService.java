package org.crusoe.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.crusoe.dto.RoleDTO;
import org.crusoe.entity.Permission;
import org.crusoe.entity.Resource;

import org.crusoe.repository.jpa.PermissionDao;
import org.crusoe.repository.jpa.ResourceDao;
import org.crusoe.repository.jpa.RoleDao;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("permissionService")
@Transactional
public class PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	@Transactional
	public void addPermission(Permission permission) {

		permissionDao.save(permission);

	}

	@Transactional
	public Page<Permission> getPermissions(Pageable page) {

		return permissionDao.findAll(page);
		// return resources;
	}

	@Transactional
	public Permission load(long id) {
		// TODO Auto-generated method stub
		return permissionDao.findOne(id);
	}

	@Transactional
	public void remove(long id) {
		// TODO Auto-generated method stub

		permissionDao.delete(Long.valueOf(id));
	}

	@Transactional
	public void update(Permission permission) {
		// TODO Auto-generated method stub
		permissionDao.save(permission);
	}
}

package org.crusoe.myauth.service;

import java.util.List;

import javax.annotation.Resource;

import org.crusoe.myauth.dao.IBaseDao;
import org.crusoe.myauth.model.ResourceInfo;
import org.crusoe.myauth.model.RoleInfo;
import org.crusoe.myauth.util.Page;
import org.crusoe.myauth.util.PageRequestParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
@Transactional
public class RoleService {
	@Resource(name = "resourceDao")
	private IBaseDao<ResourceInfo, Long> resourceDao;

	@Resource(name = "roleDao")
	private IBaseDao<RoleInfo, Long> roleDao;

	@Transactional
	public RoleInfo load(long roleId) {
		// TODO Auto-generated method stub
		RoleInfo roleInfo = roleDao.load(Long.valueOf(roleId));

		return roleInfo;
	}

	@Transactional
	public Page<RoleInfo> getRoles(PageRequestParam page) {
		Page<RoleInfo> roles = null;

		return roles;

	}

	@Transactional
	public List<RoleInfo> getAllRoles() {
		return roleDao.list();

	}

	public RoleInfo get(Long id) {
		// TODO Auto-generated method stub
		return roleDao.get(id);
	}

	public void update(RoleInfo roleInfo) {
		// TODO Auto-generated method stub
		roleDao.update(roleInfo);
	}
}

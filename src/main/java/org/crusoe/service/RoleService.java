package org.crusoe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.crusoe.entity.Role;
import org.crusoe.entity.User;
import org.crusoe.repository.jpa.ResourceDao;
import org.crusoe.repository.jpa.RoleDao;
import org.crusoe.util.persisterce.DynamicSpecifications;
import org.crusoe.util.persisterce.SearchFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
@Transactional
public class RoleService {
	@Resource(name = "resourceDao")
	private ResourceDao resourceDao;

	@Resource(name = "roleDao")
	private RoleDao roleDao;

	@Transactional
	public Role load(long roleId) {
		// TODO Auto-generated method stub
		Role role = roleDao.findOne(Long.valueOf(roleId));

		return role;
	}

	@Transactional
	public Page<Role> getRoles(Pageable pageable) {

		return (Page<Role>) roleDao.findAll(pageable);

	}

	@Transactional
	public List<Role> getAllRoles() {
		return (List<Role>) roleDao.findAll();

	}

	public Role get(Long id) {
		// TODO Auto-generated method stub
		return roleDao.findOne(id);
	}

	public void update(Role role) {
		// TODO Auto-generated method stub
		roleDao.save(role);
	}

	public void save(Role role) {
		// TODO Auto-generated method stub
		roleDao.save(role);
	}

	public Page<Role> searchRole(
			HashMap<String, Object> paramMap, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		Map<String, SearchFilter> filters = SearchFilter.parse3(paramMap);
		 Specification<Role> spec =
		 DynamicSpecifications.bySearchFilter2(filters.values(), Role.class);

		return roleDao.findAll(spec,pageRequest);
	}
}

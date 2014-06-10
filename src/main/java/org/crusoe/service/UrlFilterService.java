package org.crusoe.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.crusoe.dto.PermissionDTO;
import org.crusoe.dto.RoleDTO;
import org.crusoe.entity.Permission;
import org.crusoe.entity.Role;
import org.crusoe.service.security.ShiroFilerChainManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class UrlFilterService {
	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;
	@Autowired
	private PermissionService permissionService;

	public void init() {
		shiroFilerChainManager.initFilterChains(findAll());
	}

	@Transactional(readOnly = false)
	private List<PermissionDTO> findAll() {
		List<PermissionDTO> perms = Lists.newArrayList();
		Iterable<Permission> allPerms = permissionService.findAll();
		for (Permission perm : allPerms) {
			PermissionDTO permissionDTO = new PermissionDTO();
			permissionDTO.setId(perm.getId());
			permissionDTO.setDescription(perm.getDescription());
			permissionDTO.setUrl(perm.getUrl());
			permissionDTO.setToken(perm.getToken());
			permissionDTO.setRoles(new ArrayList<RoleDTO>());
			for (Role role : perm.getRoles()) {
				RoleDTO roleDTO = new RoleDTO();
				roleDTO.setId(role.getId());
				roleDTO.setName(role.getName());
				permissionDTO.getRoles().add(roleDTO);
			}
			perms.add(permissionDTO);

		}

		return perms;

	}
}

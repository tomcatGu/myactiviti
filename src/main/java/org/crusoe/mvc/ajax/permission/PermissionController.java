package org.crusoe.mvc.ajax.permission;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.crusoe.dto.PermissionDTO;
import org.crusoe.dto.ResourceDTO;
import org.crusoe.dto.RoleDTO;
import org.crusoe.dto.UserDTO;
import org.crusoe.entity.Permission;
import org.crusoe.entity.Resource;
import org.crusoe.entity.Role;
import org.crusoe.service.PermissionService;
import org.crusoe.service.ResourceService;
import org.crusoe.service.RoleService;
import org.crusoe.service.security.ShiroFilerChainManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/permission")
public class PermissionController {

	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;
	@Autowired
	private PermissionService permissionService;

	private RoleService roleService;

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@PostConstruct
	public void initFilterChain() {
		shiroFilerChainManager.initFilterChains(findAll());
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		PermissionDTO permissionDTO = new PermissionDTO();
		List<Role> roles = roleService.getAllRoles();
		Iterator iter = roles.iterator();
		while (iter.hasNext()) {
			RoleDTO roleDTO = new RoleDTO();
			BeanUtils.copyProperties((Role) iter.next(), roleDTO);
			permissionDTO.getRoles().add(roleDTO);

		}
		model.addAttribute("permission", permissionDTO);
		return "permission/createForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	@Transactional
	public @ResponseBody
	Map<String, ? extends Object> create(
			@Valid @RequestBody PermissionDTO newPermission,
			RedirectAttributes redirectAttributes) {

		Permission permission = new Permission();

		BeanUtils.copyProperties(newPermission, permission);
		permission.setRoles(new ArrayList<Role>());
		try {
			permissionService.save(permission);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (newPermission.getRoles() != null) {
			Iterator iter = newPermission.getRoles().iterator();
			while (iter.hasNext()) {
				RoleDTO roleDTO = (RoleDTO) iter.next();
				Role role = roleService.load(roleDTO.getId());
				role.getPermissions().add(permission);
				roleService.update(role);
				permission.getRoles().add(role);
			}
			permissionService.save(permission);
		}
		shiroFilerChainManager.initFilterChains(findAll());
		redirectAttributes.addFlashAttribute("message", "创建资源成功");
		return Collections.singletonMap("id", permission.getId());
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String getEditForm(@PathVariable long id, Model model) {

		return "permission/editForm";
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String getIndexForm(Model model) {
		PermissionDTO permission = new PermissionDTO();

		model.addAttribute(permission);

		return "permission/index";
	}

	@RequestMapping(value = "listPermissions", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> listPermissions(@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size)
			throws IllegalAccessException, InvocationTargetException {
		Sort sortRequest = "desc".equals(order.toLowerCase()) ? new Sort(
				Direction.DESC, new String[] { sort }) : new Sort(
				Direction.ASC, new String[] { sort });
		PageRequest pageRequest = new PageRequest(start / size, size,
				sortRequest);

		Page<Permission> permissions = permissionService
				.getPermissions(pageRequest);
		List<PermissionDTO> permissionDTOList = new ArrayList<PermissionDTO>();
		Iterator iter = permissions.iterator();
		while (iter.hasNext()) {
			Permission permission = (Permission) iter.next();
			PermissionDTO permissionDTO = new PermissionDTO();
			BeanUtils.copyProperties(permission, permissionDTO);
			permissionDTO.setRoles(new ArrayList<RoleDTO>());
			for (Role role : permission.getRoles()) {
				RoleDTO roleDTO = new RoleDTO();
				roleDTO.setId(role.getId());
				roleDTO.setName(role.getName());
				permissionDTO.getRoles().add(roleDTO);
			}
			permissionDTOList.add(permissionDTO);
		}

		Map<String, Object> rets = new ConcurrentHashMap<String, Object>();
		rets.put("count", permissions.getTotalElements());
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", permissionDTOList);
		return rets;
	}

	// add a Resource when RequestMethod.POST
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> create(
			@RequestBody PermissionDTO permisstion, HttpServletResponse response) {

		return Collections.singletonMap("id", 1);
	}

	// get a Resource when RequestMethod.get
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	PermissionDTO get(@PathVariable long id) {
		// System.out.println("delete:id=" + id);
		Permission permission = permissionService.load(id);
		PermissionDTO permissionDTO = new PermissionDTO();

		return permissionDTO;

	}

	// delete a Resource when RequestMethod.DELETE
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {

	}

	// update a Resource when RequestMethod.PUT
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	Map<String, ? extends Object> update(
			@RequestBody PermissionDTO permissionDTO) {
		Permission permission = new Permission();

		BeanUtils.copyProperties(permissionDTO, permission);

		try {
			permissionService.save(permission);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Collections.singletonMap("id", permission.getId());
	}

	@Transactional
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

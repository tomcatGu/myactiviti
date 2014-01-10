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

import javax.servlet.http.HttpServletResponse;

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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/resource")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;

	private RoleService roleService;

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		PermissionDTO permission = new PermissionDTO();

		model.addAttribute(permission);
		return "permission/createForm";
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

	@RequestMapping(value = "listPermissions", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> listPermissions(@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size)
			throws IllegalAccessException, InvocationTargetException {

		PageRequest pageRequest = new PageRequest(start / size, size, new Sort(
				order));
		Page<Permission> permissions = permissionService
				.getPermissions(pageRequest);
		List<PermissionDTO> permissionDTOList = new ArrayList<PermissionDTO>();
		Iterator iter = permissions.iterator();
		while (iter.hasNext()) {
			Permission permission = (Permission) iter.next();
			PermissionDTO permissionDTO = new PermissionDTO();
			BeanUtils.copyProperties(permission, permissionDTO);
			permissionDTOList.add(permissionDTO);
		}

		Map<String, Object> rets = new ConcurrentHashMap<String, Object>();
		rets.put("count", permissions.getTotalElements());
		rets.put("start", start);
		rets.put("size", size);
		rets.put("tasks", permissionDTOList);
		return rets;
	}

	// add a Resource when RequestMethod.POST
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
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {

	}

	// update a Resource when RequestMethod.PUT
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	Map<String, ? extends Object> update(@RequestBody PermissionDTO permission) {

		return Collections.singletonMap("id", permission.getId());
	}

}

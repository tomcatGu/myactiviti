package org.crusoe.mvc.ajax.resource;

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

import org.crusoe.myauth.model.ResourceInfo;
import org.crusoe.myauth.model.RoleInfo;
import org.crusoe.myauth.service.IAclSecurityUtil;
import org.crusoe.myauth.service.ResourceService;
import org.crusoe.myauth.service.RoleService;
import org.crusoe.myauth.util.Page;
import org.crusoe.myauth.util.PageRequestParam;
import org.crusoe.myauth.vo.Resource;
import org.crusoe.myauth.vo.Role;
import org.crusoe.myauth.vo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/resource")
public class ResourceController {
	private Validator validator;

	private ResourceService resourceService;

	private RoleService roleService;

	private IAclSecurityUtil aclSecurity;

	@Autowired
	public void setAclSecurity(IAclSecurityUtil aclSecurity) {
		this.aclSecurity = aclSecurity;
	}

	@Autowired
	public ResourceController(Validator validator) {
		this.validator = validator;
	}

	@Autowired
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		Resource resource = new Resource();

		List<RoleInfo> roles = roleService.getAllRoles();
		Iterator iter = roles.iterator();
		while (iter.hasNext()) {
			RoleInfo roleInfo = (RoleInfo) iter.next();
			Role role = new Role();
			BeanUtils.copyProperties(roleInfo, role);
			resource.getRoles().add(role);

		}

		model.addAttribute(resource);
		return "resource/createForm";
	}

	@RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
	public String getUpdateForm(@PathVariable long id, Model model) {
		ResourceInfo resourceInfo = resourceService.load(id);
		Resource resource = new Resource();
		BeanUtils.copyProperties(resourceInfo, resource);
		resource.setRoles(new HashSet<Role>());

		Iterator iter = resourceInfo.getRoles().iterator();
		while (iter.hasNext()) {
			RoleInfo roleInfo = (RoleInfo) iter.next();
			Role role = new Role();
			BeanUtils.copyProperties(roleInfo, role);
			resource.getRoles().add(role);

		}

		HashSet<Role> allRoles = new HashSet<Role>();
		List<RoleInfo> roles = roleService.getAllRoles();
		iter = roles.iterator();
		while (iter.hasNext()) {
			RoleInfo roleInfo = (RoleInfo) iter.next();
			Role role = new Role();
			BeanUtils.copyProperties(roleInfo, role);
			// resource.getRoles().add(role);
			allRoles.add(role);

		}
		model.addAttribute("allRoles", allRoles);

		model.addAttribute(resource);

		return "resource/editForm";
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String getIndexForm(Model model) {
		Resource resource = new Resource();
		model.addAttribute(resource);

		return "resource/index";
	}

	@RequestMapping(value = "getResources", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getResources(@RequestParam("_search") String search,
			@RequestParam("nd") String nd, @RequestParam("rows") int rows,
			@RequestParam("page") int page, @RequestParam("sidx") String sidx,
			@RequestParam("sord") String sort, HttpServletResponse response) {

		// System.out.println(order);
		Map<String, Object> rets = new ConcurrentHashMap<String, Object>();
		PageRequestParam p = new PageRequestParam();
		p.setPage(page - 1);
		p.setRows(rows);
		p.setSort(sidx);
		p.setOrder(sort.toUpperCase());
		Page<ResourceInfo> resourceInfos = resourceService.getResources(p);
		List<Resource> resources = new ArrayList<Resource>();
		Iterator iter = resourceInfos.getResult().iterator();
		while (iter.hasNext()) {

			ResourceInfo ri = (ResourceInfo) iter.next();
			Resource r = new Resource();

			BeanUtils.copyProperties(ri, r);
			r.setRoles(null);

			Set<Role> roles = new HashSet<Role>();
			Iterator roleIter = ri.getRoles().iterator();
			while (roleIter.hasNext()) {
				RoleInfo roleInfo = (RoleInfo) roleIter.next();
				Role role = new Role();
				role.setId(roleInfo.getId());
				role.setName(roleInfo.getName());
				roles.add(role);

			}
			r.setRoles(roles);

			resources.add(r);
		}
		rets.put("total", resourceInfos.getTotalCount() / rows + 1);
		rets.put("page", page);
		rets.put("records", resourceInfos.getTotalCount());
		rets.put("rows", resources);

		return rets;
	}

	// add a Resource when RequestMethod.POST
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> create(@RequestBody Resource resource,
			HttpServletResponse response) {
		ResourceInfo resourceInfo = new ResourceInfo();
		BeanUtils.copyProperties(resource, resourceInfo);
		resourceInfo.setRoles(null);
		HashSet<RoleInfo> roles = new HashSet<RoleInfo>();
		resourceService.addResource(resourceInfo);
		if (resource.getRoles() != null && resource.getRoles().size() > 0) { // 根据role.id的值来查找RoleInfo对象
			RoleInfo roleInfo;
			Iterator iter = resource.getRoles().iterator();
			while (iter.hasNext()) {
				Role role = (Role) iter.next();
				roleInfo = roleService.get(Long.valueOf(role.getId()));

				roles.add(roleInfo);
				roleInfo.getResources().add(resourceInfo);
				roleService.update(roleInfo);
			}
		}
		aclSecurity.addPermission(resourceInfo, BasePermission.ADMINISTRATION,
				ResourceInfo.class);
		return Collections.singletonMap("id", resourceInfo.getId());
	}

	// get a Resource when RequestMethod.get
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Resource get(@PathVariable long id) {
		// System.out.println("delete:id=" + id);
		ResourceInfo ri = resourceService.load(id);
		Resource r = new Resource();
		BeanUtils.copyProperties(ri, r);

		Set<Role> roles = new HashSet<Role>();
		Iterator roleIter = ri.getRoles().iterator();
		while (roleIter.hasNext()) {
			RoleInfo roleInfo = (RoleInfo) roleIter.next();
			Role role = new Role();
			role.setId(roleInfo.getId());
			role.setName(roleInfo.getName());
			roles.add(role);

		}
		r.setRoles(roles);

		return r;

	}

	// delete a Resource when RequestMethod.DELETE
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {
		// System.out.println("delete:id=" + id);
		ResourceInfo ri = resourceService.load(id);

		Set<Role> roles = new HashSet<Role>();
		Iterator roleIter = ri.getRoles().iterator();
		while (roleIter.hasNext()) {
			RoleInfo roleInfo = (RoleInfo) roleIter.next();
			roleInfo.getResources().remove(ri);
			roleService.update(roleInfo);
		}

		resourceService.remove(id);
	}

	// update a Resource when RequestMethod.PUT
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	Map<String, ? extends Object> update(@RequestBody Resource resource) {
		// System.out.println("delete:id=" + id);
		// resourceService.remove(id);
		ResourceInfo resourceInfo = resourceService.load(resource.getId());

		if (resourceInfo.getRoles() != null
				&& resourceInfo.getRoles().size() > 0) { 

			Iterator iter = resourceInfo.getRoles().iterator();
			while (iter.hasNext()) {
				RoleInfo roleInfo = (RoleInfo) iter.next();
				roleInfo.getResources().remove(resourceInfo);
				roleService.update(roleInfo);
			}
		}
		BeanUtils.copyProperties(resource, resourceInfo);

		resourceInfo.setRoles(null);
		resourceService.update(resourceInfo);
		HashSet<RoleInfo> roles = new HashSet<RoleInfo>();

		if (resource.getRoles() != null && resource.getRoles().size() > 0) { // 根据role.id的值来查找RoleInfo对象
			RoleInfo roleInfo;
			Iterator iter = resource.getRoles().iterator();
			while (iter.hasNext()) {
				Role role = (Role) iter.next();
				roleInfo = roleService.get(Long.valueOf(role.getId()));

				roles.add(roleInfo);
				roleInfo.getResources().add(resourceInfo);
				roleService.update(roleInfo);
			}
		}
		return Collections.singletonMap("id", resource.getId());
	}

}

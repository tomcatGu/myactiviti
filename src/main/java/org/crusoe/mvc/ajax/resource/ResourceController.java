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

import org.crusoe.dto.ResourceDTO;
import org.crusoe.dto.RoleDTO;
import org.crusoe.dto.UserDTO;
import org.crusoe.entity.Resource;

import org.crusoe.entity.Role;


import org.crusoe.service.ResourceService;
import org.crusoe.service.RoleService;
import org.crusoe.util.Page;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ResourceController {

	private ResourceService resourceService;

	private RoleService roleService;

	@Autowired
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		ResourceDTO resource = new ResourceDTO();

		List<Role> roles = roleService.getAllRoles();
		Iterator iter = roles.iterator();
		while (iter.hasNext()) {


		}

		model.addAttribute(resource);
		return "resource/createForm";
	}

	/*
	 * @ModelAttribute("form") public ResourceForm getAllRoles() { HashSet<Long>
	 * allRoles = new HashSet<Long>(); List<RoleInfo> roles =
	 * roleService.getAllRoles(); Iterator iter = roles.iterator(); while
	 * (iter.hasNext()) { RoleInfo roleInfo = (RoleInfo) iter.next(); Role role
	 * = new Role(); BeanUtils.copyProperties(roleInfo, role); //
	 * resource.getRoles().add(role); allRoles.add(role.getId());
	 * 
	 * } ResourceForm form = new ResourceForm(); form.setRoles(allRoles); return
	 * form; }
	 */

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String getEditForm(@PathVariable long id, Model model) {
		Resource resource = resourceService.load(id);
		ResourceDTO resourceDTO = new ResourceDTO();
		BeanUtils.copyProperties(resource, resourceDTO);
		resourceDTO.setRoles(new HashSet<RoleDTO>());
		/*
		 * Iterator iter = resource.getRoles().iterator(); while
		 * (iter.hasNext()) { RoleInfo roleInfo = (RoleInfo) iter.next();
		 * RoleDTO role = new RoleDTO(); BeanUtils.copyProperties(roleInfo,
		 * role); resourceDTO.getRoles().add(role);
		 * 
		 * }
		 * 
		 * HashSet<RoleDTO> allRoles = new HashSet<RoleDTO>(); List<RoleInfo>
		 * roles = roleService.getAllRoles(); iter = roles.iterator(); while
		 * (iter.hasNext()) { RoleInfo roleInfo = (RoleInfo) iter.next();
		 * RoleDTO role = new RoleDTO(); BeanUtils.copyProperties(roleInfo,
		 * role); // resource.getRoles().add(role); allRoles.add(role);
		 * 
		 * }
		 * 
		 * 
		 * model.addAttribute("allRoles", allRoles);
		 * model.addAttribute(resource);
		 */
		return "resource/editForm";
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String getIndexForm(Model model) {
		ResourceDTO resource = new ResourceDTO();
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
		PageRequest pagerequest = new PageRequest(1, 5, Sort.Direction.DESC,
				"id");
		Page<Resource> resourceInfos = (Page<Resource>) resourceService
				.getResources(pagerequest);
		List<ResourceDTO> resources = new ArrayList<ResourceDTO>();
		Iterator iter = resourceInfos.getResult().iterator();
		while (iter.hasNext()) {


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
	Map<String, ? extends Object> create(@RequestBody ResourceDTO resource,
			HttpServletResponse response) {
		
		
		return Collections.singletonMap("id", 1);
	}

	// get a Resource when RequestMethod.get
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	ResourceDTO get(@PathVariable long id) {
		// System.out.println("delete:id=" + id);
		Resource ri = resourceService.load(id);
		ResourceDTO r = new ResourceDTO();
		r.setName("name");
		r.setId(1L);
		
		//BeanUtils.copyProperties(ri, r);

		Set<RoleDTO> roles = new HashSet<RoleDTO>();
		/*
		 * Iterator roleIter = ri.getRoles().iterator(); while
		 * (roleIter.hasNext()) { RoleInfo roleInfo = (RoleInfo)
		 * roleIter.next(); RoleDTO role = new RoleDTO();
		 * role.setId(roleInfo.getId()); role.setName(roleInfo.getName());
		 * roles.add(role);
		 * 
		 * } r.setRoles(roles);
		 */
		return r;

	}

	// delete a Resource when RequestMethod.DELETE
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {
		// System.out.println("delete:id=" + id);
		Resource ri = resourceService.load(id);

		Set<RoleDTO> roles = new HashSet<RoleDTO>();
		/*
		 * Iterator roleIter = ri.getRoles().iterator(); while
		 * (roleIter.hasNext()) { RoleInfo roleInfo = (RoleInfo)
		 * roleIter.next(); roleInfo.getResources().remove(ri);
		 * roleService.update(roleInfo); }
		 */
		resourceService.remove(id);
	}

	// update a Resource when RequestMethod.PUT
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	Map<String, ? extends Object> update(@RequestBody ResourceDTO resource) {
		// System.out.println("delete:id=" + id);
		// resourceService.remove(id);
		Resource resourceInfo = resourceService.load(resource.getId());

		/*
		 * if (resourceInfo.getRoles() != null && resourceInfo.getRoles().size()
		 * > 0) {
		 * 
		 * Iterator iter = resourceInfo.getRoles().iterator(); while
		 * (iter.hasNext()) { RoleInfo roleInfo = (RoleInfo) iter.next();
		 * roleInfo.getResources().remove(resourceInfo);
		 * roleService.update(roleInfo); } } BeanUtils.copyProperties(resource,
		 * resourceInfo);
		 * 
		 * resourceInfo.setRoles(null); resourceService.update(resourceInfo);
		 * HashSet<RoleInfo> roles = new HashSet<RoleInfo>();
		 * 
		 * if (resource.getRoles() != null && resource.getRoles().size() > 0) {
		 * // 根据role.id的值来查找RoleInfo对象 RoleInfo roleInfo; Iterator iter =
		 * resource.getRoles().iterator(); while (iter.hasNext()) { RoleDTO role
		 * = (RoleDTO) iter.next(); roleInfo =
		 * roleService.get(Long.valueOf(role.getId()));
		 * 
		 * roles.add(roleInfo); roleInfo.getResources().add(resourceInfo);
		 * roleService.update(roleInfo); } }
		 */
		return Collections.singletonMap("id", resource.getId());
	}

}

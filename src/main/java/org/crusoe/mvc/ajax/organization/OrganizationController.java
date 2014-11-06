package org.crusoe.mvc.ajax.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.crusoe.dto.OrganizationDTO;
import org.crusoe.entity.Organization;
import org.crusoe.entity.User;
import org.crusoe.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "organization")
public class OrganizationController {
	@Autowired
	private OrganizationService organizationService;

	@RequestMapping(value = "index")
	public String index(ServletRequest request) {

		return "organization/index";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody
	OrganizationDTO create(@RequestBody OrganizationDTO oDTO) {

		Organization o = new Organization();
		o.setName(oDTO.getText());
		if (oDTO.getParent() != null && !oDTO.getParent().equals("#")) {
			Organization parent = organizationService.findById(Long
					.parseLong(oDTO.getParent()));
			o.setParent(parent);
		}
		o = organizationService.create(o);
		oDTO.setId(o.getId());
		return oDTO;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public @ResponseBody
	OrganizationDTO update(@RequestBody OrganizationDTO oDTO) {

		Organization o = new Organization();
		o.setId(oDTO.getId());
		o.setName(oDTO.getText());
		o.setSequence(oDTO.getSequence());
		if (oDTO.getParent() != null && !oDTO.getParent().equals("#")) {
			Organization parent = organizationService.findById(Long
					.parseLong(oDTO.getParent()));
			o.setParent(parent);
		}
		o = organizationService.update(o);
		return oDTO;
	}

	@RequestMapping(value = "{id}")
	public @ResponseBody
	OrganizationDTO getOrganization(@PathVariable("id") Long id) {

		Organization o = organizationService.findById(id);
		OrganizationDTO oDTO = new OrganizationDTO();
		oDTO.setId(o.getId());
		oDTO.setText(o.getName());
		oDTO.setSequence(o.getSequence());
		if (organizationService.findChildrenByParent(o.getId()).isEmpty()) {

			oDTO.setChildren(false);
		} else
			oDTO.setChildren(true);
		return oDTO;

	}

	@RequestMapping(method = RequestMethod.DELETE)
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	public @ResponseBody
	Map<String, ? extends Object> batchDelete(
			@RequestParam(value = "items[]", required = false) Long[] items)
			throws Exception {
		for (int i = 0; i < items.length; i++) {
			organizationService.deleteById(items[i]);

		}
		Map<String, String> msgs = new HashMap<String, String>();

		msgs.put("msg", "删除组织成功");
		return msgs;
	}

	@RequestMapping(value = "ajaxRoots")
	public @ResponseBody
	List<OrganizationDTO> ajaxRoots(@RequestParam("id") String id) {
		List<Organization> organizationRoots = new ArrayList<Organization>();
		List<OrganizationDTO> organizationDTORoots = new ArrayList<OrganizationDTO>();
		if (id.equals("#")) {
			organizationRoots = organizationService.findRoot();
		} else {
			organizationRoots = organizationService.findChildrenByParent(Long
					.parseLong(id));
		}
		Iterator iter = organizationRoots.iterator();
		while (iter.hasNext()) {
			Organization organization = (Organization) iter.next();
			OrganizationDTO organizationDTO = new OrganizationDTO();
			organizationDTO.setId(organization.getId());
			if (organization.getParent() == null) {
				organizationDTO.setParent("#");
			} else {
				organizationDTO.setParent(organization.getParent().getId()
						.toString());
			}
			if (organizationService.findChildrenByParent(organization.getId())
					.isEmpty()) {

				organizationDTO.setChildren(false);
			} else
				organizationDTO.setChildren(true);
			organizationDTO.setText(organization.getName());
			organizationDTORoots.add(organizationDTO);
		}
		return organizationDTORoots;

	}

	@RequestMapping(value = "ajaxChildren")
	public @ResponseBody
	List<OrganizationDTO> ajaxChildren(@RequestParam("id") String id) {
		List<Organization> organizationRoots = new ArrayList<Organization>();
		List<OrganizationDTO> organizationDTORoots = new ArrayList<OrganizationDTO>();

		organizationRoots = organizationService.findChildrenByParent(Long
				.parseLong(id));

		Iterator iter = organizationRoots.iterator();
		while (iter.hasNext()) {
			Organization organization = (Organization) iter.next();
			OrganizationDTO organizationDTO = new OrganizationDTO();
			organizationDTO.setId(organization.getId());

			organizationDTO.setParent(organization.getParent().getId()
					.toString());

			if (organizationService.findChildrenByParent(organization.getId())
					.isEmpty()) {

				organizationDTO.setChildren(false);
			} else
				organizationDTO.setChildren(true);
			organizationDTO.setText(organization.getName());
			organizationDTORoots.add(organizationDTO);
		}
		return organizationDTORoots;

	}
}

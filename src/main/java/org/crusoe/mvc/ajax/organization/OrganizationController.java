package org.crusoe.mvc.ajax.organization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;

import org.crusoe.dto.OrganizationDTO;
import org.crusoe.entity.Organization;
import org.crusoe.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		o = organizationService.create(o);
		oDTO.setId(o.getId());
		return oDTO;
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

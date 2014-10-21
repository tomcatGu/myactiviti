package org.crusoe.mvc.ajax.organization;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.crusoe.dto.OrganizationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "organization")
public class OrganizationController {
	@RequestMapping(value = "index")
	public String index(ServletRequest request) {

		return "organization/index";
	}

	@RequestMapping(value = "ajaxRoots")
	public @ResponseBody
	List<OrganizationDTO> ajaxRoots() {
		List<OrganizationDTO> organizationRoots = new ArrayList<OrganizationDTO>();
		OrganizationDTO organization = new OrganizationDTO();
		organization.setId("1");
		organization.setText("Root 1");
		organization.setParent("#");
		
		organization.setId("2");
		organization.setText("Child 1");
		organization.setParent("1");
		organizationRoots.add(organization);

		return organizationRoots;

	}

	@RequestMapping(value = "ajaxChildren")
	public @ResponseBody
	List<OrganizationDTO> ajaxChildren() {
		List<OrganizationDTO> organizationChildren = new ArrayList<OrganizationDTO>();

		OrganizationDTO organization = new OrganizationDTO();
		organization.setId("2");
		organization.setText("Child 1");
		organization.setParent("1");
		return organizationChildren;

	}
}

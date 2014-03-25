package org.crusoe.mvc.ajax.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.crusoe.dto.repository.DeploymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/runtime/repository")
public class RepositoryController {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private FormService formService;

	@RequestMapping(value = "listRepositories", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> getDeployments(@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			Model model) {

		List<Object> objects = new ArrayList<Object>();
		List<Deployment> deployments = repositoryService
				.createDeploymentQuery().listPage(start, size);

		for (Deployment deployment : deployments) {
			DeploymentDTO deploymentDTO = new DeploymentDTO();
			deploymentDTO.setId(deployment.getId());
			deploymentDTO.setName(deployment.getName());
			deploymentDTO.setDeploymentTime(deployment.getDeploymentTime());
			objects.add(deploymentDTO);

		}
		long count = repositoryService.createDeploymentQuery().count();
		HashMap<String, Object> rets = new HashMap<String, Object>();
		rets.put("count", count);
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", objects);
		return rets;
	}

	@RequestMapping(value = "index")
	public String index(Model model) {

		return "/repository/index";
	}

}

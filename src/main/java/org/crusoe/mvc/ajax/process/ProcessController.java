package org.crusoe.mvc.ajax.process;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.crusoe.dto.ProcessDefinitionDTO;
import org.crusoe.dto.HistoriceProcessInstanceDTO;
import org.crusoe.dto.ProcessInstanceDTO;
import org.crusoe.dto.repository.DeploymentDTO;
import org.crusoe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/runtime/process")
public class ProcessController {
	@RequestMapping(value = "index")
	public String index(Model model) {

		return "/process/index";

	}

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	protected AccountService accountService;

	@RequestMapping(value = "listProcessDefinitions", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> getProcessDefinitions(
			@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			Model model) {

		List<Object> objects = new ArrayList<Object>();
		List<ProcessDefinition> processDefinitionList = repositoryService
				.createProcessDefinitionQuery().listPage(start, size);

		for (ProcessDefinition processDefinition : processDefinitionList) {
			ProcessDefinitionDTO pdDTO = new ProcessDefinitionDTO();
			pdDTO.setId(processDefinition.getId());
			pdDTO.setName(processDefinition.getName());
			pdDTO.setKey(processDefinition.getKey());
			pdDTO.setVersion(processDefinition.getVersion());
			pdDTO.setDeploymentId(processDefinition.getDeploymentId());
			pdDTO.setDescription(processDefinition.getDescription());
			pdDTO.setResourceName(processDefinition.getResourceName());
			pdDTO.setDiagramResourceName(processDefinition
					.getDiagramResourceName());
			pdDTO.setCategory(processDefinition.getCategory());
			objects.add(pdDTO);

		}
		long count = repositoryService.createProcessDefinitionQuery().count();
		HashMap<String, Object> rets = new HashMap<String, Object>();
		rets.put("count", count);
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", objects);
		return rets;
	}

	@RequestMapping(value = "{id}/start", method = RequestMethod.GET)
	public String start(@PathVariable String id, Model model) {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessDefinition processDefinition = repositoryService
				.getProcessDefinition(id);

		// runtimeService.addUserIdentityLink(
		// processInstance.getProcessInstanceId(), "admin", "candidate");
		String startFormKey = formService.getStartFormKey(processDefinition
				.getId());

		if (startFormKey != null) {
			model.addAttribute("processDefinitionId", processDefinition.getId());
			return startFormKey;
		} else {
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser != null)
				identityService.setAuthenticatedUserId(currentUser
						.getPrincipal().toString());
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceById(id);
			return "redirect:/runtime/tasks/index";

		}
	}

	@RequestMapping(value = "finishedProcessIndex/{processDefinitionId}", method = RequestMethod.GET)
	public String finishedProcessIndex(
			@PathVariable String processDefinitionId, Model model) {
		model.addAttribute("processDefinitionId", processDefinitionId);
		return "process/finishedInstanceIndex";
	}

	@RequestMapping(value = "listFinishedProcessInstances/{processDefinitionId}", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> listFinishedProcessInstances(
			@PathVariable String processDefinitionId,
			@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			Model model) throws IllegalAccessException,
			InvocationTargetException {

		List<Object> objects = new ArrayList<Object>();
		List<HistoricProcessInstance> finishedProcessInstances = historyService
				.createHistoricProcessInstanceQuery()
				.processDefinitionId(processDefinitionId).finished()
				.listPage(start, size);

		List<HistoricActivityInstance> hainstances = historyService
				.createHistoricActivityInstanceQuery()
				.processDefinitionId(processDefinitionId).finished().list();
		for (HistoricActivityInstance hai : hainstances) {

			String activityName = hai.getActivityName();

		}
		for (HistoricProcessInstance historicProcessInstance : finishedProcessInstances) {
			HistoriceProcessInstanceDTO piDTO = new HistoriceProcessInstanceDTO();
			BeanUtils.copyProperties(piDTO, historicProcessInstance);

			objects.add(piDTO);

		}
		long count = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionId(processDefinitionId).finished().count();
		HashMap<String, Object> rets = new HashMap<String, Object>();
		rets.put("count", count);
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", objects);
		return rets;
	}

	@RequestMapping(value = "activeProcessIndex/{processDefinitionId}", method = RequestMethod.GET)
	public String activeProcessIndex(@PathVariable String processDefinitionId,
			Model model) {
		model.addAttribute("processDefinitionId", processDefinitionId);
		return "process/activeInstanceIndex";
	}

	@RequestMapping(value = "listActiveProcessInstances/{processDefinitionId}", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> listActiveProcessInstances(
			@PathVariable String processDefinitionId,
			@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			Model model) throws IllegalAccessException,
			InvocationTargetException {

		List<Object> objects = new ArrayList<Object>();
		List<ProcessInstance> activeProcessInstances = runtimeService
				.createProcessInstanceQuery()
				.processDefinitionId(processDefinitionId).active()
				.listPage(start, size);

		for (ProcessInstance processInstance : activeProcessInstances) {
			ProcessInstanceDTO piDTO = new ProcessInstanceDTO();
			BeanUtils.copyProperties(piDTO, processInstance);
			// processInstance

			objects.add(piDTO);

		}
		long count = runtimeService.createProcessInstanceQuery()
				.processDefinitionId(processDefinitionId).active().count();
		HashMap<String, Object> rets = new HashMap<String, Object>();
		rets.put("count", count);
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", objects);
		return rets;
	}

}
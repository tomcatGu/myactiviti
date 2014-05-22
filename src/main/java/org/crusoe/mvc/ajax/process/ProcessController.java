package org.crusoe.mvc.ajax.process;

import java.io.InputStream;
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
import org.apache.poi.util.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.crusoe.command.GenFlowImageCmd;
import org.crusoe.dto.ProcessDefinitionDTO;
import org.crusoe.dto.HistoriceProcessInstanceDTO;
import org.crusoe.dto.ProcessInstanceDTO;
import org.crusoe.dto.repository.DeploymentDTO;
import org.crusoe.service.AccountService;
import org.crusoe.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/runtime/process")
public class ProcessController extends BaseServiceImpl {
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

	/**
	 * 显示流程图
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "processDiagram/{processDefinitionId}")
	public ResponseEntity<byte[]> getProcessDiagram(
			@PathVariable String processDefinitionId) throws Exception {

		ProcessDefinition procDef = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		String diagramResourceName = procDef.getDiagramResourceName();

		InputStream imageStream = this.commandExecutor
				.execute(new GenFlowImageCmd(repositoryService,
						processDefinitionId));
		// InputStream imageStream = repositoryService.getResourceAsStream(
		// procDef.getDeploymentId(), diagramResourceName);

		byte[] bb = IOUtils.toByteArray(imageStream);
		HttpHeaders headers = new HttpHeaders();

		headers.setContentLength(bb.length);

		headers.setCacheControl("no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
		headers.setPragma("no-cache");
		headers.setContentType(MediaType.IMAGE_PNG);

		return new ResponseEntity<byte[]>(bb, headers, HttpStatus.OK);
	}

	/**
	 * 获取跟踪信息
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String getProcessMap() throws Exception { String procDefId =
	 * getRequest().getParameter("procDefId"); String executionId =
	 * getRequest().getParameter("executionId"); ProcessDefinition
	 * processDefinition = repositoryService
	 * .createProcessDefinitionQuery().processDefinitionId(procDefId)
	 * .singleResult();
	 * 
	 * ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
	 * String processDefinitionId = pdImpl.getId();// 流程标识
	 * 
	 * ProcessDefinitionEntity def = (ProcessDefinitionEntity)
	 * ((RepositoryServiceImpl) repositoryService)
	 * .getDeployedProcessDefinition(processDefinitionId); ActivityImpl actImpl
	 * = null;
	 * 
	 * ExecutionEntity execution = (ExecutionEntity) runtimeService
	 * .createExecutionQuery().executionId(executionId).singleResult();// 执行实例
	 * 
	 * String activitiId = execution.getActivityId();// 当前实例的执行到哪个节点 //
	 * List<String>activitiIds = //
	 * runtimeService.getActiveActivityIds(executionId);
	 * 
	 * List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点 //
	 * for(String activitiId : activitiIds){ for (ActivityImpl activityImpl :
	 * activitiList) { String id = activityImpl.getId(); if
	 * (id.equals(activitiId)) {// 获得执行到那个节点 actImpl = activityImpl; break; } }
	 * // }
	 * 
	 * getRequest().setAttribute("coordinateObj", actImpl);
	 * getRequest().setAttribute("procDefId", procDefId); return SUCCESS; }
	 */

}
package org.crusoe.mvc.ajax.process;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.util.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.crusoe.dto.HistoricProcessInstanceDTO;
import org.crusoe.dto.ProcessDefinitionDTO;
import org.crusoe.dto.ProcessInstanceDTO;
import org.crusoe.dto.repository.ActivityDTO;
import org.crusoe.dto.task.TaskDTO;
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

import com.google.common.collect.Lists;

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
	private TaskService taskService;

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

	@RequiresUser
	@RequestMapping(value = "{id}/start", method = RequestMethod.GET)
	public String start(@PathVariable String id, Model model) {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessDefinition processDefinition = repositoryService
				.getProcessDefinition(id);

		// runtimeService.addUserIdentityLink(
		// processInstance.getProcessInstanceId(), "admin", "candidate");

		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.getPrincipal() != null)
			identityService.setAuthenticatedUserId(currentUser.getPrincipal()
					.toString());
		String startFormKey = formService.getStartFormKey(processDefinition
				.getId());

		if (startFormKey != null) {
			model.addAttribute("processDefinitionId", processDefinition.getId());
			// model.addAttribute("historicView", false);
			return startFormKey;
		} else {

			ProcessInstance processInstance = runtimeService
					.startProcessInstanceById(id);
			return "redirect:/runtime/tasks/index";

		}
	}

	@RequiresUser
	@RequestMapping(value = "{id}/startAndRedirectToTaskForm", method = RequestMethod.GET)
	public String startAndRedirectToTaskForm(@PathVariable String id,
			Model model) {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessDefinition processDefinition = repositoryService
				.getProcessDefinition(id);

		// runtimeService.addUserIdentityLink(
		// processInstance.getProcessInstanceId(), "admin", "candidate");

		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.getPrincipal() != null)
			identityService.setAuthenticatedUserId(currentUser.getPrincipal()
					.toString());

		ProcessInstance processInstance = runtimeService
				.startProcessInstanceById(id);
		runtimeService.updateBusinessKey(processInstance.getId(), "草稿");
		Task task = taskService.createTaskQuery()
				.processInstanceId(processInstance.getId())
				.taskAssignee(currentUser.getPrincipal().toString())
				.singleResult();
		if (task != null) {
			String formKey = formService.getTaskFormData(task.getId())
					.getFormKey();
			model.addAttribute("taskId", task.getId());
			return formKey;

		} else
			return "redirect:/runtime/tasks/index";

		// }
	}

	@RequestMapping(value = "finishedProcessIndex/{processDefinitionId}", method = RequestMethod.GET)
	public String finishedProcessIndex(
			@PathVariable String processDefinitionId, Model model) {
		model.addAttribute("processDefinitionId", processDefinitionId);
		return "process/finishedInstanceIndex";
	}

	@RequestMapping(value = "userProcessIndex/{username}", method = RequestMethod.GET)
	public String userProcessIndex(@PathVariable String username, Model model) {
		model.addAttribute("username", username);
		return "process/userProcessInstanceIndex";
	}

	@RequestMapping(value = "allProcessIndex", method = RequestMethod.GET)
	public String allProcessIndex(Model model) {

		return "process/allProcessInstanceIndex";
	}

	@RequestMapping(value = "listAllProcessInstances", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> listAllProcessInstances(
			@RequestParam("processInstanceName") String processInstanceName,
			@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			Model model) throws IllegalAccessException,
			InvocationTargetException {

		List<Object> objects = new ArrayList<Object>();
		List<HistoricProcessInstance> userAllProcessInstances = historyService
				.createHistoricProcessInstanceQuery()
				.processDefinitionKey(processInstanceName)
				.listPage(start, size);

		List<HistoricActivityInstance> hainstances = historyService
				.createHistoricActivityInstanceQuery()

				.list();
		for (HistoricActivityInstance hai : hainstances) {

			// String activityName = hai.getActivityName();

		}
		for (HistoricProcessInstance historicProcessInstance : userAllProcessInstances) {
			HistoricProcessInstanceDTO piDTO = new HistoricProcessInstanceDTO();
			piDTO.setBusinessKey(historicProcessInstance.getBusinessKey());
			piDTO.setId(historicProcessInstance.getId());
			piDTO.setProcessDefinitionId(historicProcessInstance
					.getProcessDefinitionId());
			piDTO.setStartTime(historicProcessInstance.getStartTime());
			piDTO.setEndTime(historicProcessInstance.getEndTime());
			piDTO.setStartUserId(historicProcessInstance.getStartUserId());
			piDTO.setUsername(accountService.findUserByLoginName(
					piDTO.getStartUserId()).getName());
			if (piDTO.getEndTime() == null)
				piDTO.setStatus("activited");
			else
				piDTO.setStatus("finished");

			objects.add(piDTO);

		}
		long count = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(processInstanceName).count();
		HashMap<String, Object> rets = new HashMap<String, Object>();
		rets.put("count", count);
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", objects);
		return rets;
	}

	@RequestMapping(value = "listUserProcessInstances/{username}", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> listUserProcessInstances(
			@PathVariable String username, @RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			Model model) throws IllegalAccessException,
			InvocationTargetException {

		List<Object> objects = new ArrayList<Object>();
		List<HistoricProcessInstance> userAllProcessInstances = historyService
				.createHistoricProcessInstanceQuery().startedBy(username)
				.orderByProcessInstanceStartTime().desc().listPage(start, size);

		List<HistoricActivityInstance> hainstances = historyService
				.createHistoricActivityInstanceQuery()

				.list();

		for (HistoricProcessInstance historicProcessInstance : userAllProcessInstances) {
			//taskService.createTaskQuery().processInstanceBusinessKey(historicProcessInstance.getBusinessKey()).processInstanceId(historic)
			HistoricProcessInstanceDTO piDTO = new HistoricProcessInstanceDTO();
			piDTO.setBusinessKey(historicProcessInstance.getBusinessKey());
			piDTO.setId(historicProcessInstance.getId());
			piDTO.setProcessDefinitionId(historicProcessInstance
					.getProcessDefinitionId());
			piDTO.setStartTime(historicProcessInstance.getStartTime());
			piDTO.setEndTime(historicProcessInstance.getEndTime());
			piDTO.setStartUserId(historicProcessInstance.getStartUserId());
			piDTO.setUsername(accountService.findUserByLoginName(
					piDTO.getStartUserId()).getName());
			if (piDTO.getEndTime() == null)
				piDTO.setStatus("activited");
			else
				piDTO.setStatus("finished");

			objects.add(piDTO);

		}
		long count = historyService.createHistoricProcessInstanceQuery()
				.startedBy(username).count();
		HashMap<String, Object> rets = new HashMap<String, Object>();
		rets.put("count", count);
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", objects);
		return rets;
	}

	@RequestMapping(value = "processInstanceDetail/{processInstanceId}", method = RequestMethod.GET)
	public String processInstanceDetail(@PathVariable String processInstanceId,
			Model model) {
		List<HistoricTaskInstance> historicTaskInstances = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).list();
		List<TaskDTO> todoList = new ArrayList<TaskDTO>();
		HistoricProcessInstance hpi = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();

		HistoricProcessInstanceDTO hpiDTO = new HistoricProcessInstanceDTO();
		hpiDTO.setId(hpi.getId());
		hpiDTO.setBusinessKey(hpi.getBusinessKey());
		hpiDTO.setProcessDefinitionId(hpi.getProcessDefinitionId());
		hpiDTO.setStartTime(hpi.getStartTime());
		hpiDTO.setEndTime(hpi.getEndTime());
		hpiDTO.setStartUserId(hpi.getStartUserId());
		hpiDTO.setUsername(accountService.findUserByLoginName(
				hpiDTO.getStartUserId()).getName());

		String formKey = formService.getStartFormKey(hpi
				.getProcessDefinitionId());
		if (formKey != null) {
			model.addAttribute("hasStartForm", true);
		} else {

			model.addAttribute("hasStartForm", false);
		}

		model.addAttribute("processInstanceStart", hpiDTO);

		for (HistoricTaskInstance hti : historicTaskInstances) {
			TaskDTO taskDTO = new TaskDTO();
			taskDTO.setId(hti.getId());
			taskDTO.setProcessDefinitionId(hti.getProcessDefinitionId());
			taskDTO.setTaskDefinitionKey(hti.getTaskDefinitionKey());
			taskDTO.setStartTime(hti.getStartTime());
			taskDTO.setEndTime(hti.getEndTime());
			taskDTO.setName(hti.getName());
			taskDTO.setAssignee(hti.getAssignee());
			taskDTO.setAssignee(accountService.findUserByLoginName(
					hti.getAssignee()).getName());
			todoList.add(taskDTO);
		}
		model.addAttribute("tasks", todoList);
		return "process/processInstanceDetail";
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
			HistoricProcessInstanceDTO piDTO = new HistoricProcessInstanceDTO();
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
		// String diagramResourceName = procDef.getDiagramResourceName();

		// InputStream imageStream = this.commandExecutor
		// .execute(new GenFlowImageCmd(repositoryService,
		// processDefinitionId));
		// InputStream imageStream = repositoryService.getResourceAsStream(
		// procDef.getDeploymentId(), diagramResourceName);

		InputStream imageStream = repositoryService
				.getProcessDiagram(processDefinitionId);

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
	@RequestMapping(value = "activityCoordinate/{processDefinitionId}")
	public @ResponseBody
	HashMap<String, Object> getActivityCoordinate(
			@PathVariable String processDefinitionId) throws Exception {
		HashMap<String, Object> rets = new HashMap<String, Object>();

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList = def.getActivities();
		List<ActivityDTO> coordinates = Lists.newArrayList();
		for (ActivityImpl activityImpl : activitiList) {
			ActivityDTO activityDTO = new ActivityDTO();
			BeanUtils.copyProperties(activityDTO, activityImpl);
			coordinates.add(activityDTO);
		}
		rets.put("coordinates", coordinates);
		return rets;
	}

	@RequestMapping(value = "allProcesses", method = RequestMethod.DELETE)
	public @ResponseBody
	Map<String, ? extends Object> batchDelete(
			@RequestParam(value = "items[]", required = false) String[] items)
			throws Exception {

		for (int i = 0; i < items.length; i++) {
			if (runtimeService.createProcessInstanceQuery()
					.processInstanceId(items[i]).singleResult() != null) {

				runtimeService.deleteProcessInstance(items[i], "");
			}
			historyService.deleteHistoricProcessInstance(items[i]);
			// taskService.deleteTask(items[i]);

		}
		Map<String, String> msgs = new HashMap<String, String>();

		msgs.put("msg", "删除成功");
		return msgs;
	}

}
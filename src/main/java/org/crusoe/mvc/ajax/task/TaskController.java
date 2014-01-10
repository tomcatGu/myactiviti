package org.crusoe.mvc.ajax.task;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.crusoe.dto.ResourceDTO;
import org.crusoe.dto.task.TaskDTO;
import org.crusoe.entity.User;
import org.crusoe.service.AccountService;
import org.crusoe.util.DataTablesUtil;
import org.crusoe.util.JSONUtil;
import org.crusoe.web.datatables.DataTableReturnObject;
import org.crusoe.web.datatables.JSONParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/runtime/tasks")
public class TaskController {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private TaskService taskService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private FormService formService;

	@RequestMapping(value = "todo", method = RequestMethod.GET)
	public String getTodo(Map<String, Object> map) {

		map.put("version", 2);
		return "leave/leaveForm";
	}

	@RequestMapping(value = "index")
	public String getIndexForm() {
		return "task/index";
	}

	@RequestMapping(value = "claim/{taskId}")
	public String claimTask(@PathVariable("taskId") String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		User user = accountService.findUserByLoginName(SecurityUtils
				.getSubject().getPrincipal().toString());
		taskService.claim(task.getId(), user.getLoginName());
		String formKey = formService.getTaskFormData(task.getId()).getFormKey();

		return formKey;
	}

	@RequestMapping(value = "getTasks", method = RequestMethod.POST)
	public @ResponseBody
	DataTableReturnObject getTasks(@RequestBody JSONParam[] params) {

		// convertToMap定义于父类，将参数数组中的所有元素加入一个HashMap
		HashMap<String, Object> paramMap = JSONUtil.convertToMap(params);
		int sEcho = Integer.valueOf(paramMap.get("sEcho").toString());
		PageRequest pageRequest = DataTablesUtil
				.extractPageRequestFromDataTablesParam(paramMap);
		User user = accountService.findUserByLoginName(SecurityUtils
				.getSubject().getPrincipal().toString());

		Object[] todoList = taskService
				.createTaskQuery()
				.taskAssignee(user.getLoginName())
				.listPage(pageRequest.getPageNumber(),
						pageRequest.getPageSize()).toArray();
		long count = taskService.createTaskQuery()
				.taskAssignee(user.getLoginName()).count();

		return new DataTableReturnObject(count, todoList.length, sEcho,
				todoList);

	}

	@RequestMapping(value = "listTasks", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> listTasks(@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size)
			throws IllegalAccessException, InvocationTargetException {

		// convertToMap定义于父类，将参数数组中的所有元素加入一个HashMap

		User user = accountService.findUserByLoginName(SecurityUtils
				.getSubject().getPrincipal().toString());

		List<Task> taskList = taskService.createTaskQuery()
				.taskAssignee(user.getLoginName()).listPage(start, size);
		List<TaskDTO> todoList = new ArrayList();
		int i = 0;
		for (Task task : taskList) {
			TaskDTO taskDTO = new TaskDTO();
			taskDTO.setId(task.getId());
			taskDTO.setName(task.getName());
			taskDTO.setAssignee(task.getAssignee());
			// BeanUtils.copyProperties(task, taskDTO);
			todoList.add(taskDTO);

		}
		long count = taskService.createTaskQuery()
				.taskAssignee(user.getLoginName()).count();
		HashMap<String, Object> rets = new HashMap<String, Object>();
		rets.put("count", count);
		rets.put("start", start);
		rets.put("size", size);
		rets.put("tasks", todoList);

		return rets;

	}
}

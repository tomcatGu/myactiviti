package org.crusoe.mvc.ajax.history;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.shiro.SecurityUtils;
import org.crusoe.dto.task.TaskDTO;
import org.crusoe.entity.User;
import org.crusoe.service.workflow.governmentInformationDisclosure.GovernmentInformationDisclosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "runtime/search")
public class HistoryController {
	@Autowired
	GovernmentInformationDisclosureService gidService;

	@RequestMapping(value = "searchHistoryTasks", method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> listHistoryTasks(
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "DESC") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size)
			throws IllegalAccessException, InvocationTargetException {

		HashMap<String, Object> rets = gidService.search(new String[] {
				"title", "content" }, keyword, start, size);
		return rets;

	}

	@RequestMapping(value = "searchProcessInstance", method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> listProcessInstance(
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "DESC") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size)
			throws IllegalAccessException, InvocationTargetException {

		HashMap<String, Object> rets = gidService.searchProcessInstance(
				new String[] { "title", "content" }, keyword, start, size);
	
		return rets;

	}
}

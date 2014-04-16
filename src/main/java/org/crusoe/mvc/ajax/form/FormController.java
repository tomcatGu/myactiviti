package org.crusoe.mvc.ajax.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crusoe.entity.User;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.crusoe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/runtime/form")
public class FormController {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private FormService formService;
	@Autowired
	private IdentityService identityService;

	@Autowired
	protected AccountService accountService;

	@RequestMapping(value = "submitStartForm/{processDefinitionId}", method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> submitForm(
			@PathVariable String processDefinitionId,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = null;
		Map<String, String[]> map1 = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = map1.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				if ("assigneeList".equals(name)) {
					List<String> assigneeList = new ArrayList<String>();
					for (int i = 0; i < values.length; i++) {

						User user = accountService.getUser(Long
								.parseLong(values[i]));
						assigneeList.add(user.getLoginName());

					}
					returnMap.put(name, assigneeList);
					continue;
				} else {
					for (int i = 0; i < values.length; i++) {

						value = values[i] + ",";
					}
					value = value.substring(0, value.length() - 1);
				}
			} else {
				value = valueObj.toString();

			}
			returnMap.put(name, value);
		}
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null)
			identityService.setAuthenticatedUserId(currentUser.getPrincipal()
					.toString());
		formService.submitStartFormData(processDefinitionId, returnMap);

		HashMap<String, Object> rets = new HashMap<String, Object>();
		rets.put("msg", "OK");
		return rets;
	}
}
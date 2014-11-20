package org.crusoe.mvc.interceptor;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.crusoe.entity.workflow.governmentInformationDisclosure.StatisticalSheet;
import org.crusoe.service.workflow.governmentInformationDisclosure.StatisticalSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatisticlaSheetInterceptor {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	protected StatisticalSheetService statisticalSheetService;

	@Pointcut("execution(* org.crusoe.mvc.ajax.task.TaskController.batchDelete*(..))")
	private void StatisticalSheetInterceptor() {
	}

	@Before("StatisticalSheetInterceptor() && args(items)")
	public void batchDelete(String[] items) throws Exception {
		// System.out.println("before delete.....");
		if (items == null)
			return;
		for (int i = 0; i < items.length; i++) {
			Task task = taskService.createTaskQuery().taskId(items[i])
					.singleResult();
			if (task != null) {
				Object obj = taskService.getVariable(items[i], "result");
				if (obj instanceof StatisticalSheet) {
					StatisticalSheet ss = (StatisticalSheet) obj;
					if (ss.getId() != null)
						statisticalSheetService.delete(ss.getId());
				}
			}

		}
	}
}

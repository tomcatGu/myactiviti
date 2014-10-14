package org.crusoe.mvc.interceptor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatisticlaSheetInterceptor {
	@Pointcut("execution(* org.crusoe.mvc.ajax.task.TaskController.*(..))")
	public void StatisticalSheetInterceptor() {
	}

	@Before("StatisticalSheetInterceptor() && args(items)")
	public void batchDelete(String[] items) throws Exception {
		System.out.print("before delete.....");
		/*
		 * for (int i = 0; i < items.length; i++) { String processInstanceId =
		 * taskService.createTaskQuery()
		 * .taskId(items[i]).singleResult().getProcessInstanceId(); //
		 * runtimeService.deleteProcessInstance(processInstanceId, "");
		 * StatisticalSheet ss = (StatisticalSheet) taskService.getVariable(
		 * processInstanceId, "result"); if (ss.getId() != null)
		 * statisticalSheetService.delete(ss.getId());
		 * 
		 * }
		 */
	}
}

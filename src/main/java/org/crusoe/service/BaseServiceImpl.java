package org.crusoe.service;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.ServiceImpl;
import org.springframework.beans.factory.InitializingBean;

public class BaseServiceImpl extends ServiceImpl implements InitializingBean {

	@Resource
	ProcessEngine processEngine;

	@Override
	public void afterPropertiesSet() throws Exception {

		ProcessEngineImpl engine = (ProcessEngineImpl) processEngine;

		this.setCommandExecutor(engine.getProcessEngineConfiguration()
				.getCommandExecutor());

	}

}
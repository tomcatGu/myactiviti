package org.crusoe.service;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JPAGroupManagerFactory implements SessionFactory {
	@Autowired
	private ActivitiGroupManager activitiGroupManager;

	public Class<?> getSessionType() {
		// TODO Auto-generated method stub
		return ActivitiGroupManager.class;
	}

	public Session openSession() {
		// TODO Auto-generated method stub
		return activitiGroupManager;
	}

}

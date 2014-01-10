package org.crusoe.service;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.crusoe.repository.jpa.ResourceDao;
import org.crusoe.repository.jpa.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivitiGroupManager extends GroupEntityManager {
	@Autowired
	private ResourceDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Override
	public Group createNewGroup(String groupId) {
		// TODO Auto-generated method stub
		return super.createNewGroup(groupId);
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		// TODO Auto-generated method stub
		return super.findGroupsByUser(userId);
	}

}

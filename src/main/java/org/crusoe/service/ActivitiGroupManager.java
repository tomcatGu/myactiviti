package org.crusoe.service;

import java.util.Iterator;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.crusoe.entity.Role;
import org.crusoe.repository.jpa.ResourceDao;
import org.crusoe.repository.jpa.RoleDao;
import org.crusoe.repository.jpa.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class ActivitiGroupManager extends GroupEntityManager {
	@Autowired
	private UserDao userDao;

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
		org.crusoe.entity.User user = userDao.findByLoginName(userId);
		List<Group> groups = Lists.newArrayList();
		List<Role> roles = user.getRoles();
		Iterator iter = roles.iterator();

		while (iter.hasNext()) {
			Role role = (Role) iter.next();
			GroupEntity group = new GroupEntity();
			group.setId(role.getId().toString());
			group.setName(role.getName());
			group.setRevision(1);
			group.setType("assignment");
			groups.add(group);

		}

		return groups;
	}

}

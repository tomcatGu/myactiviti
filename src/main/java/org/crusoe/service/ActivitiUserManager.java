package org.crusoe.service;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

import org.crusoe.repository.jpa.ResourceDao;
import org.crusoe.repository.jpa.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivitiUserManager extends UserEntityManager {
	@Autowired
	private ResourceDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Override
	public Boolean checkPassword(String userId, String password) {
		// TODO Auto-generated method stub
		User user = (User) userDao.findOne(Long.parseLong(userId));
		return user.getPassword().equals(password);

	}

	@Override
	public User createNewUser(String userId) {
		// TODO Auto-generated method stub
		throw new ActivitiException(
				"JPA user manager doesn't support creating a new user");
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		// TODO Auto-generated method stub
		return super.findGroupsByUser(userId);
	}

	@Override
	public UserEntity findUserById(String userId) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
		// TODO Auto-generated method stub
		return super.findUserByQueryCriteria(query, page);
	}

}

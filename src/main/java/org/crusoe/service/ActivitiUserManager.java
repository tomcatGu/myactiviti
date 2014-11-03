package org.crusoe.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

import org.crusoe.entity.Role;
import org.crusoe.repository.jpa.ResourceDao;
import org.crusoe.repository.jpa.RoleDao;
import org.crusoe.repository.jpa.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class ActivitiUserManager extends UserEntityManager {
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AccountService accountService;

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

		org.crusoe.entity.User user = userDao.findByLoginName(userId);
		List<Group> groups = Lists.newArrayList();
		List<Role> roles = user.getRoles();
		Iterator iter = roles.iterator();

		while (iter.hasNext()) {
			Role role = (Role) iter.next();
			GroupEntity group = new GroupEntity();
			group.setId(role.getId().toString());
			group.setName(role.getName());
			// group.setType(role.)
			groups.add(group);

		}

		return groups;
	}

	@Override
	public UserEntity findUserById(String loginName) {
		// TODO Auto-generated method stub
		UserEntity userEntity = new UserEntity();
		org.crusoe.entity.User user = userDao.findByLoginName(loginName);
		userEntity.setId(user.getLoginName());
		userEntity.setEmail(user.getEmail());
		userEntity.setFirstName(user.getName());
		return userEntity;
	}

	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
		// TODO Auto-generated method stub
		return super.findUserByQueryCriteria(query, page);
	}

}

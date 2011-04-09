package org.crusoe.myauth.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.crusoe.myauth.dao.IBaseDao;
import org.crusoe.myauth.model.RoleInfo;
import org.crusoe.myauth.model.UserInfo;
import org.crusoe.myauth.vo.Role;
import org.crusoe.myauth.vo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("myUserDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	@Resource(name = "userDao")
	private IBaseDao<UserInfo, String> userDao;

	@Resource(name = "roleDao")
	private IBaseDao<RoleInfo, String> roleDao;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		// TODO Auto-generated method stub
		return findUserByUsername(username);
	}

	@Transactional
	public UserDetails findUserByUsername(String username) {

		UserInfo user = null;
		try {

			userDao.find("from UserInfo u where u.username=?", username);

		} catch (Exception e) {

			System.out.println(e.toString());
			if (username.equals("jimi")) {
				System.out.println("lookup jimi......");

			}
			throw new UsernameNotFoundException("User " + username
					+ "is not found");

		}

		user = new UserInfo();
		user.setUsername("jimis");
		user.setPassword("jimis");
		user.setAccountNonLocked(true);

		RoleInfo ri = new RoleInfo();

		ri.setName("ROLE_USER");
		ri.setValue("ROLE_USER");

		HashSet<RoleInfo> roles = new HashSet<RoleInfo>();
		roles.add(ri);
		user.setRoles((java.util.Set<RoleInfo>) roles);
		return user;

	}

	@Transactional
	public void addUser(User user) {

		HashSet<RoleInfo> roles = new HashSet<RoleInfo>();

		if (user.getRoles() != null) { // 鏍规嵁role.id鐨勫�鏉ユ煡鎵綬oleInfo瀵硅薄
			RoleInfo roleInfo;
			Iterator iter = user.getRoles().iterator();
			while (iter.hasNext()) {
				Role role = (Role) iter.next();
				roleInfo = roleDao.load(String.valueOf(role.getId()));
				roles.add(roleInfo);
			}
		}

		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(user, userInfo);
		userInfo.setRoles(roles);
		// 淇濆瓨
		userDao.save(userInfo);

	}
}

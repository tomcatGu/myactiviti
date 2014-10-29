package org.crusoe.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.UnknownAlgorithmException;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.crusoe.entity.Role;
import org.crusoe.entity.User;
import org.crusoe.repository.jpa.RoleDao;
import org.crusoe.repository.jpa.UserDao;
import org.crusoe.service.ShiroDbRealm.ShiroUser;
import org.crusoe.util.Encodes;
import org.crusoe.util.persisterce.DynamicSpecifications;
import org.crusoe.util.persisterce.SearchFilter;
import org.crusoe.util.security.Digests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * 用户管理业务类.
 * 
 * @author gwx
 */
// Spring Service Bean的标识.
@Service
@Transactional(readOnly = true)
public class AccountService {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory
			.getLogger(AccountService.class);

	private UserDao userDao;

	private RoleDao roleDao;

	// private NotifyMessageProducer notifyProducer; // JMS消息发送

	// private ApplicationStatistics applicationStatistics;

	/**
	 * 在保存用户时,发送用户修改通知消息, 由消息接收者异步进行较为耗时的通知邮件发送.
	 * 
	 * 如果企图修改超级用户,取出当前操作员用户,打印其信息然后抛出异常.
	 * 
	 * @throws Exception
	 * 
	 */
	@Transactional(readOnly = false)
	public void saveUser(User user) throws Exception {

		if (isSupervisor(user)) {
			logger.warn("操作员{}尝试修改超级管理员用户", getCurrentUserName());
			throw new Exception("不能修改超级管理员用户");
		}

		// 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPassword())) {
			entryptPassword(user);
		}

		userDao.save(user);

	}

	@Transactional(readOnly = false)
	public void changePassword(User user) throws Exception {

		// 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPassword())) {
			entryptPassword(user);
		}

		userDao.save(user);

	}

	@Transactional(readOnly = false)
	public void deleteUser(User user) throws Exception {

		if (isSupervisor(user)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new Exception("不能删除超级管理员用户");
		}
		userDao.delete(user);

	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		String salt = rng.nextBytes().toHex();

		String hashedPasswordHex = new SimpleHash(HASH_ALGORITHM,
				user.getPassword(), salt, HASH_INTERATIONS).toHex();

		user.setPassword(hashedPasswordHex);
		user.setPasswordSalt(salt);
	}

	public boolean isCorrectPassword(User user, String comparePassword) {

		// RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		String salt = user.getPasswordSalt();

		String hashedPasswordHex = new SimpleHash(HASH_ALGORITHM,
				comparePassword, salt, HASH_INTERATIONS).toHex();
		if (user.getPassword().equals(hashedPasswordHex))
			return true;
		else
			return false;
	}

	public Page<User> searchUser(Map<String, Object> searchParams,
			Pageable pageRequest) {

		Map<String, SearchFilter> filters = SearchFilter.parse3(searchParams);
		Specification<User> spec = DynamicSpecifications.bySearchFilter2(
				filters.values(), User.class);

		return userDao.findAll(spec, pageRequest);
	}

	/**
	 * 获取全部用户对象，并在返回前完成LazyLoad属性的初始化。
	 */
	public List<User> getAllUserInitialized() {
		List<User> result = (List<User>) userDao.findAll();
		for (User user : result) {
			// Hibernates.initLazyProperty(user.getRoleList());
		}
		return result;
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(User user) {
		return (user.getId() != null && user.getId() == 1L);
	}

	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	/**
	 * 按名称查询用户, 并对用户的延迟加载关联进行初始化.
	 */
	public User findUserByNameInitialized(String name) {
		User user = userDao.findByName(name);
		if (user != null) {
			// Hibernates.initLazyProperty(user.getRoleList());
		}
		return user;
	}

	/**
	 * 获取当前用户数量.
	 */
	public Long getUserCount() {
		return userDao.count();
	}

	public User findUserByLoginName(String loginName) {
		User user = userDao.findByLoginName(loginName);
		if (user == null) {
			// Hibernates.initLazyProperty(user.getRoleList());
			user = new User();
			user.setId(0L);
			user.setLoginName("1234");
			user.setName("5678");
			user.setPassword("abcd");

			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String salt = rng.nextBytes().toHex();

			String hashedPasswordHex = new SimpleHash(HASH_ALGORITHM,
					user.getPassword(), salt, HASH_INTERATIONS).toHex();

			user.setPassword(hashedPasswordHex);
			user.setPasswordSalt(salt);
			user.setStatus("enable");

		}
		return user;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	// --------------------//
	// Role Management //
	// --------------------//

	public List<Role> getAllRole() {
		return (List<Role>) roleDao.findAll();
	}

	// -----------------//
	// Setter methods //
	// -----------------//

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public Page<User> searchUser(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return userDao.findAll(pageRequest);
	}

	// users: "1,2,3..."etc
	public List<String> resolveUsername(String users, String separatorChars) {
		List<String> usernames = Lists.newArrayList();
		String[] ids = StringUtils.split(users, separatorChars);
		for (String id : ids) {
			User user = userDao.findOne(Long.parseLong(id));
			usernames.add(user.getLoginName());
		}
		return usernames;
	}

	public Page<User> findByOrganization(Long organizationId,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return userDao.findByOrganization(organizationId, pageRequest);
	}

}

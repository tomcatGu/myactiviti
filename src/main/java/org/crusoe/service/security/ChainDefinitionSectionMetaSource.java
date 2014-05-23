package org.crusoe.service.security;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.crusoe.entity.Permission;
import org.crusoe.entity.Resource;
import org.crusoe.entity.Role;
import org.crusoe.entity.User;
import org.crusoe.repository.jpa.PermissionDao;
import org.crusoe.repository.jpa.ResourceDao;
import org.crusoe.service.AccountService;
import org.crusoe.service.ShiroDbRealm.ShiroUser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class ChainDefinitionSectionMetaSource implements
		FactoryBean<Ini.Section> {

	private PermissionDao permissionDao;

	private String filterChainDefinitions;

	/**
	 * 默认premission字符串
	 */
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	public Section getObject() throws BeansException {

		// 获取所有Resource
		List<Permission> list = (List<Permission>) permissionDao.findAll();

		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 循环permission的url,逐个添加到section中。section就是filterChainDefinitionMap,
		// 里面的键就是链接URL,值就是存在什么条件才能访问该链接
		for (Iterator it = list.iterator(); it.hasNext();) {

			Permission permission = (Permission) it.next();
			// 如果不为空值添加到section中
			if (StringUtils.isNotEmpty(permission.getUrl())
					&& StringUtils.isNotEmpty(permission.getToken())) {
				section.put(
						permission.getUrl(),
						MessageFormat.format(PREMISSION_STRING,
								permission.getToken()));
			}

		}

		return section;
	}

	/**
	 * 通过filterChainDefinitions对默认的url过滤定义
	 * 
	 * @param filterChainDefinitions
	 *            默认的url过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Class getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}

	public PermissionDao getPermissionDao() {
		return permissionDao;
	}

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

}
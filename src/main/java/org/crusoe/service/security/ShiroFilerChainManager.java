package org.crusoe.service.security;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.crusoe.dto.PermissionDTO;
import org.crusoe.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-25
 * <p>
 * Version: 1.0
 */
@Service
public class ShiroFilerChainManager {

	@Autowired
	private DefaultFilterChainManager filterChainManager;

	private Map<String, NamedFilterList> defaultFilterChains;

	@PostConstruct
	public void init() {
		defaultFilterChains = new HashMap<String, NamedFilterList>(
				filterChainManager.getFilterChains());
	}

	public void addFilterChains(PermissionDTO permissionDTO) {

		String url = permissionDTO.getUrl();
		String roles = null;
		for (RoleDTO role : permissionDTO.getRoles()) {
			roles += role.getName() + ",";

		}
		// 注册roles filter
		if (!StringUtils.isEmpty(roles)) {
			filterChainManager.addToChain(url, "roles", roles);
		}
		// 注册perms filter
		if (!StringUtils.isEmpty(permissionDTO.getToken())) {
			filterChainManager.addToChain(url, "perms",
					permissionDTO.getToken());
		}

	}

	public void initFilterChains(List<PermissionDTO> urlFilters) {
		// 1、首先删除以前老的filter chain并注册默认的
		filterChainManager.getFilterChains().clear();
		if (defaultFilterChains != null) {
			filterChainManager.getFilterChains().putAll(defaultFilterChains);
		}

		// 2、循环URL Filter 注册filter chain

		for (PermissionDTO urlFilter : urlFilters) {
			String url = urlFilter.getUrl();
			// 注册roles filter
			String roles = "";
			for (RoleDTO role : urlFilter.getRoles()) {
				roles += role.getName() + ",";

			}

			if (!StringUtils.isEmpty(urlFilter.getRoles())) {
				filterChainManager.addToChain(url, "roles",
						roles.substring(0, roles.length() - 1));
			}
			// 注册perms filter
			if (!StringUtils.isEmpty(urlFilter.getToken())) {
				filterChainManager.addToChain(url, "perms",
						urlFilter.getToken());
			}
		}

	}

}

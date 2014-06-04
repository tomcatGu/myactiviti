package org.crusoe.service.security;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.crusoe.dto.PermissionDTO;
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
			if (!StringUtils.isEmpty(urlFilter.getRoles())) {
				filterChainManager.addToChain(url, "roles",
						urlFilter.getRoles().toString());
			}
			// 注册perms filter
			if (!StringUtils.isEmpty(urlFilter.getToken())) {
				filterChainManager.addToChain(url, "perms",
						urlFilter.getToken().toString());
			}
		}

	}

}

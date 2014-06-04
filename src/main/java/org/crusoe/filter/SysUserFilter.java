package org.crusoe.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.crusoe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-15
 * <p>
 * Version: 1.0
 */
public class SysUserFilter extends PathMatchingFilter {

	@Autowired
	private AccountService accountService;

	@Override
	protected boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		if (SecurityUtils.getSubject().getPrincipal() != null) {
			String username = SecurityUtils.getSubject().getPrincipal().toString();
			request.setAttribute("user",
					accountService.findUserByLoginName(username).getLoginName());
		}
		return true;
	}
}

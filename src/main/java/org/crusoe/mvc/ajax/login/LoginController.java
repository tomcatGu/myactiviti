package org.crusoe.mvc.ajax.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author calvin
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String fail(
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,
			@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password,
			Model model) {
		UsernamePasswordToken token = new UsernamePasswordToken(userName,
				password);

		// TODO: Make this a user option instead of hard coded in.
		token.setRememberMe(true);

		Subject currentUser = SecurityUtils.getSubject();

		try {
			currentUser.login(token);
			logger.info("AUTH SUCCESS");

		} catch (UnknownAccountException uae) {
			logger.info("AUTH MSSG: " + uae.getMessage());
		} catch (IncorrectCredentialsException ice) {
			logger.info("AUTH MSSG: " + ice.getMessage());
		} catch (LockedAccountException lae) {
			logger.info("AUTH MSSG: " + lae.getMessage());
		} catch (ExcessiveAttemptsException eae) {
			logger.info("AUTH MSSG: " + eae.getMessage());
		} catch (AuthenticationException ae) {
			logger.info("AUTH MSSG: " + ae.getMessage());
		}
		if (currentUser.isAuthenticated()) {

			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			// 获取login前的页面地址
			SavedRequest redirectUrl = WebUtils
					.getAndClearSavedRequest(request);
			String reUrl = "/";
			if (redirectUrl != null) {
				reUrl = redirectUrl.getRequestURI();
				reUrl = reUrl.substring(reUrl.indexOf("/", 1) + 1);
			}

			return "redirect:" + reUrl;
		}
		model.addAttribute("err", "用户名或密码错误。");
		return "login";

	}

}

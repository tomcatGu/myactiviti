package org.crusoe.mvc.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthorizationExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {
		// 是否为ajax请求
		String requestType = request.getHeader("X-Requested-With");
		if (exception instanceof AuthorizationException) {
			// response.setStatus(413);// 无权限异常 主要用于ajax请求返回
			// response.addHeader("Error-Json",
			// "{code:413,msg:'nopermission',script:''}");
			// response.setContentType("text/html;charset=utf-8");

			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")) {
				// 如果是ajax请求响应头会有，x-requested-with
				response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
				try {
					response.sendError(413);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if ("XMLHttpRequest".equals(requestType)) {
				return new ModelAndView();
			}
			return new ModelAndView("redirect:login");
		}
		return null;
	}

}

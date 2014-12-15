package org.crusoe.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class HTTPStatusCodeAuthenticationFilter extends AccessControlFilter {

	/**
	 * This method is copied from AuthenticationFilter
	 * <p>
	 * Determines whether the current subject is authenticated.
	 * <p/>
	 * The default implementation
	 * {@link #getSubject(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 * acquires} the currently executing Subject and then returns
	 * {@link org.apache.shiro.subject.Subject#isAuthenticated()
	 * subject.isAuthenticated()};
	 * 
	 * @return true if the subject is authenticated; false if the subject is
	 *         unauthenticated
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		Subject subject = getSubject(request, response);
		boolean isAuthenticated = subject.isAuthenticated();
		return isAuthenticated;
	}

	/**
	 * Takes responsibility for returning an appropriate response when access is
	 * not allowed.
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		accessDeniedResponse(request, response);
		return false;
	}

	/**
	 * Provides a 401 Not Authorized HTTP status code to the client with a
	 * custom challenge scheme that the client understands and can respond to.
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void accessDeniedResponse(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		httpResponse.addHeader("WWW-Authentication", "ACME-AUTH");
		
		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	@Override
	public boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;  
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;  
        //      if (httpServletRequest.getSession().getAttribute("user") == null) {  
        if (!SecurityUtils.getSubject().isAuthenticated()) {  
            //判断session里是否有用户信息  
            if (httpServletRequest.getHeader("x-requested-with") != null  
                    && httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {  
                //如果是ajax请求响应头会有，x-requested-with  
                httpServletResponse.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
                //return;  
            }  
  
        }  
		return super.onPreHandle(request, response, mappedValue);
	}

}

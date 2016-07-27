/**   
* @Title: AuthenticatingFilter.java 
* @Package com.frame.kangan.web.security 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年6月13日 下午2:44:42 
* @version V1.0   
*/
package com.frame.kangan.web.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.frame.kangan.controller.TestController;

/** 
* @ClassName: FrameAuthenticatingFilter 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年6月13日 下午2:44:42 
*  
*/
public class FrameAuthenticatingFilter extends AccessControlFilter{
	
	  private static final Log logger = LogFactory.getLog(FrameAuthenticatingFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated();
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		String account = request.getParameter("account");
		String password = request.getParameter("password");	
		FrameAuthenticationToken token = new FrameAuthenticationToken(account, password);
		try {
			Subject subject = getSubject(request, response);
			subject.login(token);
			return true;
		} catch (AuthenticationException e) {
			logger.error(e.getMessage());
			WebUtils.redirectToSavedRequest(request, response, getLoginUrl());
		}
		return false;
	}

	@Override
	protected void cleanup(ServletRequest request, ServletResponse response, Exception existing)
			throws ServletException, IOException {
		if (existing instanceof UnauthenticatedException
				|| (existing instanceof ServletException && existing.getCause() instanceof UnauthenticatedException)) {
			try {
				onAccessDenied(request, response);
				existing = null;
			} catch (Exception e) {
				existing = e;
			}
		}
		super.cleanup(request, response, existing);

	}
	
}

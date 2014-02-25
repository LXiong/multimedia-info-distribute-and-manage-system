package com.yunling.mediacenter.web.struts2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.yunling.mediacenter.db.service.UserRoleService;
import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.Functions;
import com.yunling.mediacenter.web.SessionUser;

public class UserRoleInterceptor 
	extends AbstractInterceptor
	implements Interceptor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5573395807822933327L;
	
	private UserRoleService userRoleService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String nameSpace = invocation.getProxy().getNamespace();
		String action = invocation.getProxy().getActionName();		
		String method = invocation.getProxy().getMethod();
		if ("/".equals(nameSpace) && "".equals(action)) {
			action = "home";
		}
		String actionPath = nameSpace+"/"+action+"!"+method;
		HttpServletRequest request = (HttpServletRequest) 
			invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		request.setAttribute(Constants.CURRENT_ACTION, actionPath);
		
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		SessionUser su = (SessionUser) session.get(Constants.SESSION_USER_KEY);
		if (su != null) {
			long id = su.getUserId();
			// load function configurations from database.
			Set<Functions> functionSet = new HashSet<Functions>();
			functionSet.add(Functions.Basic);
			functionSet.addAll(userRoleService.getFunctionsByUserId(id));
			
			request.setAttribute(Constants.SESSION_USER_FUNCTIONS, functionSet);
		}		
		return invocation.invoke();
	}

	public UserRoleService getUserRoleService() {
		return userRoleService;
	}

	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}


}
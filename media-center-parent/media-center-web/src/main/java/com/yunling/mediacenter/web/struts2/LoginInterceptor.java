package com.yunling.mediacenter.web.struts2;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.SessionUser;
import com.yunling.mediacenter.web.actions.LoginAction;

public class LoginInterceptor 
	implements Interceptor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8382826312149879551L;
	
	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		if (invocation.getAction() instanceof Action) {
			Action action = (Action) invocation.getAction();
			ActionMapping mapping = (ActionMapping) 
				invocation.getInvocationContext().get(ServletActionContext.ACTION_MAPPING);
			Map<String, Object> session = invocation.getInvocationContext().getSession();
			if (action instanceof LoginAction) {
				String methodName = invocation.getProxy().getMethod();
				if (!"logout".equals(methodName) && session.containsKey(Constants.SESSION_USER_KEY)) {
					return "home";
				}
			} else {
				if (session.containsKey(Constants.SESSION_USER_KEY)) {
					SessionUser sessionUser = (SessionUser) invocation.getInvocationContext().getSession().get(Constants.SESSION_USER_KEY);
					if (mapping!=null) {
						HttpServletRequest requst = (HttpServletRequest) 
							invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
						String method = mapping.getMethod();
						if (method == null || "".equals(method)) {
							requst.setAttribute("actionPath", mapping.getNamespace()+mapping.getName());
						} else {
							requst.setAttribute("actionPath", mapping.getNamespace()+mapping.getName()+"!"+method);
						}
						requst.setAttribute("logined", Boolean.TRUE);
						requst.setAttribute("loginedName", sessionUser.getLoginname());
					}
				} else {
					return Action.LOGIN;
				}
			}
		}
		String result = invocation.invoke();
		return result;
	}

}

package com.yunling.mediacenter.web.struts2;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.yunling.mediacenter.web.Constants;

public class FlashMessageInterceptor 
	implements Interceptor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3848486959045953966L;
	
	@Override
	public void destroy() {

	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		if (session.get(Constants.FLASH_MESSAGE)!=null) {
			Object msg = session.get(Constants.FLASH_MESSAGE);
			ServletActionContext.getRequest().setAttribute(Constants.FLASH_MESSAGE, msg);
			session.remove(Constants.FLASH_MESSAGE);
		}
		return invocation.invoke();
	}


}

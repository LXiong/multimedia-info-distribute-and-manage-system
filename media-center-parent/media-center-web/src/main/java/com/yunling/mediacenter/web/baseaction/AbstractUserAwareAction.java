package com.yunling.mediacenter.web.baseaction;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.SessionUser;
import com.yunling.spring.web.SecurityUser;

abstract public class AbstractUserAwareAction 
	extends BaseAction
	implements SessionAware
{
	protected Map<String, Object> session;
	protected String postToken;
	
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	protected SecurityUser getUser() {
		if (SecurityContextHolder.getContext() instanceof SecurityContext
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			return (SecurityUser) auth.getPrincipal();
		}
		return null;
	}

	protected SessionUser getSessionUser()
	{
		return (SessionUser) this.session.get(Constants.SESSION_USER_KEY);
	}
	protected String getUserName() {
		if (getUser()!=null) {
			return getUser().getDispName();
		}
		if (getSessionUser()!=null) {
			return getSessionUser().getLoginname();
		}
		return "unknown";
	}
	protected long getCurrentUserId() {
		SecurityUser suser = getUser();
		if (suser != null) {
			return suser.getId();
		}
		SessionUser sessionUser = getSessionUser();
		if (sessionUser!=null) {
			return sessionUser.getUserId();
		}
		return -1;
	}
	
	public boolean isPostBack() {
		return "1".equals(postToken) || "true".equalsIgnoreCase(postToken);
	}

	public String getPostToken() {
		return postToken;
	}

	public void setPostToken(String postToken) {
		this.postToken = postToken;
	}
	
}

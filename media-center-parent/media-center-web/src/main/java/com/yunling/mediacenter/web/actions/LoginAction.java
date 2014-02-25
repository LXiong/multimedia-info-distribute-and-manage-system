package com.yunling.mediacenter.web.actions;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.yunling.mediacenter.db.model.LoginLog;
import com.yunling.mediacenter.db.model.Users;
import com.yunling.mediacenter.db.service.LoginLogService;
import com.yunling.mediacenter.db.service.UserService;
import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.SessionUser;
import com.yunling.mediacenter.web.baseaction.BaseAction;
import com.yunling.mediacenter.web.struts2.JspLayout;

/**
 * @author Administrator
 * 
 */
public class LoginAction extends BaseAction implements SessionAware {
	private Map<String, Object> session;
	private String loginname;
	private String password;

	public LoginLogService getLoginlogservice() {
		return loginlogservice;
	}

	public void setLoginlogservice(LoginLogService loginlogservice) {
		this.loginlogservice = loginlogservice;
	}

	private UserService userService;
	private LoginLogService loginlogservice;

	public String execute() throws Exception {
		setLayout(JspLayout.NONE);
		return SUCCESS;
	}

	public String login() {
		Users user = userService.getUserByIdPassword(loginname, password);
		LoginLog loginlog = new LoginLog();
		loginlog.setRemoteIp(ServletActionContext.getRequest().getRemoteAddr());
		if (user == null) {
			loginlog.setLogintime(new Date());
			loginlog.setIssuccess("N");
			loginlog.setLoginId(0);
			loginlogservice.add(loginlog);
			return LOGIN;
		}
		loginlog.setLogintime(new Date());
		loginlog.setIssuccess("Y");
		loginlog.setLoginId(user.getUserId());
		loginlogservice.add(loginlog);
		userService.updateLastLogin(user.getUserId());

		SessionUser suser = new SessionUser();
		suser.setLoginname(user.getLoginName());
		suser.setUserId(user.getUserId());
		suser.setPassword(password);
		suser.setEmail(user.getEmail());
		session.put(Constants.SESSION_USER_KEY, suser);
		return HOME;
	}

	public String logout() {
		session.remove(Constants.SESSION_USER_KEY);
		return LOGIN;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
}

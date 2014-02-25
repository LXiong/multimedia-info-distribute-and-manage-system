package com.yunling.mediacenter.web.actions;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yunling.mediacenter.db.model.Users;
import com.yunling.mediacenter.db.service.UserService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

@Results({ @Result(name = "redirect-personal", location = "personal", type = "redirect") })
public class PersonalAction extends AbstractUserAwareAction {
	private static final String PASSWORD = "password";
	private UserService userService;
	private Users userInfo;

	@Override
	public String execute() throws Exception {
		Users user = userService.getUserById(getCurrentUserId());
		request.put("user", user);

		String oper = getSingleParam("oper");
		if (StringUtils.equals("edit", oper)) {
			this.userInfo = user;
			return "edit";
		}
		return SUCCESS;
	}

	public String save() throws Exception {
		if (userInfo != null && userInfo.getUserId() == getCurrentUserId()) {
			Users user = userService.getUserById(getCurrentUserId());
			user.setEmail(userInfo.getEmail());
			user.setTelephone(userInfo.getTelephone());
			user.setUserName(userInfo.getUserName());
			userService.updateUser(user);
		}
		return "redirect-personal";
	}

	public String password() throws Exception {
		String oldPass = getSingleParam("oldpass");
		String newpass = getSingleParam("newpass");
		String confirmPass = getSingleParam("repass");
		if (StringUtils.isEmpty(oldPass) || StringUtils.isEmpty(newpass)
				|| StringUtils.isEmpty(confirmPass)) {
			return PASSWORD;
		} else if (!StringUtils.equals(newpass, confirmPass)) {
			addActionError(getText("personal.password.mismatch"));
		} else {
			Users user = userService.getUserById(getCurrentUserId());
			if (StringUtils.equals(user.getPassword(), oldPass)) {
				user.setPassword(newpass);
				userService.changePassword(user);
				addActionMessage(getText("personal.password.changed"));
			} else {
				addActionError(getText("personal.wrong.old"));
			}
		}
		return PASSWORD;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Users getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Users userInfo) {
		this.userInfo = userInfo;
	}

}

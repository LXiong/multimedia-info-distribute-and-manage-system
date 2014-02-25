package com.yunling.mediacenter.web.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunling.mediacenter.db.model.OperationLog;
import com.yunling.mediacenter.db.model.Roles;
import com.yunling.mediacenter.db.model.UserRole;
import com.yunling.mediacenter.db.service.OperationLogService;
import com.yunling.mediacenter.db.service.RoleService;
import com.yunling.mediacenter.db.service.UserRoleService;
import com.yunling.mediacenter.db.service.UserService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class UserRoleAction extends AbstractUserAwareAction {
	private long userId;

	private UserService userService;
	private RoleService roleService;
	private UserRoleService userRoleService;
	private OperationLogService operationLogService;

	private HttpServletResponse response = ServletActionContext.getResponse();
	private HttpServletRequest rawrequest = ServletActionContext.getRequest();

	/*
	 * 查询当前用户的角色
	 */
	public String execute() throws Exception {
		Integer sum = userService.selectUserCount();
		Integer page = 0;

		try {
			page = Integer.parseInt(rawrequest.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		request.put("map", userService.list(page, sum));
		return SUCCESS;
	}

	/*
	 * 保存当前用户的角色
	 */
	public String saveRole() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String ids = rawrequest.getParameter("roleId");
		if (ids.equals("-999")) {
			userRoleService.delByUserId(userId);
			OperationLog log = getOperationLog(userId, "Y", "user-role",
					"delete");
			operationLogService.add(log);
			// ActionContext.getContext().put("alert",this.getText("user.role.all.remove"));
			// ActionContext.getContext().put("location","user-role.action");
			request.put("alert", this.getText("user.role.all.remove"));
			request.put("location", "user.action");
			return "prompt";
		}
		String[] roleIds = ids.substring(5).split(",");
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRoleService.delByUserId(userId);
		for (String roleId : roleIds) {
			log.debug("Save Role: {}, {}", userId, roleId);
			userRole.setRoleId(Long.parseLong(roleId));
			userRoleService.addUserRole(userRole);
		}
		OperationLog log = getOperationLog(userId, "Y", "user-role", "update");
		operationLogService.add(log);
		// ActionContext.getContext().put("alert",this.getText("change.role.success"));
		// ActionContext.getContext().put("location","user-role.action");
		request.put("alert", this.getText("change.role.success"));
		request.put("location", "user.action");
		return "prompt";
	}

	public String update() throws Exception {
		List<Roles> roles = roleService.getRoleByUserId(userId);
		List<Roles> notroles = roleService.notRoleByUserId(userId);
		// ActionContext.getContext().put("roles",roles);
		// ActionContext.getContext().put("notroles",notroles);
		request.put("roles", roles);
		request.put("notroles", notroles);
		return "update";
	}

	public OperationLog getOperationLog(long id, String issuccess,
			String updateobject, String action) {
		OperationLog log = new OperationLog();
		log.setCurrentuserId(getCurrentUserId());
		log.setObjectid(id);
		log.setOperationtime(new Date());
		log.setAction(action);
		log.setUpdateobject(updateobject);
		log.setIssuccess(issuccess);
		return log;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the roleService
	 */
	public RoleService getRoleService() {
		return roleService;
	}

	/**
	 * @param roleService the roleService to set
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * @return the userRoleService
	 */
	public UserRoleService getUserRoleService() {
		return userRoleService;
	}

	/**
	 * @param userRoleService the userRoleService to set
	 */
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	/**
	 * @return the operationLogService
	 */
	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	/**
	 * @param operationLogService the operationLogService to set
	 */
	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

}

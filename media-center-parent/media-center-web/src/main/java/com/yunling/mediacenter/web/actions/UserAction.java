package com.yunling.mediacenter.web.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.OperationLog;
import com.yunling.mediacenter.db.model.UserStb;
import com.yunling.mediacenter.db.model.Users;
import com.yunling.mediacenter.db.service.OperationLogService;
import com.yunling.mediacenter.db.service.UserRoleService;
import com.yunling.mediacenter.db.service.UserService;
import com.yunling.mediacenter.web.actions.vo.UserVo;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

//@Results( { @Result(name = "redirect-list", location = "user!list.action", type = "redirect") })
public class UserAction extends AbstractUserAwareAction {
	private String jsonStr;
	private Long userId;
	private String loginName;
	private String userName;
	private String password;
	private Long telephone;
	private String email;
	
	private UserService userService;
	private UserRoleService userRoleService;
	private OperationLogService operationLogService;
	
	private HttpServletResponse rawResponse = ServletActionContext.getResponse();

	public String execute() throws Exception {
		log.debug("User Manager.");
		List<Users> users = userService.getUsers();
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(users);
			log.debug(jsonStr);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * @date 9/9/2011
	 * @author LingJun
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		log.debug("create User.");
		Users user = new Users();
		if(loginName == null || loginName.isEmpty() || 
				password == null || password.isEmpty()) {
			return null;
		}
		user.setLoginName(loginName);
		if(StringUtils.isEmpty(userName)) {
			userName = "";
		}
		user.setUserName(userName);
		user.setPassword(password);
		if(telephone == null) {
			telephone = 0L;
		}
		user.setTelephone(telephone);
		if(StringUtils.isEmpty(email)) {
			email = "";
		}
		user.setEmail(email);
		userService.createUser(user);
		OperationLog log = getOperationLog( "Y", "user:" + user.getLoginName(), "add");
		operationLogService.add(log);
		return null;
	}
	
	/**
	 * @date 9/9/2011
	 * @author LingJun
	 * @return
	 * @throws Exception
	 */
	public String update()  throws Exception {
		log.debug("update User.");
		Users user = new Users();
		if(loginName == null || loginName.isEmpty()) {
			return null;
		}
		user.setLoginName(loginName);
		if(StringUtils.isEmpty(userName)) {
			userName = "";
		}
		user.setUserName(userName);
		if(telephone == null) {
			telephone = 0L;
		}
		user.setTelephone(telephone);
		if(StringUtils.isEmpty(email)) {
			email = "";
		}
		user.setEmail(email);
		userService.updateUser(user);
		OperationLog log = getOperationLog("Y", "user:" + user.getLoginName(), "update");
		operationLogService.add(log);
		return null;
	}
	
	/**
	 * @date 9/9/2011
	 * @author LingJun
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		log.debug("Delete User " + loginName);
		String currentUser = this.getSessionUser().getLoginname();
		log.debug("now the user is: " + currentUser);
		OperationLog loger = null;
		Users user = userService.getUserByLoginName(loginName);
		if("admin".equals(loginName) || loginName.equals(currentUser)) {
			rawResponse.getWriter().print(
					this.getText("user.can.not.be.deleted"));
			loger = getOperationLog("N", "userId:" + userId, "delete");
			operationLogService.add(loger);
			return null;
		} else {
			log.debug("now delete the user: " + loginName + ", and its relatives");
			userRoleService.delByUserId(user.getUserId());
			userService.delByLoginName(loginName);
			rawResponse.getWriter().print(this.getText("user.have.been.deleted"));
			loger = getOperationLog("Y", "userId:" + userId, "delete");
			operationLogService.add(loger);
			return null;
		}
	}
	
	/**
	 * @date 9/13/2011
	 * @author LingJun
	 * @return
	 * @throws Exception
	 */
	public String check() throws Exception {
		Users user = userService.getUserByLoginName(loginName);
		if (user == null) {
			rawResponse.getWriter().print(true);
		} else {
			rawResponse.getWriter().print(false);
		}
		return null;
	}
	
	public String visitStbPower() {
		List<GroupType> list = userService.groupTypeList();
		List<Groups> list2 = userService.groupsList();
		List<UserStb> list3 = userService.getUserStbById(userId);
		request.put("groupTypeList", list);
		request.put("groupsList", list2);
		request.put("UserStbList", list3);
		return "stb";
	}
	
	public String saveStbPower() {
		// String groups =
		// ServletActionContext.getRequest().getParameter("groupsArray");
		// String types =
		// ServletActionContext.getRequest().getParameter("typeArray");
		String groups = parameters.get("groupsArray") != null
				&& parameters.get("groupsArray").length > 0 ? parameters
				.get("groupsArray")[0] : null;
		String types = (parameters.get("typeArray") != null && parameters
				.get("typeArray").length > 0) ? parameters.get("typeArray")[0]
				: null;
		long[] typeArr = null;
		long[] groupArr = null;
		if (StringUtils.isNotEmpty(types)) {
			String[] type = types.split(",");
			typeArr = new long[type.length];
			for (int i = 0; i < type.length; ++i) {
				try {
					typeArr[i] = Long.parseLong(type[i]);
				} catch (NumberFormatException e) {
					if (log.isDebugEnabled()) {
						log.debug("Failed to parse type id {} ", type[i]);
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(groups)) {
			String[] group = groups.split(",");
			groupArr = new long[group.length];
			for (int i = 0; i < group.length; ++i) {
				try {
					groupArr[i] = Long.parseLong(group[i]);
				} catch (NumberFormatException e) {
					if (log.isDebugEnabled()) {
						log.debug("Failed to parse group id {} ", group[i]);
					}
				}
			}
		}
		userService.setUserStb(userId, groupArr, typeArr);
		request.put("alert", this.getText("handle.success"));
		request.put("location", "user.action");
		return "prompt";
	}

	public OperationLog getOperationLog(String issuccess,
			String updateobject, String action) {
		OperationLog log = new OperationLog();
		log.setCurrentuserId(getCurrentUserId());
		log.setObjectid(getCurrentUserId());
		log.setOperationtime(new Date());
		log.setAction(action);
		log.setUpdateobject(updateobject);
		log.setIssuccess(issuccess);
		return log;
	}
	
	/**
	 * @return the jsonStr
	 */
	public String getJsonStr() {
		return jsonStr;
	}

	/**
	 * @param jsonStr the jsonStr to set
	 */
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the telephone
	 */
	public Long getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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






	private HttpServletRequest rawRequest = ServletActionContext.getRequest();

	private UserVo vo;

	public UserVo getVo() {
		return vo;
	}

	public void setVo(UserVo vo) {
		this.vo = vo;
	}

	/*
	 * 查询所有用户
	 */
	public String list() throws Exception {
		Integer sum = userService.selectUserCount();
		Integer page = 0;
		try {
			page = Integer.parseInt(ServletActionContext.getRequest()
					.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		Map<String, Object> map = userService.list(page, sum);
		request.put("map", map);
		return "list";
	}

	/*
	 * 根据主键删除
	 */
	public String delById() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		if (userId != getCurrentUserId()) {
			userRoleService.delByUserId(userId);
			userService.delById(userId);
			OperationLog log = getOperationLog("Y", "userId:"+userId, "delete");
			operationLogService.add(log);
			// ActionContext.getContext().put("alert",this.getText("delete.success"));
			// ActionContext.getContext().put("location","user!list.action");
			request.put("alert", this.getText("delete.success"));
			request.put("location", "user!list.action");
		}
		return "prompt";
	}

	/*
	 * 添加用户
	 */
	public String add() throws Exception {
		return "add";
	}

	public String addUser() throws Exception {
		Users uu = new Users();
		rawResponse.setContentType("text/html;charset=utf-8");
		uu.setLoginName(vo.getLoginname());
		uu.setUserName(vo.getUsername());
		uu.setPassword(vo.getPassword());
		uu.setEmail(vo.getEmail());
		uu.setTelephone(vo.getTelephone());
		uu.setLastLoginTime(new SimpleDateFormat("yyyy-MM-dd")
				.parse("2010-10-13"));
		userService.addUser(uu);
		OperationLog log = getOperationLog( "Y", "user:"+uu.getLoginName(), "add");
		operationLogService.add(log);
		// ActionContext.getContext().put("alert",this.getText("add.success"));
		// ActionContext.getContext().put("location","user!list.action");
		request.put("alert", this.getText("add.success"));
		request.put("location", "user!list.action");
		return "prompt";
	}

	/*
	 * 修改密码
	 */
	public String password() throws Exception {
		return "changepassword";
	}

	public String changePassword() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		String pass = rawRequest.getParameter("pass");
		String newpass = rawRequest.getParameter("newpass");
		String loginname = (String) rawRequest.getAttribute("loginedName");
		Users user = userService.findUserByLoginName(loginname);
		user.setLoginName(loginname);
		OperationLog log = null;
		if (user.getPassword().equals(pass)) {
			user.setPassword(newpass);
			userService.changePassword(user);
			log = getOperationLog("Y", "user", "update");
			// ActionContext.getContext().put("result",this.getText("password.update.success"));
			request.put("result", this.getText("password.update.success"));
		} else {
			log = getOperationLog("N", "user", "update");
			// ActionContext.getContext().put("result",this.getText("password.update.fail"));
			request.put("result", this.getText("password.update.fail"));
		}
		operationLogService.add(log);
		return "passwordresult";
	}

	/*
	 * 用户编辑
	 */
	public String edit() throws Exception {
		Users user = userService.getUserById(userId);
		// ActionContext.getContext().put("user",user);
		request.put("user", user);
		return "edit";
	}

	public String edit2() throws Exception {
		Users user = new Users();
		rawResponse.setContentType("text/html;charset=utf-8");
		user.setUserId(vo.getUserId());
		user.setLoginName(vo.getLoginname());
		user.setUserName(vo.getUsername());
		user.setEmail(vo.getEmail());
		user.setTelephone(vo.getTelephone());
		
		String newpass1 = getSingleParam("newpassword");
		String newpass2 = getSingleParam("newpassword2");
		if (!StringUtils.isEmpty(newpass1) && StringUtils.equals(newpass1, newpass2)) {
			user.setPassword(newpass1);
		}
		userService.updateUser(user);
		OperationLog log = getOperationLog("Y", "user:"+vo.getLoginname(),
				"update");
		operationLogService.add(log);
		// ActionContext.getContext().put("alert",this.getText("update.information.success"));
		// ActionContext.getContext().put("location","user!list.action");
		request.put("alert", this.getText("update.information.success"));
		request.put("location", "user!list.action");
		return "prompt";
	}

}

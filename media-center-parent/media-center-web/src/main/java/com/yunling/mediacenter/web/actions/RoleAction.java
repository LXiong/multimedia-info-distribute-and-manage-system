package com.yunling.mediacenter.web.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.TextProvider;
import com.yunling.mediacenter.db.model.Authority;
import com.yunling.mediacenter.db.model.OperationLog;
import com.yunling.mediacenter.db.model.RoleFunction;
import com.yunling.mediacenter.db.model.Roles;
import com.yunling.mediacenter.db.model.UserRole;
import com.yunling.mediacenter.db.service.AuthorityService;
import com.yunling.mediacenter.db.service.OperationLogService;
import com.yunling.mediacenter.db.service.RoleFunctionService;
import com.yunling.mediacenter.db.service.RoleService;
import com.yunling.mediacenter.db.service.UserRoleService;
import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.Functions;
import com.yunling.mediacenter.web.actions.vo.RoleVo;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;
import com.yunling.mediacenter.web.struts2.EnumUtils;

@Results({
	@Result(name = "redirect-list", location = "role!list.action", type = "redirect")
	,
	@Result(name = "redirect-showauth", location = "role!showauth.action?roleid=${roleid}", type = "redirect")
	})
public class RoleAction extends AbstractUserAwareAction {
	private Logger log = LoggerFactory.getLogger(RoleAction.class);
	
	private String jsonStr;
	private Long roleId;
	private String roleName;
	private String describe;
	
	private RoleService roleService;
	private RoleFunctionService roleFunctionService;
	private OperationLogService operationLogService;
	
	private HttpServletResponse rawResponse = ServletActionContext.getResponse();
	
	public String execute() throws Exception {
		log.debug("Role Manager.");
		List<Roles> roles = roleService.getRoles();
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(roles);
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
	 * @author LingJun
	 * @date 2011/9/14
	 * @return
	 * @throws Exception
	 */
	public String check() throws Exception {
		log.debug("Role name check.");
		if(roleService.getRoleByName(roleName) == null) {
			rawResponse.getWriter().print(true);
		} else {
			rawResponse.getWriter().print(false);
		}
		return null;
	}
	
	/**
	 * @author LingJun
	 * @date 2011/9/14
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		log.debug("create Role.");
		Roles role = new Roles();
		if(roleName == null || roleName.isEmpty()) {
			return null;
		}
		role.setRoleName(roleName);
		if(StringUtils.isEmpty(describe)) {
			describe = "";
		}
		role.setDescribe(describe);
		roleService.createRole(role);
		return null;
	}
	
	/**
	 * @author LingJun
	 * @date 2011/9/14
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		log.debug("update Role.");
		Roles role = new Roles();
		if(roleName == null || roleName.isEmpty()) {
			return null;
		}
		role.setRoleId(roleId);
		role.setRoleName(roleName);
		if(StringUtils.isEmpty(describe)) {
			describe = "";
		}
		role.setDescribe(describe);
		roleService.updateRole(role);
		return null;
	}
	
	/**
	 * @author LingJun
	 * @date 2011/9/14
	 * @return
	 * @throws Exception
	 */
	public String delRole() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		log.debug("Delete Role " + roleId);
		List<UserRole> list = userRoleService.getUserRoleByRoleId(roleId);
		if (list != null && list.size() > 0) {
			rawResponse.getWriter().print(
					this.getText("role.can.not.be.deleted"));
			// request.put("alert", this.getText("role.can.not.be.deleted"));
			// request.put("location", "role.action");
			// return "prompt";
			return null;
		}
		roleFunctionService.delRoleFunctionByRoleId(roleId);
		roleService.delRoleById(roleId);
		rawResponse.getWriter().print(this.getText("role.have.been.deleted"));
		// request.put("alert", this.getText("role.have.been.deleted"));
		// request.put("location", "role.action");
		// return "prompt";
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String function() throws Exception {
		List<Functions> function = roleFunctionService
				.getFunctionsByRoleid(roleId);
		List<Functions> list = new ArrayList<Functions>();
		for (Functions f : Functions.values()) {
			list.add(f);
		}
		if (function.size() >= 1) {
			for (Functions f : function) {
				for (Functions f2 : Functions.values()) {
					if (f.equals(f2)) {
						list.remove(f);
					}
				}
			}
		}
		Map<String, String> map = getLocalizedMap(this, list);
		Map<String, String> map2 = getLocalizedMap(this, function);
		// ActionContext.getContext().put("list",map);
		// ActionContext.getContext().put("function", map2);
		request.put("list", map);
		request.put("function", map2);
		return "function";
	}
	
	public String saveFunctions() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		String ids = ServletActionContext.getRequest().getParameter("ids");
		if (ids.equals("-999")) {
			roleFunctionService.delRoleFunctionByRoleId(roleId);
			OperationLog log = createOpertionLog(roleId, "Y", "role-function",
					"delete");
			operationLogService.add(log);
			// ActionContext.getContext().put("alert",this.getText("role.already.use"));
			// ActionContext.getContext().put("location","role!list.action");
			// request.put("alert", this.getText("role.already.use"));
			request.put("alert", this.getText("change.role.function.success"));
			request.put("location", "role.action");
			return "prompt";
		}
		String[] funcid = ids.substring(5).split(",");
		RoleFunction rolefunction = new RoleFunction();
		rolefunction.setRoleId(roleId);
		roleFunctionService.delRoleFunctionByRoleId(roleId);
		for (String fid : funcid) {
			rolefunction.setFuncId(fid);
			roleFunctionService.addRoleFunction(rolefunction);
		}
		operationLogService.add(createOpertionLog(roleId, "Y", "role-function",
				"update"));
		// ActionContext.getContext().put("alert",this.getText("change.role.function.success"));
		// ActionContext.getContext().put("location","role!list.action");
		request.put("alert", this.getText("change.role.function.success"));
		request.put("location", "role.action");
		return "prompt";
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
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the describe
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * @param describe the describe to set
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
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
	 * @return the roleFunctionService
	 */
	public RoleFunctionService getRoleFunctionService() {
		return roleFunctionService;
	}

	/**
	 * @param roleFunctionService the roleFunctionService to set
	 */
	public void setRoleFunctionService(RoleFunctionService roleFunctionService) {
		this.roleFunctionService = roleFunctionService;
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






	private List<Roles> roles;
	private long roleid;
	private String[] auth;
	private RoleVo vo;
	
	private UserRoleService userRoleService;
	private OperationLogService operationlogservice;
	private AuthorityService authorityService;
	
	public String list() throws Exception {
		Integer sum = roleService.selectRoleCount();
		Integer page = 0;
		try {
			page = Integer.parseInt(ServletActionContext.getRequest()
					.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		Map<String, Object> map = roleService.list(page, sum);
		// ActionContext.getContext().put("map",map);
		request.put("map", map);
		return "list";
	}

	public String add() throws Exception {
		return "add";
	}

	public String addRole() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		Roles role = new Roles();
		role.setRoleName(vo.getRolename());
		role.setDescribe(vo.getDescribe());
		roleService.addRole(role);
		operationlogservice.add(createOpertionLog(role.getRoleId(), "Y", "role", "add"));
		request.put("alert", this.getText("role.add.success"));
		request.put("location", "role!list.action");
		return "prompt";
	}

	public String edit() throws Exception {
		Roles role = roleService.getRoleById(roleid);
		request.put("role", role);
		return "edit";
	}

	public String editRole() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		Roles role = new Roles();
		role.setRoleId(vo.getRoleid());
		role.setRoleName(vo.getRolename());
		role.setDescribe(vo.getDescribe());
		roleService.updateRole(role);
		OperationLog log = createOpertionLog(vo.getRoleid(), "Y", "role",
				"update");
		operationlogservice.add(log);
		request.put("alert", this.getText("role.update.success"));
		request.put("location", "role!list.action");
		return "prompt";
	}

	public String del() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		List<UserRole> userRole = userRoleService.getUserRoleByRoleId(roleid);
		if (userRole.size() < 1) {
			roleFunctionService.delRoleFunctionByRoleId(roleid);
			roleService.delRoleById(roleid);
			OperationLog log = createOpertionLog(roleid, "Y", "role", "delete");
			operationlogservice.add(log);
			request.put("alert", this.getText("role.delete.success"));
			request.put("location", "role!list.action");
		} else {
			OperationLog log = createOpertionLog(roleid, "N", "role", "delete");
			operationlogservice.add(log);
			request.put("alert", this.getText("role.already.use"));
			request.put("location", "role!list.action");
		}
		return "prompt";
	}
	
	public String showauth() throws Exception {
		List<Authority> authorityList = authorityService.listGrantedByRole(roleid);
		request.put("authList", authorityList);
		Roles role = roleService.getRoleById(roleid);
		request.put("role", role);
		return "showauth";
	}
	
	public String saveauth() throws Exception {
		if (auth != null) {
			List<Long> authIdList = new ArrayList<Long>();
			for(String id : auth) {
				try {
					long authId = Long.parseLong(id);
					authIdList.add(authId);
				} catch (Exception e) {
				}
			}
			try {
				roleService.updateAuthorities(roleid, authIdList);
			} catch(Exception e) {
				log.error("Failed to update authority", e);
				throw e;
			}
			String message = getText("role.auth.saved");
			session.put(Constants.FLASH_MESSAGE, message);
		}
		
		return "redirect-showauth";
	}

	public String saveRoleFunction() throws Exception {
		rawResponse.setContentType("text/html;charset=utf-8");
		String ids = ServletActionContext.getRequest().getParameter("ids");
		if (ids.equals("-999")) {
			roleFunctionService.delRoleFunctionByRoleId(roleId);
			OperationLog log = createOpertionLog(roleId, "Y", "role-function",
					"delete");
			operationlogservice.add(log);
			// ActionContext.getContext().put("alert",this.getText("role.already.use"));
			// ActionContext.getContext().put("location","role!list.action");
			request.put("alert", this.getText("role.already.use"));
			request.put("location", "role.action");
			return "prompt";
		}
		String[] funcid = ids.substring(5).split(",");
		RoleFunction rolefunction = new RoleFunction();
		rolefunction.setRoleId(roleId);
		roleFunctionService.delRoleFunctionByRoleId(roleId);
		for (String fid : funcid) {
			rolefunction.setFuncId(fid);
			roleFunctionService.addRoleFunction(rolefunction);
		}
		operationlogservice.add(createOpertionLog(roleId, "Y", "role-function",
				"update"));
		// ActionContext.getContext().put("alert",this.getText("change.role.function.success"));
		// ActionContext.getContext().put("location","role!list.action");
		request.put("alert", this.getText("change.role.function.success"));
		request.put("location", "role.action");
		return "prompt";
	}

	public OperationLog createOpertionLog(long id, String issuccess,
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

	public static Map<String, String> getLocalizedMap(TextProvider textProvider) {
		return EnumUtils.getResourceOptions(textProvider, "enum.functions.",
				Functions.values());
	}

	public static Map<String, String> getLocalizedMap(
			TextProvider textProvider, Collection<Functions> values) {
		return EnumUtils.getResourceOptions(textProvider, "enum.functions.",
				values);
	}
	
	public OperationLogService getOperationlogservice() {
		return operationlogservice;
	}

	public void setOperationlogservice(OperationLogService operationlogservice) {
		this.operationlogservice = operationlogservice;
	}
	public long getRoleid() {
		return roleid;
	}

	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}

	public RoleVo getVo() {
		return vo;
	}

	public void setVo(RoleVo vo) {
		this.vo = vo;
	}

	public UserRoleService getUserRoleService() {
		return userRoleService;
	}

	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public String[] getAuth() {
		return auth;
	}

	public void setAuth(String[] auth) {
		this.auth = auth;
	}
}

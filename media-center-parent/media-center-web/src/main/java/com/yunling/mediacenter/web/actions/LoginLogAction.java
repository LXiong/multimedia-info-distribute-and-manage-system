package com.yunling.mediacenter.web.actions;

import org.apache.commons.lang.xwork.StringUtils;

import com.yunling.commons.utils.PageUtil;
import com.yunling.mediacenter.db.service.LoginLogService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class LoginLogAction extends AbstractUserAwareAction {
	private LoginLogService loginlogservice;
	private String from;
	private String to;
	private String isok;

	@Override
	public String execute() throws Exception {
		if (!StringUtils.equals("Y", isok) && !StringUtils.equals("N", isok)) {
			isok = "";
		}
		
		int total = loginlogservice.countBy(from, to, isok);
		PageUtil pu = new PageUtil(getSingleParam("page"), total,
				webConfig.getPageSize());
		request.put(
				"logList",
				loginlogservice.listBy(from, to, isok, pu.getBegin(),
						pu.getEnd()));
		request.put("pageBean", pu);
		return SUCCESS;
	}

	public LoginLogService getLoginlogservice() {
		return loginlogservice;
	}

	public void setLoginlogservice(LoginLogService loginlogservice) {
		this.loginlogservice = loginlogservice;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	public String getIsok() {
		return isok;
	}

	public void setIsok(String isok) {
		this.isok = isok;
	}
}

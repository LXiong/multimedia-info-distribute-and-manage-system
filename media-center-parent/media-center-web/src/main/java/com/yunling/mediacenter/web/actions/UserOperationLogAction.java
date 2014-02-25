package com.yunling.mediacenter.web.actions;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;

import com.yunling.commons.utils.PageUtil;
import com.yunling.mediacenter.db.model.OperationLog;
import com.yunling.mediacenter.db.service.OperationLogService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class UserOperationLogAction extends AbstractUserAwareAction {
	private OperationLogService operationLogService;
	private String from;
	private String to;
	private String isok;

	@Override
	public String execute() throws Exception {
		if (!StringUtils.equals("Y", isok) && !StringUtils.equals("N", isok)) {
			isok = "";
		}
		int total = operationLogService.countBy(from, to, isok);
		PageUtil pageUtil = new PageUtil(getSingleParam("page"), total,
				webConfig.getPageSize());
		List<OperationLog> logList = operationLogService.listBy(from, to, isok,
				pageUtil.getBegin(), pageUtil.getEnd());
		request.put("logList", logList);
		request.put("pageBean", pageUtil);
		return SUCCESS;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
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
	 * @param to
	 *            the to to set
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

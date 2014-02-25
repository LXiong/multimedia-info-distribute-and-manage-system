package com.yunling.mediacenter.web.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.AuthenticateRecord;
import com.yunling.mediacenter.db.service.AuthenticateRecordService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-11-23
 * 
 */
public class AuthenticateRecordAction extends AbstractUserAwareAction {
	private Logger log = LoggerFactory
			.getLogger(AuthenticateRecordAction.class);
	private List<AuthenticateRecord> logList;
	private AuthenticateRecordService authenticateRecordService;
	private int page;
	private int pageNums;
	private List<String> statusList;
	private String result;
	private String from;
	private String to;
	private String stbMac;

	@Override
	public String execute() throws Exception {
		statusList = authenticateRecordService.getResults();
		AuthenticateRecord authenticateRecord = new AuthenticateRecord();
		authenticateRecord.setAuthStatus(result);
		authenticateRecord.setStbMac(stbMac);
		int counts = authenticateRecordService.count(authenticateRecord, from,
				to);
		int pageSize = authenticateRecordService.getPageSize();
		pageNums = counts % pageSize == 0 ? counts / pageSize : counts
				/ pageSize + 1;
		if (page == 0) {
			page = 1;
		}
		if (pageNums == 0) {
			page = 0;
			return SUCCESS;
		}
		int begin = (page - 1) * pageSize + 1;
		int end = page * pageSize;
		log.debug("begin {} and end {}", begin, end);
		log.debug("from {} to {}", from, to);
		logList = authenticateRecordService.getLogs(authenticateRecord, begin,
				end, from, to);
		log.debug("result logList: {}", logList);
		return SUCCESS;
	}

	/**
	 * @return the logList
	 */
	public List<AuthenticateRecord> getLogList() {
		return logList;
	}

	/**
	 * @param logList
	 *            the logList to set
	 */
	public void setLogList(List<AuthenticateRecord> logList) {
		this.logList = logList;
	}

	/**
	 * @return the authenticateRecordService
	 */
	public AuthenticateRecordService getAuthenticateRecordService() {
		return authenticateRecordService;
	}

	/**
	 * @param authenticateRecordService
	 *            the authenticateRecordService to set
	 */
	public void setAuthenticateRecordService(
			AuthenticateRecordService authenticateRecordService) {
		this.authenticateRecordService = authenticateRecordService;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the pageNums
	 */
	public int getPageNums() {
		return pageNums;
	}

	/**
	 * @param pageNums
	 *            the pageNums to set
	 */
	public void setPageNums(int pageNums) {
		this.pageNums = pageNums;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
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

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the stbMac
	 */
	public String getStbMac() {
		return stbMac;
	}

	/**
	 * @param stbMac
	 *            the stbMac to set
	 */
	public void setStbMac(String stbMac) {
		this.stbMac = stbMac;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
}

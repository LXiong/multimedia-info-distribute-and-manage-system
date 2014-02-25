package com.yunling.mediacenter.web.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.WebControlRecord;
import com.yunling.mediacenter.db.service.WebControlRecordService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-11-23
 * 
 */
public class WebControlRecordAction extends AbstractUserAwareAction {
	private List<WebControlRecord> logList;
	private WebControlRecordService webControlRecordService;
	private int page;
	private int pageNums;
	private List<String> commands;
	private String command;
	private String from;
	private String to;
	private String stbMac;
	private Logger log = LoggerFactory.getLogger(WebControlRecordAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		commands = webControlRecordService.getCommands();
		WebControlRecord webControlRecord = new WebControlRecord();
		webControlRecord.setCommand(command);
		webControlRecord.setStbMac(stbMac);
		int counts = webControlRecordService.count(webControlRecord, from, to)
				.getCounts();
		int pageSize = webControlRecordService.getPageSize();
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
		logList = webControlRecordService.getLogs(webControlRecord, begin, end,
				from, to);
		log.debug("result logList: {}", logList);
		return SUCCESS;
	}

	/**
	 * @return the logList
	 */
	public List<WebControlRecord> getLogList() {
		return logList;
	}

	/**
	 * @param logList
	 *            the logList to set
	 */
	public void setLogList(List<WebControlRecord> logList) {
		this.logList = logList;
	}

	/**
	 * @return the webControlRecordService
	 */
	public WebControlRecordService getWebControlRecordService() {
		return webControlRecordService;
	}

	/**
	 * @param webControlRecordService
	 *            the webControlRecordService to set
	 */
	public void setWebControlRecordService(
			WebControlRecordService webControlRecordService) {
		this.webControlRecordService = webControlRecordService;
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
	 * @return the commands
	 */
	public List<String> getCommands() {
		return commands;
	}

	/**
	 * @param commands
	 *            the commands to set
	 */
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command
	 *            the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
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

}

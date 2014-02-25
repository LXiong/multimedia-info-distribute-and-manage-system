package com.yunling.mediacenter.web.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.WatcherReportRecord;
import com.yunling.mediacenter.db.service.WatcherReportRecordService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-11-23
 * 
 */
public class WatcherReportRecordAction extends AbstractUserAwareAction {
	private List<WatcherReportRecord> logList;
	private WatcherReportRecordService watcherReportRecordService;
	private int page;
	private int pageNums;
	private String from;
	private String to;
	private String stbMac;
	private Logger log = LoggerFactory
			.getLogger(WatcherReportRecordAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		WatcherReportRecord watcherReportRecord = new WatcherReportRecord();
		watcherReportRecord.setMac(stbMac);
		int counts = watcherReportRecordService.count(watcherReportRecord,
				from, to).getCounts();
		int pageSize = watcherReportRecordService.getPageSize();
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
		logList = watcherReportRecordService.getLogs(watcherReportRecord,
				begin, end, from, to);
		log.debug("result logList: {}", logList);
		return SUCCESS;
	}

	/**
	 * @return the logList
	 */
	public List<WatcherReportRecord> getLogList() {
		return logList;
	}

	/**
	 * @param logList
	 *            the logList to set
	 */
	public void setLogList(List<WatcherReportRecord> logList) {
		this.logList = logList;
	}

	/**
	 * @return the watcherReportRecordService
	 */
	public WatcherReportRecordService getWatcherReportRecordService() {
		return watcherReportRecordService;
	}

	/**
	 * @param watcherReportRecordService
	 *            the watcherReportRecordService to set
	 */
	public void setWatcherReportRecordService(
			WatcherReportRecordService watcherReportRecordService) {
		this.watcherReportRecordService = watcherReportRecordService;
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

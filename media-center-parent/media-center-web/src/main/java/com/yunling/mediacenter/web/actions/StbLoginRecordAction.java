package com.yunling.mediacenter.web.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.StbLoginRecord;
import com.yunling.mediacenter.db.service.StbLoginRecordService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-11-23
 * 
 */
public class StbLoginRecordAction extends AbstractUserAwareAction {
	private Logger log = LoggerFactory.getLogger(StbLoginRecordAction.class);
	private List<StbLoginRecord> logList;
	private StbLoginRecordService stbLoginRecordService;
	private int page;
	private int pageNums;
	private List<String> results;
	private String result;
	private String from;
	private String to;
	private String stbMac;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		results = stbLoginRecordService.getResults();
		StbLoginRecord stbLoginRecord = new StbLoginRecord();
		stbLoginRecord.setStatus(result);
		stbLoginRecord.setMac(stbMac);
		int counts = stbLoginRecordService.count(stbLoginRecord, from, to)
				.getCounts();
		int pageSize = stbLoginRecordService.getPageSize();
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
		logList = stbLoginRecordService.getLogs(stbLoginRecord, begin, end,
				from, to);
		log.debug("result logList: {}", logList);
		return SUCCESS;
	}

	/**
	 * @return the logList
	 */
	public List<StbLoginRecord> getLogList() {
		return logList;
	}

	/**
	 * @param logList
	 *            the logList to set
	 */
	public void setLogList(List<StbLoginRecord> logList) {
		this.logList = logList;
	}

	/**
	 * @return the stbLoginRecordService
	 */
	public StbLoginRecordService getStbLoginRecordService() {
		return stbLoginRecordService;
	}

	/**
	 * @param stbLoginRecordService
	 *            the stbLoginRecordService to set
	 */
	public void setStbLoginRecordService(
			StbLoginRecordService stbLoginRecordService) {
		this.stbLoginRecordService = stbLoginRecordService;
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
	 * @return the results
	 */
	public List<String> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(List<String> results) {
		this.results = results;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
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

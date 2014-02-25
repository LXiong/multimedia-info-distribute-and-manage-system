package com.yunling.mediacenter.web.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-10-13
 * 
 */
public class AuditAction extends AbstractUserAwareAction {
	private Logger log = LoggerFactory.getLogger(AuditAction.class);
	private List<Stb> stbs;
	private StbService stbService;
	private String stbStatus;
	private String macStr;
	private Long typeId;
	private Long groupId;
	private int pageNums; // Total Pages
	private int page; // Current Page

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		int pageSize = webConfig.getPageSize();
		Stb stb = new Stb();
		if (this.stbStatus == null) {
			this.stbStatus = "";
		}
		stb.setStbStatus(this.stbStatus);
		stb.setTypeId(typeId);
		stb.setGroupId(groupId);
		// Get total records
		int counts = stbService.countAuditStbs(stb, getCurrentUserId());
		log.debug("stb list size: {}", counts);
		pageNums = counts % pageSize == 0 ? counts / pageSize : counts
				/ pageSize + 1;
		if (page == 0) {
			page = 1;
		}
		if (pageNums == 0) {
			page = 0;
			return SUCCESS;
		}

		stbs = stbService.searchAuditStbs(stb, (page - 1) * pageSize + 1, page
				* pageSize, getCurrentUserId());
		log.debug("result stb list: {}", stbs);
		return SUCCESS;
	}

	/**
	 * Get Pending STB List
	 * 
	 * @return
	 * @throws Exception
	 */
	public String stbList() throws Exception {

		return SUCCESS;
	}

	/**
	 * Audit
	 * 
	 * @return
	 * @throws Exception
	 */
	public String audit() throws Exception {
		Stb stb = new Stb();
		String[] macArr = macStr.split("l");
		for (int i = 0; i < macArr.length; i++) {
			stb.setStbMac(macArr[i]);
			stb.setStbStatus("active");
			stb.setStbDisabled("f");
			stbService.audit(stb);
		}
		return null;
	}

	/**
	 * Refuse
	 * 
	 * @return
	 * @throws Exception
	 */
	public String refuse() throws Exception {
		Stb stb = new Stb();
		String[] macArr = macStr.split("l");
		for (int i = 0; i < macArr.length; i++) {
			stb.setStbMac(macArr[i]);
			stb.setStbStatus("refused");
			stbService.audit(stb);
		}
		return null;
	}

	/**
	 * @return the stbs
	 */
	public List<Stb> getStbs() {
		return stbs;
	}

	/**
	 * @param stbs
	 *            the stbs to set
	 */
	public void setStbs(List<Stb> stbs) {
		this.stbs = stbs;
	}

	/**
	 * @return the stbService
	 */
	public StbService getStbService() {
		return stbService;
	}

	/**
	 * @param stbService
	 *            the stbService to set
	 */
	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

	/**
	 * @return the stbStatus
	 */
	public String getStbStatus() {
		return stbStatus;
	}

	/**
	 * @param stbStatus
	 *            the stbStatus to set
	 */
	public void setStbStatus(String stbStatus) {
		this.stbStatus = stbStatus;
	}

	/**
	 * @return the macStr
	 */
	public String getMacStr() {
		return macStr;
	}

	/**
	 * @param macStr
	 *            the macStr to set
	 */
	public void setMacStr(String macStr) {
		this.macStr = macStr;
	}

	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId
	 *            the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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

}

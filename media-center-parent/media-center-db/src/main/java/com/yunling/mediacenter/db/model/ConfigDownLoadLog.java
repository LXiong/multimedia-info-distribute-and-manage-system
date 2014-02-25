package com.yunling.mediacenter.db.model;

import java.util.Date;

public class ConfigDownLoadLog {
	private long userId;
	private Date downloadtime;
	private String downloaditem;
	private String userName;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getDownloadtime() {
		return downloadtime;
	}

	public void setDownloadtime(Date downloadtime) {
		this.downloadtime = downloadtime;
	}

	public String getDownloaditem() {
		return downloaditem;
	}

	public void setDownloaditem(String downloaditem) {
		this.downloaditem = downloaditem;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}

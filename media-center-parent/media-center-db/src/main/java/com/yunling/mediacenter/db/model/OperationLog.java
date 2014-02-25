package com.yunling.mediacenter.db.model;

import java.util.Date;

public class OperationLog {
	private Date operationtime;
	private long CurrentuserId;
	private String action;
	private String updateobject;
	private long objectid;
	private String issuccess;
	private String userName;

	public Date getOperationtime() {
		return operationtime;
	}

	public void setOperationtime(Date operationtime) {
		this.operationtime = operationtime;
	}

	public long getCurrentuserId() {
		return CurrentuserId;
	}

	public void setCurrentuserId(long currentuserId) {
		CurrentuserId = currentuserId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUpdateobject() {
		return updateobject;
	}

	public void setUpdateobject(String updateobject) {
		this.updateobject = updateobject;
	}

	public long getObjectid() {
		return objectid;
	}

	public void setObjectid(long objectid) {
		this.objectid = objectid;
	}

	public String getIssuccess() {
		return issuccess;
	}

	public void setIssuccess(String issuccess) {
		this.issuccess = issuccess;
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

package com.yunling.mediacenter.db.model;

import java.util.Date;

/**
 * @author LingJun
 * @date 2010-12-20
 * 
 */
public class BoxDownLog {
	private Long id;
	private String mac;
	private String policyNumber;
	private String videoName;
	private Long doneSize;
	private Long totalSize;
	private Date logTime;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac
	 *            the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * @return the policyNumber
	 */
	public String getPolicyNumber() {
		return policyNumber;
	}

	/**
	 * @param policyNumber
	 *            the policyNumber to set
	 */
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	/**
	 * @return the videoName
	 */
	public String getVideoName() {
		return videoName;
	}

	/**
	 * @param videoName
	 *            the videoName to set
	 */
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	/**
	 * @return the doneSize
	 */
	public Long getDoneSize() {
		return doneSize;
	}

	/**
	 * @param doneSize
	 *            the doneSize to set
	 */
	public void setDoneSize(Long doneSize) {
		this.doneSize = doneSize;
	}

	/**
	 * @return the totalSize
	 */
	public Long getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize
	 *            the totalSize to set
	 */
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @return the logTime
	 */
	public Date getLogTime() {
		return logTime;
	}

	/**
	 * @param logTime
	 *            the logTime to set
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

}

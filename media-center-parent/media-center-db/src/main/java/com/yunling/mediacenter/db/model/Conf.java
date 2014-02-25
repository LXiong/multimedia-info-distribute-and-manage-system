/**
 * 
 */
package com.yunling.mediacenter.db.model;

import java.util.Date;

/**
 * @author LingJun
 * @date 2010-11-12
 * 
 */
public class Conf {
	private Long confId;
	private String confName;
	private String confVersion;
	private Date createTime;
	private Date updateTime;
	
	private int begin;
	private int end;
	
	/**
	 * @return the confId
	 */
	public Long getConfId() {
		return confId;
	}
	/**
	 * @param confId the confId to set
	 */
	public void setConfId(Long confId) {
		this.confId = confId;
	}
	/**
	 * @return the confName
	 */
	public String getConfName() {
		return confName;
	}
	/**
	 * @param confName the confName to set
	 */
	public void setConfName(String confName) {
		this.confName = confName;
	}
	/**
	 * @return the confVersion
	 */
	public String getConfVersion() {
		return confVersion;
	}
	/**
	 * @param confVersion the confVersion to set
	 */
	public void setConfVersion(String confVersion) {
		this.confVersion = confVersion;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the begin
	 */
	public int getBegin() {
		return begin;
	}
	/**
	 * @param begin the begin to set
	 */
	public void setBegin(int begin) {
		this.begin = begin;
	}
	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}
	
}

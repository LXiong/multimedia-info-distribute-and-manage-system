/**
 * 
 */
package com.yunling.mediacenter.web.actions.vo;

import java.util.Date;

/**
 * @author LingJun
 * @date 2010-12-29
 * 
 */
public class MediaDownStatusVo {
	private String stbMac;
	private String percent;
	private Date startTime;
	private Date endTime;
	private Long speed;
	/**
	 * @return the stbMac
	 */
	public String getStbMac() {
		return stbMac;
	}
	/**
	 * @param stbMac the stbMac to set
	 */
	public void setStbMac(String stbMac) {
		this.stbMac = stbMac;
	}
	
	/**
	 * @return the percent
	 */
	public String getPercent() {
		return percent;
	}
	/**
	 * @param percent the percent to set
	 */
	public void setPercent(String percent) {
		this.percent = percent;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the speed
	 */
	public Long getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(Long speed) {
		this.speed = speed;
	}
	
}

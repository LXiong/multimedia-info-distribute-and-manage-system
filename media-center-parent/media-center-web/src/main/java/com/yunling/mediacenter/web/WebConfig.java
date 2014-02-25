package com.yunling.mediacenter.web;

/**
 * This class is used to store configuration values.
 * The values should be assigned in Spring configuration file.
 * @author Simon Xianyu
 *
 */
public class WebConfig {
	
	private int pageSize;
	private String picUrl;
	private String instantMessagePath;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getInstantMessagePath() {
		return instantMessagePath;
	}

	public void setInstantMessagePath(String instantMessagePath) {
		this.instantMessagePath = instantMessagePath;
	}

}

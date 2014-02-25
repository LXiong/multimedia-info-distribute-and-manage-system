package com.yunling.mediaman.web;

public class WebConfig {

	private int pageSize = 20;
	private String uploadPath;
	// private String videoPath;
	private String previewPath;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	/**
	 * @return the previewPath
	 */
	public String getPreviewPath() {
		return previewPath;
	}

	/**
	 * @param previewPath
	 *            the previewPath to set
	 */
	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}

}

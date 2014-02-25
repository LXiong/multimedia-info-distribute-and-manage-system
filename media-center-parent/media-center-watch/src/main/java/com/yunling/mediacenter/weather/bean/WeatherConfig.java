package com.yunling.mediacenter.weather.bean;

public class WeatherConfig {
	String dataRoot;
	String ftpImageRoot;
	//String urlTemplate = "http://m.weather.com.cn/data/{city_code}.html";

	public String getDataRoot() {
		return dataRoot;
	}

	public void setDataRoot(String dataRoot) {
		this.dataRoot = dataRoot;
	}

	public String getFtpImageRoot() {
		return ftpImageRoot;
	}

	public void setFtpImageRoot(String ftpImageRoot) {
		this.ftpImageRoot = ftpImageRoot;
	}

}

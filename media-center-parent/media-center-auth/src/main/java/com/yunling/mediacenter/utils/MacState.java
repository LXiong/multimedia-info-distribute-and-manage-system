package com.yunling.mediacenter.utils;

public enum MacState {
	NEW("0001","You are a new machine, I`ll register it into the database, waiting for auditing, please."), 
	//STRANGER("0002", "You look like a stranger, don`t connect to me."), 
	WAITING_AUDIT("0003","We are auditing you register, waiting for the result, please."), 
	PENDING("0003","We are auditing you register, waiting for the result, please."),
	BLOCKED("0004", "You have been blocked."), 
	MD5_WRONG("0005","I am sorry, your md5 code is wrong, confirm it , please."), 
	NOTINSTALLED("0006", "No software installed."),
	INSTALLED_PENDING("0007", "Software has been installed, waiting for auditing."),
	NORMAL("0000", "ok");
	private String status;
	private String message;

	private MacState(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String status() {
		return status;
	}

	public String message() {
		return message;
	}
}

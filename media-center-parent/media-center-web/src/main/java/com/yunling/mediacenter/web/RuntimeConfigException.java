package com.yunling.mediacenter.web;

public class RuntimeConfigException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4603589821844263473L;
	
	private String key;
	
	public RuntimeConfigException(String key, String message) {
		super(message);
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}

package com.yunling.mediacenter.utils;

import java.util.HashMap;
import java.util.Map;

public class StbMessage {
	String target = "STB";
	String action;
	Map<String, String> params = new HashMap<String, String>();
	
	public StbMessage(Map<String, String> params, String...strings){
		this.params = params;
		if(strings.length < 1){
			this.action = "zzzzzzzzzzzzzzzzzzzz";
			this.target = "zzzzzzzzzzzzzzzzzzzz";
		}
		if(strings.length > 0){
			this.action = strings[0];
		}
		if(strings.length > 1){
			this.target = strings[1];
		}
	}

	public String message() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("target=" + target + "\r\n");
		strBuf.append("action=" + action + "\r\n");
		for (String key : params.keySet()) {
			strBuf.append(key + "=" + params.get(key) + "\r\n");
		}
		strBuf.append("\r\n");
		return strBuf.toString();
	}
}
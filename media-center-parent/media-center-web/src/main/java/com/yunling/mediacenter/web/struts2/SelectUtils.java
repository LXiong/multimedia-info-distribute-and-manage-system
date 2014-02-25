package com.yunling.mediacenter.web.struts2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.TextProvider;

public class SelectUtils {
	public static Map<String,String> getOptionMap(TextProvider textProvider,  Map<String,String> originMap) {
		Map<String, String> result = new HashMap<String, String>();
		for(Map.Entry<String,String> entry:originMap.entrySet()) {
				result.put(entry.getKey(), textProvider.getText(entry.getValue()) );
		}
		return result;
	}
	public static Map<String,String> getOptionMap(TextProvider textProvider, Collection<String> values, Map<String,String> originMap) {
		Map<String, String> result = new HashMap<String, String>();
		for(String value : values) {
			if (originMap.containsKey(value)) {
				result.put(value, textProvider.getText(originMap.get(value)) );
			} else {
				result.put(value, value);
			}
		}
		return result;
	}
}

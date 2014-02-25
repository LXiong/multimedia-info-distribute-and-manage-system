package com.yunling.mediacenter.web.struts2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.TextProvider;

public class EnumUtils {

	public static <T extends Enum<T>> Map<String,String> getResourceOptions(TextProvider textProvider,String head, T[] values) {
		Map<String,String> options = new HashMap<String, String>();
		for(T f : values) {
			options.put(f.name(), textProvider.getText(head+f.name()) );
		}
		return options;
	}
	
	public static <T extends Enum<T>> Map<String,String> getResourceOptions(TextProvider textProvider,String head, Collection<T> values) {
		Map<String,String> options = new HashMap<String, String>();
		for(T f : values) {
			options.put(f.name(), textProvider.getText(head+f.name()) );
		}
		return options;
	}
	
}

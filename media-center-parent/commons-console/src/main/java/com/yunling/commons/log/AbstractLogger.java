package com.yunling.commons.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

abstract public class AbstractLogger {
	public static final String TIME="TIME";
	protected Map<String, Object> createParam() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(TIME, new Date());
		return paramMap;
	}
}

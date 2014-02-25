package com.yunling.policyman.db.model;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

public class Helper {
	private static FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	public static String format(Object v) {
		if (v == null) {
			return null;
		}
		if (v instanceof Date) {
			return fdf.format(v);
		}
		
		return String.valueOf(v);
	}
}

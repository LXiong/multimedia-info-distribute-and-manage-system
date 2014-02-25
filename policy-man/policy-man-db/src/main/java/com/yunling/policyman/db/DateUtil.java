package com.yunling.policyman.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date parse(String value, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
		}
		return null;
	}
}

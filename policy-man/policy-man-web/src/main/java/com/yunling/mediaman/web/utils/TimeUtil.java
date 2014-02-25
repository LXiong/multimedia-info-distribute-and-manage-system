package com.yunling.mediaman.web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String beginningOfDay(Date day) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return sdf.format(c.getTime());
	}

	public static String beginningOfDay() {
		return beginningOfDay(Calendar.getInstance().getTime());
	}

	public static String endingOfDay(Date day) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return sdf.format(c.getTime());
	}

	public static String endingOfDay() {
		return endingOfDay(Calendar.getInstance().getTime());
	}

	public static String convert(Date day) {
		return sdf.format(day);
	}
}
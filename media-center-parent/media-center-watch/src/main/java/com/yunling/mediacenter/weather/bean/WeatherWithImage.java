package com.yunling.mediacenter.weather.bean;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WeatherWithImage {
	static String IMAGE_BASE_DIR = null;
	String city;
	String date;
	String weather;
	String dayFileName;
	String nightFileName;
	String lowTemp;
	String highTemp;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getImageDaytime() {
		return IMAGE_BASE_DIR + "d" + dayFileName + ".gif";
	}

	public void setImageDaytime(String path, String num) {
		setBaseDir(path);
		dayFileName = String.valueOf(Integer.parseInt(num) + 1);
	}

	public String getImageNight() {
		return IMAGE_BASE_DIR + "d" + nightFileName + ".gif";
	}

	public void setImageNight(String path, String num) {
		setBaseDir(path);
		if ("99".equalsIgnoreCase(num)) {
			nightFileName = dayFileName;
		} else {
			nightFileName = String.valueOf(Integer.parseInt(num) + 1);
		}
	}

	public String getLowTemp() {
		return lowTemp;
	}

	public void setLowTemp(String lowTemp) {
		this.lowTemp = lowTemp;
	}

	public String getHighTemp() {
		return highTemp;
	}

	public void setHighTemp(String highTemp) {
		this.highTemp = highTemp;
	}

	public String getTemp() {
		return getHighTemp() + "~" + getLowTemp();
	}

	public void setBaseDir(String path) {
		if (IMAGE_BASE_DIR == null || "".equals(IMAGE_BASE_DIR)) {
			if (!path.endsWith("/")) {
				path = path + "/";
			}
			IMAGE_BASE_DIR = path;
		}
	}

	public void setTemp(String temp) {
		if (temp.contains("~")) {
			String[] temps = temp.split("~");
			if (temps[0].compareTo(temps[1]) >= 0) {
				setHighTemp(temps[0]);
				setLowTemp(temps[1]);
			} else {
				setHighTemp(temps[1]);
				setLowTemp(temps[0]);
			}
		}
	}

	public Map<String, String> asMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("date", date);
		map.put("weather", weather);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		String imagePath;
		if (hour > 6 && hour < 18) {
			imagePath = getImageDaytime();
		} else {
			imagePath = getImageNight();
		}
		map.put("weather_pic_file_path", imagePath);
		map.put("min_temp", lowTemp);
		map.put("max_temp", highTemp);
		map.put("city", city);
		return map;
	}
}

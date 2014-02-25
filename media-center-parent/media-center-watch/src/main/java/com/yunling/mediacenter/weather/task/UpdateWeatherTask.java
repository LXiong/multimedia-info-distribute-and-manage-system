package com.yunling.mediacenter.weather.task;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import com.yunling.mediacenter.db.model.City;
import com.yunling.mediacenter.db.service.CityService;
import com.yunling.mediacenter.utils.UpdateAndLoadWeatherUtil;
import com.yunling.mediacenter.weather.bean.WeatherConfig;
import com.yunling.mediacenter.weather.bean.WeatherWithImage;

public class UpdateWeatherTask extends TimerTask {
	static HttpClient client = new DefaultHttpClient();
	static Map<String, String> requestTemplate = new HashMap<String, String>();
	WeatherConfig config;
	static String URL_TEMPLATE = "http://m.weather.com.cn/data/{city_code}.html";
	CityService cityService;
	Map<String, WeatherWithImage> allCitiesWeather;

	@Override
	public void run() {
		Iterator<City> it = cityService.findAll().iterator();
		Map<String, HttpGet> cityRequests = new HashMap<String, HttpGet>();
		while (it.hasNext()) {
			City city = it.next();
			String cityQueryWeatherId = city.getQueryWeatherId();
			if (cityQueryWeatherId != null) {
				String url = URL_TEMPLATE.replace("{city_code}",
						cityQueryWeatherId);
				HttpGet get = new HttpGet(url);
				
				cityRequests.put(cityQueryWeatherId, get);
			}
		}
		getAllCitiesWeather().putAll(
				UpdateAndLoadWeatherUtil.downloadWeathers(cityRequests, config
						.getDataRoot(), config.getFtpImageRoot()));
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public Map<String, WeatherWithImage> getAllCitiesWeather() {
		return allCitiesWeather;
	}

	public void setAllCitiesWeather(
			Map<String, WeatherWithImage> allCitiesWeather) {
		this.allCitiesWeather = allCitiesWeather;
	}

	public void setConfig(WeatherConfig config) {
		this.config = config;
	}

	public long firstUpdateTime() {
		long oneHourAgo = System.currentTimeMillis() - 60 * 60 * 1000;
		List<File> files = Arrays.asList(new File(config.getDataRoot())
				.listFiles());
		Iterator<File> it = files.iterator();
		while (it.hasNext()) {
			File file = it.next();
			long modifiedTime = file.lastModified();
			if (oneHourAgo > modifiedTime) {
				return 0;
			}
		}
		//return 0;
		return 60 * 60 * 1000;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// UpdateAndLoadWeatherUtil.loadWeather("/home/stb/weather/data/",
		// "/home/stb/weather/images/", "101010100");
		 ClassPathResource res = new
		 ClassPathResource("applicationContextBase.xml");
		 DefaultListableBeanFactory factory = new
		 DefaultListableBeanFactory();
		 XmlBeanDefinitionReader reader = new
		 XmlBeanDefinitionReader(factory);
		 reader.loadBeanDefinitions(res);
//		ClassPathResource res = new ClassPathResource(
//				".");
//		System.out.println(res.getFile().getAbsolutePath());
	}
}

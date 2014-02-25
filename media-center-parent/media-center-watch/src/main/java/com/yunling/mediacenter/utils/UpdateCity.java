package com.yunling.mediacenter.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.yunling.mediacenter.db.model.City;
import com.yunling.mediacenter.db.service.CityService;


public class UpdateCity{

	FileSystemXmlApplicationContext context;
	
	public void initEnv(){
		String[] path = { "conf/applicationContextBase.xml",
				"conf/applicationContextMon.xml",
				"conf/applicationContextMyBatis.xml",
				"conf/applicationContextService.xml" };
		context = new FileSystemXmlApplicationContext(path);
	}
	
	@SuppressWarnings("unchecked")
	public void updateCity() throws FileNotFoundException, IOException{
		CityService cityService = (CityService)context.getBean("cityService");
		
		Properties weatherProps = new Properties();
		weatherProps.load(new FileReader(new File("code_city.properties")));
		@SuppressWarnings("rawtypes")
		Map<String, String> cityWeatherIds = (Map)weatherProps;
		
		Properties pinyinProps = new Properties();
		pinyinProps.load(new FileReader(new File("city_pinyin.properties")));
		@SuppressWarnings("rawtypes")
		Map<String, String> cityPinyinNames = (Map)pinyinProps;
		
		List<City> cities = cityService.findAll();
		Iterator<City> it = cities.iterator();
		while(it.hasNext()){
			System.out.println("###########################");
			City city = it.next();
			String cityName = city.getCityName();
			Iterator<Entry<String, String>> en_it = cityWeatherIds.entrySet().iterator();
			while(en_it.hasNext()){
				Entry<String, String> en = en_it.next();
				if(cityName.startsWith(en.getKey())){
					city.setQueryWeatherId(en.getValue());
					city.setCityNamePinyin(cityPinyinNames.get(en.getKey()));
					cityService.updateWeatherAndPinyinName(city);
					break;
				}
			}
		}
		//assertEquals(true, 1 == 1);
	}
	public static void main(String[] args) throws FileNotFoundException, IOException{
		UpdateCity uc = new UpdateCity();
		uc.initEnv();
		uc.updateCity();
	}
}

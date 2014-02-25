/**
 * 
 */
package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.City;

/**
 * @author LingJun
 * @date 2010-10-12
 * 
 */
public interface CityService {
	public List<City> findByProvinceId(String provinceId);
	public City findByCityName(City city);
	public City findById(long id);
	public void updateWeatherAndPinyinName(City city);
	public List<City> findAll();
}

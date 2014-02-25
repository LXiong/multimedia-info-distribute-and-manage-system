/**
 * 
 */
package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.CityMapper;
import com.yunling.mediacenter.db.model.City;
import com.yunling.mediacenter.db.service.CityService;

/**
 * @author LingJun
 * @date 2010-10-12
 * 
 */
public class CityServiceImpl extends BaseServiceImpl implements CityService {

	@Override
	public List<City> findByProvinceId(final String provinceId) {
		return getMapper().findByProvinceId(provinceId);
	}

	@Override
	public City findByCityName(final City city) {
		return getMapper().findByCityName(city);
	}

	private CityMapper getMapper() {
		return getMapper(CityMapper.class);
	}

	@Override
	public City findById(long id) {
		return getMapper().findById(id);
	}

	@Override
	public void updateWeatherAndPinyinName(City city) {
		getMapper().updateNewColumns(city);
	}

	@Override
	public List<City> findAll() {
		return getMapper().findAll();
	}
}

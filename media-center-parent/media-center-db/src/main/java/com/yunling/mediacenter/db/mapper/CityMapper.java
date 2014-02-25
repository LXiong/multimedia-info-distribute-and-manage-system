/**
 * 
 */
package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.City;

/**
 * @author LingJun
 * @date 2010-10-12
 * 
 */
public interface CityMapper {
	public List<City> findByProvinceId(String provinceId);
	public City findByCityName(City city);
	public City findById(@Param(value="id")long id);
	public void updateNewColumns(City city);
	public List<City> findAll();
}

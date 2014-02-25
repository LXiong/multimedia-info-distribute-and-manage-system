/**
 * 
 */
package com.yunling.mediacenter.db.mapper;

import java.util.List;

import com.yunling.mediacenter.db.model.Province;

/**
 * @author LingJun
 * @date 2010-10-11
 * 
 */
public interface ProvinceMapper {
	public List<Province> listProvince();
	public Province findProvinceByName(Province provicne);
}

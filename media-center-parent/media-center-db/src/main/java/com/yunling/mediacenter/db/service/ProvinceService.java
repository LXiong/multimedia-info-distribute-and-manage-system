/**
 * 
 */
package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Province;

/**
 * @author LingJun
 * @date 2010-10-11
 * 
 */
public interface ProvinceService {
	public List<Province> listProvince();
	public Province findProvinceByName(Province provicne);
}

/**
 * 
 */
package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.District;

/**
 * @author LingJun
 * @date 2010-10-12
 * 
 */
public interface DistrictService {
	public List<District> findByCityId(String cityId);
	public District findByDistrictName(District district);
}

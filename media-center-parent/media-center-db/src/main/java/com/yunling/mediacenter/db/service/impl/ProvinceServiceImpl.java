/**
 * 
 */
package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.ProvinceMapper;
import com.yunling.mediacenter.db.model.Province;
import com.yunling.mediacenter.db.service.ProvinceService;

/**
 * @author LingJun
 * @date 2010-10-11
 * 
 */
public class ProvinceServiceImpl extends BaseServiceImpl implements
		ProvinceService {
	
	private ProvinceMapper getMapper() {
		return getMapper(ProvinceMapper.class);
	}

	@Override
	public List<Province> listProvince() {
		return getMapper().listProvince();
	}

	@Override
	public Province findProvinceByName(final Province province) {
		return getMapper().findProvinceByName(province);
	}

}

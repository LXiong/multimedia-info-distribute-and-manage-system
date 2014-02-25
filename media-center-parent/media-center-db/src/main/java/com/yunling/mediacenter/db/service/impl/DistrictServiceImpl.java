/**
 * 
 */
package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.DistrictMapper;
import com.yunling.mediacenter.db.model.District;
import com.yunling.mediacenter.db.service.DistrictService;

/**
 * @author LingJun
 * @date 2010-10-12
 * 
 */
public class DistrictServiceImpl extends BaseServiceImpl implements
		DistrictService {
	
	private DistrictMapper getMapper() {
		return getMapper(DistrictMapper.class);
	}
	@Override
	public List<District> findByCityId(final String cityId) {
		return getMapper().findByCityId(cityId);
	}

	@Override
	public District findByDistrictName(final District district) {
		return getMapper().findByDistrictName(district);
	}

}

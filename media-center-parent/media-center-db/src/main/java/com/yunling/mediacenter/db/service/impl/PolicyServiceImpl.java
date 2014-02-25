package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.PolicyMapper;
import com.yunling.mediacenter.db.model.Policy;
import com.yunling.mediacenter.db.service.PolicyService;

public class PolicyServiceImpl extends BaseServiceImpl implements PolicyService {
	private PolicyMapper getMapper() {
		return getMapper(PolicyMapper.class);
	}
	
	public void addPolicy(final Policy pp) {
		getMapper().addPolicy(pp);
	}

	public List<Policy> listLatest(final int count) {
		return getMapper().listLatest(count);
	}
	
	public List<Policy> cityLatest(String city, int count){
	    return getMapper().cityLatest(city, count);
	}
}

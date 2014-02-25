package com.yunling.mediacenter.db.service.impl;

import java.util.List;

import com.yunling.mediacenter.db.mapper.PolicyMediaMapper;
import com.yunling.mediacenter.db.model.Videos;
import com.yunling.mediacenter.db.service.PolicyMediaService;

/**
 * @author LingJun
 * @date 2011-8-8
 * 
 */
public class PolicyMediaServiceImpl extends BaseServiceImpl implements PolicyMediaService {

	private PolicyMediaMapper getMapper() {
		return getMapper(PolicyMediaMapper.class);
	}
	
	/* (non-Javadoc)
	 * @see com.yunling.mediacenter.db.service.PolicyMediaService#findVideoByPolicyid(java.lang.String)
	 */
	@Override
	public List<Videos> findMediaByPolicyid(Long policyId) {
		return getMapper().findMediaByPolicyid(policyId);
	}

}

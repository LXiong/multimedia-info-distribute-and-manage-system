package com.yunling.mediacenter.db.service;

import java.util.List;

import com.yunling.mediacenter.db.model.Videos;

/**
 * @author LingJun
 * @date 2011-8-8
 * 
 */
public interface PolicyMediaService {

	public List<Videos> findMediaByPolicyid(Long policyId);
}

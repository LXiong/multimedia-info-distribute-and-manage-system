package com.yunling.mediacenter.db.mapper;

import java.util.List;

import com.yunling.mediacenter.db.model.Videos;

public interface PolicyMediaMapper {

	public List<Videos> findMediaByPolicyid(Long policyId);
}

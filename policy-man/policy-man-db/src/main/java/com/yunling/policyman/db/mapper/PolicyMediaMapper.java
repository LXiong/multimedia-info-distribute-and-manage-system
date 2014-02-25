package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BasePolicyMediaMapper;
import com.yunling.policyman.db.model.PolicyMedia;

public interface PolicyMediaMapper
	extends BasePolicyMediaMapper
{

	List<PolicyMedia> listByPolicy(@Param("policy_id") long id);

	void deleteByPolicy(@Param("policy_id")Long id);

	List<PolicyMedia> listVideoByPolicy(@Param("policy_id") long id);

}

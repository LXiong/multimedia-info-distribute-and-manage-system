package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BaseStbGroupMapper;
import com.yunling.policyman.db.model.StbGroup;

public interface StbGroupMapper
	extends BaseStbGroupMapper
{

	List<StbGroup> listByPublishedPolicy(@Param("policyId") Long policyId);
}
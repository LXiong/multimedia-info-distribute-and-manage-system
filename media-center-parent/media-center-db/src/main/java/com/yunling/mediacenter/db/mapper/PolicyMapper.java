package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.Policy;

public interface PolicyMapper {

	public Object addPolicy(Policy pp);
	public List<Policy> listLatest(int count);
	public List<Policy> cityLatest(@Param(value="city")String city, @Param(value="count")int count);
}

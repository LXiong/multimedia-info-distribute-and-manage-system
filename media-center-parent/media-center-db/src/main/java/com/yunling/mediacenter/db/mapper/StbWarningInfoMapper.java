package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.StbWarningInfo;

public interface StbWarningInfoMapper {
	
	public StbWarningInfo getById(@Param(value="id")long id);

	public List<StbWarningInfo> query(@Param(value="info")StbWarningInfo info);
	
	public List<StbWarningInfo> pageWithoutCondition(@Param(value="page")int page);
	
	public void insert(StbWarningInfo info);

	public void delete(@Param("id")long id);

	public void update(@Param(value="info")StbWarningInfo info);
	
}

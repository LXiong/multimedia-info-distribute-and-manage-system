package com.yunling.mediacenter.db.mapper;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.StbSoftwareVersion;

public interface StbSoftwareVersionMapper {
	public void insertRecord(@Param(value = "mac") String mac,
			@Param(value = "type") String type,
			@Param(value = "version") String version);

	public void updateRecord(@Param(value = "mac") String mac,
			@Param(value = "type") String type,
			@Param(value = "version") String version);
	public StbSoftwareVersion selectRecord(@Param(value="mac")String mac, @Param(value="type")String type);
}

package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.PublishRecord;

public interface PublishRecordMapper {

	public List<PublishRecord> listByGroupType(@Param(value="type_id")Long typeId);

	public List<PublishRecord> getLatestOfGroupType(@Param(value="type_id")Long typeId);
	
	public List<PublishRecord> listLatest(@Param(value = "num")int num);

	public List<PublishRecord> listLatestByGroups(@Param(value="group_id")long groupId);
}

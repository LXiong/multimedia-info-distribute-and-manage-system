package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BasePublishTaskMapper;
import com.yunling.policyman.db.model.PublishTask;

public interface PublishTaskMapper
	extends BasePublishTaskMapper
{

	List<PublishTask> listByStatus(@Param("status")String string);

}
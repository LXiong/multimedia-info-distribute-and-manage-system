package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.OperationLog;

public interface OperationLogMapper {

	Object addOperationLog(OperationLog log);

	int countBy(@Param("beginTime") String from, @Param("endTime") String to,
			@Param("isok") String isok);

	List<OperationLog> listBy(@Param("beginTime") String from,
			@Param("endTime") String to, @Param("isok") String isok,
			@Param("begin") int begin, @Param("end") int end);
}

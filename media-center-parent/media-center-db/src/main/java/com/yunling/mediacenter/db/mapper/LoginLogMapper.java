package com.yunling.mediacenter.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.mediacenter.db.model.LoginLog;

public interface LoginLogMapper {

	Object addLoginLog(LoginLog loginlog);
	
	int countBy(@Param("beginTime")String beginTime, @Param("endTime")String endTime, 
			@Param("isSuccess") String isSuccess);
	
	List<LoginLog> listBy(@Param("beginTime")String beginTime, @Param("endTime")String endTime, 
			@Param("isSuccess") String isSuccess,
			@Param("begin") int begin, @Param("end") int end);
}

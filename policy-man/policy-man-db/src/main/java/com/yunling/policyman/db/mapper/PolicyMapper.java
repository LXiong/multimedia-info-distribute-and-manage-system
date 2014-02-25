package com.yunling.policyman.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunling.policyman.db.mapper.base.BasePolicyMapper;
import com.yunling.policyman.db.model.Policy;

public interface PolicyMapper
	extends BasePolicyMapper
{

	void setSubmitted(@Param("policy_id") long id,@Param("user_name") String userName);

	void setPassed(@Param("policy_id") long id,@Param("user_name") String userName);

	void rejectPolicy(@Param("policy_id") long id,@Param("user_name") String userName, 
			@Param("reason") String reason);

	List<Policy> listEditablePaged(@Param("begin")long begin, @Param("end")long end);
	List<Policy> listPassedPaged(@Param("begin")long begin, @Param("end")long end);
	List<Policy> listSubmittedPaged(@Param("begin")long begin, @Param("end")long end);
	
	List<Policy> listByStatus(@Param("status")String status);

	void setPublished(@Param("policy_id")long id, @Param("user_name")String username);
	
	void reviewPublish(@Param("policy_id")long id, @Param("user_name")String username);

	int countStatus(Policy policy);

}

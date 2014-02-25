package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.Policy;
import com.yunling.policyman.db.service.base.BasePolicyService;

public interface PolicyService
	extends BasePolicyService
{

	void insertAll(Policy policy);

	void updateAll(Policy policy);

	void setSubmitted(long id, String username);
	
	void setPassed(long id, String username);

	void deleteWhole(long id);
	
	void setPublished(long id, String username);
	
	List<Policy> listEditablePaged(int begin, int end);

	List<Policy> listSubmittedPaged(int begin, int end);
	
	List<Policy> listPassedPaged(int begin, int end);

	void rejectPolicy(long id, String userDispName, String reason);
	
	List<Policy> listByStatus(String status);
	
	List<Policy> listPageByStatus(String status, int begin, int end);

	void publishTo(long id, List<Long> stbGroupIdList, String userDispName);
	
	void reviewPublish(long id, List<Long> stbGroupIdList, String userDispName);

	void copyToNew(Policy policy);
	
	int countStatus(Policy policy);

}
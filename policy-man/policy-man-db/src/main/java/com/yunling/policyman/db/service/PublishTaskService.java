package com.yunling.policyman.db.service;

import java.util.List;

import com.yunling.policyman.db.model.PublishTask;
import com.yunling.policyman.db.service.base.BasePublishTaskService;

public interface PublishTaskService
	extends BasePublishTaskService
{
	List<PublishTask> getAllPendingTask();
	
	void publish(PublishTask task);
}
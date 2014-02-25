package com.yunling.policyman.db.service.impl;

import java.util.List;

import com.yunling.policyman.db.model.PublishTask;
import com.yunling.policyman.db.service.PublishTaskService;
import com.yunling.policyman.db.service.impl.base.BasePublishTaskServiceImpl;

public class PublishTaskServiceImpl
	extends BasePublishTaskServiceImpl
	implements PublishTaskService
{

	@Override
	public List<PublishTask> getAllPendingTask() {
		return getMapper().listByStatus("pending");
	}

	@Override
	public void publish(PublishTask task) {
		task.setStatus("published");
		//task.setPublishTime(new Date());
		getMapper().update(task);
	}
	

}
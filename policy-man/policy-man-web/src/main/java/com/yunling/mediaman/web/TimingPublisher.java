package com.yunling.mediaman.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.policyman.db.model.Policy;
import com.yunling.policyman.db.model.PolicyMedia;
import com.yunling.policyman.db.model.PublishTask;
import com.yunling.policyman.db.model.StbGroupLevelTwo;
import com.yunling.policyman.db.model.TimedLayout;
import com.yunling.policyman.db.service.PolicyMediaService;
import com.yunling.policyman.db.service.PolicyService;
import com.yunling.policyman.db.service.PublishTaskService;
import com.yunling.policyman.db.service.StbGroupLevelTwoService;
import com.yunling.policyman.db.service.StbGroupService;
import com.yunling.policyman.db.service.TimedLayoutService;

public class TimingPublisher {
	Logger log = LoggerFactory.getLogger(TimingPublisher.class);

	PublishTaskService publishTaskService;

	PolicyService policyService;

	PolicyMediaService policyMediaService;

	StbGroupService stbGroupService;

	StbGroupLevelTwoService stbGroupLevelTwoService;

	TimedLayoutService timedLayoutService;

	PolicyExporter policyExporter;

	public void publish() {
		Date now = new Date();
		List<PublishTask> tasks = publishTaskService.getAllPendingTask();
		Iterator<PublishTask> it = tasks.iterator();
		while (it.hasNext()) {
			PublishTask task = it.next();
			if (now.after(task.getPublishTime())) {
				Policy policy = policyService.getByKey(task.getPolicyId());
				List<TimedLayout> layoutList = timedLayoutService
						.listByPolicy(task.getPolicyId());
				List<PolicyMedia> policyMediaList = policyMediaService
						.listVideoByPolicy(task.getPolicyId());
				Iterator<String> groupIdIt = Arrays.asList(
						task.getPublishAreas().split("-")).iterator();

				List<StbGroupLevelTwo> groups = new ArrayList<StbGroupLevelTwo>();
				while (groupIdIt.hasNext()) {
					long groupId = Long.valueOf(groupIdIt.next());
					log.info("##############get group with id " + groupId);
					groups.add(stbGroupLevelTwoService.getByKey(groupId));
				}
				policyExporter.exportFile(policy, layoutList, policyMediaList,
						groups, new ArrayList<Object[]>());
				publishTaskService.publish(task);
			}
		}
	}

	public PublishTaskService getPublishTaskService() {
		return publishTaskService;
	}

	public void setPublishTaskService(PublishTaskService publishTaskService) {
		this.publishTaskService = publishTaskService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public PolicyMediaService getPolicyMediaService() {
		return policyMediaService;
	}

	public void setPolicyMediaService(PolicyMediaService policyMediaService) {
		this.policyMediaService = policyMediaService;
	}

	public StbGroupService getStbGroupService() {
		return stbGroupService;
	}

	public void setStbGroupService(StbGroupService stbGroupService) {
		this.stbGroupService = stbGroupService;
	}

	public TimedLayoutService getTimedLayoutService() {
		return timedLayoutService;
	}

	public void setTimedLayoutService(TimedLayoutService timedLayoutService) {
		this.timedLayoutService = timedLayoutService;
	}

	public PolicyExporter getPolicyExporter() {
		return policyExporter;
	}

	public void setPolicyExporter(PolicyExporter policyExporter) {
		this.policyExporter = policyExporter;
	}

	public StbGroupLevelTwoService getStbGroupLevelTwoService() {
		return stbGroupLevelTwoService;
	}

	public void setStbGroupLevelTwoService(
			StbGroupLevelTwoService stbGroupLevelTwoService) {
		this.stbGroupLevelTwoService = stbGroupLevelTwoService;
	}
}

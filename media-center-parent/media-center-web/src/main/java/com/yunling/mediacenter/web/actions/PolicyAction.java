package com.yunling.mediacenter.web.actions;

import java.util.List;

import com.yunling.mediacenter.db.model.PublishRecord;
import com.yunling.mediacenter.db.model.Videos;
import com.yunling.mediacenter.db.service.PolicyMediaService;
import com.yunling.mediacenter.db.service.PublishRecordService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class PolicyAction extends AbstractUserAwareAction {

	private PolicyMediaService policyMediaService;
	private PublishRecordService publishRecordService;
	private Long policyId;
	private String policyNumber;

	public String execute() throws Exception {
		List<PublishRecord> publishPolicies = publishRecordService.listLatest(20);
		log.debug("PolicyAction: publish policies list size is {}", publishPolicies.size());
		request.put("policies", publishPolicies);
		return SUCCESS;
	}

	/**
	 * get the medias which in the policy
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		List<Videos> videos = policyMediaService.findMediaByPolicyid(policyId);
		request.put("videos", videos);
		return "detail";
	}

	/**
	 * @return the policyMediaService
	 */
	public PolicyMediaService getPolicyMediaService() {
		return policyMediaService;
	}

	/**
	 * @param policyMediaService the policyMediaService to set
	 */
	public void setPolicyMediaService(PolicyMediaService policyMediaService) {
		this.policyMediaService = policyMediaService;
	}

	/**
	 * @return the publishRecordService
	 */
	public PublishRecordService getPublishRecordService() {
		return publishRecordService;
	}

	/**
	 * @param publishRecordService the publishRecordService to set
	 */
	public void setPublishRecordService(PublishRecordService publishRecordService) {
		this.publishRecordService = publishRecordService;
	}

	/**
	 * @return the policyId
	 */
	public Long getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	/**
	 * @return the policyNumber
	 */
	public String getPolicyNumber() {
		return policyNumber;
	}

	/**
	 * @param policyNumber the policyNumber to set
	 */
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

}

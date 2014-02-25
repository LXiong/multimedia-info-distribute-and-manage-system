package com.yunling.mediacenter.web.actions;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class StatAction extends AbstractUserAwareAction {

	private StbService stbService;
	
	@Override
	public String execute() throws Exception {
		List<Map<String, Object>> stbPolicyStat = stbService.countStbByPolicy(getCurrentUserId());
		request.put("stbPolicyStat", stbPolicyStat);
		return "policy";
	}
	
	public StbService getStbService() {
		return stbService;
	}
	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

}

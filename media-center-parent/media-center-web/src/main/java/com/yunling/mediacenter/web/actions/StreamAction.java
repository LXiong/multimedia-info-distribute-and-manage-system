package com.yunling.mediacenter.web.actions;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;

import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.service.GroupTypeService;
import com.yunling.mediacenter.db.service.GroupsService;
import com.yunling.mediacenter.server.remote.WebControl;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class StreamAction extends AbstractUserAwareAction {
	
	private WebControl localService;
	private GroupTypeService groupTypeService;
	private GroupsService groupService;

	@Override
	public String execute() throws Exception {
		List<GroupType> groupTypeList = groupTypeService.listWithChild(this.getCurrentUserId());
		this.request.put("groupTypeList", groupTypeList);
		
		return SUCCESS;
	}
	
	public String config() throws Exception {
		Object groupParam = this.parameters.get("group2");
		Object ipParam = this.parameters.get("ip");
		String startTime = getSingleParam("startTime");
		String endTime = getSingleParam("endTime");
		if (groupParam instanceof String[] && ((String[]) groupParam).length>0 
				&& ipParam instanceof String[] && ((String[]) ipParam).length>0
				&& startTime != null && endTime != null) {
			String[] group2Arr = (String[]) groupParam;
			String groupList = StringUtils.join(group2Arr,',');
			String[] ipArr = (String[]) ipParam;
			String ip = ipArr[0];
			try {
				localService.configStream(ip, groupList, startTime, endTime);
				request.put("resultFlag", Boolean.TRUE);
			} catch (Exception e) {
				request.put("resultFlag", Boolean.FALSE);
				request.put("errorMsg", e.getMessage());
			}
		} else {
			request.put("resultFlag", Boolean.TRUE);
			request.put("code", "missing-param");
		}
		
		setLayout(JSON);
		return "config";
	}

	public GroupTypeService getGroupTypeService() {
		return groupTypeService;
	}

	public void setGroupTypeService(GroupTypeService groupTypeService) {
		this.groupTypeService = groupTypeService;
	}

	public GroupsService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupsService groupService) {
		this.groupService = groupService;
	}

	public WebControl getLocalService() {
		return localService;
	}

	public void setLocalService(WebControl localService) {
		this.localService = localService;
	}

}

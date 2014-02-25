package com.yunling.mediacenter.web.actions;

import org.apache.struts2.ServletActionContext;

import com.yunling.mediacenter.db.service.ConfigDownLoadLogService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class ConfigDownLoadLogAction extends AbstractUserAwareAction {
	private ConfigDownLoadLogService configdownloadlogservice;

	public ConfigDownLoadLogService getConfigdownloadlogservice() {
		return configdownloadlogservice;
	}

	public void setConfigdownloadlogservice(
			ConfigDownLoadLogService configdownloadlogservice) {
		this.configdownloadlogservice = configdownloadlogservice;
	}

	@Override
	public String execute() throws Exception {
		Integer sum = configdownloadlogservice.selectConfigDownLoadLogCount();
		Integer page = 0;
		try {
			page = Integer.parseInt(ServletActionContext.getRequest()
					.getParameter("page"));

		} catch (Exception e) {
			page = 1;
		}
		request.put("map", configdownloadlogservice.list(page, sum));
		return SUCCESS;
	}

}

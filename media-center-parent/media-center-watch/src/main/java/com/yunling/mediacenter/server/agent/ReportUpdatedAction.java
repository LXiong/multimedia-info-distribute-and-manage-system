package com.yunling.mediacenter.server.agent;

import java.util.Map;

import org.jboss.netty.channel.Channel;

public class ReportUpdatedAction extends RequestBaseAction {

	@Override
	public String execute(Channel ch, Map<String, String> params) {
		String mac = this.getKeyByValue(getMacChannels(), ch);
		getLog().info("@Report updated with mac : " + mac);
		getStbService().downloaded(mac);
		//getStbService().updateDownloadStatus(mac, "updated");
		getLog().info("@Update status of " + mac + "to online.");
		return null;
	}

}

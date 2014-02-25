package com.yunling.mediacenter.server.agent;

import java.util.Map;

import org.jboss.netty.channel.Channel;

public class ReportUpdatingAction extends RequestBaseAction {

	@Override
	public String execute(Channel ch, Map<String, String> params) {
		String mac = this.getKeyByValue(getMacChannels(), ch);
		getLog().info("@Report updating with mac : " + mac);
		getStbService().downloading(mac);
		getLog().info("@Update status of " + mac + "to updating.");
		return null;
	}

}

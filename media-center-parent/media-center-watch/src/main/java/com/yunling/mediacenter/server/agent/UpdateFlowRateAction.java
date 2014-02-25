package com.yunling.mediacenter.server.agent;

import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.Stb;

public class UpdateFlowRateAction extends RequestBaseAction {

	@Override
	public String execute(Channel ch, Map<String, String> params) {
		getLog().info("params:" + params.toString());
		String mac = this.getKeyByValue(this.getMacChannels(), ch);
		if(mac != null){
			Stb stb = this.getStbService().findByMac(mac);
			getLog().info("get stb success.");
			stb.addReceivedKiloByte(params.get("recv"));
			stb.addSentKiloByte(params.get("send"));
			this.getStbService().updateFlowRate(stb);
			getLog().info("update flow rate success.");
		}else{
			getLog().debug("can not find mac with channel :" + ch.toString());
		}
		return null;
	}

}

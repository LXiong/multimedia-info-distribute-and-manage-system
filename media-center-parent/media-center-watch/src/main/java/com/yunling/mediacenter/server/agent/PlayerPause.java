package com.yunling.mediacenter.server.agent;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.Channel;

public class PlayerPause extends RequestBaseAction {
	public String execute(Channel ch, Map<String, String> params) {
		Map<String, String> res = new HashMap<String, String>();
		//for pressure test
		res.put("test_action", "pause");
		res.put("response", "pause");
		String mac = this.getKeyByValue(getMacChannels(), ch);
		if (mac != null) {
			getStbService().macPause(mac);
			getLog().info("Pause the stb:" + mac);
			res.put("status_code", "0000");
			ch.write(this.getMessageFromMap(res));
			return "success";
		}else{
			this.getLog().info("mac address not found ");
			res.put("status_code", "0400");
			res.put("message", "can_not_found_the_mac");
			ch.write(this.getMessageFromMap(res));
			return "failure";
		}
	}
}

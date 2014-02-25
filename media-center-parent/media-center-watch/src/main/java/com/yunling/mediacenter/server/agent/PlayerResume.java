package com.yunling.mediacenter.server.agent;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.Channel;

public class PlayerResume extends RequestBaseAction {
	public String execute(Channel ch, Map<String, String> params) {
		Map<String, String> res = new HashMap<String, String>();
		//for pressure test
		res.put("test_action", "resume");
		res.put("response", "resume");
		String mac = this.getKeyByValue(getMacChannels(), ch);
		if (mac != null) {
			getStbService().macResume(mac);
			getLog().info("Resume the stb:" + mac);
			res.put("status_code", "0000");
			ch.write(this.getMessageFromMap(res));
			return "success";
		}else{
			res.put("status_code", "0400");
			res.put("message", "can_not_found_the_mac");
			getLog().info("mac address not found ");
			ch.write(this.getMessageFromMap(res));
			return "failure";
		}
	}
}

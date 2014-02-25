package com.yunling.mediacenter.server.agent;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.Stb;

public class UpdateActivePolicy extends RequestBaseAction {
	public String execute(Channel ch, Map<String, String> params) {
		Map<String, String> res = new HashMap<String, String>();
		//for pressure test
		res.put("test_action", "change_active_policy");
		res.put("response", "change_active_policy");
		String mac = this.getKeyByValue(getMacChannels(), ch);
		String activePolicy = params.remove("active_policy");
		int activePolicySuccessNum = Integer.valueOf(params
				.remove("active_policy_success_num"));
		int activePolicyFailedNum = Integer.valueOf(params
				.remove("active_policy_failed_num"));
		Stb stb = this.getStbService().findByMac(mac);
		if (stb != null) {
			res.put("status_code", "0000");
			this.getStbService().updateActivePolicy(mac, activePolicy,
					activePolicySuccessNum, activePolicyFailedNum);
			ch.write(this.getMessageFromMap(res));
			return "success";
		} else {
			res.put("status_code", "0400");
			res.put("message", "can_not_find_the_stb");
			ch.write(this.getMessageFromMap(res));
			return "failure";
		}
	}
}

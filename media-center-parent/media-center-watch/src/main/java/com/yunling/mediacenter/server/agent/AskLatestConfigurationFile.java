package com.yunling.mediacenter.server.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.Config;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.utils.StbMessage;

public class AskLatestConfigurationFile extends RequestBaseAction {
	public String execute(Channel ch, Map<String, String> params) {
		String mac = this.getKeyByValue(getMacChannels(), ch);
		if (mac != null) {
			Stb stb = getStbService().findByMac(mac);
			Config config = new Config();
			config.setConfId(stb.getConfId());
			List<Config> configs = getConfigService().getConfigs(config);
			Map<String, String> res = new HashMap<String, String>();
			//for pressure test
			res.put("test_action", "latest_conf");
			if (configs.size() == 0) {
				this.getLog().info("configuration file not found");
				res.put("status_code", "0400");
				res.put("message", "not found");
			} else {
				this.getLog().info("found configuration file");
				res.put("status_code", "0000");
				for (Config c : configs) {
					res.put(c.getConfigKey(), c.getConfigValue());
				}
			}
			StbMessage msg = new StbMessage(res, "modify");
			this.getLog().info("send configuration file");
			ch.write(msg.message());
			return "success";
		}else{
			this.getLog().info("mac address not found ");
			return "failure";
		}

		
	}
}

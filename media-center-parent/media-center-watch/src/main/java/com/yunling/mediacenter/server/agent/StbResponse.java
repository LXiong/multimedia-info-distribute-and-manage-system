package com.yunling.mediacenter.server.agent;

import java.util.Map;

import org.jboss.netty.channel.Channel;

public class StbResponse extends RequestBaseAction {
	// default is final result
	private boolean isFinal = true;

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public String execute(Channel ch, Map<String, String> params) {
		String key = params.remove("command_key");
		params.put("put_in_map_time", String.valueOf(System.currentTimeMillis()));
		getQueryResults().put(key, params);
//		getLog().info("----------response command_key:" + key + "----------");
//		getLog().info("----------response results:" + params + "----------");
//		getLog().info("results" + getQueryResults());
		return "hello, aries_tiger";
	}
}

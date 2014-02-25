package com.yunling.mediacenter.server.agent;

import java.util.Map;

import org.jboss.netty.channel.Channel;

public interface RequestAction {
	public String execute(Channel ch, Map<String, String> params);
	public boolean isFinal();
}

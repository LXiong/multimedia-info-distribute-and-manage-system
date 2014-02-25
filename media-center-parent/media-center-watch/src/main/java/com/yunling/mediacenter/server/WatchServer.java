package com.yunling.mediacenter.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.commons.console.ConsoleMain;
import com.yunling.mediacenter.utils.WatchServerConfiguration;

public class WatchServer implements ConsoleMain {
	private Logger log = LoggerFactory.getLogger(WatchServer.class);
	private ServerBootstrap bootstrap;
	private ChannelGroup allChannels;
	private WatchServerConfiguration config;
	
	@Override
	public void init(String[] args, Map<String, Object> context)
			throws Exception {
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
	}


	@Override
	public void run(Map<String, Object> context) throws Exception {
		bootstrap.bind(new InetSocketAddress(config.getPort()));
		log.info("local ip : " + InetAddress.getLocalHost());
		log.info("watch server is running on port:" + config.getPort());
	}

	@Override
	public void release() throws Exception {
		allChannels.close();
	}
	public void setBootstrap(ServerBootstrap bootstrap){
		this.bootstrap = bootstrap;
	}

	public void setAllChannels(ChannelGroup allChannels) {
		this.allChannels = allChannels;
	}

	public WatchServerConfiguration getConfig() {
		return config;
	}


	public void setConfig(WatchServerConfiguration config) {
		this.config = config;
	}
	
}

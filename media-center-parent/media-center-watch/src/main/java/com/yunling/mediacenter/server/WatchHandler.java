package com.yunling.mediacenter.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.service.StbLoginRecordService;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.server.agent.RequestAction;
import com.yunling.mediacenter.server.remote.WatcherReport;
import com.yunling.mediacenter.utils.WatchServerConfiguration;

public class WatchHandler extends IdleStateAwareChannelHandler {

	private Logger log = LoggerFactory.getLogger(WatchHandler.class);
	private WatcherReport watchReporter;
	private Map<String, Channel> macChannels;
	private ChannelGroup allChannels;
	private WatchServerConfiguration config;
	private StbService stbService;
	private StbLoginRecordService stbLoginRecordService;
	private Map<String, RequestAction> requestActions;
	private int maxConnections;

	public WatchHandler() {
		super();
	}

	@Override
	public void channelConnected(ChannelHandlerContext context,
			ChannelStateEvent event) throws Exception {
		if(allChannels.size() >= getMaxConnections()){
			log.info("Connection has reached its maximum");
			ChannelFuture fu = event.getChannel().write("Connection has reached its maximum");
			fu.addListener(new ChannelFutureListener(){
				@Override
				public void operationComplete(ChannelFuture future)
						throws Exception {
					future.getChannel().close();
				}
			});
		}else{
			allChannels.add(event.getChannel());
			log.info("----------Some one connected----------");
		}
	}

	/**
	 * 
	 */
	@Override
	public void messageReceived(ChannelHandlerContext context,
			MessageEvent event) throws IOException {
		RequestAction action = null;
		Channel ch = event.getChannel();
		String req = (String) event.getMessage();
		log.info("----------Stb request is :" + req + "----------");
		log.info("stb ip is : {}", ch.getRemoteAddress());
		Map<String, String> params = convertStringToMap(req);
		addIpAndPortToMap(ch.getRemoteAddress().toString().replace("/", "")
				.trim(), params);
		if (params.containsKey("request")) {
			log.info("action is request");
			action = requestActions.get(params.remove("request"));
		} else if (params.containsKey("response")) {
			log.info("action is response");
			params.remove("response");
			action = requestActions.get("response");
		}
		if (action == null) {
			log.info("can not find the action to respond");
		} else {
			log.info("execute the action with params:" + params);
			action.execute(ch, params);
		}
	}

	@Override
	public void channelIdle(ChannelHandlerContext context, IdleStateEvent event) {
		Channel ch = event.getChannel();
		boolean isIdle = (event.getState() == IdleState.READER_IDLE)
				|| (event.getState() == IdleState.WRITER_IDLE)
				|| (event.getState() == IdleState.ALL_IDLE);
		if (isIdle) {
			log
					.info("The stb don`t report online or send message for a long time, and I`ll close the channel.");
			ch.close();
		}
	}

	@Override
	public void channelClosed(ChannelHandlerContext context,
			ChannelStateEvent event) throws Exception {
		Channel ch = event.getChannel();
		String mac = getKeyByValue(getMacChannels(), ch);
		if (mac != null) {
			if (stbService.findByMac(mac) != null) {
				stbService.macOffline(mac);
				String[] ipPort = ch.getRemoteAddress().toString().split(":");
				stbLoginRecordService.insertRecord("LOGOUT", mac, ipPort[0],
						ipPort[1], getConfig().getLocalOutnetAddress(), String
								.valueOf(config.getPort()), "logout");
				log.info("Stb with mac:" + mac + " is offline.");
			}
			log.info("Remove mac and channel from map");
			getMacChannels().remove(mac);
			log.info("Report stb logoff to authserver");
			watchReporter.stbLogoff(getConfig().getLocalOutnetAddress(), mac);
		}
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent event) {
		log.info("----------Stb closed connection begin----------");
		event.getChannel().close();
		log.info("----------Stb closed connection end----------");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent event) {
		event.getCause().printStackTrace();
		event.getChannel().close();
		log.info("Exception caught, and I will close this connection.----------");
	}

	public void breakConnection(String msg, Channel ch) {
		ChannelFuture future = ch.write(msg);
		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture fu) throws Exception {
				fu.getChannel().close();
			}

		});
	}

	public void notifyClient(String msg, Channel ch) {
		ch.write(msg);
	}

	public Map<String, RequestAction> getRequestActions() {
		return requestActions;
	}

	public void setRequestActions(Map<String, RequestAction> requestActions) {
		this.requestActions = requestActions;
	}

	public Map<String, Channel> getMacChannels() {
		return macChannels;
	}

	public void setMacChannels(Map<String, Channel> macChannels) {
		this.macChannels = macChannels;
	}

	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

	public void setWatchReporter(WatcherReport watchReporter) {
		this.watchReporter = watchReporter;
	}

	public void setAllChannels(ChannelGroup allChannels) {
		this.allChannels = allChannels;
	}

	String getKeyByValue(Map<String, Channel> map, Channel key) {
		Iterator<Entry<String, Channel>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Channel> pair = it.next();
			if(key.equals(pair.getValue())){
				return pair.getKey();
			}
		}
		return null;
	}

	public void setConfig(WatchServerConfiguration config) {
		this.config = config;
	}

	private Map<String, String> convertStringToMap(String request) {
		Map<String, String> res = new HashMap<String, String>();
		String[] temp = request.split("\r\n");
		for (String pair : temp) {
			if (pair.split("=").length < 2) {
				res.put(pair.split("=")[0], "");
			} else {
				res.put(pair.split("=")[0], pair.split("=")[1]);
			}
		}
		return res;
	}

	private void addIpAndPortToMap(String ipPort, Map<String, String> params) {
		String[] ipPorts = ipPort.split(":");
		params.put("stb_ip", ipPorts[0]);
		params.put("stb_port", ipPorts[1]);
	}

	public void setStbLoginRecordService(
			StbLoginRecordService stbLoginRecordService) {
		this.stbLoginRecordService = stbLoginRecordService;
	}

	public WatcherReport getWatchReporter() {
		return watchReporter;
	}

	public ChannelGroup getAllChannels() {
		return allChannels;
	}

	public WatchServerConfiguration getConfig() {
		return config;
	}

	public StbService getStbService() {
		return stbService;
	}

	public StbLoginRecordService getStbLoginRecordService() {
		return stbLoginRecordService;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

}

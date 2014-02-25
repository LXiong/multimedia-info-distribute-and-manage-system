package com.yunling.mediacenter.server;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.server.actions.Authenticater;

public class AuthenticateHandler extends SimpleChannelHandler {
	private Logger log = LoggerFactory.getLogger(AuthenticateHandler.class);
	Authenticater authenticater;

	@Override
	public void channelConnected(ChannelHandlerContext context,
			ChannelStateEvent event) {
		log.info("----------some one connected............----------");
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent event) {
		Channel ch = event.getChannel();
		log.info("original-req:" + event.getMessage());
		Map<String, String> reqMap = convertRequest((String) event.getMessage());
		Pattern ipPattern = Pattern.compile("\\D*((\\d{1,3}\\.){3}\\d{1,3}).*");
		Matcher matcher = ipPattern.matcher(ch.getRemoteAddress().toString());
		if(matcher.matches()){
			reqMap.put("remote_ip", matcher.group(1));
		}else{
			reqMap.put("remote_ip", ch.getRemoteAddress().toString());
		}
		String response = getAuthenticater().process(reqMap);
		log.info("get response from authenticater:" + response);
		ChannelFuture fu = ch.write(response);
		fu.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				future.getChannel().close();

			}

		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext context,
			ExceptionEvent event) {
		event.getCause().printStackTrace();
		System.out.println("exception caught.......");
	}

	public Authenticater getAuthenticater() {
		//use spring look up method to override
		return authenticater;
	}

	public Map<String, String> convertRequest(String originalRequest) {
		Map<String, String> result = new HashMap<String, String>();
		String[] pairs = originalRequest.split("\r\n");
		try {
			for (String pair : pairs) {
				result.put(pair.split("=")[0], pair.split("=")[1]);
			}
		} catch (Exception e) {
			log
					.info("something wrong when convert the request, may be the format of the request is invalid");
		}
		return result;
	}
	

}
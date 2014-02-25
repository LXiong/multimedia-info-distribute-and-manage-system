package com.yunling.mediacenter.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class AuthRequestDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext context, Channel channel,
			ChannelBuffer buffer) throws Exception {
		String reqString = buffer.toString();
		if(reqString.startsWith("authenticate") && reqString.endsWith("\r\n\r\n")){
			return reqString;
		}else{
			return null;
		}
	}

}

package com.yunling.mediacenter.server;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class AuthPipelineFactory implements ChannelPipelineFactory {
	AuthenticateHandler authenticateHandler;

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		// pipeline.addLast("frame", new DelimiterBasedFrameDecoder(8192,
		// Delimiters.lineDelimiter()));
		pipeline.addLast("frame", new DelimiterBasedFrameDecoder(8192,
				ChannelBuffers.wrappedBuffer("\r\n\r\n".getBytes())));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		pipeline.addLast("reqDecoder", new AuthRequestDecoder());
		pipeline.addLast("handler", authenticateHandler);
		return pipeline;
	}

	public AuthenticateHandler getAuthenticateHandler() {
		return authenticateHandler;
	}

	public void setAuthenticateHandler(AuthenticateHandler handler) {
		this.authenticateHandler = handler;
	}

}

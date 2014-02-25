package com.yunling.mediacenter.server;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.CharsetUtil;
import org.jboss.netty.util.Timer;

public class WatchPipelineFactory implements ChannelPipelineFactory {
	private final Timer timer;
	private int readerIdleTimeSeconds;
	private int writerIdleTimeSeconds;
	private int allIdleTimeSeconds;
	private ChannelHandler handler;

	public WatchPipelineFactory(Timer timer) {
		this.timer = timer;
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		// pipeline.addLast("frame", new DelimiterBasedFrameDecoder(8192,
		// Delimiters.lineDelimiter()));
		pipeline.addLast("frame", new DelimiterBasedFrameDecoder(8192,
				ChannelBuffers.wrappedBuffer("\r\n\r\n".getBytes())));
		//uniform encode and decode.
		pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		// hard code, refactor after a while
		pipeline.addLast("idleHandler", new IdleStateHandler(timer,
				readerIdleTimeSeconds, writerIdleTimeSeconds,
				allIdleTimeSeconds));
		pipeline.addLast("handler", handler);

		return pipeline;
	}

	public ChannelHandler getHandler() {
		return handler;
	}

	public void setHandler(ChannelHandler handler) {
		this.handler = handler;
	}

	public void setReaderIdleTimeSeconds(int readerIdleTimeSeconds) {
		this.readerIdleTimeSeconds = readerIdleTimeSeconds;
	}

	public void setWriterIdleTimeSeconds(int writerIdleTimeSeconds) {
		this.writerIdleTimeSeconds = writerIdleTimeSeconds;
	}

	public void setAllIdleTimeSeconds(int allIdleTimeSeconds) {
		this.allIdleTimeSeconds = allIdleTimeSeconds;
	}

}

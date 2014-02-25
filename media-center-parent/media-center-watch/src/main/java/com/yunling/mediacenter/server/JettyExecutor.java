package com.yunling.mediacenter.server;

import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.commons.console.ConsoleMain;
import com.yunling.mediacenter.server.remote.impl.WebControlImpl;

public class JettyExecutor implements ConsoleMain {
	private Logger log = LoggerFactory.getLogger(JettyExecutor.class);
	private Server server;
	private WebControlImpl webControl;

	@Override
	public void init(String[] paths, Map<String, Object> context)
			throws Exception {
		ServletContextHandler jettyContext = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		jettyContext.setContextPath("/");
		jettyContext.addServlet(new ServletHolder(webControl), "/web_control");
		server.setHandler(jettyContext);
		log.info("---------------jetty inited--------------");
	}

	@Override
	public void release() throws Exception {

	}

	@Override
	public void run(Map<String, Object> context) throws Exception {
		server.start();
		server.join();
	}
	public void setWebControl(WebControlImpl webControl) {
		this.webControl = webControl;
	}

	public void setServer(Server server) {
		this.server = server;
	}

}

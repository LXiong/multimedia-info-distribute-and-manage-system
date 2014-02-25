package com.yunling.mediacenter.server;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.commons.console.ConsoleExecutor;
import com.yunling.commons.console.ConsoleMain;
import com.yunling.mediacenter.db.service.WatcherReportRecordService;
import com.yunling.mediacenter.server.remote.impl.AuthenticateQueryImpl;
import com.yunling.mediacenter.server.remote.impl.WatcherReportImpl;
import com.yunling.mediacenter.util.LicenseUtil;
import com.yunling.mediacenter.utils.Statistics;

public class AuthServer implements ConsoleMain {
	private Logger log = LoggerFactory.getLogger(AuthServer.class);
	int authPort;
	ServerBootstrap bootstrap;
	Server jettyServer;
	Statistics statistics;
	WatcherReportRecordService watcherReportRecordService;
	
	private String customerId;
	private int clientNumber;
	private String license;
	
	@Override
	public void init(String[] args, Map<String, Object> context)
			throws Exception {
		log.info("begin check license.....");
		if (!LicenseUtil.verfiy(license, customerId, "media-center", clientNumber)) {
			log.info("Invalid license.");
			System.out.println("Invalid license");
			System.exit(-1);
		}
		log.info("validate license success");
		log.info("netty init-----------------");
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		
		log.info("jetty init-----------------");
		ServletContextHandler jettyContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
		jettyContext.setContextPath("/");
		jettyContext.addServlet(new ServletHolder(new AuthenticateQueryImpl(statistics)), "/authenticate_query");
		jettyContext.addServlet(new ServletHolder(new WatcherReportImpl(statistics, watcherReportRecordService)), "/watch_report");
		jettyServer.setHandler(jettyContext);
		
	}

	@Override
	public void release() throws Exception {
		
	}

	@Override
	public void run(Map<String, Object> context) throws Exception {
		log.info("netty bind to " + authPort);
		bootstrap.bind(new InetSocketAddress(authPort));
		//execute per 1.5 min
		new Timer().schedule(new WatchServerClean(), 0, 90000);
		
		log.info("jetty start and run.");
		jettyServer.start();
		jettyServer.join();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String[] paths = {
			"conf/applicationContextBase.xml",	
			"conf/applicationContextAuth.xml",	
			"conf/applicationContextService.xml",
		};
		ConsoleExecutor.execute("authServer", paths, args, null);
	}
	public void setBootstrap(ServerBootstrap bootstrap){
		this.bootstrap = bootstrap;
	}

	public void setJettyServer(Server jettyServer) {
		this.jettyServer = jettyServer;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public void setAuthPort(int authPort) {
		this.authPort = authPort;
	}
	private class WatchServerClean extends TimerTask{

		@Override
		public void run() {
			log.info("------------------clean begin------------------");
			log.info("-------------clean idle watch server begin--------------------");
			statistics.cleanIdleWatchServer(System.currentTimeMillis());
			log.info("-------------clean idle watch server end----------------");
			log.info("-------------clean idle stbs begin-------------");
			statistics.cleanIdlePendingStbs("pending", System.currentTimeMillis());
			statistics.revertInstalledPendingStbs(System.currentTimeMillis());
			log.info("-------------clean idle stbs end--------------");
			log.info("------------------clean end------------------");
		}
		
	}
	
	public void setWatcherReportRecordService(
			WatcherReportRecordService watcherReportRecordService) {
		this.watcherReportRecordService = watcherReportRecordService;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

}

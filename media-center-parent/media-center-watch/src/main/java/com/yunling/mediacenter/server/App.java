package com.yunling.mediacenter.server;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.yunling.commons.console.ConsoleExecutor;
import com.yunling.commons.console.ConsoleMain;
import com.yunling.mediacenter.db.model.StbLoginRecord;
import com.yunling.mediacenter.db.service.StbLoginRecordService;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.server.remote.WatcherReport;
import com.yunling.mediacenter.util.LicenseUtil;
import com.yunling.mediacenter.utils.WatchServerConfiguration;
import com.yunling.mediacenter.weather.task.UpdateWeatherTask;

public class App implements ConsoleMain {
	//private Logger log = LoggerFactory.getLogger(App.class);
	private JettyExecutor jettyExecutor;
	private WatchServer watchServer;
	private WatchServerConfiguration config;
	private StbService stbService;
	private StbLoginRecordService stbLoginRecordService;
	private WatcherReport watchReporter;
	private UpdateWeatherTask updateWeatherTask;

	private String customerId;
	private int clientNumber;
	private String license;

	public static void main(String[] args) throws Exception {
		String[] path = { "conf/applicationContextBase.xml",
				"conf/applicationContextMon.xml",
				"conf/applicationContextMyBatis.xml",
				"conf/applicationContextService.xml" };
		ConsoleExecutor.execute("watchApplication", path, args, null);
	}

	@Override
	public void init(String[] args, Map<String, Object> context)
			throws Exception {
		if (!LicenseUtil.verfiy(license, customerId, "media-center",
				clientNumber)) {
			System.out.println("Invalid license");
			System.exit(-1);
		}
		watchServer.init(args, context);
		jettyExecutor.init(args, context);
	}

	@Override
	public void run(Map<String, Object> context) throws Exception {
		watchServer.run(context);

		watchReporter.register(getConfig().getLocalOutnetAddress(), getConfig()
				.getLocalInnetAddress(), config.getPort());
		clearLastLoginStbs();

		new Timer().schedule(new TimerTask(){
			public void run(){
				watchReporter.refresh(getConfig().getLocalOutnetAddress(),
						getConfig().getLocalInnetAddress(), config.getPort());
			}
		}, 0, 60000);

		new Timer().schedule(updateWeatherTask, 0, 60 * 60 * 1000);
		jettyExecutor.run(context);
	}

	@Override
	public void release() throws Exception {

	}

	public JettyExecutor getJettyExecutor() {
		return jettyExecutor;
	}

	public void setJettyExecutor(JettyExecutor jettyExecutor) {
		this.jettyExecutor = jettyExecutor;
	}

	public WatchServer getWatchServer() {
		return watchServer;
	}

	public void setWatchServer(WatchServer watchServer) {
		this.watchServer = watchServer;
	}

	public void setWatchReporter(WatcherReport watchReporter) {
		this.watchReporter = watchReporter;
	}

	public void setConfig(WatchServerConfiguration config) {
		this.config = config;
	}

	public WatchServerConfiguration getConfig() {
		return config;
	}

	public WatcherReport getWatchReporter() {
		return watchReporter;
	}

	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

	public void setStbLoginRecordService(
			StbLoginRecordService stbLoginRecordService) {
		this.stbLoginRecordService = stbLoginRecordService;
	}

	public void setUpdateWeatherTask(UpdateWeatherTask updateWeatherTask) {
		this.updateWeatherTask = updateWeatherTask;
	}

	private void clearLastLoginStbs() {
		/**
		 * stbs which login this watch server success
		 */
		List<StbLoginRecord> lastLoginSuccessOnThisWatchStbs = stbLoginRecordService
				.lastLoginSuccessOn(config.getLocalOutnetAddress());
		for (StbLoginRecord stblog : lastLoginSuccessOnThisWatchStbs) {
			// offline everyone
			if (stblog.getMac() != null) {
				stbService.macOffline(stblog.getMac());
				stbLoginRecordService.insertRecord("logout", stblog.getMac(),
						stblog.getStbIp(), stblog.getStbPort(), config
								.getLocalOutnetAddress(), String.valueOf(config
								.getPort()), "watch_server_restart");
			}
		}
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

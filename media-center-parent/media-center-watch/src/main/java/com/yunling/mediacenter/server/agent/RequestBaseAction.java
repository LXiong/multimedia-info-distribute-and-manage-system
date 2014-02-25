package com.yunling.mediacenter.server.agent;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.service.CityService;
import com.yunling.mediacenter.db.service.ConfigService;
import com.yunling.mediacenter.db.service.ModuleFileService;
import com.yunling.mediacenter.db.service.PackageFilesService;
import com.yunling.mediacenter.db.service.PackagesService;
import com.yunling.mediacenter.db.service.PolicyService;
import com.yunling.mediacenter.db.service.StbLoginRecordService;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.db.service.StbSoftwareVersionService;
import com.yunling.mediacenter.db.service.StbWarningInfoService;
import com.yunling.mediacenter.server.remote.WatcherReport;
import com.yunling.mediacenter.utils.WatchServerConfiguration;
import com.yunling.mediacenter.weather.bean.WeatherConfig;
import com.yunling.mediacenter.weather.bean.WeatherWithImage;

public abstract class RequestBaseAction implements RequestAction {
	private Logger log = LoggerFactory.getLogger(RequestBaseAction.class);
	private StbService stbService;
	private StbSoftwareVersionService stbSoftwareVersionService;
	private ConfigService configService;
	private PolicyService policyService;
	private ModuleFileService moduleFileService;
	private PackageFilesService packageFilesService;
	private Map<String, Channel> macChannels;
	private WatcherReport watchReporter;
	private StbLoginRecordService stbLoginRecordService;
	private StbWarningInfoService stbWarningInfoService;
	private Map<String, WeatherWithImage> allCitiesWeather;
	private WeatherConfig weatherConfig;
	private CityService cityService;
	private WatchServerConfiguration config;
	private Map<String, Map<String, String>> queryResults;

	private PackagesService packageService;

	abstract public String execute(Channel ch, Map<String, String> params);

	protected void refuseConnect(Channel ch, String msg) {
		ChannelFuture future = ch.write(msg);

		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture fu) throws Exception {
				fu.getChannel().close();
				getStbService().updateToken(
						getKeyByValue(getMacChannels(), fu.getChannel()),
						DigestUtils.md5Hex(RandomStringUtils.random(10)));
			}
		});
	}

	protected String getKeyByValue(Map<String, Channel> map, Channel key) {
		for (Map.Entry<String, Channel> entry : map.entrySet()) {
			if (key.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public String getMessageFromMap(Map<String, String> res) {
		StringBuffer msg = new StringBuffer();
		Iterator<Entry<String, String>> it = res.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> pair = it.next();
			msg.append(pair.getKey() + "=" + pair.getValue() + "\r\n");
		}
		msg.append("\r\n");
		return msg.toString();
	}

	// if the stb is request not response, unuse
	public boolean isFinal() {
		return false;
	}

	public Map<String, Channel> getMacChannels() {
		return macChannels;
	}

	public void setMacChannels(Map<String, Channel> macChannels) {
		this.macChannels = macChannels;
	}

	public StbService getStbService() {
		return stbService;
	}

	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

	public Map<String, Map<String, String>> getQueryResults() {
		return queryResults;
	}

	public void setQueryResults(Map<String, Map<String, String>> queryResults) {
		this.queryResults = queryResults;
	}

	public Logger getLog() {
		return log;
	}

	public WatcherReport getWatchReporter() {
		return watchReporter;
	}

	public void setWatchReporter(WatcherReport watchReporter) {
		this.watchReporter = watchReporter;
	}

	public WatchServerConfiguration getConfig() {
		return config;
	}

	public void setConfig(WatchServerConfiguration config) {
		this.config = config;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService confService) {
		this.configService = confService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public StbLoginRecordService getStbLoginRecordService() {
		return stbLoginRecordService;
	}

	public void setStbLoginRecordService(
			StbLoginRecordService stbLoginRecordService) {
		this.stbLoginRecordService = stbLoginRecordService;
	}

	public ModuleFileService getModuleFileService() {
		return moduleFileService;
	}

	public void setModuleFileService(ModuleFileService moduleFileService) {
		this.moduleFileService = moduleFileService;
	}

	public StbSoftwareVersionService getStbSoftwareVersionService() {
		return stbSoftwareVersionService;
	}

	public void setStbSoftwareVersionService(
			StbSoftwareVersionService stbSoftwareVersionService) {
		this.stbSoftwareVersionService = stbSoftwareVersionService;
	}

	public PackageFilesService getPackageFilesService() {
		return packageFilesService;
	}

	public void setPackageFilesService(PackageFilesService packageFilesService) {
		this.packageFilesService = packageFilesService;
	}

	public StbWarningInfoService getStbWarningInfoService() {
		return stbWarningInfoService;
	}

	public void setStbWarningInfoService(
			StbWarningInfoService stbWarningInfoService) {
		this.stbWarningInfoService = stbWarningInfoService;
	}

	public Map<String, WeatherWithImage> getAllCitiesWeather() {
		return allCitiesWeather;
	}

	public void setAllCitiesWeather(
			Map<String, WeatherWithImage> allCitiesWeather) {
		this.allCitiesWeather = allCitiesWeather;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public WeatherConfig getWeatherConfig() {
		return weatherConfig;
	}

	public void setWeatherConfig(WeatherConfig weatherConfig) {
		this.weatherConfig = weatherConfig;
	}

	/**
	 * @return the packageService
	 */
	public PackagesService getPackageService() {
		return packageService;
	}

	/**
	 * @param packageService
	 *            the packageService to set
	 */
	public void setPackageService(PackagesService packageService) {
		this.packageService = packageService;
	}

}

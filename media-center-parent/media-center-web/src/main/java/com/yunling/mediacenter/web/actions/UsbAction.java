package com.yunling.mediacenter.web.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.time.FastDateFormat;

import com.yunling.commons.utils.PageUtil;
import com.yunling.mediacenter.db.model.Conf;
import com.yunling.mediacenter.db.model.Config;
import com.yunling.mediacenter.db.model.ConfigDownLoadLog;
import com.yunling.mediacenter.db.service.ConfService;
import com.yunling.mediacenter.db.service.ConfigDownLoadLogService;
import com.yunling.mediacenter.db.service.ConfigService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class UsbAction extends AbstractUserAwareAction
{
	private final static FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd");
	private final static FastDateFormat formatCompactDateTime = FastDateFormat.getInstance("yyyyMMddHHmm");
	private final static FastDateFormat formatCompactDate = FastDateFormat.getInstance("yyyyMMdd");
	
	private ConfigDownLoadLogService configdownloadlogservice;
	private InputStream stream4Down;
	
	private ConfService confService;
	private ConfigService configService;
	private Long confId;
	private String confName;
	
	private int pageNums;	// Total pages
	private int pageSize;
	
	private String md5(byte[] data) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return "fail";
		}
		md.reset();
		md.update(data);
		byte[] md5Code = md.digest();
		return Hex.encodeHexString(md5Code);
	}
	
	private void createLog(String downloaditem){
		ConfigDownLoadLog log = new ConfigDownLoadLog();
		log.setDownloadtime(new Date());
		log.setDownloaditem(downloaditem);
		log.setUserId(getCurrentUserId());
		configdownloadlogservice.add(log);
	}
	
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String downtimeverify() throws Exception{
		createLog("配置验证文件");
		extractDate();
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE,180);
		String expire = formatCompactDateTime.format(c.getTime());
		String content = "date:"+extractDate()+
			" "+expire+" KFC YUNLING";
		byte[] data = content.getBytes();
		String result = "md5:"+md5(data)+"\nexpire_at:"+expire;
		exportString("date.conf.md5", result);
		return "downloadStream";
	}
	
	public String downtime() throws Exception{
		createLog("时间配置文件");
		extractDate();
		String data = "date:"+extractDate();
		exportString("date.conf", data);
	    return "downloadStream";
	}
	
	private String extractDate(){
		String date = getSingleParam("dateinput");
		String hour = getSingleParam("hour");
		String minute = getSingleParam("minute");
		if(hour.length()==1){
			hour = 0+hour;
		}
		if(minute.length()==1){
			minute=0+minute;
		}
		String year = date.substring(0,4);
		String month = date.substring(5,7);
		String day = date.substring(8);
		String result = month+day+hour+minute+year;
		return result; 
	}

	private void exportString(String filename, String md5Code) {
		byte[] data1 = md5Code.getBytes();
		stream4Down = new ByteArrayInputStream(data1);
		setDownloadHead(filename, data1.length);
	}
	
	public String time() throws Exception {
		Calendar cal = Calendar.getInstance();
		request.put("currentDate", fdf.format(cal.getTime()));
		request.put("currentHour", cal.get(Calendar.HOUR_OF_DAY));
		request.put("currentMinute",cal.get(Calendar.MINUTE));
		return "time";
	}
	
	/**
	 * get the configuration files
	 * @return
	 * @throws Exception
	 */
	public String config() throws Exception {
		int total = confService.countAll();
		PageUtil pu = new PageUtil(getSingleParam("page"), total, 
				pageSize>0 ? pageSize :webConfig.getPageSize());
		List<Conf> confs = confService.list(pu.getBegin(), pu.getEnd());
		request.put("confs", confs);
		request.put("pageBean",pu);
		return "config";
	}
	/**
	 * output the configuration file contents
	 * @return
	 * @throws Exception
	 */
	public String confContent() throws Exception {
		Config config = new Config();
		config.setConfId(confId);
		List<Config> configs = configService.getConfigs(config);
		request.put("configs", configs);
		return "content";
	}
	
	public String exportConfig() throws Exception {
		StringBuilder configFileContent = createConfigFileContent();
		this.stream4Down = new ByteArrayInputStream(configFileContent.toString().getBytes());
		this.setDownloadHead("stb.conf", configFileContent.length());
		addLog("conf");
		return "downloadStream";
	}
	
	public String exportConfigMd5() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 180);
		String date = formatCompactDate.format(calendar.getTime());
		StringBuilder configFileContent = createConfigFileContent();
		configFileContent.append(" ");
		configFileContent.append(date);
		configFileContent.append(" KFC YUNLING");
		String md5 = this.md5(configFileContent.toString().getBytes());
		StringBuilder sb = new StringBuilder("md5:\t");
		sb.append(md5);
		sb.append("\nexpire_at:\t");
		sb.append(date);
		this.stream4Down = new ByteArrayInputStream(sb.toString().getBytes());
		this.setDownloadHead("stb.conf.md5", sb.length());
		addLog("conf.md5");
		return "downloadStream";
	}

	private StringBuilder createConfigFileContent() {
		Config configSample = new Config();
		configSample.setConfId(confId);
		List<Config> configs = configService.getConfigs(configSample);
		StringBuilder sb = new StringBuilder(configs.size()*15);
		for(Config config : configs) {
			sb.append(config.getConfigKey());
			sb.append(":\t");
			sb.append(config.getConfigValue());
			sb.append("\n");
		}
		return sb;
	}
	
	private void addLog(String downloadItem) {
		ConfigDownLoadLog configLog = new ConfigDownLoadLog();
		long userId = getCurrentUserId();
		log.info("userId: {} download {}", userId, downloadItem);
		configLog.setUserId(userId);
		configLog.setDownloaditem(downloadItem);
		Date downloadTime = new Date();
		configLog.setDownloadtime(downloadTime);
		configdownloadlogservice.add(configLog);
	}
	
	/**
	 * @return the confService
	 */
	public ConfService getConfService() {
		return confService;
	}

	/**
	 * @param confService the confService to set
	 */
	public void setConfService(ConfService confService) {
		this.confService = confService;
	}

	/**
	 * @return the configService
	 */
	public ConfigService getConfigService() {
		return configService;
	}

	/**
	 * @param configService the configService to set
	 */
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	/**
	 * @return the confId
	 */
	public Long getConfId() {
		return confId;
	}

	/**
	 * @param confId the confId to set
	 */
	public void setConfId(Long confId) {
		this.confId = confId;
	}

	/**
	 * @return the confName
	 */
	public String getConfName() {
		return confName;
	}

	/**
	 * @param confName the confName to set
	 */
	public void setConfName(String confName) {
		this.confName = confName;
	}

	/**
	 * @return the pageNums
	 */
	public int getPageNums() {
		return pageNums;
	}

	/**
	 * @param pageNums the pageNums to set
	 */
	public void setPageNums(int pageNums) {
		this.pageNums = pageNums;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public ConfigDownLoadLogService getConfigdownloadlogservice() {
		return configdownloadlogservice;
	}
	public void setConfigdownloadlogservice(
			ConfigDownLoadLogService configdownloadlogservice) {
		this.configdownloadlogservice = configdownloadlogservice;
	}
	public InputStream getStream4Down() {
		return stream4Down;
	}
}

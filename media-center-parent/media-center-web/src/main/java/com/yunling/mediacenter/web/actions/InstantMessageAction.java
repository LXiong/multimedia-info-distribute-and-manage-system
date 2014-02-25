package com.yunling.mediacenter.web.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.server.remote.WebControl;
import com.yunling.mediacenter.web.actions.vo.InstantMessage;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

public class InstantMessageAction extends AbstractUserAwareAction {
	
	private static List<String> ALLOW_STATUS = Arrays.asList(new String[]{"online", "updating"});
	private InstantMessage message = new InstantMessage();
	private List<InstantMessage> messages = new ArrayList<InstantMessage>();
	private List<Stb> stbs = new ArrayList<Stb>();
	
	/* outer dependencies */
	private StbService stbService;
	private WebControl localService;

	Logger log = LoggerFactory.getLogger(InstantMessageAction.class);
	@Override
	public String execute() throws Exception {
		message = new InstantMessage("Hello, world!");

		return SUCCESS;
	}

	public String send(){
		List<String> macs = message.getStbMac();
		Map<String, String> params = message.getMessageParam();
		List<String> macAddresses = new ArrayList<String>();
		if (macs.contains("0")) {
			List<Stb> stbs = stbService.listOnlineByGroup(Long
					.valueOf(message.getGroupId()));
			for (Stb stb : stbs) {
				macAddresses.add(stb.getStbMac());
			}
		} else {
			log.info("macs@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + macs);
			Iterator<String> it = macs.iterator();
			while(it.hasNext()){
				String mac = it.next();
				Stb stb = stbService.findByMac(mac);
				log.info("stb with mac : " + mac);
				if (stb == null) {
					continue;
				}
				log.info(stb.toString());
				log.info("stb status " + stb.getStbStatus());
				if(ALLOW_STATUS.contains(stb.getStbStatus())){
					log.info("add mac to addresses");
					macAddresses.add(stb.getStbMac());
				}
			}
		}
		saveToFile(params, macAddresses);
		localService.sendInstantMessage(params, macAddresses);
		return SUCCESS;
	}

	public String histroies() {
		messages = loadHistroies();
		//log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@num" + messages.size());
		return "histroies";
	}
	
	public String show(){
		File file = new File(webConfig.getInstantMessagePath(), message.getFileName() + ".txt");
		message = this.loadOneHistroy(file);
		stbs = getStbService().listMacIn(message.getStbMac());
		return "show";
	}
	
	public String terminals(){
		stbs = getStbService().listMacIn(loadOneHistroy(new File(webConfig.getInstantMessagePath(), message.getFileName()+".txt")).getStbMac());
		return "terminals";
	}

	public void saveToFile(Map<String, String> content, List<String> macs) {
		Map<String, String> localMap = new HashMap<String, String>();
		localMap.putAll(content);
		localMap.put("send_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()));
		File dir = new File(webConfig.getInstantMessagePath());
		File instantMessageFile = new File(dir, new SimpleDateFormat(
				"yyyyMMdd-HHmmss").format(new Date())
				+ ".txt");
		OutputStream os = null;
		try {
			if (!dir.exists()){
				dir.mkdirs();
			}
			os = new FileOutputStream(instantMessageFile);
			log.info("##########################");
			log.info(instantMessageFile.getAbsolutePath());
			StringBuilder sb = new StringBuilder();
			Iterator<Entry<String, String>> it = localMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> pair = it.next();
				sb.append(pair.getKey() + ":");
				sb.append(pair.getValue() + "\r\n");
			}

			sb.append("target_addresses:[");
			sb.append(StringUtils.join(macs, ","));
			sb.append("]\r\n");
			os.write(sb.toString().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<InstantMessage> loadHistroies() {
		List<InstantMessage> list = new ArrayList<InstantMessage>();
		File dir = new File(webConfig.getInstantMessagePath());
		File[] filearr = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".txt");
			}
			
		});
		if(filearr != null){
			List<File> files = Arrays.asList(filearr);
			Iterator<File> it = files.iterator();
			while (it.hasNext()) {
				File file = it.next();
				//log.debug("#########################" + (file == null));
				InstantMessage msg = loadOneHistroy(file);
				if (msg != null)
					list.add(msg);
			}
		}
		return list;
	}

	public InstantMessage loadOneHistroy(File file) {
		//log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + file.getAbsolutePath());
		InstantMessage msg = new InstantMessage();
		msg.setFileName(file.getName());
		try {
			List<String> lines = FileUtils.readLines(file);
			Iterator<String> it = lines.iterator();
			while (it.hasNext()) {
				String line = StringUtils.trimToEmpty(it.next());
				int index = line.indexOf(":");
				if (index > -1) {
					String key = StringUtils.trimToEmpty(line.substring(0, index));
					String value = StringUtils.trimToEmpty(line.substring(index + 1, line
							.length()));
					msg.setAttribute(key, value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return msg;
	}
	
	public InstantMessage getMessage() {
		return message;
	}

	public void setMessage(InstantMessage message) {
		this.message = message;
	}

	public StbService getStbService() {
		return stbService;
	}

	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

	public WebControl getLocalService() {
		return localService;
	}

	public void setLocalService(WebControl localService) {
		this.localService = localService;
	}

	public List<InstantMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<InstantMessage> messages) {
		this.messages = messages;
	}

	public List<Stb> getStbs() {
		return stbs;
	}

	public void setStbs(List<Stb> stbs) {
		this.stbs = stbs;
	}

}

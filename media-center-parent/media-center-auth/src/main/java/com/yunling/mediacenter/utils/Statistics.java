package com.yunling.mediacenter.utils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.model.WatchServerStatus;
import com.yunling.mediacenter.db.service.StbLoginRecordService;
import com.yunling.mediacenter.db.service.StbService;

public class Statistics {
	private Logger log = LoggerFactory.getLogger(Statistics.class);
	// auth server start up time
	private Date startUpTime = new Date();
	// watch server ip => stb logon this watch server
	private Set<WatchServerStatus> watcherAddresses = new ConcurrentSkipListSet<WatchServerStatus>();
	// auth success
	private List<String> authenticateSuccess = new LinkedList<String>();
	// auth failure
	private List<String> authenticateFailure = new LinkedList<String>();
	// auth , need audit
	private List<String> authenticateAudit = new LinkedList<String>();

	private StbService stbService;
	private StbLoginRecordService stbLoginRecordService;

	/**
	 * for authenticate self
	 * 
	 * @param remoteIp
	 */
	public void increaseAuthenticateSuccess(String remoteIp) {
		authenticateSuccess.add(remoteIp);
	}

	public void increaseAuthenticateFailure(String remoteIp) {
		authenticateFailure.add(remoteIp);
	}

	public void increaseAuthenticateAudit(String remoteIp) {
		authenticateAudit.add(remoteIp);
	}

	public String getUsableIpAndPort(boolean is3g) {
		if (watcherAddresses.size() < 1) {
			return "0.0.0.0:0000";
		} else {
			WatchServerStatus wss = Collections.min(watcherAddresses);
			if (is3g) {
				return wss.getWatcherInnetIp() + ":" + wss.getWatchPort();
			} else {
				return wss.getWatcherIp() + ":" + wss.getWatchPort();
			}

		}
	}

	/**
	 * for web query
	 * 
	 * @return
	 */

	public String getStartUpTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(startUpTime);
	}

	public int authenticateSuccessCount() {
		return authenticateSuccess.size();
	}

	public int authenticateFailureCount() {
		return authenticateFailure.size();
	}

	public int authenticateAuditCount() {
		return authenticateAudit.size();
	}

	public List<WatchServerStatus> allWatchServerStatus() {
		List<WatchServerStatus> all = new LinkedList<WatchServerStatus>();
		all.addAll(watcherAddresses);
		return all;
	}

	public WatchServerStatus getWatchServerStatusByIp(String ip) {
		for (Iterator<WatchServerStatus> it = watcherAddresses.iterator(); it
				.hasNext();) {
			WatchServerStatus wss = it.next();
			if (wss.getWatcherIp().equalsIgnoreCase(ip)) {
				return wss;
			}
		}
		return null;
	}

	/**
	 * for watch report
	 * 
	 * @param watcherIp
	 */
	public String registerWatcher(String watcherOutIp, String watcherInIp,
			int port) {
		WatchServerStatus wss = new WatchServerStatus(watcherOutIp,
				watcherInIp, port);
		
		Iterator<WatchServerStatus> it = watcherAddresses.iterator();
		while (it.hasNext()) {
			WatchServerStatus existWss = it.next();
			if (wss.equals(existWss)) {
				/**
				 * don`t clear the watch server
				 */
//				Set<String> macs = existWss.getLoginStbMac();
//				Iterator<String> macIt = macs.iterator();
//				while (macIt.hasNext()) {
//					String mac = macIt.next();
//					stbService.macOffline(mac);
//					stbLoginRecordService.insertRecord("logout", mac, "unknow",
//							"unknow", watcherOutIp, String.valueOf(port),
//							"logout");
//				}
				it.remove();
				watcherAddresses.add(wss);
				return "registered";
			}
		}
		log.info("add this server with outnet ip:" + watcherOutIp
				+ " and innet ip:" + watcherInIp + " and port:" + port
				+ " to auth server");
		watcherAddresses.add(wss);
		return "success";

	}

	public String unregisterWatcher(String watcherIp, int port) {
		WatchServerStatus wss = new WatchServerStatus(watcherIp, port);
		if (!watcherAddresses.contains(wss)) {
			log.info("------------unregister watcher Addresses:"
					+ wss.getWatcherIp());
			watcherAddresses.remove(wss);
			log.info("watcherAddresses:" + watcherAddresses);
			return "success";
		}
		return "unregistered";
	}

	public String increaseWatcherLogin(String watcherIp, String mac) {
		Iterator<WatchServerStatus> it = watcherAddresses.iterator();
		while (it.hasNext()) {
			WatchServerStatus wss = it.next();
			if (wss.getWatcherIp().equalsIgnoreCase(watcherIp)) {
				wss.getLoginStbMac().add(mac);
				log.info("now there are " + wss.getStbCount()
						+ " stbs on this server " + watcherIp);
				return "success";
			}
		}
		return "failure";
	}

	public String decreaseWatcherLogin(String watcherIp, String mac) {
		Iterator<WatchServerStatus> it = watcherAddresses.iterator();
		while (it.hasNext()) {
			WatchServerStatus wss = it.next();
			if (wss.getWatcherIp().equalsIgnoreCase(watcherIp)) {
				wss.getLoginStbMac().remove(mac);
				log.info("now there are " + wss.getStbCount()
						+ " stbs on this server " + watcherIp);
				return "success";
			}
		}
		return "failure";
	}

	public String refreshStatus(String watcherIp, String outIp, int port) {
		WatchServerStatus wss = new WatchServerStatus(watcherIp, port);
		Iterator<WatchServerStatus> it = watcherAddresses.iterator();
		while (it.hasNext()) {
			WatchServerStatus ws = it.next();
			if (wss.equals(ws)) {
				ws.updateRefreshTime(new Date());
				log.info("watch server:" + ws.getWatcherIp() + ":"
						+ ws.getWatchPort() + " has refresh it`s state.");
				return "success";
			}
		}
		log.info("this ip and port does not exist in this auth server"
				+ " and I will register it on this auth server");
		return registerWatcher(watcherIp, outIp, port);

	}

	public void cleanIdleWatchServer(long now) {

		for (Iterator<WatchServerStatus> it = watcherAddresses.iterator(); it
				.hasNext();) {
			WatchServerStatus wss = it.next();
			if ((now - wss.getRefreshTime().getTime()) / 60000 > 2) {
				Iterator<String> macIt = wss.getLoginStbMac().iterator();
				while(macIt.hasNext()){
					String mac = macIt.next();
					stbService.macOffline(mac);
					stbLoginRecordService.insertRecord("logout", mac, "unknow",
							"unknow", wss.getWatcherIp(), String.valueOf(wss.getWatchPort()),
							"logout");
				}
				it.remove();
			}
		}
	}
	
	public void cleanIdlePendingStbs(String status, long now){
		List<Stb> pendingStbs = stbService.listByStatus(status);
		if(pendingStbs.size() == 0){
			return ;
		}
		Iterator<Stb> it = pendingStbs.iterator();
		long timeAge = now - (5*60*1000);
		while(it.hasNext()){
			Stb stb = it.next();
			if(stb.getLastAccessTime() != null){
				if(timeAge > stb.getLastAccessTime().getTime()){
					log.info("---------will delete a pending stb which don`t access more than 30 minutes");
					stbService.delete(stb);
					log.info("---------have deleted a pending stb ----------------");
				}
			}
		}
	}
	
	public void revertInstalledPendingStbs(long now){
		List<Stb> pendingStbs = stbService.listByStatus("installed_pending");
		if(pendingStbs.size() == 0){
			return ;
		}
		Iterator<Stb> it = pendingStbs.iterator();
		long timeAge = now - (5*60*1000);
		while(it.hasNext()){
			Stb stb = it.next();
			if(stb.getLastAccessTime() != null){
				if(timeAge > stb.getLastAccessTime().getTime()){
					log.info("---------will revert an installed_pending stb which don`t access more than 30 minutes");
					stbService.updateStatus(stb.getStbMac(), "notinstalled");
					log.info("---------have reverted an installed_pending stb ----------------");
				}
			}
		}
	}

	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

	public void setStbLoginRecordService(StbLoginRecordService stbLoginRecordService) {
		this.stbLoginRecordService = stbLoginRecordService;
	}
}

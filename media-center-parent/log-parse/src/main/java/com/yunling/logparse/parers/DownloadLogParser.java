package com.yunling.logparse.parers;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.BoxDownLog;
import com.yunling.mediacenter.db.model.BoxDownStatus;
import com.yunling.mediacenter.db.service.BoxDownLogService;
import com.yunling.mediacenter.db.service.BoxDownStatusService;

public class DownloadLogParser extends AbstractParser {
	private Logger log = LoggerFactory.getLogger(DownloadLogParser.class);
	private BoxDownLogService boxDownLog;
	private BoxDownStatusService boxDownStatus;

	@Override
	protected boolean parse(BufferedReader br, String macAddress)
			throws Exception {
		String logStr = null;
		try {
			while ((logStr = br.readLine()) != null) {
				if (isEndLine(logStr.trim())) {
					log.debug("end line: {}", logStr);
				} else if (!StringUtils.isEmpty(logStr.trim())) {
					this.parseLogStr(macAddress, logStr.trim());
				}
			}
		} catch (Exception e) {
			log.debug("error in line {}", logStr);
			log.error("failed to parse the download log!", e);
			return false;
		} finally {
			IOUtils.closeQuietly(br);
		}
		return true;
	}

	/**
	 * 
	 * @param macAddress
	 * @param logStr
	 * @throws Exception
	 */
	/*private void parseLogStr(String macAddress, String logStr) throws Exception {
		String[] strArr = logStr.split("\\|");
		String policyNumber = null; // policy version
		String videoName = logStr.substring(logStr.indexOf("name=") + 5,
				logStr.indexOf('.') + 4).trim(); // file name
		Long doneSize = -1L;
		Long totalSize = -1L;
		Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.parse(logStr.substring(0, 23));

		log.info("log info is: {}", logStr);
		if (logStr.startsWith("start download")) {
			if (logStr.indexOf("xml") != -1) {
				// this file is the policy file
				policyNumber = "-1";
			} else {
				policyNumber = strArr[2].trim();
			}
		} else if (logStr.startsWith("finish download")) {
			if (logStr.indexOf("xml") != -1) {
				// this file is the policy file
				policyNumber = "-1";
			} else {
				policyNumber = strArr[2].trim();
			}
			doneSize = 0L;
			totalSize = 0L;
		} else if (logStr.startsWith("ftp downloading")) {
			policyNumber = strArr[4].trim();
			String done = strArr[1].trim();
			doneSize = Long.parseLong(done.substring(done.indexOf("done") + 5));
			String total = strArr[2].trim();
			totalSize = Long
					.parseLong(total.substring(total.indexOf("total") + 6));
		} else {
			log.warn("not expected line");
			return;
		}
		// insert down load logs
		this.updateDownLog(macAddress, policyNumber, videoName, doneSize,
				totalSize, time);
		// insert down load status logs
		this.updateDownStatusLog(macAddress, policyNumber, videoName, doneSize,
				totalSize, time, logStr);
	}*/
	
	/**
	 * 
	 * @author LingJun
	 * @date 2012-7-10
	 *
	 * @param macAddress
	 * @param logStr
	 * @throws Exception
	 */
	private void parseLogStr(String macAddress, String logStr) throws Exception {
		String[] strArr = logStr.split("\\|");
		String policyNumber = null; // policy version
		String videoName = logStr.substring(logStr.indexOf("name=") + 5,
				logStr.indexOf('.') + 4).trim(); // file name
		Long doneSize = -1L;
		Long totalSize = -1L;
		Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.parse(logStr.substring(0, 23));

		log.info("log info is: {}", logStr);
		if (logStr.contains("media")) { // media download
			policyNumber = strArr[2].trim();
		} else if (logStr.contains("finish")) { // policy download
			// policyNumber = videoName.substring(0, videoName.indexOf("."));
			policyNumber = "-1";
			doneSize = 0L;
			totalSize = 0L;
		} else if (logStr.contains("ftp")) { // downloading
			policyNumber = strArr[4].trim();
			String done = strArr[1].trim();
			doneSize = Long.parseLong(done.substring(done.indexOf("done") + 5));
			String total = strArr[2].trim();
			totalSize = Long
					.parseLong(total.substring(total.indexOf("total") + 6));
		} else {
			log.warn("not expected line");
			return;
		}
		// insert down load logs
		this.updateDownLog(macAddress, policyNumber, videoName, doneSize,
				totalSize, time);
		// insert down load status logs
		this.updateDownStatusLog(macAddress, policyNumber, videoName, doneSize,
				totalSize, time, logStr);
	}

	/**
	 * 
	 * @param macAddress
	 * @param policyNumber
	 * @param videoName
	 * @param doneSize
	 * @param totalSize
	 * @param time
	 */
	private void updateDownLog(String macAddress, String policyNumber,
			String videoName, Long doneSize, Long totalSize, Date time)
			throws Exception {
		BoxDownLog downLog;
		downLog = new BoxDownLog();
		downLog.setMac(macAddress);
		downLog.setPolicyNumber(policyNumber);
		downLog.setVideoName(videoName);
		downLog.setDoneSize(doneSize);
		downLog.setTotalSize(totalSize);
		downLog.setLogTime(time);
		boxDownLog.insertLog(downLog);
	}

	/**
	 * 
	 * @param macAddress
	 * @param policyNumber
	 * @param videoName
	 * @param doneSize
	 * @param totalSize
	 * @param time
	 * @param str
	 * @throws Exception
	 */
	private void updateDownStatusLog(String macAddress, String policyNumber,
			String videoName, Long doneSize, Long totalSize, Date time,
			String str) throws Exception {
		BoxDownStatus downStatus;
		downStatus = new BoxDownStatus();
		downStatus.setMac(macAddress);
		downStatus.setPolicyNumber(policyNumber);
		downStatus.setVideoName(videoName);
		
		BoxDownStatus ds = boxDownStatus.getDownStatus(downStatus);
		if (str.contains("media")) { // media download
//			downStatus.setStartTime(time);
			downStatus.setIsFinished("Y");
			
			if (ds != null) {
				if (ds.getTotalSize() == null || ds.getTotalSize() == -1L) {
					downStatus.setDoneSize(0L);
					downStatus.setTotalSize(0L);
				} else {
					downStatus.setDoneSize(ds.getDoneSize());
					downStatus.setTotalSize(ds.getTotalSize());
				}
				// if it's finished, then the end time should not be changed.
				if("Y".equals(ds.getIsFinished()) && ds.getEndTime()!=null) {
					downStatus.setEndTime(ds.getEndTime());
				} else {
					downStatus.setEndTime(time);
				}
			} else {
				downStatus.setDoneSize(0L);
				downStatus.setTotalSize(0L);
				downStatus.setEndTime(time);
			}
//			downStatus.setDoneSize(-1L);
//			downStatus.setTotalSize(-1L);
			boxDownStatus.insertOrUpdate(downStatus);
		} else if (str.contains("finish")) { // policy download
			downStatus.setIsFinished("Y");
			if (ds != null) {
				if("Y".equals(ds.getIsFinished()) && ds.getEndTime()!=null) {
					downStatus.setEndTime(ds.getEndTime());
				} else {
					downStatus.setEndTime(time);
				}
			} else {
				downStatus.setEndTime(time);
			}
//			downStatus.setDoneSize(doneSize);
//			downStatus.setTotalSize(totalSize);
			boxDownStatus.insertOrUpdate(downStatus);
		} else if (str.contains("ftp")) {
			if(ds != null) {
				if(ds.getStartTime() != null) {
					downStatus.setStartTime(ds.getStartTime());
				} 
			} else {
				downStatus.setStartTime(time);
			}
			downStatus.setEndTime(time);
			downStatus.setDoneSize(doneSize);
			downStatus.setTotalSize(totalSize);
			downStatus.setIsFinished("N");
			boxDownStatus.insertOrUpdate(downStatus);
		}
	}

	/**
	 * @return the boxDownLog
	 */
	public BoxDownLogService getBoxDownLog() {
		return boxDownLog;
	}

	/**
	 * @param boxDownLog
	 *            the boxDownLog to set
	 */
	public void setBoxDownLog(BoxDownLogService boxDownLog) {
		this.boxDownLog = boxDownLog;
	}

	/**
	 * @return the boxDownStatus
	 */
	public BoxDownStatusService getBoxDownStatus() {
		return boxDownStatus;
	}

	/**
	 * @param boxDownStatus
	 *            the boxDownStatus to set
	 */
	public void setBoxDownStatus(BoxDownStatusService boxDownStatus) {
		this.boxDownStatus = boxDownStatus;
	}

}

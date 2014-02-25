package com.yunling.logparse.parers;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.BoxPerfLog;
import com.yunling.mediacenter.db.service.BoxPerfLogService;

public class PerformanceParser extends AbstractParser {
	private BoxPerfLogService boxPerfLogService;
	private Logger log = LoggerFactory.getLogger(PerformanceParser.class);

	@Override
	protected boolean parse(BufferedReader br, String macAddress) {
		log.debug("PerformanceParser start");
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				log.info("log info is: {}", line);
				line = line.trim();
				if (isEndLine(line) || StringUtils.isEmpty(line)) {
					continue;
				}
				BoxPerfLog box = new BoxPerfLog();
				box.setMemUsed(Long.parseLong(line.substring(
						line.indexOf("=") + 1, line.indexOf(" | d")).trim()));
				box.setDiskUsed(Long.parseLong(line.substring(
						line.indexOf("k") + 2, line.indexOf(" | c")).trim()));
				box.setCpuUsed(Long.parseLong(line.substring(
						line.indexOf("u") + 2, line.lastIndexOf("|") - 1).trim()));
				box.setLogTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(line.substring(0, 23)));
				box.setMac(macAddress);
				boxPerfLogService.add(box);
			}
		} catch (NumberFormatException e) {
			log.debug("error in line {}", line);
			log.error("NumberFormatException", e);
			return false;
		} catch (IOException e) {
			log.debug("error in line {}", line);
			log.error("IOException", e);
			return false;
		} catch (ParseException e) {
			log.debug("error in line {}", line);
			log.error("ParseException", e);
			return false;
		} finally {
			IOUtils.closeQuietly(br);
		}
		log.debug("PerformanceParser end");
		return true;
	}

	public BoxPerfLogService getBoxPerfLogService() {
		return boxPerfLogService;
	}

	public void setBoxPerfLogService(BoxPerfLogService boxPerfLogService) {
		this.boxPerfLogService = boxPerfLogService;
	}
}

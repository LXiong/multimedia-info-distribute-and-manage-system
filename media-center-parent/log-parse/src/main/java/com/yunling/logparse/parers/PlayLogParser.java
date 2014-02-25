package com.yunling.logparse.parers;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.BoxPlayLog;
import com.yunling.mediacenter.db.service.BoxPlayLogService;

public class PlayLogParser extends AbstractParser {
	private BoxPlayLogService boxPlayLogService;
	private Logger log = LoggerFactory.getLogger(PlayLogParser.class);

	@Override
	protected boolean parse(BufferedReader br, String macAddress) {
		log.debug("PlayLogParser start");
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				log.info("log info is: {}", line);
				line = line.trim();
				if (isEndLine(line) || StringUtils.isEmpty(line)) {
					continue;
				}
				BoxPlayLog box = new BoxPlayLog();
				box.setMac(macAddress);
				box.setVideoName(line.substring(line.indexOf("=") + 1,
						line.indexOf("|", line.indexOf("=")) - 1));
				box.setPolicyNumber(line.substring(
						line.indexOf("|", line.indexOf("=")) + 2,
						line.lastIndexOf("|") - 1));
				box.setLogTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(line.substring(0, 23)));
				boxPlayLogService.add(box);
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
		log.debug("PlayLogParser end");
		return true;
	}

	public BoxPlayLogService getBoxPlayLogService() {
		return boxPlayLogService;
	}

	public void setBoxPlayLogService(BoxPlayLogService boxPlayLogService) {
		this.boxPlayLogService = boxPlayLogService;
	}

	public static void main(String[] args) {
		String line = "play video | name=AAF81A803266FF7F94035F22C89C675D.AVI | 201101132355 |  2011-01-14 12:41:32,953";
		System.out.println(line.substring(line.indexOf("=") + 1,
				line.indexOf("|", line.indexOf("=")) - 1));

	}
}

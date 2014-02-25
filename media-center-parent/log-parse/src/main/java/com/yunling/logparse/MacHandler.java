package com.yunling.logparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.logparse.parers.AbstractParser;

public class MacHandler {
	private Logger log = LoggerFactory.getLogger(MacHandler.class);

	private String backupPath;
	private String errorPath;
	private Map<String, AbstractParser> parserMap;

	public void processLog(MacDir macDir) {
		// List files
		File[] files = macDir.getMacDir().listFiles();
		for (File file : files) {
			if (!file.isFile() || !file.exists()) {
				continue;
			}
			for (Map.Entry<String, AbstractParser> entry : parserMap.entrySet()) {
				String head = entry.getKey();
				if (file.getName().startsWith(head)) {
					InputStream in = null;
					if (!isFileUploadingEnd(file)) {
						// skip not ended file.
						continue;
					}
					boolean result;
					try {
						in = new FileInputStream(file);
						result = entry.getValue().parse(in, macDir.getMac());
					} catch (Exception e) {
						log.debug("Current parser is {} ", entry.getValue()
								.getClass());
						log.error("While parsing log", e);
						result = false;
					} finally {
						IOUtils.closeQuietly(in);
					}
					if (result) {
						// moveToBackup(file, macDir.getMac());
						this.deleteLog(file);
					} else {
						this.moveToError(file, macDir.getMac());
					}
				}
			}
		}
	}

	private boolean isFileUploadingEnd(File file) {
		InputStream in1 = null;
		BufferedReader br = null;
		boolean isEnded = false;
		try {
			in1 = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in1));
			String line = null;
			String lastLine = null;
			while ((line = br.readLine()) != null) {
				lastLine = line;
				if (StringUtils.equals("----end----",
						StringUtils.trim(lastLine))) {
					isEnded = true;
					break;
				}
			}
		} catch (Exception e) {
			log.debug("Current file is {} ", file.getName());
			log.error("While reading log", e);
		} finally {
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(in1);
		}
		return isEnded;
	}

	private void moveToError(File file, String mac) {
		File macSubDir = new File(errorPath, mac);
		if (!macSubDir.exists()) {
			boolean flag = macSubDir.mkdirs();
			if (!flag) {
				log.warn("Failed to create directory {} ",
						macSubDir.getAbsoluteFile());
			}
		}
		try {
			FileUtils.copyFileToDirectory(file, macSubDir);
			FileUtils.deleteQuietly(file);
		} catch (IOException e) {
			// e.printStackTrace();
			log.error("Failed to move log file to error directory", e);
		}
	}

	@SuppressWarnings("unused")
	private void moveToBackup(File file, String mac) {
		File macSubDir = new File(backupPath, mac);
		if (!macSubDir.exists()) {
			boolean flag = macSubDir.mkdirs();
			if (!flag) {
				log.warn("Failed to create directory {} ",
						macSubDir.getAbsoluteFile());
			}
		}
		try {
			FileUtils.copyFileToDirectory(file, macSubDir);
			FileUtils.deleteQuietly(file);
		} catch (IOException e) {
			log.error("Failed to move log file to backup directory", e);
		}
	}

	/**
	 * 
	 * @param file
	 *            create by L.J. on 6/17/2011
	 */
	private void deleteLog(File file) {
		try {
			FileUtils.deleteQuietly(file);
		} catch (Exception e) {
			log.error("Failed to delete log file", e);
		}
	}

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}

	public String getErrorPath() {
		return errorPath;
	}

	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}

	public Map<String, AbstractParser> getParserMap() {
		return parserMap;
	}

	public void setParserMap(Map<String, AbstractParser> parserMap) {
		this.parserMap = parserMap;
	}
}

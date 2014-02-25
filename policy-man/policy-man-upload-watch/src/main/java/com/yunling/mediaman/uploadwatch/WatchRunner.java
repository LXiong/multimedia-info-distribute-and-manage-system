package com.yunling.mediaman.uploadwatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.policyman.db.model.UploadingVideo;
import com.yunling.policyman.db.service.UploadingVideoService;

public class WatchRunner implements Runnable {
	private static Logger log = LoggerFactory.getLogger(WatchRunner.class);

	private boolean filterWithExt = false;
	private String watchPath;
	private File watchDir;
	private boolean runFlag;
	private int millis= 20 * 1000;
	private UploadingVideoService uploadingVideoService;
	private List<String> extensions;
	
	@Override
	public void run() {
		this.exec("sync");
		runFlag = true;
		while (runFlag) {
			this.exec("sync");
			File[] files = watchDir.listFiles();
			for(File file: files) {
				String name = file.getName();
				
				if (filterWithExt && !checkExt(name)) {
					continue;
				}
				UploadingVideo video = uploadingVideoService.findByOriginName(name);
				if (video == null) {
					video = new UploadingVideo();
					video.setOriginName(name);
					video.setUploaded(Boolean.FALSE);
					video.setCurrentSize(file.length());
					video.setLastModifiedTime(new Date());
					try {
						uploadingVideoService.insert(video);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				} else {
					if (Boolean.TRUE.equals(video.getUploaded())) {
						// Because the uploading is completed.
						continue;
					}
					
					if (video.getExpectedSize() != null && video.getExpectedSize().equals(file.length())) {
						String md5 = calculateMd5(file);
						if (md5 == null) {
							log.error("Failed to generate md5 code for file {} ", file.getName());
						}
						video.setCode(md5);
						video.setUploaded(Boolean.TRUE);
					} else if (video.getCurrentSize()!=null && video.getCurrentSize().equals(file.length())) {
						// Ignore because the size is not changed.
						continue;
					}
					video.setCurrentSize(file.length());
					video.setLastModifiedTime(new Date());
					uploadingVideoService.update(video);
				}
			}
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String calculateMd5(File file) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] md5Array = DigestUtils.md5(in);
			return Hex.encodeHexString(md5Array);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
		return null;
	}

	private boolean checkExt(String name) {
		if (name == null) {
			return false;
		}
		for(String ext : extensions) {
			if (name.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	private void exec(String command) {
		try {
			Runtime run = Runtime.getRuntime();
			run.exec(command);
		} catch (Exception e) {
			log.error("sync command execute error!");
		}
	}
	
	public void setWatchPath(String watchPath) {
		this.watchPath = watchPath;
		watchDir = new File(watchPath);
		if (!watchDir.exists()) {
			throw new RuntimeException("The directory doesn't exists");
		}
	}

	public String getWatchPath() {
		return watchPath;
	}
	
	public int getMillis() {
		return millis;
	}

	public void setMillis(int millis) {
		this.millis = millis;
	}

	public UploadingVideoService getUploadingVideoService() {
		return uploadingVideoService;
	}

	public void setUploadingVideoService(UploadingVideoService uploadingVideoService) {
		this.uploadingVideoService = uploadingVideoService;
	}

	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}

	public List<String> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<String> extensions) {
		this.extensions = extensions;
	}

	public boolean isFilterWithExt() {
		return filterWithExt;
	}

	public void setFilterWithExt(boolean filterWithExt) {
		this.filterWithExt = filterWithExt;
	}
}

package com.yunling.mediaman.uploadwatch;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.policyman.db.model.UploadingVideo;
import com.yunling.policyman.db.model.Video;
import com.yunling.policyman.db.model.VideoStatus;
import com.yunling.policyman.db.service.UploadingVideoService;
import com.yunling.policyman.db.service.VideoService;

public class MoveRunner implements Runnable {
	private String sourcePath;
	private String destPath;
	private File sourceDir;
	private File destinationDir;
	private VideoService videoService;
	private UploadingVideoService uploadingVideoService;
	private boolean runFlag;
	private int millis = 30 * 1000;

	private static Logger log = LoggerFactory.getLogger(MoveRunner.class);

	@Override
	public void run() {
		runFlag = true;
		while (runFlag) {
			//this.exec("sync");
			int count = videoService.countWaitingMoved();
			log.debug("-------------Waiting be moved file num is: " + count);
			if (count > 0) {
				List<Video> videoList = videoService.listWaitingMoved();
				for (Video v : videoList) {
					File s = new File(sourceDir, v.getOriginName());
					log.debug("++++++++++Origin file path is: "
							+ s.getAbsolutePath());
					System.out.println(s.getAbsolutePath());
					if (s.exists()) {
						log.debug("---------------Move file: " + s.getName());
						// move file to destination folder
						UploadingVideo uvideo = uploadingVideoService
								.findByOriginName(v.getOriginName());
						File newFile = new File(new File(destinationDir,
								getDir(v.getMediaType())), v.getFileName());
						try {
							log.debug("+++++++++++++new file path is: "
									+ newFile.getAbsolutePath());
							log.debug("Begin movvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvve...");
							//FileUtils.moveFile(s, newFile);
							this.exec("mv " + s.getAbsolutePath() + " " + newFile.getAbsolutePath());
							log.debug("End movvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvve...");
						} catch (Exception e) {
							log.debug("Exception happened when movinnnnnnnnnnnnnnnnnnnnnnng.");
							if (s.exists()) {
								log.debug("source file exists.");
							} else if (newFile.exists()) {
								log.debug("Error: dest file exists.");
							} else {
								log.debug("Error: IO error occurs moving the file.");
							}
						}
						v.setStatus(VideoStatus.NORMAL);
						videoService.update(v);
						if (uvideo != null) {
							uploadingVideoService.deleteByKey(uvideo.getId());
						}

					} else {
						log.debug("------------File not exists.");
						v.setStatus(VideoStatus.NOT_IN_SOURCE_DIR);
						log.debug("************set video status to NOT_IN_SOURCE_DIR.");
						videoService.update(v);
						continue;
					}
				}
			}
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
			}
		}
	}

	private String getDir(String mediaType) {
		if (StringUtils.equalsIgnoreCase("video", mediaType)) {
			return "VIDEO";
		} else if ("image".equalsIgnoreCase(mediaType)) {
			return "IMAGE";
		} else if ("text".equalsIgnoreCase(mediaType)) {
			return "TXT";
		}
		return "";
	}

	private void exec(String command) {
		try {
			Runtime run = Runtime.getRuntime();
			run.exec(command);
		} catch (Exception e) {
			log.error("sync command execute error!");
		}
	}

	/**
	 * @return the sourcePath
	 */
	public String getSourcePath() {
		return sourcePath;
	}

	/**
	 * @return the destPath
	 */
	public String getDestPath() {
		return destPath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
		File f = new File(sourcePath);
		if (!f.exists() || !f.isDirectory()) {
			throw new RuntimeException("Cannot find the source directory");
		}
		this.sourceDir = f;
	}

	public void setDestPath(String destPath) {
		this.destPath = destPath;
		File f = new File(destPath);
		if (!f.exists() || !f.isDirectory()) {
			throw new RuntimeException("Cannot find the destination directory");
		}
		this.destinationDir = f;
	}

	public VideoService getVideoService() {
		return videoService;
	}

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
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

	public void setUploadingVideoService(
			UploadingVideoService uploadingVideoService) {
		this.uploadingVideoService = uploadingVideoService;
	}

}

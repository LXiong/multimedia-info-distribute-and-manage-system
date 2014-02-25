package com.yunling.policyman.db.model;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.yunling.policyman.db.model.base.BaseVideo;

public class Video extends BaseVideo implements Comparable<Video> {
	static List<String> audios = Arrays.asList("cmf", "mp3", "cda", "mid",
			"wav", "vqf", "ra", "asf", "asx", "wma", "wax");
	static List<String> videos = Arrays.asList("mpg", "mlv", "mpe", "mpeg",
			"dat", "m2v", "vob", "tp", "ts", "avi", "mov", "asf", "mp4", "wmv",
			"rm", "ra", "ram", "rmvb", "swf", "flv", "qt", "mov", "asf", "3gp");
	static List<String> images = Arrays.asList("bmp", "gif", "jpg", "jpeg",
			"tiff", "psd", "png", "svg", "pcx", "dxf", "wmf", "emf", "lic",
			"eps", "tga", "flc", "pcs", "pic", "avd", "qtm");

	public Video() {
		super();
	}

	public Video(String code, String fileName, String tag, String description) {
		this.code = code;
		this.fileName = fileName;
		this.tag = tag;
		this.description = description;

	}

	public String getType() {
		int size = getFileName().split(".").length;
		if (size < 2) {
			return "audio";
		}
		String extenteName = getFileName().split(".")[size - 1].toLowerCase();
		if (audios.contains(extenteName)) {
			return "audio";
		}
		if (videos.contains(extenteName)) {
			return "video";
		}
		if (images.contains(extenteName)) {
			return "picture";
		}
		return "unknow";

	}

	public String getCodeName() {
		return code+ "."+FilenameUtils.getExtension(getOriginName());
	}

	public String[] toJsonArray() {
		return new String[] { getCode(), getFileName(), getTag(),
				getDescription(), getType() };
	}

	public void setStatus(VideoStatus status) {
		this.setStatus(String.valueOf(status));
	}

	@Override
	public int compareTo(Video v) {
		return (this.hashCode() - v.hashCode());
	}

}
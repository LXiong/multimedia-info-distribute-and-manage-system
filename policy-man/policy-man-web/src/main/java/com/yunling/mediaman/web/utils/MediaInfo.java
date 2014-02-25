package com.yunling.mediaman.web.utils;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MediaInfo {
	private boolean exist = true;
	private int nbStream;
	private String format;
	private int width;
	private int height;
	private double duration;
	
//	public boolean isImage() {
//		if (nbStream==1 && "image2".equals(format)) {
//			return true;
//		}
//		return false;
//	}
//	
//	public boolean isVideo() {
//		return nbStream>1;
//	}
	
	@Override
	public String toString() {
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.append("format", format)
		.append("nbstream", nbStream)
		.append("width", width)
		.append("height", height)
		.append("duration", duration);
		
		return tsb.toString();
	}
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}

	public int getNbStream() {
		return nbStream;
	}

	public void setNbStream(int nbStream) {
		this.nbStream = nbStream;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

}

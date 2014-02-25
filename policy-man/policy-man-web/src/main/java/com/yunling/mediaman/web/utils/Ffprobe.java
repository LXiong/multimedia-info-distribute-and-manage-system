package com.yunling.mediaman.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

public class Ffprobe {
	
	private String ffpath = "";
	private String ffcmd = "ffprobe"; 
	
	public MediaInfo getInfo(String filepath) {
		MediaInfo info = new MediaInfo();
		InputStream in = null;
		BufferedReader br = null;
		File targetFile = new File(filepath);
		if (!targetFile.exists()) {
			info.setExist(false);
			return info;
		}
		ProcessBuilder pb = new ProcessBuilder(ffpath+ffcmd, "-show_format", "-show_streams", filepath);
		try {
			Process process = pb.start();
			in = process.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while ((line = br.readLine())!=null) {
				line = line.trim();
				if (line.startsWith("format_name=")) {
					info.setFormat(line.substring(line.indexOf('=')+1));
				} else if (line.startsWith("nb_streams=")) {
					try {
						info.setNbStream(Integer.parseInt(line.substring(line.indexOf('=')+1)));
					} catch (NumberFormatException e) {
//						e.printStackTrace();
					}
				} else if (line.startsWith("width=")) {
					try {
						info.setWidth(Integer.parseInt(line.substring(line.indexOf('=')+1)));
					} catch (NumberFormatException e) {
//						e.printStackTrace();
					}
				} else if (line.startsWith("height=")) {
					try {
						info.setHeight(Integer.parseInt(line.substring(line.indexOf('=')+1)));
					} catch (NumberFormatException e) {
//						e.printStackTrace();
					}
				} else if (line.startsWith("duration=")) {
					try {
						info.setDuration((double) Float.parseFloat(line.substring(line.indexOf('=')+1)));
					} catch (NumberFormatException e) {
//						e.printStackTrace();
					}
				} else if (line.startsWith("[/FORMAT]")) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(in);
		}
		return info;
	}
	
	public float getDuration(String filepath) {
		InputStream in = null;
		BufferedReader br = null;
		
		ProcessBuilder pb = new ProcessBuilder(ffpath+ffcmd, "-show_format", filepath);
		try {
			Process process = pb.start();
			in = process.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine())!=null) {
				if (line.startsWith("duration=")) {
					try {
						float result = Float.parseFloat(line.trim().substring(line.indexOf('=')+1));
						return result;
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				if (line.startsWith("[/FORMAT]") || "".equals(line)) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(in);
		}
		return -1;
	}
	public static void main(String[] args) {
		Ffprobe probe = new Ffprobe();
		probe.ffpath = "D:\\tool\\";
		System.out.println(probe.getDuration("E:\\tmp\\ec92deee808c8394066ef5713082b142.wmv"));
		System.out.println(probe.getInfo("E:\\tmp\\ec92deee808c8394066ef5713082b142.wmv"));
	}
	public String getFfpath() {
		return ffpath;
	}
	public void setFfpath(String ffpath) {
		this.ffpath = ffpath;
	}
	public String getFfcmd() {
		return ffcmd;
	}
	public void setFfcmd(String ffcmd) {
		this.ffcmd = ffcmd;
	}

}

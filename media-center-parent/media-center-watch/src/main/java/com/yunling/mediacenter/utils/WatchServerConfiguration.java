package com.yunling.mediacenter.utils;

public class WatchServerConfiguration {
	String localInnetAddress;
	String localOutnetAddress;
	int port;

	public String getLocalInnetAddress() {
		return localInnetAddress;
	}

	public void setLocalInnetAddress(String localInnetAddress) {
		this.localInnetAddress = localInnetAddress;
	}

	public String getLocalOutnetAddress() {
		return localOutnetAddress;
	}

	public void setLocalOutnetAddress(String localOutnetAddress) {
		this.localOutnetAddress = localOutnetAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}

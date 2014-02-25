package com.yunling.logparse;

public class LogParseMXBean
	implements ILogParseMXBean
{
	private LogParse logParse;

	public void stop() {
		logParse.interrupt();
	}

	public LogParse getLogParse() {
		return logParse;
	}

	public void setLogParse(LogParse logParse) {
		this.logParse = logParse;
	}
}

package com.yunling.logparse.parers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

abstract public class AbstractParser {
	public boolean parse(InputStream in, String macAddress) throws Exception {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			return parse(br, macAddress);
		} finally {
			IOUtils.closeQuietly(br);
		}
	}
	protected boolean isEndLine(String str) {
		return StringUtils.equals("----end----", str);
	}
	abstract protected boolean parse(BufferedReader br, String macAddress) throws Exception;
}

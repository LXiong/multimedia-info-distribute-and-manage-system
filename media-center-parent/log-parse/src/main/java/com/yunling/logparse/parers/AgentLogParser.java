package com.yunling.logparse.parers;

import java.io.BufferedReader;

public class AgentLogParser extends AbstractParser {

	@Override
	protected boolean parse(BufferedReader br, String macAddress)
			throws Exception {
		return true;
	}

}

package com.yunling.commons.console;

import java.util.Map;

public interface ConsoleMain {
	public void init(String[] args, Map<String, Object> context) throws Exception;
	public void run(Map<String, Object> context) throws Exception;
	public void release() throws Exception;
}

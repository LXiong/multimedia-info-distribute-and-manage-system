package com.yunling.commons.console;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ConsoleExecutor {
	
	public static Map<String, Object> execute(String[] paths, String[] args,Map<String, Object> context) {
		return execute("consoleEntry", paths, args, context);
	}
	
	public static Map<String, Object> execute(String beanName, String[] paths, String[] args) {
		return execute(beanName, paths, args, null);
	}

	public static Map<String, Object> execute(String beanName, String[] paths, String[] args,Map<String, Object> context) {
		if (context == null) {
			context = new HashMap<String, Object>();
		}
		FileSystemXmlApplicationContext fsCtx = new FileSystemXmlApplicationContext(paths);
		Object bean = fsCtx.getBean(beanName);
		if (!(bean instanceof ConsoleMain)) {
			throw new RuntimeException("Failed to find console program entry class");
		}
		
		ConsoleMain entryBean = (ConsoleMain) bean;
		try {
			entryBean.init(args, context);
			entryBean.run(context);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				entryBean.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return context;
	}
}

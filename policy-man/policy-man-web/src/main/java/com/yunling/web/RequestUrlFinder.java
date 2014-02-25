package com.yunling.web;

import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class RequestUrlFinder {
	private String ctlPackage = "com.yunling.mediaman.web.ctl";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RequestUrlFinder self = new RequestUrlFinder();
		self.run();
	}

	private void run() {
		ResolverUtil<Object> util = new ResolverUtil<Object>();
		Set<Class<?>> ctlSet = util.findAnnotated(Controller.class, ctlPackage).getClasses();
		for(Class<?> ctlClass  : ctlSet) {
			System.out.println("---class:"+ctlClass.getSimpleName());
			RequestMapping ctlMapping = ctlClass.getAnnotation(RequestMapping.class);
			if (ctlMapping == null) continue;
			for(Method m : ctlClass.getMethods()) {
				RequestMapping methodMapping = m.getAnnotation(RequestMapping.class);
				if (methodMapping != null) {
					System.out.println(StringUtils.join(ctlMapping.value()) + 
							StringUtils.join(methodMapping.value()) );
				}
			}
		}
	}

}

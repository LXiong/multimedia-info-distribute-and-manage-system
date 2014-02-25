package com.yunling.mediaman.web.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.yunling.web.SecurityUser;

public class UserUtil {

	public static SecurityUser getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof SecurityUser)) {
			return null;
		}
		return (SecurityUser) principal;
	}
	
	public static String getUsername() {
		return getUser()!=null? getUser().getUsername():null;
	}
	
	public static String getUserDispName() {
		return getUser()!=null? getUser().getUsername():null;
	}
	
}

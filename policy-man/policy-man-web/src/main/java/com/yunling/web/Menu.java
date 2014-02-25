package com.yunling.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;

public class Menu {
	static AntPathMatcher matcher = new AntPathMatcher();
	private String name;
	private String key;
	private String match;
	private String action;
	private Set<String> authoritySet = new HashSet<String>();
	private List<Menu> children = new ArrayList<Menu>();
	
	private Menu parent;
	private String[] patterns;
	
	@Override
	public String toString() {
		return action;
	}
	
	public boolean match(String path) {
		String[] patterns = getMatchPatterns();
		if (patterns == null) {
			return false;
		}
		for(String pattern :  patterns) {
			if (matcher.match(pattern, path)) {
				return true;
			}
		}
		return false;
	}
	
	public void addMenu(Menu child) {
		child.setParent(this);
		this.children.add(child);
	}
	public String getMsgKey() {
		if (key!=null) return getKey();
		return getName();
	}
	public String[] getMatchPatterns() {
		if (patterns == null) {
			String match = getMatch();
			if (match == null) {
				return null;
			}
			patterns = StringUtils.split(match,',');
		}
		return patterns;
	}
	
	public List<Menu> getChildren()
	{
		return children;
	}
	
	public void setAuthority(String str) {
		if (StringUtils.isEmpty(str)) {
			return;
		}
		String[] authArr = StringUtils.split(str, ",");
		for(String auth : authArr) {
			this.authoritySet.add(auth);
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public String getMatch() {
		if (match==null) {
			return this.getAction()+"*";
		}
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}

}

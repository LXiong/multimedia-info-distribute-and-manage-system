package com.yunling.mediacenter.web.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;

import com.yunling.mediacenter.web.Functions;

public class Menu {

	private String name;
	private String key;
	private String action;
	private String method;
	private String nameSpace;
	private String path;
	private String function;
	private String exclude;
	private Set<Functions> functionSet = new HashSet<Functions>();
	private Set<Functions> excludeSet = new HashSet<Functions>();
	private String match;
	
	private List<Menu> children = new ArrayList<Menu>();
	
	private Menu parent;
	private String actionPath;
	
	public void addMenu(Menu child) {
		child.setParent(this);
		this.children.add(child);
	}
	public String getMsgKey() {
		if (key!=null) return getKey();
		return getName();
	}
	public Set<Functions> getFunctionSet() {
		return functionSet;
	}
	public void setFunction(String function) {
		this.function = function;
		if (function!=null) {
			String[] functions = function.split(",");
			for(String f : functions) {
				this.functionSet.add(Functions.valueOf(f));
			}
		}
	}
	public void setExclude(String function) {
		this.exclude = StringUtils.trimToNull(function);
		if (!StringUtils.isEmpty(exclude)) {
			String[] funcs = exclude.split(",");
			for(String f: funcs) {
				this.excludeSet.add(Functions.valueOf(f));
			}
		}
	}
	public String getMatch() {
		if (match==null) {
			if (StringUtils.isEmpty(method)) {
				match =  "/"+this.getAction()+"*";
			} else {
				match = "/"+this.getAction()+"!"+getMethod()+"*";
			}
		}
		return match;
	}
	
	public List<Menu> getChildren()
	{
		return Collections.unmodifiableList(children);
	}
	public String getMethod() {
		if (StringUtils.isEmpty(method)) {
			return "execute";
		}
		return method;
	}
	
	public String getNameSpace() {
		if (StringUtils.isEmpty(nameSpace)) {
			if (this.parent!=null) {
				return parent.getNameSpace();
			}
			return "";
		}
		return nameSpace;
	}
	
	public String getActionPath()	{
		if (actionPath==null) {
			actionPath = getNameSpace()+"/"+getAction()+"!"+getMethod();
		}
		return actionPath;
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
	
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getPath() {
		return path;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFunction() {
		return function;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public String getUrlMethod() {
		return method;
	}
	public Set<Functions> getExcludeSet() {
		return excludeSet;
	}
	public String getExclude() {
		return exclude;
	}
}

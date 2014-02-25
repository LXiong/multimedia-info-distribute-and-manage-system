package com.yunling.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class MenuBar extends Menu {

	private static PathMatcher matcher = new AntPathMatcher();
	private Map<String,Menu> menuMap;
	
	public List<Menu> getWalkPath(String url) {
		List<Menu> walked = new ArrayList<Menu>();
		List<String> matchedList = new ArrayList<String>();
		for(String pattern : menuMap.keySet()) {
			if (matcher.match(pattern, url)) {
				matchedList.add(pattern);
			}
		}
		Collections.sort(matchedList, matcher.getPatternComparator(url) );
		if (matchedList.size()>0) {
			Menu menu = menuMap.get(matchedList.get(0));
			while (menu != null && menu.getParent()!=null) {
				walked.add(0, menu);
				menu = menu.getParent();
			}
		}
		return walked;
	}
	
	public void collectAll() {
		menuMap = new HashMap<String, Menu>();
		collect(menuMap, this);
	}
	
	protected void collect(Map<String,Menu> menuMap, Menu menu) {
		for(Menu sub: menu.getChildren()) {
			if (menuMap.containsKey(sub.getMatch())) {
				continue;
			}
			if (!(sub.getParent() instanceof MenuBar)) {
				menuMap.put(sub.getMatch(), sub);
			}
			collect(menuMap,sub);
		}
	}
}

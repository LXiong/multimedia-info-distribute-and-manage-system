package com.yunling.mediacenter.web.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class MenuBar extends Menu {

	private static PathMatcher matcher = new AntPathMatcher();
	
	private Map<String, Menu> menuMap = new HashMap<String, Menu>();
	private Map<String, Menu> menuMap1 = new HashMap<String, Menu>();
	private Map<String, List<Menu>> relatedMap = new HashMap<String, List<Menu>>();
	
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
	
	@Override
	public String getPath() {
		return "";
	}
	
	@Override
	public String getNameSpace() {
		if (super.getNameSpace()!=null) {
			return super.getNameSpace();
		}
		return "/";
	}
	
	public void buildMap() {
		collectMap2(this);
		collectAll();
	}
	
	private void collectMap2(Menu menu) {
		for(Menu child : menu.getChildren()) {
			String actionpath = child.getActionPath();
			menuMap1.put(actionpath, child);
			List<Menu> relatedMenus = new ArrayList<Menu>();
			relatedMenus.add(child);
			Menu p = child;
			while (( p = p.getParent())!=null) {
				if (p == this) continue;
				relatedMenus.add(0, p);
			}
			relatedMap.put(actionpath, relatedMenus);
			collectMap2(child);
		}
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
			if (!(sub.getParent() instanceof MenuBar) || sub.getChildren() == null || sub.getChildren().size()==0) {
				menuMap.put(sub.getMatch(), sub);
			}
			collect(menuMap,sub);
		}
	}
	
	public Map<String, Menu> getMenuMap() {
		return menuMap1;
	}

	public Map<String, List<Menu>> getRelatedMap() {
		return relatedMap;
	}

}

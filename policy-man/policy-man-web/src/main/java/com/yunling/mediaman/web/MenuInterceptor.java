package com.yunling.mediaman.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.yunling.web.Menu;
import com.yunling.web.MenuBar;

public class MenuInterceptor
	extends HandlerInterceptorAdapter
	implements HandlerInterceptor
{
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	private MenuBar menuBar;
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// check menu
		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
		List<Menu> walkPath = menuBar.getWalkPath(lookupPath);
		if (walkPath != null && modelAndView!=null) {
			modelAndView.addObject("walkPath", walkPath);
			if (walkPath.size()>1) {
				modelAndView.addObject("lastWalked", walkPath.get(walkPath.size()-1));
			}
		}
	}
	
	public MenuBar getMenuBar() {
		return menuBar;
	}
	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}
}

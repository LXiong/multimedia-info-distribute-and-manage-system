package com.yunling.mediacenter.web.struts2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.util.UrlPathHelper;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.menu.Menu;
import com.yunling.mediacenter.web.menu.MenuBar;

public class MenuInterceptor implements Interceptor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7674761004811138737L;

	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	private MenuBar mainMenu;

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute(Constants.MAIN_MENU, mainMenu);
		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
		if ("/".equals(lookupPath)) {
			lookupPath="/home!execute";
		} else if (lookupPath.endsWith(".action")) {
			lookupPath = lookupPath.substring(0, lookupPath.length()-".action".length() );
		}
		List<Menu> walkPath = mainMenu.getWalkPath(lookupPath);
		request.setAttribute("walkPath", walkPath);
		if (walkPath!=null && walkPath.size()>0) {
			request.setAttribute("lastWalked", walkPath.get(walkPath.size()-1));
		}
		return invocation.invoke();
	}

	public MenuBar getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MenuBar mainMenu) {
		this.mainMenu = mainMenu;
	}

}

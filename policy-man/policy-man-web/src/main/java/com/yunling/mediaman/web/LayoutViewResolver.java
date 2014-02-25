package com.yunling.mediaman.web;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.yunling.web.MenuBar;

public class LayoutViewResolver extends InternalResourceViewResolver
	implements InitializingBean
{

	private String defaultLayout;
	private Map<String, String> layoutMapping;
	private MenuBar menuBar;
	
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView view = super.buildView(viewName);
		String layout = defaultLayout;
		for(Map.Entry<String, String> entry : layoutMapping.entrySet()) {
			if (viewName.startsWith(entry.getKey())) {
				if ("none".equals(entry.getValue())) {
					return view;
				}
				layout = entry.getValue();
				break;
			}
		}
		view.setUrl(getPrefix() + layout + getSuffix());
		view.addStaticAttribute("mainPage",getPrefix() + viewName + getSuffix());
		view.addStaticAttribute("mainMenu", menuBar);
		return view;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	public String getDefaultLayout() {
		return defaultLayout;
	}

	public void setDefaultLayout(String defaultLayout) {
		this.defaultLayout = defaultLayout;
	}

	public Map<String, String> getLayoutMapping() {
		return layoutMapping;
	}

	public void setLayoutMapping(Map<String, String> layoutMapping) {
		this.layoutMapping = layoutMapping;
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}

}

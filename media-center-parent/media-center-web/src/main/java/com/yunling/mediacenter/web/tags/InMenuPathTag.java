package com.yunling.mediacenter.web.tags;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.menu.Menu;
import com.yunling.mediacenter.web.menu.MenuBar;

public class InMenuPathTag extends ConditionalTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8726856257463565631L;
	
	private Menu menu;
	
	public InMenuPathTag() {
		super();
		init();
	}
	
	@Override
	protected boolean condition() throws JspTagException {
		if (menu == null) {
			return false;
		}
		
		String currentAction = (String) pageContext.getRequest().getAttribute(Constants.CURRENT_ACTION);
		MenuBar main = (MenuBar) pageContext.getRequest().getAttribute(Constants.MAIN_MENU);
		Map<String, List<Menu>> relatedMenuMap = main.getRelatedMap();
		List<Menu> menus = relatedMenuMap.get(currentAction);
		if (menus == null) {
			return false;
		}
		return menus.contains(menu);
	}
	
	private void init() {
		this.menu = null;
	}
	
	
	@Override
	public void release() {
		super.release();
		init();
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}

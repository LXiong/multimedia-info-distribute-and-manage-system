package com.yunling.web.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import com.yunling.web.Menu;

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
		
		return false;
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

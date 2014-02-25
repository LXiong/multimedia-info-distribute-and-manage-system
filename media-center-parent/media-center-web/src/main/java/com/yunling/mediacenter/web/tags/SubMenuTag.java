package com.yunling.mediacenter.web.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.Functions;
import com.yunling.mediacenter.web.menu.Menu;

public class SubMenuTag extends ConditionalTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2579605032476797710L;
	
	private Menu menu;
	private String var;
	
	public SubMenuTag() {
		super();
		init();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean condition() throws JspTagException {
		if (menu == null) return false;
		if (var == null) return false;
		List<Menu> children = menu.getChildren();
		List<Menu> avaiables = new ArrayList<Menu>();
		
		Set<Functions> functions = (Set<Functions>) pageContext.getRequest().getAttribute(Constants.SESSION_USER_FUNCTIONS);
		if (functions == null) return false;
		
		for(Menu child : children) {
			boolean enabled = false;
			if (child.getExcludeSet().size()>0) {
				boolean excluded = false;
				for(Functions efunc : functions) {
					if (child.getExcludeSet().contains(efunc)) {
						excluded = true;
						break;
					}
				}
				if (excluded) {
					continue;
				}
			}
			if (child.getFunctionSet()==null || child.getFunctionSet().size()==0) {
				enabled = true;
			} else {
				for(Functions efunc : functions) {
					if (child.getFunctionSet().contains(efunc)) {
						enabled = true;
						break;
					}
				}
			}
			if (enabled) avaiables.add(child);
		}
		
		if (avaiables.size()>0) {
			exposeVariables(avaiables);
			return true;
		}
		return false;
	}
	
	private void init() {
		this.var = null;
		this.menu = null;
	}
	
	private void exposeVariables(List<Menu> avaiables) throws JspTagException {
		pageContext.setAttribute(var, avaiables, PageContext.PAGE_SCOPE);
	}
	
	private void unExposeVariables() {
		if (var != null)
            pageContext.removeAttribute(var, PageContext.PAGE_SCOPE);
	}
	
	@Override
	public void release() {
		super.release();
		unExposeVariables();
		init();
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

}

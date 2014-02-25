package com.yunling.web.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

public class AuthTag extends ConditionalTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4450045609543383642L;
	
	private String functions;
	
	@Override
	protected boolean condition() throws JspTagException {
		return false;
	}
	public String getFunctions() {
		return functions;
	}

}

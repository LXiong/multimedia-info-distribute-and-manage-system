package com.yunling.mediacenter.web.tags;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.Functions;

public class AuthTag extends ConditionalTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4450045609543383642L;
	
	private String functions;
	private Set<Functions> functionSet;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean condition() throws JspTagException {
		Set<Functions> userFunctions = (Set<Functions>) pageContext.getRequest().getAttribute(Constants.SESSION_USER_FUNCTIONS);
		if (userFunctions == null) return false;
		
		for(Functions func : userFunctions) {
			if (functionSet.contains(func)) return true;
		}
		return false;
	}
	public String getFunctions() {
		return functions;
	}
	public void setFunctions(String functions) {
		this.functions = functions;
		if (functions == null) {
			throw new IllegalArgumentException("Functions must be set.");
		}
		functionSet = new HashSet<Functions>();
		String[] functionStr = functions.trim().split(",;");
		for(String fstr : functionStr) {
			Functions func = Functions.valueOf(fstr);
			functionSet.add(func);
		}
	}

}

package com.yunling.web.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yunling.web.Menu;

public class SubMenuTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8863772208872700606L;
	private String menuVar;
    private Menu menu;
    
    @Override
    public int doEndTag() throws JspException {
    	pageContext.removeAttribute(menuVar);
    	return super.doEndTag();
    }

    // If access expression evaluates to "true" return
    public int doStartTag() throws JspException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null || menu.getChildren() == null || menu.getChildren().size()==0) {
        	return SKIP_BODY;
        }
        List<Menu> submenuList = new ArrayList<Menu>();
        WebInvocationPrivilegeEvaluator evaluator = getPrivilegeEvaluator();
        String contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
        for(Menu child : menu.getChildren()) {
			if (walkMenu(evaluator, contextPath,
                    child, currentUser)) {
        		submenuList.add(child);
        	}
        }
        if (submenuList.size()==0) {
        	return SKIP_BODY;
        }
        pageContext.setAttribute(menuVar, submenuList, PageContext.PAGE_SCOPE);
        return EVAL_BODY_INCLUDE;
    }

    private boolean walkMenu(WebInvocationPrivilegeEvaluator evaluator,
			String contextPath, Menu menu,
			Authentication currentUser) {
    	if (menu.getAction()!=null) {
    		return evaluator.isAllowed(contextPath,
                    menu.getAction(), null, currentUser);
    	}
    	if (menu.getChildren()!=null && menu.getChildren().size()>0) {
    		for(Menu child : menu.getChildren()) {
    			if (walkMenu(evaluator, contextPath, child, currentUser)) {
    				return true;
    			}
    		}
    	}
		return false;
	}

	private WebInvocationPrivilegeEvaluator getPrivilegeEvaluator() throws JspException {
        ServletContext servletContext = pageContext.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        Map<String, WebInvocationPrivilegeEvaluator> wipes = ctx.getBeansOfType(WebInvocationPrivilegeEvaluator.class);

        if (wipes.size() == 0) {
            throw new JspException("No visible WebInvocationPrivilegeEvaluator instance could be found in the application " +
                    "context. There must be at least one in order to support the use of URL access checks in 'authorize' tags.");
        }

        return (WebInvocationPrivilegeEvaluator) wipes.values().toArray()[0];
    }

	public String getMenuVar() {
		return menuVar;
	}

	public void setMenuVar(String menuVar) {
		this.menuVar = menuVar;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
package com.yunling.mediacenter.web.struts2;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.apache.struts2.views.util.UrlHelper;

import com.opensymphony.xwork2.ActionInvocation;
import com.yunling.mediacenter.web.RuntimeConfigException;

public class JspLayoutResult extends StrutsResultSupport  {
	
	public static final String LAYOUT = "Layout";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7789508515674194307L;
	
	private Map<String, String> layoutMap;
	private String defaultLayout;
	private String pagePath = "/WEB-INF/content/";
	
	public JspLayoutResult() {
		super();
	}
	
	public JspLayoutResult(String location) {
		super(location);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {
		PageContext pageContext = ServletActionContext.getPageContext();

        if (pageContext != null) {
            pageContext.include(finalLocation);
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            String layoutName = getLayoutName(request);
            String layoutFile = getLayoutFile(request);
            RequestDispatcher dispatcher = null;
            if (isNoLayout(layoutName)) {
            	dispatcher = request.getRequestDispatcher(finalLocation);
            } else {
            	dispatcher = request.getRequestDispatcher(layoutFile);
            }

            //add parameters passed on the location to #parameters
            // see WW-2120
            if (invocation != null && finalLocation != null && finalLocation.length() > 0
                    && finalLocation.indexOf("?") > 0) {
                String queryString = finalLocation.substring(finalLocation.indexOf("?") + 1);
                Map parameters = (Map) invocation.getInvocationContext().getContextMap().get("parameters");
                Map queryParams = UrlHelper.parseQueryString(queryString, true);
                if (queryParams != null && !queryParams.isEmpty())
                    parameters.putAll(queryParams);
            }

            // if the view doesn't exist, let's do a 404
            if (dispatcher == null) {
                response.sendError(404, "result '" + finalLocation + "' not found");
                return;
            }

            // If we're included, then include the view
            // Otherwise do forward
            // This allow the page to, for example, set content type
            
            if (!response.isCommitted() && (request.getAttribute("javax.servlet.include.servlet_path") == null)) {
            	if (isNoLayout(layoutName)) {
            		request.setAttribute("struts.view_uri", finalLocation);
            	} else {
	            	request.setAttribute("mainPage", finalLocation);
	                request.setAttribute("struts.view_uri", layoutFile);
	                request.setAttribute("struts.request_uri", request.getRequestURI());
            	}
            	
                dispatcher.forward(request, response);
            } else {
                dispatcher.include(request, response);
            }
        }
	}

	private boolean isNoLayout(String layoutName) {
		return JspLayout.JSON.equalsIgnoreCase(layoutName) 
			|| JspLayout.AJAX.equalsIgnoreCase(layoutName)
			|| JspLayout.NONE.equalsIgnoreCase(layoutName);
	}
	
	public void setLayoutMap(String m) {
		this.layoutMap = new HashMap<String, String>();
		if (m == null) {
			return;
		}
		String[] pairs = m.split(";");
		if (pairs == null || pairs.length ==0) {
			return;
		}
		for(String pairString : pairs) {
			String[] pair = pairString.split(":");
			if (pair.length!=2) {
				throw new RuntimeConfigException("layout.layoutmap.error", "The Result layout map is wrong");
			}
			layoutMap.put(pair[0], pagePath == null ? pair[1] : ensureSlash(pagePath)+pair[1]);
		}
	}
	
	private String ensureSlash(String src) {
		if (src.endsWith("/")) return src;
		return src+"/";
	}
	
	private String getLayoutFile(HttpServletRequest request)
	{
		String layoutName = getLayoutName(request);
		if (isNoLayout(layoutName)) {
			return "";
		}
		if (layoutName == null || layoutName.length()==0) {
			layoutName = JspLayout.DEFAULT;
		}
		String layoutFile = layoutMap.get(layoutName);
		if (layoutFile == null && !JspLayout.DEFAULT.equalsIgnoreCase(layoutName)) {
			throw new RuntimeConfigException("layout.missing", "layout is missing");
		}
		if (layoutFile == null && JspLayout.DEFAULT.equalsIgnoreCase(layoutName)) {
			layoutFile = getDefaultLayout();
		}
		if (layoutFile !=null) {
			return layoutFile;
		}
		
		return "/WEB-INF/content/layout.jsp";
	}

	private String getLayoutName(HttpServletRequest request) {
		String layoutName = (String) request.getAttribute(LAYOUT);
		return layoutName;
	}

	public String getDefaultLayout() {
		return defaultLayout;
	}

	public void setDefaultLayout(String defaultLayout) {
		this.defaultLayout = defaultLayout;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

}

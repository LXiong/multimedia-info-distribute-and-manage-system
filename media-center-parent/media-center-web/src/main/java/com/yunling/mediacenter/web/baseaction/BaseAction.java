package com.yunling.mediacenter.web.baseaction;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.yunling.mediacenter.web.WebConfig;

public abstract class BaseAction 
implements Action, RequestAware, ParameterAware, LocaleProvider, TextProvider, ValidationAware
{

	public static final String HOME = "home";
	public static final String ADMIN = "admin";
	public static final String DAILY = "daily";
	public static final String JSON = "json";
	public static final String AJAX = "ajax";
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	private final ValidationAwareSupport validationAware = new ValidationAwareSupport(); 
	
	private final transient TextProvider textProvider = new TextProviderFactory().createInstance(getClass(), this);
	
	protected Map<String,Object> request;
	protected Map<String, String[]> parameters;
	protected List<String> cssList = new ArrayList<String>();
	protected List<String> jsList= new ArrayList<String>();

	protected Map<String,String> jsMap;

	protected Map<String,String> cssMap;
	
	protected WebConfig webConfig;
	
	protected String getSingleParam(String key) {
		String[] paramArr = parameters.get(key);
		if (paramArr!=null && paramArr.length>0) {
			return paramArr[0];
		}
		return null;
	}
	
	public void setLayout(String layout) {
		request.put("Layout", layout);
	}
	public void addCssRef(String css) {
		cssList.add(cssMap.get(css));
	}
	public void addJavascriptRef(String js) {
		jsList.add(jsMap.get(js));
	}
	
    public Locale getLocale() {
        ActionContext ctx = ActionContext.getContext();
        if (ctx != null) {
            return ctx.getLocale();
        } else {
            log.debug("Action context not initialized");
            return null;
        }
    }
    
    protected void setDownloadHead(String filename, int length) {
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		if (StringUtils.contains(agent, "Firefox")) {
			setStackValue("contentDisposition",
					"attachment;filename=\""+filename+"\"");
		} else if (StringUtils.contains(agent, "IE")) {
			try {
				setStackValue("contentDisposition",
						"attachment;filename="+URLEncoder.encode(filename,"utf-8"));
			} catch (UnsupportedEncodingException e) {
			}
		} else {
			setStackValue("contentDisposition",
					"attachment;filename="+filename);
		}
		
		setStackValue("contentLength",String.valueOf(length));
	}
    
	protected void setStackValue(String key, Object value) {
		ActionContext.getContext().getValueStack().set(key, value);
	}
    
    public boolean hasKey(String key) {
        return textProvider.hasKey(key);
    }

    public String getText(String aTextName) {
        return textProvider.getText(aTextName);
    }

    public String getText(String aTextName, String defaultValue) {
        return textProvider.getText(aTextName, defaultValue);
    }

    public String getText(String aTextName, String defaultValue, String obj) {
        return textProvider.getText(aTextName, defaultValue, obj);
    }

    public String getText(String aTextName, List<Object> args) {
        return textProvider.getText(aTextName, args);
    }

    public String getText(String key, String[] args) {
        return textProvider.getText(key, args);
    }

    public String getText(String aTextName, String defaultValue, List<Object> args) {
        return textProvider.getText(aTextName, defaultValue, args);
    }

    public String getText(String key, String defaultValue, String[] args) {
        return textProvider.getText(key, defaultValue, args);
    }

    public String getText(String key, String defaultValue, List<Object> args, ValueStack stack) {
        return textProvider.getText(key, defaultValue, args, stack);
    }

    public String getText(String key, String defaultValue, String[] args, ValueStack stack) {
        return textProvider.getText(key, defaultValue, args, stack);
    }

    public ResourceBundle getTexts() {
        return textProvider.getTexts();
    }

    public ResourceBundle getTexts(String aBundleName) {
        return textProvider.getTexts(aBundleName);
    }
    
	public String getTitleKey() {
		return "default.title";
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public List<String> getCssList() {
		return cssList;
	}

	public List<String> getJsList() {
		return jsList;
	}

	public Map<String, String> getJsMap() {
		return jsMap;
	}

	public void setJsMap(Map<String, String> jsMap) {
		this.jsMap = jsMap;
	}

	public Map<String, String> getCssMap() {
		return cssMap;
	}

	public void setCssMap(Map<String, String> cssMap) {
		this.cssMap = cssMap;
	}
	public Map<String, String[]> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}
	
	public void setActionErrors(Collection<String> errorMessages) {
        validationAware.setActionErrors(errorMessages);
    }

    public Collection<String> getActionErrors() {
        return validationAware.getActionErrors();
    }

    public void setActionMessages(Collection<String> messages) {
        validationAware.setActionMessages(messages);
    }

    public Collection<String> getActionMessages() {
        return validationAware.getActionMessages();
    }

    public void setFieldErrors(Map<String, List<String>> errorMap) {
        validationAware.setFieldErrors(errorMap);
    }

    public Map<String, List<String>> getFieldErrors() {
        return validationAware.getFieldErrors();
    }
    
    public void addActionError(String anErrorMessage) {
        validationAware.addActionError(anErrorMessage);
    }

    public void addActionMessage(String aMessage) {
        validationAware.addActionMessage(aMessage);
    }

    public void addFieldError(String fieldName, String errorMessage) {
        validationAware.addFieldError(fieldName, errorMessage);
    }
    
    public boolean hasActionErrors() {
        return validationAware.hasActionErrors();
    }

    public boolean hasActionMessages() {
        return validationAware.hasActionMessages();
    }

    public boolean hasErrors() {
        return validationAware.hasErrors();
    }

    public boolean hasFieldErrors() {
        return validationAware.hasFieldErrors();
    }
    
    /**
     * Clears field errors. Useful for Continuations and other situations
     * where you might want to clear parts of the state on the same action.
     */
    public void clearFieldErrors() {
        validationAware.clearFieldErrors();
    }

    /**
     * Clears action errors. Useful for Continuations and other situations
     * where you might want to clear parts of the state on the same action.
     */
    public void clearActionErrors() {
        validationAware.clearActionErrors();
    }

    /**
     * Clears messages. Useful for Continuations and other situations
     * where you might want to clear parts of the state on the same action.
     */
    public void clearMessages() {
        validationAware.clearMessages();
    }

    /**
     * Clears all errors. Useful for Continuations and other situations
     * where you might want to clear parts of the state on the same action.
     */
    public void clearErrors() {
        validationAware.clearErrors();
    }

    /**
     * Clears all errors and messages. Useful for Continuations and other situations
     * where you might want to clear parts of the state on the same action.
     */
    public void clearErrorsAndMessages() {
        validationAware.clearErrorsAndMessages();
    }
    
    /**
     * A default implementation that validates nothing.
     * Subclasses should override this method to provide validations.
     */
    public void validate() {
    }
	public WebConfig getWebConfig() {
		return webConfig;
	}
	public void setWebConfig(WebConfig webConfig) {
		this.webConfig = webConfig;
	}
}

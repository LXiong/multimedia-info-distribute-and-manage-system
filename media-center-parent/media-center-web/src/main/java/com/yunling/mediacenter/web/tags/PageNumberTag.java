package com.yunling.mediacenter.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

public class PageNumberTag extends TagSupport
	implements TryCatchFinally
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2670031828812626836L;
	
	private int totalPage;
	private int dispCount = 10;
	private boolean middle = false;
	private int currentPage;
	private String varPage;
	private String varIsCurrent;
	
	private int index;
	private int begin;
	private int end;
	
	public PageNumberTag() {
		super();
		init();
	}
	
	private void init() {
		varPage = null;
		varIsCurrent = null;
		totalPage = 0;
		currentPage = 0;
		index = 0;
		begin = 0;
		end = 0;
	}
	
	public void release() {
        super.release();
        init();
    }

	@Override
	public int doStartTag() throws JspException {
		if (totalPage == 0) {
			return SKIP_BODY;
		}
		if (middle) {
			int half = dispCount / 2 +1 ;
			begin = Math.max(Math.min(currentPage - half, totalPage - dispCount), 0);
		} else {
			begin = (currentPage-1) / dispCount * dispCount;
		}
		end = Math.min(begin + dispCount, totalPage);
		index = begin+1;
		exposeVariables();
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doAfterBody() throws JspException {
		index ++;
		if (index > end) {
			return SKIP_BODY;
		}
		exposeVariables();
		return EVAL_BODY_AGAIN;
	}
	
	private void exposeVariables() {
		if (varPage != null) {
            pageContext.setAttribute(varPage, index);
        }
        if (varIsCurrent != null) {
            pageContext.setAttribute(varIsCurrent, index == currentPage);
        }
	}

	@Override
	public void doCatch(Throwable t) throws Throwable {
		throw t;
	}

	@Override
	public void doFinally() {
		unExposeVariables();
	}

	private void unExposeVariables() {
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getDispCount() {
		return dispCount;
	}

	public void setDispCount(int dispCount) {
		this.dispCount = dispCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getVarPage() {
		return varPage;
	}

	public void setVarPage(String varPage) {
		this.varPage = varPage;
	}

	public String getVarIsCurrent() {
		return varIsCurrent;
	}

	public void setVarIsCurrent(String varIsCurrent) {
		this.varIsCurrent = varIsCurrent;
	}

	public boolean isMiddle() {
		return middle;
	}

	public void setMiddle(boolean middle) {
		this.middle = middle;
	}

}

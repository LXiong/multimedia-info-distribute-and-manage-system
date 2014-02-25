package com.yunling.mediacenter.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.commons.lang.StringUtils;

public class PageLinkTag extends TagSupport
	implements TryCatchFinally
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2670031828812626836L;
	
	private int totalPage;
	private int dispCount = 5;
	private boolean middle = false;
	private int currentPage;
	private String link;
	private String onclick;
	
	private int begin;
	private int end;
	
	public PageLinkTag() {
		super();
		init();
	}
	
	private void init() {
		totalPage = 0;
		currentPage = 0;
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
		
		JspWriter jw = pageContext.getOut();
		try {
			jw.print(currentPage);
			jw.print("/");
			jw.print(totalPage);
			jw.print("&nbsp;");
		
			if (currentPage == 1) {
//				jw.println("<span class=\"disabled\"> |&lt; </span>" +
//						"<span class=\"disabled\"> &lt; </span>");
			} else if (currentPage >1) {
				makeLink(jw, "|&lt;", 1);
				makeLink(jw, "&lt;", currentPage-1);
			}
			
			for(int i=begin+1;i<=end;++i) {
				// outputNumbers
				if (i == currentPage) {
					jw.print("<span>");
					jw.print(i);
					jw.println("</span>");
				} else {
					makeLink(jw, String.valueOf(i),i);
				}				
			}
			
			if (currentPage == totalPage) {
//				jw.println("<span class=\"disabled\"> &gt; </span>" +
//						"<span class=\"disabled\"> &gt;| </span>");
			} else if (currentPage <totalPage) {
				makeLink(jw, "&gt;", currentPage+1);
				makeLink(jw, "&gt;|", totalPage);
			}
		} catch (IOException ex) {
			throw new JspException(ex.toString(), ex);
		}
		return SKIP_BODY;
	}

	private void makeLink(JspWriter jw, String text, int pageNum) throws IOException {
		jw.print("<a href=\"");
		jw.print(replacePage(getLink(), pageNum));
		jw.print("\"");
		if (StringUtils.isNotEmpty(onclick)) {
			jw.print(" onclick=\"");
			jw.print(replacePage(onclick, pageNum));
			jw.print("\"");
		}
		jw.print(">");
		jw.print(text);
		jw.println("</a>");
	}
	private String replacePage(String ori, int pageNum) {
		return ori.replace("{p}", String.valueOf(pageNum));
	}
	
	@Override
	public int doAfterBody() throws JspException {
//		index ++;
//		if (index > end) {
//			return SKIP_BODY;
//		}
		return SKIP_BODY;
	}
	
	@Override
	public void doCatch(Throwable t) throws Throwable {
		throw t;
	}

	@Override
	public void doFinally() {
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

	public boolean isMiddle() {
		return middle;
	}

	public void setMiddle(boolean middle) {
		this.middle = middle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

}

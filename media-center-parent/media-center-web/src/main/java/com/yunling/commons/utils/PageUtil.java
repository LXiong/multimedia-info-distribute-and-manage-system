package com.yunling.commons.utils;

import org.apache.commons.lang.StringUtils;

public class PageUtil {
	private int curPage;
	private int totalPage;
	private int total;
	private int begin;
	private int end;
	private int pageSize;
	
	public PageUtil(int page, int total, int pageSize) {
		this.pageSize = pageSize;
		this.total = total;
		this.curPage = page;
		calculate();
	}
	
	public PageUtil(String param, int total, int pageSize) {
		this.pageSize = pageSize;
		this.total = total;
		curPage = 1;
		if (!StringUtils.isEmpty(param)) {
			try {
				curPage = Integer.parseInt(param);
			} catch (NumberFormatException e) {
			}
		}
		calculate();
	}
	private void calculate() {
		curPage = Math.max(curPage, 1);
		totalPage = total /pageSize;
		if (total%pageSize > 0) {
			++totalPage;
		}
		curPage = Math.min(totalPage, curPage);
		
		begin = (curPage-1)*pageSize+1;
		end = Math.min(curPage * pageSize, total);
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
package com.yunling.mediaman.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.yunling.web.PageBean;

public class JqgridPage {
	private int page;
	private int total;
	private int records;
	private List<JqgridRow> rows;
	private JqgridUserData userdata;
	
	public JqgridPage() {
		rows = new ArrayList<JqgridRow>();
	}
	
	public void addRow(String[] arr, String rowId) {
		JqgridRow row = new JqgridRow();
		row.setId(rowId);
		row.setCell(arr);
		getRows().add(row);
	}
	
	public void setPageBean(PageBean pb) {
		setPage(pb.getCurPage());
		setTotal(pb.getTotalPage());
		setRecords(pb.getTotal());
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public List<JqgridRow> getRows() {
		return rows;
	}
	public void setRows(List<JqgridRow> rows) {
		this.rows = rows;
	}
	public JqgridUserData getUserdata() {
		return userdata;
	}
	public void setUserdata(JqgridUserData userdata) {
		this.userdata = userdata;
	}

}

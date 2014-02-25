package com.yunling.mediaman.web.vo;

public class LayoutVo {
		private Long id;
		private String layoutname;
		private String lcomment;
		private long width;
		private long height;
		private long isfull;
		private String areaid;
		public String getLcomment() {
			return lcomment;
		}
		public void setLcomment(String lcomment) {
			this.lcomment = lcomment;
		}
		public String getLayoutname() {
			return layoutname;
		}
		public void setLayoutname(String layoutname) {
			this.layoutname = layoutname;
		}
		public long getWidth() {
			return width;
		}
		public void setWidth(long width) {
			this.width = width;
		}
		public long getHeight() {
			return height;
		}
		public void setHeight(long height) {
			this.height = height;
		}
		public long getIsfull() {
			return isfull;
		}
		public void setIsfull(long isfull) {
			this.isfull = isfull;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getAreaid() {
			return areaid;
		}
		public void setAreaid(String areaid) {
			this.areaid = areaid;
		}
}

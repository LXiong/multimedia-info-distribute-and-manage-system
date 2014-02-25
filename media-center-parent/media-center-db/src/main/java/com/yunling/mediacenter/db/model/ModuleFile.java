package com.yunling.mediacenter.db.model;

import java.util.Date;

public class ModuleFile {
		private long id;
		private String filePath;
		private String version;
		private String file_comment;
		private Date releaseTime;
		private String verflyCode;
		private String module;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getFile_comment() {
			return file_comment;
		}
		public void setFile_comment(String file_comment) {
			this.file_comment = file_comment;
		}
		public String getVerflyCode() {
			return verflyCode;
		}
		public void setVerflyCode(String verflyCode) {
			this.verflyCode = verflyCode;
		}
		public Date getReleaseTime() {
			return releaseTime;
		}
		public void setReleaseTime(Date releaseTime) {
			this.releaseTime = releaseTime;
		}
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
		
}

package com.yunling.mediacenter.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

public class DatabaseVersionValidator implements InitializingBean {

	private String versionTable;
	private String dbVersion;
	private boolean enabled = true;

	@Autowired
	private DataSource dataSource;

	private boolean versionMatchFlag;
	private String fetchVersion;

	private JdbcTemplate jdbcTemplate;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (dataSource != null) {
			versionMatchFlag = false;
			jdbcTemplate = new JdbcTemplate(dataSource);
			verifyVersion();
		} else {
			throw new Exception("Failed to find dataSource");
		}
	}

	private void verifyVersion() {
		jdbcTemplate.query("SELECT * FROM " + versionTable,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						if (rs == null) {
							versionMatchFlag = false;
							return;
						}
						if (rs.getObject(1) != null) {
							String version = rs.getString(1);
							fetchVersion = version;
							if (StringUtils.equals(version, dbVersion)) {
								versionMatchFlag = true;
							}
						}
					}
				});
		if (!versionMatchFlag) {
			throw new RuntimeException("db version doesn't match, current is "
					+ dbVersion + " but database has " + fetchVersion);
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getVersionTable() {
		return versionTable;
	}

	public void setVersionTable(String versionTable) {
		this.versionTable = versionTable;
	}

	public String getDbVersion() {
		return dbVersion;
	}

	public void setDbVersion(String dbVersion) {
		this.dbVersion = dbVersion;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

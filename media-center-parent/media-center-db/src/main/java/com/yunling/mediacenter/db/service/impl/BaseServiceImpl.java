package com.yunling.mediacenter.db.service.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseServiceImpl {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private SqlSessionTemplate sqlSessionTemplate;
	
	protected <T> T getMapper(Class<T> cls) {
		return sqlSessionTemplate.getMapper(cls);
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}

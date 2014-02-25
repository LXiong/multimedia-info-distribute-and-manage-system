package com.yunling.policyman.db;

import org.mybatis.spring.SqlSessionTemplate;



public abstract class BaseServiceImpl {
	protected SqlSessionTemplate sqlSessionTemplate;
	
	protected <T> T getMapper(Class<T> mapperClass) {
		return sqlSessionTemplate.getMapper(mapperClass);
	}
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
}

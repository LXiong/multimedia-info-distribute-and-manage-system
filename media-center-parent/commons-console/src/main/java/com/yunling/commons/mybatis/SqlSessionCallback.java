package com.yunling.commons.mybatis;

import org.apache.ibatis.session.SqlSession;

/**
 * This interface is for opertions with SqlSession
 * @author Xianyu Haiping
 *
 * @param <T> Result class.
 */
public interface SqlSessionCallback<T> {

	public T execute(SqlSession session);
	
}

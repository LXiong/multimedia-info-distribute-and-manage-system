package com.yunling.commons.mybatis;

/**
 * This interface is for calling one mapper.
 * @author Xianyu Haiping
 *
 * @param <T> 
 * @param <T2>
 */
public interface MapperCallback<T,T2> {

	public T2 execute(T mapper);
}

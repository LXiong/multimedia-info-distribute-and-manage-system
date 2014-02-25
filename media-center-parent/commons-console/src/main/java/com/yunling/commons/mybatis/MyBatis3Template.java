package com.yunling.commons.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class MyBatis3Template {
	private Logger log = LoggerFactory.getLogger(MyBatis3Template.class);

	private SqlSessionFactory factory;
	
	/**
	 * This method is used to execute query with SqlSession directly.
	 * @param <T>
	 * @param callback
	 * @return
	 */
	public <T> T query(SqlSessionCallback<T> callback) {
		SqlSession session = factory.openSession();
		try {
			return (T) callback.execute(session);
		}catch (Exception e) {
			log.error("Exception in query",e);
			session.rollback();
			throw new RuntimeException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * This method is used to execute updating query with SqlSession directly.
	 * @param <T>
	 * @param callback
	 * @return
	 */
	public void update(SqlSessionCallback<?> callback) {
		SqlSession session = factory.openSession();
		try {
			callback.execute(session);
			session.commit();
		}catch (Exception e) {
			log.error("Exception in update",e);
			session.rollback();
			throw new RuntimeException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * This method is used to execute action of updating database (insert update delete) <br />
	 * Example: 
	 * <pre>
	 * tmpl.update(UserMapper.class, new MapperCallback<UserMapper, User>() { 
	 * 	  public User execute(UserMapper mapper) { 
	 *       mapper.delete(userid); 
	 * 	     return null; 
	 *    } 
	 * });
	 * </pre>
	 * @param <T> Mapper class
	 * @param <T2> Query result class
	 * @param mapperClass
	 * @param callback
	 */
	public <T,T2> void update(Class<T> mapperClass, MapperCallback<T,T2> callback) {
		SqlSession session = factory.openSession();
		try {
			T mapper = session.getMapper(mapperClass);
			callback.execute(mapper);
			session.commit();
		}catch (Exception e) {
			log.error("Exception in update",e);
			session.rollback();
			throw new RuntimeException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * This method is used to execute query <br />
	 * Example: 
	 * <pre>
	 * List<User> users = tmpl.query(UserMapper.class, new MapperCallback<UserMapper, List<User>>() { 
	 * 	  public List<User> execute(UserMapper mapper) { 
	 *       return mapper.list(); 
	 *    } 
	 * });
	 * </pre>
	 * @param <T> Mapper class
	 * @param <T2> Query result class
	 * @param mapperClass
	 * @param callback
	 */
	public <T,T2> T2 query(Class<T> mapperClass, MapperCallback<T, T2> callback) {
		SqlSession session = factory.openSession();
		try {
			T mapper = session.getMapper(mapperClass);
			return callback.execute(mapper);
		}catch (Exception e) {
			log.error("Exception in query",e);
			throw new RuntimeException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public SqlSessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SqlSessionFactory factory) {
		this.factory = factory;
	}
}

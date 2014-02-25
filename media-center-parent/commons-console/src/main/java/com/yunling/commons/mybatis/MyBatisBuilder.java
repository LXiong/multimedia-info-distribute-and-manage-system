package com.yunling.commons.mybatis;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.InitializingBean;

public class MyBatisBuilder
	implements InitializingBean
{
	private String envName="mybatis3";
	private Environment environment;
	private SqlSessionFactory factory;
	private Map<String, String> typeHandlers;
	private String modelPackage;
	private String mapperPackage;
	
	private TransactionFactory transactionFactory;
	
	public void afterPropertiesSet() throws Exception {
		if (this.transactionFactory == null) {
            this.transactionFactory = new SpringManagedTransactionFactory(this.environment.getDataSource());
        }
		Environment environment = new Environment(this.envName, transactionFactory, this.environment.getDataSource());
		Configuration conf = new Configuration(environment);
		registerModelAlias(conf.getTypeAliasRegistry());
		conf.addMappers(mapperPackage);
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		factory = builder.build(conf);
	}
	
	private void registerModelAlias(TypeAliasRegistry typeAliasRegistry) {
		ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
	    resolverUtil.find(new ResolverUtil.IsA(Object.class), 
	    		modelPackage
	    		);
	    Set<Class<? extends Class<?>>> modelSet = resolverUtil.getClasses();
	    for(Class<?> modelClass : modelSet){
	      typeAliasRegistry.registerAlias(modelClass);
	    }
	}

	public SqlSessionFactory getFactory() {
		return factory;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Map<String, String> getTypeHandlers() {
		return typeHandlers;
	}

	public void setTypeHandlers(Map<String, String> typeHandlers) {
		this.typeHandlers = typeHandlers;
	}

	public String getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	public String getMapperPackage() {
		return mapperPackage;
	}

	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

}

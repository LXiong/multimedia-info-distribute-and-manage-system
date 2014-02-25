<?xml version="1.0" encoding="UTF-8"?>
	<!--Application context definition for PetClinic on Hibernate.-->
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:jee="http://www.springframework.org/schema/jee" xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="
			http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
			http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
			http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-3.0.xsd
			http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd">

<#list schemaList as schemaDef><#t />
	<bean id="${schemaDef.modelName?uncap_first}Mapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
		<property name="addToConfig" value="true" />
		<property name="mapperInterface" value="${mapperPackage}.${schemaDef.modelName}Mapper" />
		<property name="sqlSessionTemplate" ref="${sqlSessionTemplateBean}" />
	</bean>
</#list>
</beans>
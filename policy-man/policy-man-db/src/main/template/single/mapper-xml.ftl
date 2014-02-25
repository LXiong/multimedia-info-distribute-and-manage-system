<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${schema.modelName}Mapper">

<#import "mapper-xml-macro-inc.ftl" as lib/>
	<!-- auto generate start -->
	
<#if schema.isAllowed("countAll")>
	<select id="countAll" resultType="java.lang.Integer">
		SELECT COUNT(*) as total FROM ${schema.tableName}
	</select>
</#if>

<#if schema.isAllowed("countBy")>
	<select id="countBy" resultType="java.lang.Integer" parameterType="java.util.Map">
		SELECT COUNT(*) as total FROM ${schema.tableName}
		<@lib.defaultWhere false />
	</select>
</#if>

<#if schema.isAllowed("listAll")>
	<select id="listAll" resultType="${schema.modelName}" >
		SELECT
		  <@lib.selectColList />
		FROM ${schema.tableName}
		<#if schema.defaultOrderByList?size gt 0>
		<@lib.listOrderBy schema.defaultOrderByList />
		</#if>
	</select>
</#if>

<#if schema.isAllowed("listAllPaged")>
	<select id="listAllPaged" resultType="${schema.modelName}" >
	SELECT * FROM (
	SELECT t.*, rownum as rn FROM (
		SELECT
		  <@lib.selectColList />
		FROM ${schema.tableName}
		<#if schema.defaultOrderByList?size gt 0>
		<@lib.listOrderBy schema.defaultOrderByList />
		</#if>
	) t WHERE rownum &lt;= ${r"#{"}end${r"}"}
	) WHERE rn &gt;=${r"#{"}begin${r"}"}
	</select>
</#if>

<#if schema.isAllowed("listBy")>
	<select id="listBy" resultType="${schema.modelName}" >
	<if test="begin != null and end !=null">
	SELECT * FROM (
	SELECT t.*, rownum as rn FROM (
	</if>
		SELECT
		  <@lib.selectColList />
		FROM ${schema.tableName}
		<@lib.defaultWhere true/>
	<if test="begin != null and end !=null">
	) t WHERE rownum &lt;= ${r"#{"}end${r"}"}
	) WHERE rn &gt;=${r"#{"}begin${r"}"}
	</if>
	</select>
</#if>

<#if schema.isAllowed("insert")>
	<insert id="insert" parameterType="${schema.modelName}">
		<@lib.insert />
	</insert>
</#if>

<#if schema.isAllowed("update")>
	<update id="update" parameterType="${schema.modelName}">
		UPDATE ${schema.tableName} SET <#assign columnBegin=true /><#rt />
	      <#lt /><#list schema.normalColumnDefList as columnDef >
	        <#lt /><#if !columnDef.noUpdate>
	        <#lt /><#if columnBegin><#assign columnBegin=false /><#else>,</#if>
		  	${columnDef.name} = <#rt />
		  	<#lt /><#if columnDef.updateValue?has_content>${columnDef.updateValue}<#rt />
		  	<#lt /><#else>${r"#{"}${columnDef.javaName},jdbcType=${config.toJdbcType(columnDef)}${r"}"}<#rt />
		  	<#lt /></#if>
		  	</#if>
		  </#list>
		WHERE <@lib.listKey />
	</update>
</#if>

<#if schema.isAllowed("insertOrUpdate")>
<#if  schema.key.complex || !(schema.key.generator?has_content)>
	<update id="insertOrUpdate" parameterType="${schema.modelName}">
		<@lib.insertOrUpdate />
	</update>
</#if>
</#if>

<#if schema.isAllowed("deleteByKey")>
	<delete id="deleteByKey" <#if !schema.key.complex>parameterType="${config.getJavaType(schema.key)}"</#if>>
		DELETE FROM ${schema.tableName}
		WHERE <@lib.listKey />
	</delete>
</#if>

<#if schema.isAllowed("deleteByModelKey")>
	<delete id="deleteByModelKey" parameterType="${schema.modelName}">
		DELETE FROM ${schema.tableName}
		WHERE <@lib.listKey />
	</delete>
</#if>
<#if schema.isAllowed("getByKey")>
	<select id="getByKey" <#if !schema.key.complex>parameterType="${config.getJavaType(schema.key)}"</#if> resultType="${schema.modelName}">
		SELECT
		  <@lib.selectColList />
		FROM ${schema.tableName}
		WHERE <@lib.listKey />
	</select>
</#if>
<#if schema.isAllowed("listBy")>
</#if>

	<sql id="selectColumns">
		<@lib.selectColList />
	</sql>

<#list schema.aliasList as alias>
	<sql id="alias_col_${alias.name}">
		<#list schema.columnDefList as columnDef >
		  ${alias.name}.${columnDef.name} as ${columnDef.javaName} <#if columnDef_has_next> ,</#if>
		</#list>
	</sql>
</#list>

<#macro renderRelated node>
	<#if node.relatedList?exists && node.relatedList?size gt 0 >
	<#list node.relatedList as related>

		<#assign subSchema= schemaMap[related.schemaName] />
		<#assign tagName = related.tag />
		<#if related.tag == 'collection'>
		<collection property="${related.property}" ofType="${subSchema.modelName}">
		</#if>
		<#if related.tag == 'association'>
		<association property="${related.property}" column="${related.column}" javaType="${subSchema.modelName}">
		</#if>
		<#if subSchema.key.complex>
		<#list subSchema.key.columnDefList as columnDef >
		<id property="${columnDef.javaName}" column="${related.alias}_${columnDef.name}"/>
	    </#list>
		<#else>
		<id property="${subSchema.key.javaName}" column="${related.alias}_${subSchema.key.name}"/>
		</#if>
		<#list subSchema.normalColumnDefList as columnDef >
		<result property="${columnDef.javaName}" column="${related.alias}_${columnDef.name}"/>
	    </#list>
	    <@renderRelated related/>
    	</${tagName}>
    </#list>
    </#if>
</#macro>
	
<#macro renderColumns related>
		<#assign subSchema= schemaMap[related.schemaName] />
		<#list subSchema.columnDefList as columnDef >
		, ${related.alias}.${columnDef.name} as ${related.alias}_${columnDef.name}
	    </#list>
	    <#list related.relatedList as subCollection>
	    	<@renderColumns subCollection />
	    </#list>
</#macro>
	
<#list schema.resultMapList as resultMap>
	<resultMap type="${schema.modelName}" id="${resultMap.name}">
		<#if schema.key.complex>
		<#list schema.key.columnDefList as columnDef >
		<id property="${columnDef.javaName}" column="${columnDef.name}"/>
	    </#list>
		<#else>
		<id property="${schema.key.javaName}" column="${schema.key.name}"/>
		</#if>
		<#list schema.normalColumnDefList as columnDef >
		<result property="${columnDef.javaName}" column="${columnDef.name}"/>
	    </#list>
    	<@renderRelated resultMap />
	</resultMap>
	<sql id="${resultMap.name}_columns">
		<#list schema.columnDefList as columnDef >
		<#if columnDef_index gt 0>,</#if> ${resultMap.alias}.${columnDef.name} as ${columnDef.name}
		</#list>
	    <#list resultMap.relatedList as relatedColl>
    	<@renderColumns relatedColl />
	    </#list>
	</sql>
</#list>
	<!-- auto generate end -->

	
	<!-- manual xml blocks start -->
${includeContent!}
	<!-- manual xml blocks end -->
	
</mapper>

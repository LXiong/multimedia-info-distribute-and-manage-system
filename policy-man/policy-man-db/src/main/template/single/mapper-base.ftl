package ${mapperPackage}.base;

import java.util.List;
import java.util.Map;

import ${modelPackage}.${schema.modelName};
import org.apache.ibatis.annotations.Param;

<#macro listKeyParam>
<#if schema.key.complex>
<#list schema.key.columnDefList as columnDef>
@Param("${columnDef.javaName}") ${config.toShortJavaType(columnDef)} ${columnDef.javaName}<#if columnDef_has_next>,</#if><#rt />
</#list>
<#else>
${config.toShortJavaType(schema.key)} key<#t />
</#if>
</#macro><#t />

/**
 * Please don't modify this class manually.
 * This class is generated by code-mother plugin.
 **/

public interface Base${schema.modelName}Mapper
{
<#if schema.isAllowed("countAll")>
	Integer countAll();
</#if>
<#if schema.isAllowed("countBy")>
	Integer countBy(Map<String,Object> params);
</#if>
<#if schema.isAllowed("listAll")>
	List<${schema.modelName}> listAll();
</#if>
<#if schema.isAllowed("listAllPaged")>
	List<${schema.modelName}> listAllPaged(@Param("begin")long begin, @Param("end")long end);
</#if>
<#if schema.isAllowed("listBy")>
	List<${schema.modelName}> listBy(Map<String,Object> params);
</#if>
<#if schema.isAllowed("insert")>
	Object insert(${schema.modelName} model);
</#if>
<#if schema.isAllowed("getByKey")>
	${schema.modelName} getByKey(<@listKeyParam />);
</#if>
<#if schema.isAllowed("update")>
	Object update(${schema.modelName} model);
</#if>
<#if schema.isAllowed("deleteByKey")>
	void deleteByKey(<@listKeyParam />);
</#if>
<#if schema.isAllowed("deleteByModelKey")>
	void deleteByModelKey(${schema.modelName} model);
</#if>

}
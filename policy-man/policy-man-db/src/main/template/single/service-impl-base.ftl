package ${servicePackage}.impl.base;

import java.util.List;
import java.util.Map;

import ${modelPackage}.${schema.modelName};
import ${servicePackage}.base.Base${schema.modelName}Service;
import ${mapperPackage}.${schema.modelName}Mapper;

<#macro listKeyParam>
<#if schema.key.complex>
<#list schema.key.columnDefList as columnDef>
${config.toShortJavaType(columnDef)} ${columnDef.javaName}<#if columnDef_has_next>,</#if><#rt />
</#list>
<#else>
${config.toShortJavaType(schema.key)} key<#t />
</#if>
</#macro><#t />
<#macro listKey>
<#if schema.key.complex>
<#list schema.key.columnDefList as columnDef>
${columnDef.javaName}<#if columnDef_has_next>, </#if><#rt />
</#list>
<#else>
key<#t />
</#if>
</#macro><#t />

public class Base${schema.modelName}ServiceImpl
	extends ${baseServiceClass}
	implements Base${schema.modelName}Service
{
	protected ${schema.modelName}Mapper getMapper() {
		return getMapper(${schema.modelName}Mapper.class); 
	}
<#if schema.isAllowed("countAll")>
	public Integer countAll() {
		return getMapper(${schema.modelName}Mapper.class).countAll();
	}
</#if>
<#if schema.isAllowed("countBy")>
	public Integer countBy(Map<String,Object> params) {
		return getMapper(${schema.modelName}Mapper.class).countBy(params);
	}
</#if>
<#if schema.isAllowed("listAll")>
	public List<${schema.modelName}> listAll() {
		return getMapper(${schema.modelName}Mapper.class).listAll();
	}
</#if>
<#if schema.isAllowed("listAllPaged")>
	public List<${schema.modelName}> listAllPaged(long begin, long end) {
		return getMapper(${schema.modelName}Mapper.class).listAllPaged(begin,end);
	}
</#if>
<#if schema.isAllowed("listBy")>
	public List<${schema.modelName}> listBy(Map<String,Object> params){
		return getMapper(${schema.modelName}Mapper.class).listBy(params);
	}
</#if>
<#if schema.isAllowed("insert")>
	public Object insert(${schema.modelName} model) {
		return getMapper(${schema.modelName}Mapper.class).insert(model);
	}
</#if>
<#if schema.isAllowed("update")>	
	public void update(${schema.modelName} model) {
		getMapper(${schema.modelName}Mapper.class).update(model);
	}
</#if>
<#if schema.isAllowed("deleteByKey")>
	public void deleteByKey(<@listKeyParam />) {
		getMapper(${schema.modelName}Mapper.class).deleteByKey(<@listKey />);
	}
</#if>
<#if schema.isAllowed("deleteByModel")>	
	public void deleteByModel(${schema.modelName} model) {
		getMapper(${schema.modelName}Mapper.class).deleteByModelKey(model);
	}
</#if>
<#if schema.isAllowed("getByKey")>	
	public ${schema.modelName} getByKey(<@listKeyParam />) {
		return getMapper(${schema.modelName}Mapper.class).getByKey(<@listKey />);
	}
</#if>
}
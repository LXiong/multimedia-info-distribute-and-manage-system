package ${modelPackage}.helper;

import ${modelPackage}.${schema.modelName};
import ${modelPackage}.Helper;

public class ${schema.modelName}Helper
{
	public static String[] ALL_COLUMNS = {<#list schema.columnDefList as column><#rt />
<#lt />"${column.name}"<#if column_has_next>,</#if><#t />
</#list>}; 

	public static String[] toSimpleArray(${schema.modelName} model, String[] columns) {
		String[] result = new String[columns.length];
		int i=0;
		for(String column : columns) {
		<#list schema.columnDefList as columnDef>
			if ("${columnDef.name}".equals(column)) {
				result[i] = Helper.format(model.get${columnDef.javaName?cap_first}()); 
			} <#if columnDef_has_next> else </#if>
		</#list>
			++i;
		}
		return result;
	}
	
	public static String[] toSimpleArray(${schema.modelName} model) {
		return toSimpleArray(model, ALL_COLUMNS);
	}
}
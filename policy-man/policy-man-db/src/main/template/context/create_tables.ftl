<#list schemaList as schema>
CREATE TABLE ${schema.tableName}
(
	<#if schema.key.complex>
	<#list schema.key.columnDefList as column>
	${column.name} ${config.getTypeDef(column.type).dbType}<#if column.length&gt;0 > (${column.length})</#if>,
	</#list>
	<#else>
	${schema.key.name} ${config.getTypeDef(schema.key.type).dbType} <#if schema.key.length&gt;0 >(${schema.key.length})</#if>,
	</#if>
	<#list schema.normalColumnDefList as column>
	${column.name} ${config.getTypeDef(column.type).dbType}<#if column.length&gt;0 > (${column.length?c})</#if><#if column.defaultValue?has_content> DEFAULT ${column.defaultValue}</#if>,
	</#list>
	primary key (<#if schema.key.complex ><#rt>
		<#list schema.key.columnDefList as column><#if column_index &gt; 0>,</#if>
			${column.name}<#rt>
		</#list><#rt>
	<#else> ${schema.key.name} <#rt>
	</#if><#lt>)
)
/

</#list>
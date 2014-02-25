<#list schemaList as schema>
<#if schema.key.single && schema.key.generator?has_content && schema.key.generator = "native" >
DROP SEQUENCE seq_${schema.tableName}
/
</#if>
</#list>
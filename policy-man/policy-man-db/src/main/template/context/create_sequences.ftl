<#list schemaList as schema>
<#if schema.key.single && schema.key.generator?has_content && schema.key.generator = "native" >
CREATE SEQUENCE seq_${schema.tableName}
/

</#if>
</#list>
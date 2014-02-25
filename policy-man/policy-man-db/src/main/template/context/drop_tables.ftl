<#list schemaList as schema>
DROP TABLE ${schema.tableName} cascade constraints
/
</#list>
package ${modelPackage}.base;

<#list schema.getImports(config) as imported>
import ${imported.javaType};
</#list> 

public class Base${schema.modelName} {

<#list schema.columnDefList as column>
	protected ${config.toShortJavaType(column)} ${column.javaName};
</#list>

<#list schema.columnDefList as column>
	public ${config.toShortJavaType(column)} get${column.javaName?cap_first}() {
		return this.${column.javaName};
	}
	public void set${column.javaName?cap_first} (${config.toShortJavaType(column)} value) {
		this.${column.javaName} = value;
	}
	
</#list>

}
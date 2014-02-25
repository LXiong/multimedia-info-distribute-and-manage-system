<#t /><#macro showParam name>${r"#{"}${name}${r"}"}</#macro>
<#t /><#macro defaultWhere withOrderBy=false>
		<where>
		  <#if schema.defaultConditionList?size gt 0 ><#list schema.defaultConditionList as searchCond>
		    <#if searchCond.type == "match-value">
		    AND ${searchCond.column} = '${searchCond.value}'
		    <#elseif searchCond.type == "like">
		    <if test="${searchCond.param} != null and ${searchCond.param} != ''">
		    AND ${searchCond.column} like <@showParam searchCond.param />
		    </if>
		    <#elseif searchCond.type == "match">
		    <if test="${searchCond.param} != null and ${searchCond.param} != ''">
		    AND ${searchCond.column} = <@showParam searchCond.param />
		    </if>
		    <#elseif searchCond.type=="between">
		    <if test="${searchCond.low} !=null and ${searchCond.low} != '' and ${searchCond.high} !=null and ${searchCond.high} != '' ">
		    AND ${searchCond.column} between <@showParam searchCond.low /> and <@showParam searchCond.high />
		    </if>
		    <if test="(${searchCond.low} ==null or ${searchCond.low} == '') and ${searchCond.high} !=null and ${searchCond.high} != '' ">
		    AND ${searchCond.column} &lt;= <@showParam searchCond.high />
		    </if>
		    <if test="${searchCond.low} !=null and ${searchCond.low} != '' and (${searchCond.high} ==null or ${searchCond.high} == '') ">
		    AND ${searchCond.column} &gt;= <@showParam searchCond.low />
		    </if>
		    </#if>
		  </#list></#if><#t />
	    </where>
	    <#if withOrderBy && (schema.defaultOrderByList?size gt 0)>
	    	<@listOrderBy schema.defaultOrderByList/>
	    </#if>	  
</#macro><#t />

<#macro selectColList>
		<#list schema.columnDefList as columnDef >
		  ${columnDef.name} as ${columnDef.javaName} <#if columnDef_has_next> ,</#if>
		</#list>
</#macro><#t />

<#t /><#macro listOrderBy orderByList>
	<#if orderByList?size gt 0>
		<#list orderByList as orderBy>
		<#if orderBy_index==0>ORDER BY </#if><#rt />
		  	<#lt />${orderBy.column}<#if !orderBy.asc> DESC</#if><#if orderBy_has_next>,</#if><#rt />
		</#list>
	</#if>
</#macro>

<#macro insert>
		<#if !schema.key.complex && schema.key.generator?has_content && schema.key.generator="native">
		<selectKey keyProperty="${schema.key.javaName}" resultType="${config.getJavaType(schema.key)}" order="BEFORE">
		   SELECT seq_${schema.tableName}.nextval FROM DUAL
		</selectKey>
		</#if>
		INSERT INTO ${schema.tableName} (
		  <#list schema.columnDefList as columnDef >
		  	${columnDef.name} <#if columnDef_has_next> ,</#if>
		  </#list>
		) VALUES(
		  <#list schema.columnDefList as columnDef >
		  	<#rt /><#if columnDef.insertValue?has_content>${columnDef.insertValue}<#rt />
		  	<#else>${r"#{"}${columnDef.javaName},jdbcType=${config.toJdbcType(columnDef)}${r"}"}<#rt />
		  	<#lt /></#if><#if columnDef_has_next> ,</#if>
		  </#list>
		)
</#macro>

<#macro insertOrUpdate>
	MERGE INTO ${schema.tableName}
		USING (SELECT count(*) as co FROM ${schema.tableName} 
			WHERE <@listKey />) t
		ON (t.co &lt;&gt; 0)
		WHEN NOT MATCHED THEN
			INSERT (
			  <#list schema.columnDefList as columnDef >
			  	${columnDef.name} <#if columnDef_has_next> ,</#if>
			  </#list>
			) VALUES(
			  <#list schema.columnDefList as columnDef >
			  	<#rt /><#if columnDef.insertValue?has_content>${columnDef.insertValue}<#rt />
			  	<#else>${r"#{"}${columnDef.javaName},jdbcType=${config.toJdbcType(columnDef)}${r"}"}<#rt />
			  	<#lt /></#if><#if columnDef_has_next> ,</#if>
			  </#list>
			)
		WHEN MATCHED THEN
			UPDATE SET
		      <#list schema.normalColumnDefList as columnDef >
		        <#if !columnDef.noUpdate>
			  	${columnDef.name} = <#rt />
			  	<#lt /><#if columnDef.updateValue?has_content>${columnDef.updateValue}<#rt />
			  	<#lt /><#else>${r"#{"}${columnDef.javaName},jdbcType=${config.toJdbcType(columnDef)}${r"}"}<#rt />
			  	<#lt /></#if><#if columnDef_has_next>,</#if>
			  	</#if>
			  </#list>
			WHERE <@listKey />
</#macro>

<#macro listKey>
	<#if schema.key.complex>
		<#list schema.key.columnDefList as column>
			${column.name} = ${r"#{"}${column.javaName}${r"}"}<#if column_has_next> AND </#if><#t />
		</#list>
	<#else>
		${schema.key.name} = ${r"#{"}${schema.key.javaName}${r"}"}<#t />
	</#if>
</#macro><#t />
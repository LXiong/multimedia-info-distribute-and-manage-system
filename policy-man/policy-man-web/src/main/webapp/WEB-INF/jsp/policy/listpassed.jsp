<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>

<button id="btnView" class="jqbutton"><fmt:message key="button.view" /></button>
<table id="policyList"></table>
<div id="policyPage"></div>
<fmt:message var="colNames" key="policy.table.passed.colNames" />

<sp:url var="policyViewUrl" value="/policy/viewpassed" />
<script type="text/javascript">

$(document).ready(function() {
	jQuery("#policyList").jqGrid({ 
		url:'<%=request.getContextPath()%>/policy/gridpassed',
		datatype: "json",
		colNames:[${colNames}],
		colModel:[
				{name:'id',index:'id', width:90 },				
				{name:'name',index:'name', width:100},
				{name:'startTime',index:'startTime', width:200},
				{name:'endTime',index:'endTime', width:200},
				{name:'auditAt',index:'auditAt', width:200}
				],
		rowNum:10,
		rowList:[10,20,30],
		pager: '#policyPage',
		height: 350,
		autowidth: true, 
		sortname: 'id',
		viewrecords: true,
		sortorder: "asc",
		hidegrid: false,
		caption:'<fmt:message key="policy.list" />' });
	jQuery("#policyList").jqGrid('navGrid','#policyPage',{edit:false,add:false,del:false}
	);
	
	$(".jqbutton").button();

	$('#btnView').click(function() {
		var gr = jQuery("#policyList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			location.href="${policyViewUrl}?id="+gr;
		}
		else alert('<fmt:message key="alert.select.row" />');
	});
	
});
</script>
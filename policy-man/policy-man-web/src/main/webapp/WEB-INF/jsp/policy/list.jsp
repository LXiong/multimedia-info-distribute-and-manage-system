<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>

<!--<button id='btnAdd' class="jqbutton"><fmt:message key="policy.new" /></button>
--><button id='btnEdit' class="jqbutton"><fmt:message key="button.edit" /></button>
<button id='btnDelete' class="jqbutton"><fmt:message key="button.delete" /></button>
<button id="btnView" class="jqbutton"><fmt:message key="button.view" /></button>
<table id="policyList"></table>
<div id="policyPage"></div>
<fmt:message var="colNames" key="policy.table.colNames" />

<sp:url var="policyNewUrl" value="/policy/new" />
<sp:url var="policyEditUrl" value="/policy/edit" />
<sp:url var="policyViewUrl" value="/policy/view" />
<sp:url var="policyDeleteUrl" value="/policy/delete" />
<script type="text/javascript">

$(document).ready(function() {
	jQuery("#policyList").jqGrid({ 
		url:'<%=request.getContextPath()%>/policy/grid',
		datatype: "json",
		colNames:[${colNames}],
		colModel:[
				{name:'id',index:'id', width:90 },				
				{name:'name',index:'name', width:100},
				{name:'status',index:'status', width:200}
				],
		rowNum:10,
		rowList:[10,20,30],
		height: 350,
		pager: '#policyPage',
		autowidth: true, 
		sortname: 'id',
		viewrecords: true,
		sortorder: "asc",
		hidegrid: false,
		caption:'<fmt:message key="policy.list" />' });
	jQuery("#policyList").jqGrid('navGrid','#policyPage',{edit:false,add:false,del:false}
	);
	
	$(".jqbutton").button();
	$('#btnAdd').click(function() {
		location.href="${policyNewUrl}";
	});
	$('#btnEdit').click(function() {
		var gr = jQuery("#policyList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			location.href="${policyEditUrl}?id="+gr;
		}
		else alert('<fmt:message key="alert.select.row" />');
	});
	$('#btnView').click(function() {
		var gr = jQuery("#policyList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			location.href="${policyViewUrl}?id="+gr;
		}
		else alert('<fmt:message key="alert.select.row" />');
	});
	
	$('#btnDelete').click(function() {
		var gr = jQuery("#policyList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			if (confirm('<fmt:message key="policy.delete.confirm" />"'+gr+'"?')) {
				$.post("${policyDeleteUrl}",{"id":gr}, function (data) {
					if (data.flag == 'success') {
						alert('<fmt:message key="delete.completed" />');
						$("#policyList").trigger("reloadGrid");
					} else {
						alert(data.message);
					}
				});
			}
		} 
		else alert('<fmt:message key="alert.select.row" />');
	});
});
</script>
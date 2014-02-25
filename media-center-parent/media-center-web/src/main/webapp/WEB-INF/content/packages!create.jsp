<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%>
<div id="addMsg" align="left" style="color: red; font-size: 12px" ></div>
<!-- add new package Div -->
<div class="operate">
<form id="createForm" action="packages!create.action" method="post">
	<fmt:message key='packages.version' />&nbsp;<input id="version" name="version"/>
	<fmt:message key='packages.name' />&nbsp;<input id="name" name="name"/>
	<fmt:message key='packages.illustrate' />&nbsp;<input id="illustrate" name="illustrate" />
</form>
</div>
<div align="right">
<button class="btn_64" onclick="if(check()){addNewPkg();}" ><fmt:message key="save" /></button>
</div>

<script type="text/javascript">
/**
 * check input
 */
function check() {
	var version = $("#version").val();
	var name = $("#name").val();
	if(version==null || version=="") {
		$("#addMsg").empty();
		$("#addMsg").append("Please input the version of the New Package!");
		$("#version").focus();
		return false;
	}
	if(name==null || name=="") {
		$("#addMsg").empty();
		$("#addMsg").append("Please input the name of the New Package!");
		$("#name").focus();
		return false;
	}
	/* else {
		var url = "packages!create.action";
		var params = {"version" : $("#newVersion").val(), 
				"name":$("#newName").val(), "illustrate":$("#newIllustrate").val()};
		$.post(url, params, function() {
			$("#divAddPkg").dialog( "close" );
		});
		return true;
	}*/
	return true;
}

/**
 * add a new package
 */
function addNewPkg() {
	//var manager = $("#divPackages").ligerGetGridManager();
	//manager.addRow();
	//grid.addRow();
	$("#addMsg").empty();
	$("#createForm").submit();
}
</script>

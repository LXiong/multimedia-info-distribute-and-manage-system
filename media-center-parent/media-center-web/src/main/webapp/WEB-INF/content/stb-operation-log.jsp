<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %><%@include file="inc-checkdate-js.jsp" 
%><script type="text/javascript">
$(document).ready(function() {
	var command = "${command}";
	var result = "${result}";
	if(command != null && command != "") {
		$("#command").val(command);
	}
	if(result != null && result != "") {
		$("#result").val(result);
	}
	$( "#from" ).datepicker({ dateFormat: 'yy-mm-dd' });
	$( "#to" ).datepicker({ dateFormat: 'yy-mm-dd' });
	$( "#from" ).val("${from}");
	$( "#to" ).val("${to}");
	$("#stbMac").val("${stbMac}");
});
/**
 * 
 */
function getPage(page) {
	if(check_filter_date('from','to')) {
		$("#page").val(page);
		$("#queryForm").submit();
	}
}
</script>
<div class="operate">
	<form id="queryForm" method="post" action="stb-operation-log.action" >
		<label><fmt:message key="search.condition" /><fmt:message key="Operation" /></label>
		<select id="command" name="command">
			<option value="" ><fmt:message key="Total" /></option>
			<c:forEach var="item" items="${commands }" >
				<option value="${item }" ><fmt:message key="${item }" /></option>
			</c:forEach>
		</select>&nbsp;&nbsp;
		<label><fmt:message key="stb.operation.result" /></label>
		<select id="result" name="result">
			<option value="" ><fmt:message key="Total" /></option>
			<c:forEach var="item" items="${results }" >
				<option value="${item }" ><fmt:message key="${item }" /></option>
			</c:forEach>
		</select>&nbsp;&nbsp;
		<label>MAC</label>&nbsp;<input id="stbMac" name="stbMac" value="" size="15px">&nbsp;&nbsp;
		<label><fmt:message key="stb.connect.time" /></label>
		<input type="text" id="from" name="from" ></input>
		<label>&nbsp;To&nbsp;</label>
		<input type="text" id="to" name="to" ></input>
		<input type="hidden" id="page" name="page" value="0" ></input>
		<input type="button" class="btn_64" onclick="getPage(0);" 
			value="<fmt:message key="Query" />" >
	</form>
</div>
<div class="operate">
	<div class="page">
		<yun:pageLink totalPage="${pageNums }" currentPage="${page }" 
		link="###" onclick="getPage({p})" ></yun:pageLink>
	</div>
</div>
<div>
	<table class="linetable">
		<thead><tr>
			<th><fmt:message key='stb.operation.time' /></th>
			<th><fmt:message key='stb.mac' /></th>
			<th><fmt:message key='stb.operation.user' /></th>
			<th><fmt:message key='stb.operation.command' /></th>
			<th><fmt:message key='stb.operation.result' /></th>
		</tr></thead>
		<c:forEach var="log" items="${logList }" varStatus="logStatus">
			<tr>
				<td><fmt:formatDate value="${log.runTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${log.mac }</td>
				<td>${log.userName }</td>
				<td><fmt:message key="${log.command }" /></td>
				<td><fmt:message key="${log.result }" /></td>
			</tr>
		</c:forEach>
	</table>
</div>
<div class="operate">
	<div class="page">
		<yun:pageLink totalPage="${pageNums }" currentPage="${page }" 
		link="###" onclick="getPage({p})" ></yun:pageLink>
	</div>
</div>
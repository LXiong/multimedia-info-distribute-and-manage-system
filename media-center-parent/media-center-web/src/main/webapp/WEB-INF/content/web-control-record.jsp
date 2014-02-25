<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %><%@include file="inc-checkdate-js.jsp" 
%><link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/ui-lightness/jquery-ui-1.8.5.custom.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-ui-1.8.5.custom.min.js" ></script>
<script type="text/javascript">
$(document).ready(function() {
	$( "#from" ).datepicker({ dateFormat: 'yy-mm-dd' });
	$( "#to" ).datepicker({ dateFormat: 'yy-mm-dd' });
	$( "#from" ).val("${from}");
	$( "#to" ).val("${to}");
	$("#stbMac").val("${stbMac}");
});
function getPage(page) {
	if(check_filter_date('from','to')) {
		$("#page").val(page);
		$("#queryForm").submit();
	}
}
</script>
<div class="operate">
	<form id="queryForm" method="post" action="web-control-record.action">
		<fmt:message key="search.condition" />
		<fmt:message key="Operation" />
		<select id="command" name="command">
			<option value="" ><fmt:message key="Total" /></option>
			<c:forEach var="item" items="${commands }" >
				<option value="${item }" >${item }</option>
			</c:forEach>
		</select>&nbsp;&nbsp;
		<label>MAC</label>&nbsp;<input id="stbMac" name="stbMac" value="" size="15px">&nbsp;&nbsp;
		<fmt:message key="stb.connect.time" />
		<input type="text" id="from" name="from" ></input>
		&nbsp;To&nbsp;
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
			<th><fmt:message key='stb.command.time' /></th>
			<th><fmt:message key='stb.ip' /></th>
			<th><fmt:message key='stb.mac' /></th>
			<th><fmt:message key='stb.operation.command' /></th>
			<th><fmt:message key='watch.server.ip' /></th>
			<th><fmt:message key='watch.server.port' /></th>
			<th><fmt:message key='authenticate.customer.name' /></th>
		</tr></thead>
		<c:forEach var="log" items="${logList }">
			<tr>
				<td><fmt:formatDate value="${log.createdAt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${log.stbIp }</td>
				<td>${log.stbMac }</td>
				<td>${log.command }</td>
				<td>${log.watchIp }</td>
				<td>${log.watchPort }</td>
				<td>${log.customerName }</td>
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
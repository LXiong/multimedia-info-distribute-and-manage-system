<%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%><%@include file="taglib.jsp" 
%>
<!-- list -->
<script type="text/javascript">
	function queryStb(policy) {
		$("#fieldPolicy").val(policy);
		$("#queryForm").submit();
	}
</script>
<s:url action="stb" var="stbQueryUrl" />
<form action="${stbQueryUrl }" id="queryForm">
	<input type="hidden" name="activePolicy" id="fieldPolicy" />
</form>
<div>
	<table id="table" class="linetable">
		<thead><tr>
			<th><fmt:message key='stat.policy' /></th>
			<th><fmt:message key='stat.count' /></th>
			<th>&nbsp;</th>
		</tr></thead>
		<tbody>
			<c:forEach var="stat" items="${stbPolicyStat}" >
				<tr>
				<td>${stat['POLICY'] }</td>
				<td>${stat['COUNT'] }</td>
				<td>
				<a href="###" onclick="queryStb('${stat['POLICY'] }')">
				<fmt:message key="stat.list.stb" /></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
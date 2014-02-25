<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@include file="taglib.jsp"%>
<div class="operate">
	<div class="btn">
		<input type="button" onclick="edit()" class="btn_64" value="编辑">
	</div>
</div>
<table class="linetable" style="width: 300px">
	<tr>
		<td>用户名</td>
		<td>${user.userName }</td>
	</tr>
	<tr>
		<td>电话号</td>
		<td>${user.telephone }</td>
	</tr>
	<tr>
		<td>电子邮件</td>
		<td>${user.email }</td>
	</tr>
	<tr>
		<td>上次登录时间</td>
		<td><fmt:message key="web.date.pattern" var="datePattern" /> <fmt:formatDate
				value="${user.lastLoginTime }" pattern="${datePattern}" /></td>
	</tr>
</table>
<s:url var="editUrl" action="personal">
	<s:param name="oper">edit</s:param>
</s:url>
<script type="text/javascript"> 
function edit() {
	location.href="${editUrl}";
}
</script>





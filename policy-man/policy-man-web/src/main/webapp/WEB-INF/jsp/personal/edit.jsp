<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<sp:url value="/personal" var="personalInfoUrl" />
<script type="text/javascript">
function backtoinfo() {
	location.href="${personalInfoUrl}";
}
</script>
<sp:url value="/personal/save" var="personalSaveUrl" />
<form:form action="${personalSaveUrl }" commandName="user" methodParam="post">
<form:hidden path="username"/>
<table class="linetable">
<tr><th>属性</th><th>值</th></tr>
<tbody>
<tr>
<td>用户名</td><td>${user.username }</td>
</tr>
<tr>
<td>显示名</td><td><form:input path="dispName"/></td>
</tr>
<tr>
<td>电子邮件</td><td><form:input path="email"/></td>
</tr>
<tr>
<td>电话</td><td><form:input path="phone"/></td>
</tr>
<tr>
<td colspan="2"><input type="submit" value='<fmt:message key="button.save" />' />
&nbsp;&nbsp;
<input type="button" value='<fmt:message key="button.cancel" />' onclick="backtoinfo()" />
</td>
</tr>
</tbody>
</table>
</form:form>
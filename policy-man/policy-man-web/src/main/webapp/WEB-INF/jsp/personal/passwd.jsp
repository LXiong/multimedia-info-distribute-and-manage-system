<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<form method="post" action='<sp:url value="/personal/change" />'>
<table class="linetable">
<tbody>
<tr>
<td>旧密码</td><td><input type="password" name="oldpass" /></td>
</tr>
<tr>
<td>新密码</td><td><input type="password" name="newpass1" /></td>
</tr>
<tr>
<td>密码确认</td><td><input type="password" name="newpass2" /></td>
</tr>
<tr>
<td colspan="2"><input type="submit" value='<fmt:message key="button.save" />' />
</td>
</tr>
</tbody>
</table>
</form>
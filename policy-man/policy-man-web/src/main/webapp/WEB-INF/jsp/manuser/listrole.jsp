<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<h2><fmt:message key="manuser.role.currentuser" /> ${user.username } : ${user.dispName }</h2>

<sp:url var="userListUrl" value="/manuser" />
<form action="${saveRoleUrl }" method="post" id="formRole">
<input type="hidden" name="id" value="${user.username }" />
<input type="hidden" name="role" value="" />
<ul>
<c:forEach var="role" items="${roleList }">
	<li><!-- 三元运算表达式确定是否  checked -->
		<input type="checkbox" name="role" value="${role.id }" ${role.assigned?'checked':'' } />
		${role.name }
	</li>
</c:forEach>
</ul>
</form>

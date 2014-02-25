<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<s:form action="ldap" method="post">
<s:textfield name="memberOf" key="ldap.search.memberof" />
<s:hidden name="postToken" value="1" />
<s:if test="{ #session['daily-status.user'].ldapUser }">
<s:textfield name="ldapUserId" key="ldap.userId"/>
<s:password name="ldapPassword" key="ldap.password"/>
</s:if>
<s:submit key="Submit"></s:submit>
</s:form>

<c:if test="${ldapUserList!=null}">
<s:form action="ldap" method="store" theme="simple">
	<p>Found the following users:</p>
	<table>
	<tr>
	<th></th>
	<th>Account</th>
	<th>Name</th>
	<th>Mail</th>
	</tr>
	<c:forEach var="user" items="${ldapUserList}">
	<tr>
	<td><s:checkbox name="selectedUserId" value="#{user.userId}" /></td>
	<td>${user.userId}</td>
	<td>${user.name}</td>
	<td>${user.email}</td>
	</tr>
	</c:forEach>
	</table>
</s:form>
</c:if>

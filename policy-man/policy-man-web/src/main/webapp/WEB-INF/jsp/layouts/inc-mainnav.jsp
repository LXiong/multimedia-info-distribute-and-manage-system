<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<ul class="nav">

<yun:submenu menuVar="menuList" menu="${mainMenu }">
	<c:forEach items="${menuList }" var="menu">
	<c:set var="defaultAction">${menu.action }</c:set>
	<yun:submenu menuVar="submenuList" menu="${menu }">
		<c:if test="${menu.action == null || fn:length(menu.action)==0 }">
			<c:forEach var="submenu" items="${submenuList }" varStatus="subStatus">
			<c:if test="${subStatus.index==0 }"><c:set var="defaultAction" value="${submenu.action }"></c:set></c:if>
			</c:forEach>
		</c:if>
		<c:set var="mitemClass" value="" />
		<c:if test="${not empty walkPath }">
		<c:forEach var="walked" items="${walkPath}">
			<c:if test="${menu == walked}">
				<c:set var="mitemClass" value=" current " />
			</c:if>
		</c:forEach>
		</c:if>
		<li>
			<a class="${mitemClass }" href="<sp:url value="${defaultAction}" />"><fmt:message key="${menu.msgKey}"/></a>
		</li>
	</yun:submenu>
	</c:forEach>
</yun:submenu>

</ul>
<ul class="log">
	<li><fmt:message key="Welcome" /></li>
	<li><sec:authentication property="principal.dispName"/> </li>
	<li><a href="<sp:url value="/logout"/>"><fmt:message key="Logout"/></a></li>
</ul>

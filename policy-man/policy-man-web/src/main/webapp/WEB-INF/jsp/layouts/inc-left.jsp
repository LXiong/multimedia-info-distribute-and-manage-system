<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<div class="col_l cf">
    <div id="item" class="shadow">
        <div class="innerDiv">
            <div class="item_list">
<c:forEach items="${mainMenu.children }" var="menu">
	<c:if test="${not empty walkPath }">
	<c:forEach var="walked" items="${walkPath}" varStatus="walkedIndex">
	<c:if test="${menu == walked && walkedIndex.first}">
		<h2><fmt:message key="${menu.msgKey}"/></h2>
		<ul class="cf">
		<c:forEach var="submenu" items="${menu.children}">
			<sec:authorize url="${submenu.action}">
			<c:set var="sitemClass" value=""/>
			<c:forEach var="subwalk" items="${walkPath}">
				<c:if test="${submenu == subwalk}">
					<c:set var="sitemClass" value="current"/>
				</c:if>
			</c:forEach>
        	<li class="${sitemClass}">
        		<a href='<sp:url value="${submenu.action}" />'><fmt:message key="${submenu.msgKey}"/></a>
        	</li>
        	</sec:authorize>
		</c:forEach>
		</ul>
	</c:if>
	</c:forEach>
	</c:if>
</c:forEach>
            </div>
        </div>
    </div>
</div><!-- end of col_l -->

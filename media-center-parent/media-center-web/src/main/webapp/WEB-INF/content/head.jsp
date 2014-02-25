<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<div id="head">
	<div id="head_wrapper">
		<div class="inner">
			<div class="logo">
				<a href="<%=request.getContextPath() %>"><img alt="" src="images/logo.jpg"></a>
			</div>
			<div id="nav_wrapper">
				<ul class="nav">
					<yun:submenu var="menus" menu="${mainMenu}">
						<c:forEach items="${menus}" var="menu" varStatus="menuIndex">
							<c:set var="mitemClass" value=""/>
							<yun:inPath menu="${menu}"><c:set var="mitemClass" value="current"/></yun:inPath>
							<li>
							<s:a action="%{#attr.menu.action}" namespace="%{#attr.menu.nameSpace}"
								method="%{#attr.menu.urlMethod}"
								cssClass=" %{#attr.mitemClass} "><fmt:message key="${menu.msgKey}"/></s:a>
							</li>
						</c:forEach>
					</yun:submenu>
				</ul>
				<ul class="log">
				<c:if test="${logined}">
					<li><s:text name="Welcome" /></li>
					<li>${loginedName }</li>
					<li><s:a action="/login" method="logout"><s:text name="Logout" /></s:a></li>
				</c:if>
				</ul>
			</div>
		</div>
	</div>
</div><!--head end -->

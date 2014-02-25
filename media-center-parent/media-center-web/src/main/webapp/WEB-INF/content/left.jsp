<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<div class="col_l">
    <div id="item" class="shadow">
        <div class="innerDiv">
            <div class="item_list">
            <yun:submenu var="menus" menu="${mainMenu}">
				<c:forEach items="${menus}" var="menu" varStatus="menuIndex">
				<yun:inPath menu="${menu}">
                <h2><fmt:message key="${menu.msgKey}"/></h2>
                <ul class="cf">
                	<yun:submenu var="submenus" menu="${menu}">
                	<c:forEach items="${submenus }" var="submenu">
                	<c:set var="sitemClass" value=""/>
					<yun:inPath menu="${submenu}"><c:set var="sitemClass" value="current"/></yun:inPath>
                    <li class="${sitemClass}">
                    <s:a action="%{#attr.submenu.action}" method="%{#attr.submenu.method}"
					namespace="%{#attr.submenu.nameSpace}"><fmt:message key="${submenu.msgKey}"/></s:a></li>
                    </c:forEach></yun:submenu>
                </ul>
                </yun:inPath>
                </c:forEach>
            </yun:submenu>
            </div>
        </div>
    </div>
</div><!-- end of col_l -->

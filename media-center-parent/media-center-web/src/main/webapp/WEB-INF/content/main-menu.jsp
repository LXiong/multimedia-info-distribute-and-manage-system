<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<div id="header-box">
<ul  id="head_menu">
<yun:submenu var="menus" menu="${mainMenu}">
<c:forEach items="${menus}" var="menu" varStatus="menuIndex">
	<li class="mainmenuItem " id="m${menuIndex.index }">
	<s:set var="mitemClass" value="#request.mainMenu.menuPathMap[#request.actionPath].startsWith(#attr.menu.path) ? ' mainSelected ':'' " />
	<c:if test="${menu.action eq null}">
	<a href="javascript:;" class=" ${mitemClass} "><fmt:message key="${menu.msgKey}"/></a>
	</c:if>
	<c:if test="${menu.action ne null }">
	<s:a action="%{#attr.menu.action}" namespace="%{#attr.menu.nameSpace}"
		method="%{#attr.menu.method}"
		cssClass=" %{mitemClass} "><fmt:message key="${menu.msgKey}"/></s:a>
	</c:if>
	
	<yun:submenu var="submenus" menu="${menu}">
		<ul class="submenu" id="subm${menuIndex.index }" style="visibility: hidden;">
			<c:forEach items="${submenus }" var="submenu">
				<li>
				<s:set var="sitemClass" value="#request.mainMenu.menuPathMap[#request.actionPath].startsWith(#attr.submenu.path) ? ' mainSelected ':'' " />
				<s:a action="%{#attr.submenu.action}" method="%{#attr.submenu.method}"
				namespace="%{#attr.submenu.nameSpace}"
				cssClass=" %{sitemClass}"><fmt:message key="${submenu.msgKey}"/></s:a></li>
			</c:forEach>
		</ul>
	</yun:submenu>
	</li>
</c:forEach>
</yun:submenu>
</ul>
</div>
<script type="text/javascript">
var submenutimer = 0;
var shownitem = 0;
function closemenu() {
	if (shownitem) shownitem.css('visibility', 'hidden');
}
function scheduleCloseMenu() {
	clearMenuCloseTimer();
	submenutimer = window.setTimeout(closemenu, 500);
}
function clearMenuCloseTimer() {
	if (submenutimer) {
		window.clearTimeout(submenutimer);
		submenutimer=null;
	}
}
function bindmenu() {
	$('#head_menu > li').hover(function() {
		clearMenuCloseTimer();
		closemenu();
		shownitem = $(this).find('ul').css('visibility','visible');
	}, scheduleCloseMenu);
}
$(document).ready(function () {
	bindmenu();
});
</script>
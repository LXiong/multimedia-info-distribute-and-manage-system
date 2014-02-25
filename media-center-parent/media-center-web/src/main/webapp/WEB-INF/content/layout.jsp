<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message key="${titleKey }" /></title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/global.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery.jnotify.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/ui-lightness/jquery-ui-1.8.5.custom.css">
<link media="screen" type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/colorpicker.css"></link>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/ligerui-all.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.jnotify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/additional-methods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/messages_cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/colorpicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ligerui.all.js"></script>
<c:forEach var="cssItem" items="${cssList}">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/${cssItem}">
</c:forEach>
</head>
<body>
<%@include file="head.jsp" %>

<div id="main" class="cf">
<div class="inner">
	<div id="top_nav" class="gray">当前位置：
	<c:forEach var="menupos" items="${mainMenu.relatedMap[currentAction] }"><fmt:message key="${menupos.msgKey}"/>\ </c:forEach>
	</div>
	<%@include file="left.jsp" %>
	<div class="col_r">
       <div id="item" class="shadow">
         <div class="innerDiv">
           <div class="item_list">
		      <h3><fmt:message key="${mainMenu.menuMap[currentAction].msgKey}"/></h3>
				<jsp:include page="${mainPage}"></jsp:include>
		   </div>
	     </div>
	   </div>
	</div><!-- end of col_r -->
	<div class="clr"></div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("tbody > tr").hover(function () {
		$(this).addClass("bg-hover");
	}, function() {
		$(this).removeClass("bg-hover");
	});
});
</script>
</body>
</html>



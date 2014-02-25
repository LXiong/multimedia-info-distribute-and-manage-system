<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/global.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/redmond/jquery-ui-1.8.10.custom.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/ui.jqgrid.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/jquery.ui.datetime.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/jquery.ui.tabs.paging.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/jquery.jnotify.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/colorpicker.css">

<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-ui-1.8.10.custom.min.js"></script>
<script type="text/javascript" src='<%=request.getContextPath() %>/static/js/i18n/<fmt:message key="jqgrid.locale" />'></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery.ui.datetime.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/policy.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery.ui.tabs.paging.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery.jnotify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/additional-methods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/messages_cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/colorpicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/array-ext.js"></script>
</head>
<body>
<%@include file="inc-head.jsp" %>

<div id="main">
<div id="top_nav" class="gray">当前位置：
		<c:if test="${not empty walkPath }">
			<c:forEach var="walked" items="${walkPath }">
				<fmt:message key="${walked.msgKey}"/> =&gt;
			</c:forEach>
		</c:if>
	</div>
<div class="inner">
	<%@include file="inc-left.jsp" %>
	<div class="col_r">
       <div id="item" class="shadow">
         <div class="innerDiv">
           <div class="item_list">
		      <h3><c:if test="${not empty lastWalked }">
				<fmt:message key="${lastWalked.msgKey}"/>
			  </c:if></h3>
				<jsp:include page="${mainPage}"></jsp:include>
		   </div>
	     </div>
	   </div>
	</div><!-- end of col_r -->
	<div class="clr"></div>
</div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/global.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/smoothness/jquery-ui-1.8.7.custom.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/ui.jqgrid.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-ui-1.8.7.custom.min.js"></script>
<script type="text/javascript" src='<%=request.getContextPath() %>/static/js/i18n/<fmt:message key="jqgrid.locale" />'></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery.jqGrid.min.js"></script>
</head>
<body>
<%@include file="inc-head.jsp" %>

<div id="main">
<div id="top_nav" class="gray"><a href='<sp:url value="/home" />'><fmt:message key="goto.home" /></a></div>
<div class="inner">
	<div >
       <div id="item" class="shadow">
         <div class="innerDiv">
           <div class="item_list">
		      <h3>  </h3>
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
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="access.denied" /></title>
</head>
<body>
<div id="wrapper">
<div id="header"></div>
<div id="content">
<fmt:message key="msg.access.denied" />
<br />
<a href="<%=request.getContextPath() %>/home"><fmt:message key="link.return.home"/></a>
</div>
<div id="footer"></div>
</div>
</body>
</html>
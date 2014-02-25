<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
</head>
<body>
<div id="wrapper">
<div id="header"></div>
<div id="content">
Welcome to the home page
<p>
<a href="<%=request.getContextPath() %>/logout">Logout</a>
</p>
</div>
<div id="footer"></div>
</div>
</body>
</html>
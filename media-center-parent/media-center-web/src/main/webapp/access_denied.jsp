<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@ 
taglib prefix="s" uri="/struts-tags" %><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 
prefix="fmt"%>
<fmt:message key="page.access.denied" /> <br />
<a href="<%=request.getContextPath() %>/home"><fmt:message key="back.home" /></a>

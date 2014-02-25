<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>

<script type="text/javascript">
alert("<fmt:message key='${alert}'/>");
location='<%=request.getContextPath()%>${location}';
</script>
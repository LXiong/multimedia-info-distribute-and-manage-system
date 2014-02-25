<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
[
<c:forEach var="info" items="${infos}" varStatus="listStatus">
	{
	"id":"${info.id }",
	"status":"${info.status }",
	"statusCn":"${info.statusCn }",
	"stbMac":"${info.stbMac}", 
	"warningType":"${info.warningType }", 
	"warningTypeCn":"${info.warningTypeCn }",
	"details":"${info.details }",
	"createdAt":"<fmt:formatDate value="${info.createdAt }" pattern="yyyy年MM月dd日 HH时mm分"/> "
	}
	<c:if test="${!listStatus.last}">,</c:if>
</c:forEach>
]
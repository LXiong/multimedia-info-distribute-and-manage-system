<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
[
<c:forEach var="item" items="${stbs}" varStatus="listStatus">
	{
	"stbMac":"${item.stbMac}",
	"shopNo":"${item.shopNo }",
	"shopName":"${item.shopName }",
	"groupName": "${item.groupName }"
	}
	<c:if test="${!listStatus.last}">,</c:if>
</c:forEach>
]
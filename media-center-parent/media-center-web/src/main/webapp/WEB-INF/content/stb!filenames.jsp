<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
{
<c:forEach var="item" items="${files}" varStatus="listStatus">
	"${item }":"${item }"
	<c:if test="${!listStatus.last}">,</c:if>
</c:forEach>
}
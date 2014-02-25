<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp"
 %>{<c:forEach var="item" items="${groupList}" varStatus="listStatus">
	"${item.groupId }":"${item.groupName }"
<c:if test="${!listStatus.last}">,</c:if></c:forEach>}
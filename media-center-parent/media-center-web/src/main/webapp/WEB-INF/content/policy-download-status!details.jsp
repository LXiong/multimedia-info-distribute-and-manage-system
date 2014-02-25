<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp"
 %>{<c:forEach var="item" items="${boxDownStatus}" varStatus="listStatus">
	"${item.videoName }":{
	"videoName":"${item.videoName }", 
	"isFinished":"${item.isFinished }"
	}
	<c:if test="${!listStatus.last}">,</c:if></c:forEach>
}
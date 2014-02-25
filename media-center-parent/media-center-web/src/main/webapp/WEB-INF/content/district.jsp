<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" 
    %>{<c:forEach var="dist" items="${districts}" varStatus="distStatus">
    "${dist.districtId}":"${dist.districtName }"
    <c:if test="${!distStatus.last}">,</c:if></c:forEach>}
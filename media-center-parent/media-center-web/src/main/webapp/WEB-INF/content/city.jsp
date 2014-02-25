<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" 
    %>{<c:forEach var="city" items="${cities}" varStatus="cityStatus">
    "${city.cityId}":"${city.cityName }"
    <c:if test="${!cityStatus.last}">,</c:if></c:forEach>}
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" 
    %>{<c:forEach var="prov" items="${provinces}" varStatus="provStatus">
    "${prov.provinceId}":"${prov.provinceName }"
    <c:if test="${!provStatus.last}">,</c:if></c:forEach>}
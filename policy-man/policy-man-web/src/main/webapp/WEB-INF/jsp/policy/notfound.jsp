<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>

<sp:url var="savePolicyUrl" value="/policy/save" />
<sp:url var="listPolicyUrl" value="/policy/list" />
<sp:url var="listLayoutUrl" value="/layout/sellist" />
<h2><fmt:message key="policy.not.found" /></h2>

<a href="${listPolicyUrl }"><fmt:message key="policy.backto.list" /></a>



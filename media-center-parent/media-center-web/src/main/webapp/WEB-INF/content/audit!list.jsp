<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp"
 %>{"stb":{<c:forEach var="stb" items="${stbs}" varStatus="stbStatus">
    "${stb.stbMac }":{"stbMac":"${stb.stbMac }", 
    "provinceId":"${stb.provinceId }", "provinceName":"${stb.provinceName }", 
    "cityId":"${stb.cityId }", "cityName":"${stb.cityName }", 
    "districtId":"${stb.districtId }", "districtName":"${stb.districtName }", 
    "addrDetail":"${stb.addrDetail }", "stbStatus":"${stb.stbStatus }"}
    <c:if test="${!stbStatus.last}">,</c:if></c:forEach>}, "pageNums":"${pageNums }", 
    "page":"${page }" }
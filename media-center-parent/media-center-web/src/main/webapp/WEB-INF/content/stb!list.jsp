<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp"
 %>{"stb":{
 	<c:forEach var="stb" items="${stbs}" varStatus="stbStatus">
    "${stb.stbMac }":{
    	"stbMac":"${stb.stbMac }", 
	    "provinceId":"${stb.provinceId }", 
	    "provinceName":"${stb.provinceName }", 
	    "cityId":"${stb.cityId }", 
	    "cityName":"${stb.cityName }", 
	    "districtId":"${stb.districtId }", 
	    "districtName":"${stb.districtName }", 
	    "addrDetail":"${stb.addrDetail }", 
	    "stbStatus":"${stb.stbStatus }", 
	    "groupId":"${stb.groupId }",
	    "groupName":"${stb.groupName }",
	    "confId":"${stb.confId }",
	    "confName":"${stb.confName }",
	    "activePolicy":"${stb.activePolicy }",
	    "activePolicySuccessNum":"${stb.activePolicySuccessNum }",
	    "activePolicyFailedNum":"${stb.activePolicyFailedNum }",
	    "lastOfflineTime":"${stb.lastOfflineTime }", 
	   <%--  "packageId":"${stb.packageid }", --%>
	    "packageName":"${stb.packageName }", 
	    "terminalIp":"${stb.terminalIp }",
	    "shopNo":"${stb.shopNo }",
	    "shopName":"${stb.shopName }",
	    "contacts":"${stb.contacts }",
	    "phone":"${stb.phone }", 
	    "nmem":"${stb.nmem }", 
	    "disk":"${stb.disk }", 
	    "cpu":"${stb.cpu }"
	}<c:if test="${!stbStatus.last}">,</c:if></c:forEach>
	}, 
	"pageArr":{
		<c:forEach var="i" items="${pageArr}" varStatus="arrStatus">
    	"${i }":"${i }"
    	<c:if test="${!arrStatus.last}">,</c:if></c:forEach>
    },
    "pageNums":${pageNums},
    "page":"${page }" 
}
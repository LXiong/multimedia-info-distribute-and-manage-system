<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<table class="linetable">
<thead><tr>
	<th><fmt:message key="policy.colhead.readable" /></th>
	<th><fmt:message key="policy.colhead.md5" /></th>
	<%-- <th><fmt:message key="Operation" /></th> --%>
	</tr>
</thead>
<tbody id="tbody">
<c:forEach var="video" items="${videos}">
	<tr><td>${video.name }</td>
	<td>${video.code }</td>
	<%-- <td><a href="###" onclick="javascript:downStatus('${video.fileName }');">
			<fmt:message key='policy.media.download.status' /></a></td> --%>
	</tr>
</c:forEach>
</tbody>
<tfoot id="tfoot">
<tr align="center">
	<td colspan="2"><input type="button"  value="返回" onclick="javascript:location='policy.action';"></td>
</tr>
</tfoot>
</table>
<script type="text/javascript">
$(document).ready(function(){
	show();
});

function show(){
	var tbody = $("#tbody>tr").size();
	for(var i=0;i<tbody;i++){
		$("#tbody>tr").eq(i).mouseover(function(){$(this).css("background-color","pink")});
		$("#tbody>tr").eq(i).mouseout (function(){$(this).css("background-color","white")});
 	}
}

function downStatus(videoName) {
	window.location="policy-download-status!mediaDownStatus.action?videoName=" + videoName + 
		"&policyNumber=" + ${policyNumber} + "";//"&policyId" + ${policyid} + 
}
</script>

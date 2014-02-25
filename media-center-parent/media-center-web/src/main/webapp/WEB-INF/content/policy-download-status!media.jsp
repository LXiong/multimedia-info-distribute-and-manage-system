<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" 
%><script type="text/javascript">
function page(page) {
	window.location = "policy-download-status!mediaDownStatus.action?videoName=${videoName }" + 
		"&policyNumber=${policyNumber}&isFinished=" + $("#isFinished").val();
}//"&policyId" + $--{policyId} + 

function check() {
	if( $("#isFinished").is(':checked') ) {
		$("#isFinished").val("N");
	} else {
		$("#isFinished").val("");
	}
}
</script>
<div align="left">
	<fmt:message key="policy.version" /><label>${policyNumber }</label>
	<fmt:message key="file.name" /><label>${videoName }</label>
	<fmt:message key="file.size" /><label>${videoSize }</label><p>
	<input type="checkbox" id="isFinished" value="" onchange="javascript:check();"></input>
	<fmt:message key="show.not.finish.stb.only" />
	<button class="btn_64" onclick="javascript:page(0);" >
		<fmt:message key="condition.filt" /></button>
</div>

<div>
	<table class="linetable">
		<thead><tr>
			<th><fmt:message key='stb.mac' /></th>
			<th><fmt:message key='file.download.percent' /></th>
			<th><fmt:message key='start.time' /></th>
			<th><fmt:message key='end.time' /></th>
			<th><fmt:message key='download.speed' />(B/s)</th>
		</tr></thead>
		<tbody><c:forEach var="stb" items="${mediaDownStatus}" ><tr>
			<td>${stb.stbMac }</td>
			<td>${stb.percent }</td>
			<td><fmt:formatDate value="${stb.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${stb.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${stb.speed }</td>
		</tr></c:forEach></tbody>
	</table>
</div>
<div>
	<input type="button" class="btn_64" value="<fmt:message key='back' />" onclick="javascript:history.back();" ></input>
</div>

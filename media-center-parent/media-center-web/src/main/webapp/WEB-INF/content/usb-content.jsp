<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %><%@include file="stb-js.jsp" 
%>
<s:url var="configUrl" action="usb" method="exportConfig" />
<s:url var="configMd5Url" action="usb" method="exportConfigMd5" />
<script type="text/javascript">
var rows = 0;
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
});
/**
 * 
 */
function back() {
	window.location = path + "/usb!config.action";
}
/**
 * 
 */
function exportConfig() {
	$("#confForm").attr("action", "${configUrl}");
	$("#confForm").submit();
	
}
/**
 * 
 */
function getMD5() {
	$("#confForm").attr("action", "${configMd5Url}");
	$("#confForm").submit();
}
</script>
<div>
	<div align="left">
		<label><fmt:message key="config.file.name"/></label>
		<label id="fileName">${confName }</label>&nbsp;&nbsp;&nbsp;&nbsp;
		<label><fmt:message key="config.file.get"/></label>
		[<a href="###" onclick="exportConfig();">STB.conf</a>]
		[<a href="###" onclick="getMD5();">STB.conf.md5</a>]
	</div>
	<form id="confForm" target="hiddenframe" method="post" >
		<input id="confId" name="confId" type="hidden" value="${confId }" ></input>
	</form>
	<table id="configPropts" class="linetable">
		<thead>
			<tr>
				<th style='text-align: left'><fmt:message key="config.property.key"/></th>
				<th style='text-align: left'><fmt:message key="config.property.value"/></th>
			</tr>
		</thead>
		<c:forEach var="config" items="${configs }">
			<tr>
				<td style='text-align: left'><label><fmt:message key="${config.configKey }"/></label></td>
				<td style='text-align: left'><label>${config.configValue }</label></td>
			</tr>
		</c:forEach>
	</table>
</div>
<br></br>
<div align="right">
	<button onclick="back();" class="btn_64"><fmt:message key="back"/></button>
</div>
<iframe id="hiddenframe" name="hiddenframe" width="0" height="0" frameborder="0" scrolling=no marginheight="0" marginwidth="0"></iframe>
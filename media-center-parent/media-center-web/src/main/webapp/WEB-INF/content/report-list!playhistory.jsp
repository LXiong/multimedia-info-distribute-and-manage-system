<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%> <%@include file="stb-js.jsp" 
%><%@include file="inc-checkdate-js.jsp" %>
<div class="operate">
<form id="statisForm" action="report-list!playHistoryReport.action" method="post">
<div class="btn">
	<label>MAC:&nbsp;</label><input id="mac" name="mac" value="${mac}" size="15px">
	<label><fmt:message key="file.type" />:&nbsp;</label>
	<select id="mediaType" name="mediaType">
		<option id="selDefault" value="" ></option>
		<option id="selMedia" value="video"><fmt:message key="file.type.media" /></option>
		<option id="selPicture" value="image"><fmt:message key="file.type.picture" /></option>
		<option id="selText" value="text"><fmt:message key="file.type.text" /></option>
	</select>
	<label><fmt:message key="start.end.time" />:&nbsp;</label>
	<input type="text" id="startTime" name="startTime" readonly="readonly" value="${startTime}"></input>
	---
	<input type="text" id="endTime" name="endTime" readonly="readonly" value="${endTime}"></input>
	<button class="btn1_64" onclick="getPage(0);"><fmt:message key="statistics" /></button>
	&nbsp;||&nbsp;
	<button onclick="exportData();" class="btn_64"><fmt:message key='export.data' /></button>
	<input type="hidden" id="page" name="page" value="0" ></input>
</div>
</form>
<div class="page">
	<yun:pageLink totalPage="${pageBean.totalPage}" currentPage="${pageBean.curPage}"
	link="javascript:getPage({p})"></yun:pageLink>
</div>
</div>
<table class="linetable">
<thead>
<tr>
	<th><fmt:message key="Mac" /></th>
	<th><fmt:message key="time" /></th>
	<th><fmt:message key="file.name" /></th>
	<th><fmt:message key="file.origin.name" /></th>
	<th><fmt:message key="file.align" /></th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="report" items="${playHistReport}">
	<tr>
	<td>${report.mac }</td>
	<td><fmt:formatDate value='${report.logTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
	<td>${report.videoName }</td>
	<td>${report.originName }</td>
	<td>${report.name }</td>
	</tr>
</c:forEach>
</tbody>
</table>
<div class="operate">
    <div class="page">
	    <yun:pageLink totalPage="${pageBean.totalPage}" currentPage="${pageBean.curPage}"
    	link="javascript:getPage({p})"></yun:pageLink>
    </div>                                
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-ui-timepicker-addon.js"></script> 
<script type="text/javascript">
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	$( "#startTime" ).datepicker({ dateFormat: 'yy-mm-dd' });
	$( "#endTime" ).datepicker({ dateFormat: 'yy-mm-dd' });
	//$( "#startTime" ).val("${startTime}");
	//$( "#endTime" ).val("${endTime}");
	$( "#mediaType" ).val("${mediaType}");
});
/**/
function getPage(page) {
	$("#page").val(page);
	$("#statisForm").attr('action','report-list!playHistoryReport.action');
	$("#statisForm").submit();
}
/**
 * 

function page(page) {
	location = "report-list!playHistoryReport.action?page=" + page;
} */

/**
 * export report
 */
function exportData() {
	var url = path + "/report-list!exportPlayHistoryReport.action";
	$('#statisForm').attr('action',url);
	$('#statisForm').submit();
}
</script>
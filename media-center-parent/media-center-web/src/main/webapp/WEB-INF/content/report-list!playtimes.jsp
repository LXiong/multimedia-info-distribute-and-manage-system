<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%> <%@include file="stb-js.jsp" 
%><%@include file="inc-checkdate-js.jsp" %>
<div class="operate">
<form id="statisForm" action="report-list!playTimesReport.action" method="post">
<div class="btn">
	<label>MAC:&nbsp;</label><input id="mac" name="mac" value="${mac}" size="15px">
	<label><fmt:message key="file.align" />:&nbsp;</label><input id="name" name="name" value="${name}" size="15px">
	<label><fmt:message key="start.end.time" />:&nbsp;</label>
	<input type="text" id="startTime" name="startTime" readonly="readonly"></input>
	---
	<input type="text" id="endTime" name="endTime" readonly="readonly"></input>
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
	<th><fmt:message key="file.name" /></th>
	<th><fmt:message key="file.origin.name" /></th>
	<th><fmt:message key="file.align" /></th>
	<th><fmt:message key="video.paly.times" /></th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="report" items="${playTimesReport}">
	<tr>
	<td>${report.mac}</td>
	<td>${report.videoName}</td>
	<td>${report.originName }</td>
	<td>${report.name }</td>
	<td>${report.times }</td>
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
	$( "#startTime" ).val("${startTime}");
	$( "#endTime" ).val("${endTime}");
});
/**/
function getPage(page) {
	$("#page").val(page);
	$("#statisForm").attr('action','report-list!playTimesReport.action');
	$("#statisForm").submit();
}
/**
 * 

function page(page) {
	location = "report-list!playTimesReport.action?page=" + page;
} */

/**
 * export report
 */
function exportData() {
	var url = path + "/report-list!exportPlayTimesReport.action";
	$('#statisForm').attr('action',url);
	$('#statisForm').submit();
}
</script>
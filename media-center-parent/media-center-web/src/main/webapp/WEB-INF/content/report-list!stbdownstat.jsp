<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%> <%@include file="stb-js.jsp" 
%><script type="text/javascript">
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	$("#typeId").change(function(){ getGroup("typeId", "groupId") });
	getType('typeId', 'groupId', ${typeId}, ${groupId});
	//setTimeout("getType('typeId', 'groupId', ${typeId}, ${groupId});", 500);
});

function getPage(page) {
	$("#page").val(page);
	$("#statisForm").attr('action','report-list!stbDownStat.action');
	$("#statisForm").submit();
}

/**
 * export report
 */
function exportData() {
	var url = path + "/report-list!exportStbDownStat.action";
	$('#statisForm').attr('action',url);
	$('#statisForm').submit();
}
</script>
<div class="operate">
<form id="statisForm" action="report-list!stbDownStat.action" method="post">
<div class="btn">
	<fmt:message key="top.level" />:<select id="typeId" name="typeId">
		<option value="" ><fmt:message key="Total" /></option></select>
	<fmt:message key="second.level" />:<select id="groupId" name="groupId">
		<option value="" ><fmt:message key="Total" /></option></select>
	<fmt:message key="policy.version" />：
	<input id="policyNumber" name="policyNumber" value="${policyNumber}" />
	<button class="btn1_64" onclick="getPage(0);"><fmt:message key="statistics" /></button>
	&nbsp;||&nbsp;
	<button onclick="exportData();" class="btn_64"><fmt:message key='export.data' /></button>
	<input type="hidden" id="page" name="page" value="0" ></input>
</div>
</form>
<div class="page">
	<yun:pageLink totalPage="${pageBean.totalPage}" currentPage="${pageBean.curPage}"
	link="javascript:page({p})"></yun:pageLink>
</div>
</div>
<table class="linetable">
<thead>
<tr>
	<th><fmt:message key="policy.version" /></th>
	<th><fmt:message key="stb.device" /></th>
	<%-- <th><fmt:message key="start.time" /></th> --%>
	<th><fmt:message key="end.time" /></th>
	<th><fmt:message key="policy.media.download.status" /></th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="report" items="${downStatusList}">
	<tr>
	<td>${report.policyNumber }</td>
	<td>${report.mac }</td>
	<%-- <td><fmt:formatDate value='${report.startTime}' pattern='yyyy-MM-dd hh:mm:ss'/></td> --%>
	<td><fmt:formatDate value='${report.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
	<td><fmt:message key="${report.status }" /></td>
	</tr>
</c:forEach>
</tbody>
</table>
<div class="operate">
    <div class="page">
	    <yun:pageLink totalPage="${pageBean.totalPage}" currentPage="${pageBean.curPage}"
    	link="javascript:page({p})"></yun:pageLink>
    </div>                                
</div>

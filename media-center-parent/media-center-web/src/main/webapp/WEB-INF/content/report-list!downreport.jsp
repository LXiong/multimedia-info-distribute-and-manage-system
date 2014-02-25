<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%> <%@include file="stb-js.jsp" 
%><script type="text/javascript">
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	getTypeA("typeId");
	//setTimeout("$('#typeId').val(${typeId});", 100); set in method: getTypeA 
	//$("#typeId").change(function(){ getGroup("typeId", "groupId") });
	//$("#policyNumber").val("${policyNumber}");
});
/**
 * get group type
 */
function getTypeA(type, group, typeId, groupId) {
	$.post(path + "/group!getType.action", function(data) {
		$("#" + type).empty();
		$("#" + type).append("<option value='' ><fmt:message key='Total' /></option>");
		$("#" + group).empty();
		$("#" + group).append("<option value='' ><fmt:message key='Total' /></option>");
		var sel = "";
		for(i in data) {
			if(i == typeId) {
				sel = i;
				//$("#" + type).append("<option selected='selected' value='" + i + "'>" + data[i] + "</option");
			}
			$("#" + type).append("<option value='" + i + "'>" + data[i] + "</option");
		}
		$("#" + type).val(sel);
		setTimeout("$('#typeId').val(${typeId});", 100);
		getGroup(type, group, groupId);
	}, "json");
}
/**/
function getPage(page) {
	$("#page").val(page);
	$("#statisForm").attr('action','report-list!groupDownStatus.action');
	$("#statisForm").submit();
}
/**
 * 

function page(page) {
	location = "report-list!groupDownStatus.action?page=" + page;
} */
/**
 * export report
 */
function exportData() {
	var url = path + "/report-list!exportDownStatus.action";
	$('#statisForm').attr('action',url);
	$('#statisForm').submit();
}
</script>
<div class="operate">
<form id="statisForm" action="report-list!groupDownStatus.action" method="post">
<div class="btn">
	<fmt:message key="top.level" />:<select id="typeId" name="typeId">
		<option value="0"><fmt:message key="Select" /></option></select>
	<!--<fmt:message key="second.level" />:<select id="groupId" name="groupId">
	<option value="0"><fmt:message key="Select" /></option></select>-->
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
	link="javascript:getPage({p})"></yun:pageLink>
</div>
</div>
<table class="linetable">
<thead>
<tr>
	<th><fmt:message key="policy.version" /></th>
	<th><fmt:message key="stb.download.finished.num" /></th>
	<th><fmt:message key="stb.num" /></th>
	<th><fmt:message key="stb.download.finished.percent" /></th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="report" items="${downStatusList}">
	<tr>
	<td>${report.policyNumber }</td>
	<td>${report.finishedStb}</td>
	<td>${report.finishedStb + report.notfinishedStb}</td>
	<td>${(report.finishedStb/(report.finishedStb + report.notfinishedStb))*100 }%</td>
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

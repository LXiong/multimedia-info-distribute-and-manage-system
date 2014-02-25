<%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%><%@include file="taglib.jsp" %><%@include file="stb-js.jsp" 
%><script type="text/javascript">
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	
	$("#typeSel").change(function(){ getGroup("typeSel", "groupSel") });
	$("#typeId").change(function(){ getGroup("typeId", "groupId", ${groupId}) });
	getType("typeId", "groupId", ${typeId}, ${groupId});
});
/**
 * 
 */
function getPage(page) {
	$("#page").val(page);
	$("#queryForm").attr('action','stb!statusReport.action');
	$("#queryForm").submit();
}

/**
 * export report
 */
function exportData() {
	var url = path + "/stb!exportStatusReport.action";
	$('#queryForm').attr('action',url);
	$('#queryForm').submit();
}
</script>
<!-- query conditions -->
<div  class="operate">
<form id="queryForm" method="post" action="stb!statusReport.action" >
<div class="btn">
	<label><fmt:message key="top.level" /></label>
	<select id="typeId" name="typeId"><option value="" ><fmt:message key="Total" /></option></select>
	<label><fmt:message key="second.level" /></label>
	<select id="groupId" name="groupId"><option value="" ><fmt:message key="Total" /></option></select>
	<label>MAC</label>
	<input id="stbMac" name="stbMac" value="${stbMac }" size="15px">
    <button class="btn1_64" onclick="getPage(0);"><fmt:message key="Query" /></button>
	&nbsp;||&nbsp;
	<button onclick="exportData();" class="btn_64"><fmt:message key='export.data' /></button>
	<input type="hidden" id="page" name="page" value="0" ></input>
</div>
</form>
<div class="page">
    <yun:pageLink totalPage="${pageNums}" currentPage="${page}" 
    dispCount="5" link="###" middle="true" onclick="getPage({p})"/>
</div>
</div>
<!-- List -->
<div id="tbl">
	<table id="table" class="linetable">
		<thead><tr>
			<th><fmt:message key='top.level' /></th><th><fmt:message key='second.level' /></th>
			<th><fmt:message key='Mac' /></th>
			<%-- <th><fmt:message key='package.name' /></th> --%>
			<th><fmt:message key='cup.use' /></th>
			<th><fmt:message key='mem.use' /></th><th><fmt:message key='disk.use' /></th>
		</tr></thead>
		<tbody><c:forEach var="stb" items="${stbs}" ><tr>
			<td>${stb.typeName }</td><td>${stb.groupName }</td>
			<td>${stb.stbMac }</td>
			<%-- <td>${stb.packageName }</td> --%>
			<td>
				<c:if test="${stb.cpu > 0}">${stb.cpu }%</c:if>
			</td>
			<td>
				<c:if test="${stb.nmem > 0}">${stb.nmem }%</c:if>
			</td>
			<td>
				<c:if test="${stb.disk > 0}">${stb.disk }%</c:if>
			</td>
		</tr></c:forEach></tbody>
	</table>
	<div class="operate">
		<div class="page">
			<yun:pageLink totalPage="${pageNums}" currentPage="${page}" 
			    dispCount="5" link="###" middle="true" onclick="getPage({p})"/>
		</div>
	</div>
</div>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<div align="center">
<table class="linetable">
<thead><tr>
	<th>一级分组</th>
	<th>二级分组</th>
	<th>策略版本</th>
	<th>策略名称</th>
	<th>有效时间</th>
	<!-- <th>结束时间</th> -->
	<th>创建时间</th>
	<th>更新时间</th>
	<!-- <th>文件路径</th> -->
	<th>文件大小</th>
	<!-- <th>MD5</th> -->
	<th>操作</th>
</tr></thead>
<tbody id="tbody">
<c:forEach var="policy" items="${policies}">
	<tr>
		<td>${policy.typeName  }</td>
		<td>${policy.groupName  }</td>
		<td>${policy.policyNumber  }</td>
		<td>${policy.policyName  }</td>
		<td>
			<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${policy.startTime }"  />&nbsp;to&nbsp;
			<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${policy.endTime }"  />
		</td>
		<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${policy.createdAt }"  /></td>
		<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${policy.updatedAt }"  /></td>
		<%-- <td>${policy.filePath  }</td> --%>
		<td>${policy.sizeBytes  }</td>
		<%-- <td>${policy.md5  }</td> --%>
		<td><a href="javascript:detail('${policy.policyId }', '${policy.policyNumber }')">详情</a></td>
	</tr>
</c:forEach>
</tbody>
<tfoot id="tfoot">
</tfoot>
</table>
</div>   
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

function detail(policyId, policyNumber){
	location="policy!detail.action?policyId=" + policyId + "&policyNumber=" + policyNumber;
}

/* function downStatus(policyId, policyNumber) {
	window.location="policy-download-status.action?policyNumber=" + policyNumber + 
		"&policyId=" + policyId;
} */
</script>
	
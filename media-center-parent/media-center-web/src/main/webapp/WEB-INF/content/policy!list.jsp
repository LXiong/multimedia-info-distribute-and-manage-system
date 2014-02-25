<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@include file="taglib.jsp" %>
    <div align="center">
<table class="linetable">
<thead><tr>
	<th>策略文件名称</th>
	<th>起始生效时间</th>
	<th>结束时间</th>
	<th>创建时间</th>
	<th>更新时间</th>
	<th>文件路径</th>
	<th>文件大小</th>
	<th>MD5</th>
	<th>操作</th></tr>
</thead>
<tbody id="tbody">
<c:forEach var="policy" items="${policies}">
	<tr>
	<td>${policy.policy_number  }</td>
	<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${policy.beginAt }"  /></td>
	<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${policy.endAt }"  /></td>
	<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${policy.createAt }"  /></td>
	<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${policy.update_time }"  /></td>
	<td>${policy.file_path  }</td>
	<td>${policy.size_bytes  }</td>
	<td>${policy.md5  }</td>
	<td><a href="javascript:detail('${policy.policyId }', '${policy.policy_number }')">详情</a>
		<!--<a href="###" onclick="downStatus('${policy.policyId }', '${policy.policy_number }')">
			<fmt:message key='policy.media.download.status' /></a>-->
	</td>
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

function detail(policyid, policyNumber){
	location="policy!detail.action?policyid="+policyid + "&policyNumber=" + policyNumber;
}

function downStatus(policyId, policyNumber) {
	window.location="policy-download-status.action?policyNumber=" + policyNumber + 
		"&policyId=" + policyId;
}
</script>
	
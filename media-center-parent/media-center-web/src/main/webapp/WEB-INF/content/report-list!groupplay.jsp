<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%> <%@include file="stb-js.jsp" %>
<div class="operate">
<form id="statisForm" action="report-list!groupPlayList.action" method="post" onsubmit="return check()">
<div class="btn">
	<fmt:message key="top.level" />:<select id="typeId" name="typeId">
		<option value="" ><fmt:message key="Total" /></option></select>
	<fmt:message key="second.level" />:<select id="groupId" name="groupId">
		<option value="" ><fmt:message key="Total" /></option></select>
	<label><fmt:message key="start.end.time" /></label>
	<input type="text" readonly="readonly"  value="<fmt:formatDate value='${dateinput}' pattern='yyyy-MM-dd'/>" id="dateinput" name="dateinput" class="date dateinput" size="14" ></input>
	---
	<input type="text" readonly="readonly" value="<fmt:formatDate value='${dateinput2}' pattern='yyyy-MM-dd'/>" id="dateinput2" name="dateinput2" class="date dateinput" size="14" ></input>
	<button class="btn1_64" onclick="getPage(0);"><fmt:message key="statistics" /></button>
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
	<th>媒体文件名</th>
	<th>播放次数</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="play" items="${list}">
	<tr>
	<td>${play.videoName }</td>
	<td>${play.count}</td>
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

<script type="text/javascript">
$(document).ready(function() {
	$(".dateinput").datepicker({
		showOn: "button",
		buttonImage: "images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "yy-mm-dd"
	});
	setPath("<%=request.getContextPath() %>");
	$("#typeId").change(function(){ getGroup("typeId", "groupId") });
	getType("typeId", "groupId", ${typeId}, ${groupId});
});

function getPage(page) {
	$("#page").val(page);
	$("#statisForm").attr('action','report-list!groupPlayList.action');
	$("#statisForm").submit();
}

function check(){
	if($("#typeId>option:selected").val()==""){
			alert("请先选择分组");
			return false;
	}
	if($("#groupId>option:selected").val()==""){
			alert("请选择二级分组");
			return false;
	}
	return true;
}
</script>
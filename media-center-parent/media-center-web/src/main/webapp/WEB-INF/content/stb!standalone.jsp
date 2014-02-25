<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%> <%@include file="stb-js.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
	$(".dateinput").datepicker({
		showOn: "button",
		buttonImage: "images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "yy-mm-dd"
	});
	setPath("<%=request.getContextPath() %>");
	//getType("chgType", "chgGroup");
	//$("#chgType").change(function(){ getGroup("chgType", "chgGroup") });
});
function check(){
	if($("#dateinput").val()=="" && $("#dateinput2").val()==""){
			alert("请先选择时间段");
			return false;
	}
	return true;
}
</script>
<div class="operate">
<form action="stb!standAlone.action" method="post" onsubmit="return check()">
<div class="btn">
	MAC:<input type="text" name="mac" value="${mac }">
	开始
	<input type="text" readonly="readonly"  value="<fmt:formatDate value='${dateinput}' pattern='yyyy-MM-dd'/>" id="dateinput" name="dateinput" class="date dateinput" size="14" ></input>
	结束:
	<input type="text" readonly="readonly" value="<fmt:formatDate value='${dateinput2}' pattern='yyyy-MM-dd'/>" id="dateinput2" name="dateinput2" class="date dateinput" size="14" ></input>
	<input type="submit" class="btn1_64" value="统计">
</div>
</form>
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

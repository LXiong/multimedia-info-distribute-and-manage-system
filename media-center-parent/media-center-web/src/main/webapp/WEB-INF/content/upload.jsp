<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<script type="text/javascript">
function check() {
	if($.trim($("#fileSel").val()) == ""){
		return false;
	}
}
</script>
<div id="upload" align="center" style="font-size: 12px">
	<hr align="center"></hr>
	<form action="upload!uploadFile.action" method="post" enctype="multipart/form-data" onsubmit="return check();">
		<s:file id="fileSel" name="file" value=""><fmt:message key="FileSelect" />:&nbsp;</s:file>
		<button class="btn_64"><fmt:message key="Submit"/></button>
	</form>
	<hr align="center"></hr>
</div>
${request.msg }
<br></br>
<div><table>
	<c:forEach var="item" items="${request.list }" >
		<tr><td>${item[0] }</td><td>${item[1] }${item[2] }${item[3] }${item[4] }</td>
		<td>${item[5] }</td><td>${item[6] }</td><td><fmt:message key="${item[11] }" /></td></tr>
	</c:forEach>
</table></div>
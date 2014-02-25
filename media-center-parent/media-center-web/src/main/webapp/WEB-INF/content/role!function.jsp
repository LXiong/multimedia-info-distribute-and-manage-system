<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<input type="hidden"  name="roleId" id="roleId" value="${roleId }">
<div align="center">
<table>
<tr><th>已有的功能</th><th>&nbsp;</th><th>没有的功能</th></tr>
<tr><td>
<select name="ids"  id="s1" multiple  style="width: 100px;height: 130px">
	<c:forEach items="${function}"  var="function">
			<c:if test="${function.key ne 'Basic' }">
				<option value="${function.key}">${function.value}</option>
			</c:if>
	</c:forEach>
</select>
</td>
<td><input type="button" onclick="Left2Right()" value="&gt;&gt;" /><br />
<br />
    <input type="button" onclick="Right2Left()" value="&lt;&lt;" /></td>
<td>
<select name="s2" id="s2" multiple  style="width: 100px;height: 130px">
<c:forEach items="${list }" var="list">
	<c:if test="${list.key ne 'Basic' }">
		<option value="${list.key }">${list.value}</option>
	</c:if>
</c:forEach>
</select>
</td>
<tr><td colspan="3" align="center">
	<input type="button" onclick="saveRoleFunction()"  value="保存">&nbsp;&nbsp;
	<input type="button" onclick="javascript:history.back()"  value="返回">
</td></tr>
</table>
</div>

<script type="text/javascript">
onload=function(){
}

function Left2Right() {
     $("#s1>option:selected").remove().appendTo($("#s2"));
}

function Right2Left() {
    $("#s2>option:selected").remove().appendTo($("#s1"));
}

function saveRoleFunction() {
	url="role!saveFunctions.action?ids=-999";
	var roleId = $("#roleId").val();
	for(i=0; i<$("#s1>option").size(); i++){
		url = url + "," + $("#s1>option").eq(i).val();
	}
	url = url + "&roleId=" + roleId;
	location = url;
}
</script>

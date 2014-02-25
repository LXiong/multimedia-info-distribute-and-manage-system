<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%> <%@include file="taglib.jsp" %>
<input type="hidden"  name="userId" id="userId" value="${userId }">
<div align="center">
<table>
<tr><th>已有的角色</th><th>&nbsp;</th><th>没有的角色</th></tr>
<tr>
	<td>
	<select name="ids"  id="s1" multiple  style="width: 100px;height: 130px">
	<c:forEach var="role" items="${roles }">
		<option  value="${role.roleId }">${role.roleName }</option>
	</c:forEach>
	</select>
	</td>
<td>
	<input type="button" onclick="Left2Right()" value="&gt;&gt;" />
	<br />
	<br />
    <input type="button" onclick="Right2Left()" value="&lt;&lt;" />
</td>
<td>
	<select name="s2" id="s2" multiple style="width: 100px;height: 130px">
	<c:forEach var="notrole" items="${notroles }">
		<option value="${notrole.roleId }">${notrole.roleName }</option>
	</c:forEach>
	</select>
</td>
</tr>
<tr>
<td colspan="3" align="center">
	<input  type="button"  value="保存" onclick="check()" >&nbsp;&nbsp;
	<input type="button" onclick="javascript:history.back()"  value="返回">
</td>
</tr>
</table>
</div>
<script type="text/javascript">
function Left2Right(){
     $("#s1>option:selected").remove().appendTo($("#s2"));
}

function Right2Left(){
    $("#s2>option:selected").remove().appendTo($("#s1"));
}

function check(){
	var url = "user-role!saveRole.action?roleId=-999";
	var userId = $("#userId").val();
	for(i=0; i<$("#s1>option").size(); i++) {
		url = url + "," + $("#s1>option").eq(i).val();
	}
	url = url + "&userId=" + userId;
	window.location = url;
}
</script>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<form action="role!editRole.action" onsubmit="return check()" method="post"> 
 <table class="linetable" style="width:300px;">
  <tr><th colspan="2">&nbsp;</th></tr>
	  	<tr>
	  		<td>序号: </td>
	  		<td><input type="text" name="vo.roleid" id="roleid" value="${role.roleId }" readonly="readonly"></td>
	  	</tr>
	  	<tr>
	  		<td>角色名: </td>
	  		<td><input type="text" name="vo.rolename" id="rolename" value="${role.rolename }"></td>
	  	</tr>
	  	<tr>
	  		<td>角色描述:</td>
	  		<td><input type="text" name="vo.describe" id="describe" value="${role.describe }"></td>
	  	</tr>
	  	<tr>
	  		<td colspan="2"><input type="submit" value="确认修改" style="margin-left: 150px">&nbsp;
	  			<input type="button" value="返回" onclick="javascript:history.back()">
	  		</td>
	  	</tr>
</table>
</form>
<script type="text/javascript"> 
function check(){
	if($.trim($("#rolename").val())==""){
		alert("角色名不能为空,请输入！");
		$("#rolename").focus();
		return false;
	}
	if($.trim($("#describe").val())==""){
		alert("描述不能为空,请输入！");
		$("#describe").focus();
		return false;
	}
	return true;
}
</script>
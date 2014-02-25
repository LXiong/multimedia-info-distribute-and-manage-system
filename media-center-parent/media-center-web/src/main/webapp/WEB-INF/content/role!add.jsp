<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<form name="myform" action="role!addRole.action" onsubmit="return check()" method="post">
	<table class="linetable" style="width:300px;">
	  	<tr>
	  		<td>角色名称:</td>
	  		<td><input type="text" name="vo.rolename" id="rolename"></td>
	  	</tr>
	  	<tr>
	  		<td>角色描述:</td>
	  		<td><input type="text" name="vo.describe" id="describe"></td>
	  	</tr>
	  	<tr>
	  		<td><input type="reset"></td>
	  		<td><input type="submit" value="添加">
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
		alert("角色描述不能为空,请输入！");
		$("#describe").focus();
		return false;
	}
		return true;
}
</script>

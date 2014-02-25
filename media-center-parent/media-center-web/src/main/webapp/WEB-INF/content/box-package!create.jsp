<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<form name="myform" action="box-package!create2.action" onsubmit="return check()" method="post">
	<table class="linetable" style="width:300px;">
	  	<tr>
	  		<td>软件包名称</td>
	  		<td><input type="text" name="vo.boxname" id="boxname"></td>
	  	</tr>
	  	<tr>
	  		<td>软件包描述:</td>
	  		<td><textarea name="vo.description" id="description"></textarea></td>
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
	if($.trim($("#boxname").val())==""){
			alert("软件包名称不能为空,请输入！");
			$("#boxname").focus();
			return false;
		}
	if($.trim($("#description").val())==""){
		alert("软件包描述不能为空,请输入！");
		$("#description").focus();
		return false;
	}
		return true;
}
</script>
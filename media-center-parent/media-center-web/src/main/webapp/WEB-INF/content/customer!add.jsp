<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<form name="myform" action="customer!addCustomer.action" onsubmit="return check()" method="post">
 <table class="linetable" style="width:300px;">
  <tr><th colspan="2">&nbsp;</th></tr>
	  	<tr>
	  		<td>客户名称:</td>
	  		<td><input type="text" name="vo.custname" id="custname"></td>
	  	</tr>
	  	<tr>
	  		<td>客户地址</td>
	  		<td><input type="text" name="vo.address" id="address"></td>
	  	</tr>
	  	<tr>
	  		<td>联系方式</td>
	  		<td><input type="text" name="vo.telephone" id="telephone" onkeydown="onlynum()" size="21"></td>
	  	</tr>
	  	<tr>
	  		<td><input type="reset"></td>
	  		<td><input type="submit" value="确认添加">
	  			<input type="button" value="返回" onclick="javascript:history.back()">
	  		</td>
	  	</tr>
	  	</table>
  	</form>
<script type="text/javascript"> 
function onlynum()
{
	if(!(event.keyCode>=49&&event.keyCode<=57) && event.keyCode!=8 && event.keyCode!=9)
	{
		event.returnValue=false
	}
}
function check(){
	if($.trim($("#custname").val())==""){
		alert("名称不能为空,请输入！");
		$("#custname").focus();
		return false;
	}
	if($.trim($("#address").val())==""){
		alert("地址不能为空,请输入！");
		$("#address").focus();
		return false;
	}
	
	if($.trim($("#telephone").val())==""){
		alert("密码不能为空,请输入！");
		$("#telephone").focus();
		return false;
	}
	return true;
}
</script>
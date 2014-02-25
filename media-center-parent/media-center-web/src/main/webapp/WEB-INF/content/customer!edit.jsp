<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
 <form action="customer!edit2.action" onsubmit="return check()" method="post">
 <table class="linetable" style="width:300px;">
  <tr><th colspan="2">&nbsp;</th></tr>
	  	<tr>
	  		<td>序号: </td>
	  		<td><input type="text" name="vo.custId" id="custId" value="${customer.custId }" readonly="readonly"></td>
	  	</tr>
	  	<tr>
	  		<td>客户名称: </td>
	  		<td><input type="text" name="vo.custname" id="custname" value="${customer.custname }"></td>
	  	</tr>
	  	<tr>
	  		<td>客户地址</td>
	  		<td><input type="text" name="vo.address" id="address" value="${customer.address }"></td>
	  	</tr>
	  	<tr>
	  		<td>联系方式</td>
	  		<td><input type="text" onkeydown="onlynum()" name="vo.telephone" id="telephone" value="${customer.telephone }"></td>
	  	</tr>
	  	<tr>
	  		<td colspan="2"><input type="submit" value="确认修改" style="margin-left: 150px">
	  			<input type="button" value="返回" onclick="javascript:history.back()">
	  		</td>
	  	</tr>
	  	</table>
	  </form>


<script type="text/javascript"> 
function check(){
	if(jQuery.trim($("#custname").val())==""){
		alert("客户名称不能为空,请输入！");
		$("#custname").focus();
		return false;
	}
	if($.trim($("#address").val())==""){
		alert("地址不能为空,请输入！");
		$("#address").focus();
		return false;
	}
	if($.trim($("#telephone").val())==""){
		alert("联系方式不能为空,请输入！");
		$("#telephone").focus();
		return false;
	}
	return true;
}
function onlynum()
{
	if(!(event.keyCode>=49&&event.keyCode<=57) && event.keyCode!=8 && event.keyCode!=9)
	{
		event.returnValue=false
	}
}
</script>









    
    
    

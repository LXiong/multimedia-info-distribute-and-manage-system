<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<head>
 	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/chengongyu/updatetable.css">
 </head>
 
 <table style="margin-top: 50px"><!-- border="1" bordercolor="#99ccff" -->
	  	<tr>
	  		<td class="left">原始密码: </td>
	  		<td  class="right"><input type="password" name="pass" id="pass"></td>
	  	</tr>
	  	<tr>
	  		<td class="left">新密码: </td>
	  		<td  class="right"><input type="password" name="newpass" id="newpass"></td>
	  	</tr>
	  	<tr>
	  		<td class="left">再次输入密码:</td>
	  		<td  class="right"><input type="password" name="repass" id="repass"></td>
	  	</tr>
	  	<tr>
	  		<td colspan="2"><input type="button" value="修改密码" style="margin-left: 150px" onclick="check()">
	  			<input type="button" value="返回" onclick="javascript:history.back()">
	  			
	  			
	  		</td>
	  	</tr>
</table>

<script type="text/javascript"> 
function check(){
if($("#pass").val()==""){
	alert("原始密码不能为空，请输入！");
	$("#pass").focus();
	return;
}
if($("#newpass").val()==""){
	alert("新密码不能为空，请输入！");
	$("#loginname").focus();
	return;
}
if($("#repass").val()!=$("#newpass").val()){
	alert("两次密码不一致，请重新输入！");
	$("#loginname").focus();
	return;
}		
	location="user!updatePassword2.action?pass="+$("#pass").val()+"&newpass="+$("#newpass").val();
}
</script>

    
    
    

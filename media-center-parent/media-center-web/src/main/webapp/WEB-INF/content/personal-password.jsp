<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<s:form action="personal!password" method="post" onsubmit="return check()">
	<s:actionmessage/>
	<s:actionerror/>
<table class="linetable" style="width:300px">
  	<tr>
  		<td>原始密码: </td>
  		<td><input type="password" name="oldpass" id="pass"></td>
  	</tr>
  	<tr>
  		<td>新密码: </td>
  		<td><input type="password" name="newpass" id="newpass"></td>
  	</tr>
  	<tr>
  		<td>再次输入密码:</td>
  		<td><input type="password" name="repass" id="repass"></td>
  	</tr>
  	<tr>
  		<td colspan="2">
  		<s:submit key="Submit" cssStyle="margin-left: 150px"></s:submit>
  		</td>
  	</tr>
</table>
</s:form>
<script type="text/javascript"> 
function check(){
	if($("#pass").val()==""){
		alert("原始密码不能为空，请输入！");
		$("#pass").focus();
		return false;
	}
	if($("#newpass").val()==""){
		alert("新密码不能为空，请输入！");
		$("#newpass").focus();
		return false;
	}
	if($("#newpass").val().length<4){
		alert("新密码不能小于4位字符！");
		$("#newpass").focus();
		return false;	
	}
	if($("#repass").val()!=$("#newpass").val()){
		alert("两次密码不一致，请重新输入！");
		$("#newpass").focus();
		return false;
	}
	//location="user!changePassword.action?pass="+$("#pass").val()+"&newpass="+$("#newpass").val();
	return true;
}
</script>

    
    
    

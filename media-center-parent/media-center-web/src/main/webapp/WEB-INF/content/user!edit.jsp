<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
 <form action="user!edit2.action" onsubmit="return check()" method="post">
 <table class="linetable" style="width:300px;">
  <tr><th colspan="2">&nbsp;</th></tr>
	  	<tr>
	  		<td>序号: </td>
	  		<td><input type="text" name="vo.userId" id="userid" value="${user.userId }" readonly="readonly"></td>
	  	</tr>
	  	<tr>
	  		<td>用户名: </td>
	  		<td><input type="text" name="vo.loginname" id="loginname" value="${user.loginname }"></td>
	  	</tr>
	  	<tr>
	  		<td>实名:</td>
	  		<td><input type="text" name="vo.username" id="username" value="${user.username }"></td>
	  	</tr>
	  	<tr>
	  		<td>电话:</td>
	  		<td><input type="text" name="vo.telephone" id="telephone" value="${user.telephone }"></td>
	  	</tr>
	  	<tr>
	  		<td>Email:</td>
	  		<td><input type="text" name="vo.email" id="email" value="${user.email }"></td>
	  	</tr>
	  	<tr>
	  		<td>密码:</td>
	  		<td><input type="password" name="newpassword" id="newpassword" size="21"></td>
	  	</tr>
	  	<tr>
	  		<td>再次输入密码:</td>
	  		
	  		<td><input type="password" name="newpassword2" id="newpassword2" size="21"></td>
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
	if(jQuery.trim($("#loginname").val())==""){
		alert("用户名不能为空,请输入！");
		$("#loginname").focus();
		return false;
	}
	if($.trim($("#loginname").val()).length<1){
		alert("用户名长度不能小于1个字符");
		$("#loginname").focus();
		return false;
	}
	if($.trim($("#username").val())==""){
		alert("实名不能为空,请输入！");
		$("#username").focus();
		return false;
	}
	if($.trim($("#username").val()).length<2){
		alert("实名不能小于两个字符！");
		$("#username").focus();
		return false;
	}
	var charMatch = $.trim($("#username").val()).match(/[\u4e00-\u9fa5]/g);
	var charNum = charMatch==null ? 0 :charMatch.length;
	if(charNum==0){
		alert("实名请输入汉字");
		$("#username").focus();
		$("#username").val("");
		return false;
	}
	if($.trim($("#telephone").val())==""){
		alert("联系方式不能为空,请输入！");
		$("#telephone").focus();
		return false;
	}
	if($.trim($("#telephone").val()).match(/^[0-9]*$/)==null){
		alert("联系方式请输入数字");
		$("#telephone").val("");
		return false;
	}
	if($.trim($("#email").val())==""){
		alert("邮箱不能为空,请输入！");
		$("#email").focus();
		return false;
	}
	if($("#email").val().match(/^\w+[@]\w+[.]\w+$/)==null){
        alert("您的电子邮件格式错误,请重新输入！");
        $("#email").focus();
		 return false;
	}
	if($("#newpassword").val()!=$("#newpassword2").val()){
		alert("两次密码输入不一样,请重新输入！");
		$("#newpassword").focus();
		return false;
	}
	return true;
}
</script>









    
    
    

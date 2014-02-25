<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<form name="myform" action="user!addUser.action" onsubmit="return check()" method="post">
 <table class="linetable" style="width:300px;">
  <tr><th colspan="2">&nbsp;</th></tr>
	  	<tr>
	  		<td>用户名:</td>
	  		<td><input type="text" name="vo.loginname" id="loginname" onblur="post()"></td>
	  	</tr>
	  	<tr>
	  		<td><input type="button" value="检查账号是否存在" onclick="number()"></td>
	  		<td>
	  			<input id="tt" style="border: 0px;color: red" readonly="readonly" >
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>实名:</td>
	  		<td><input type="text" name="vo.username" id="username"></td>
	  	</tr>
	  	<tr>
	  		<td>密码:</td>
	  		<td><input type="password" name="vo.password" id="password" size="21"></td>
	  	</tr>
	  	<tr>
	  		<td>再次输入密码:</td>
	  		
	  		<td><input type="password" name="repassword" id="repassword" size="21"></td>
	  	</tr>
	  	<tr>
	  		<td>电话:</td>
	  		<td><input type="text" name="vo.telephone" id="telephone"></td>
	  	</tr>
	  	
	  	<tr>
	  		<td>Email:</td>
	  		<td><input type="text" name="vo.email" id="email"></td>
	  	</tr>
	  	<tr>
	  		<td><input type="reset"></td>
	  		<td><input type="submit" value="注册">
	  		<input type="button" value="返回" onclick="javascript:history.back()">
	  		</td>
	  	</tr>
	  	</table>
  	</form>
<script type="text/javascript">
var status=false;
function check(){
	if($.trim($("#loginname").val())==""){
		alert("用户名不能为空,请输入！");
		$("#loginname").focus();
		return false;
	}
	if($.trim($("#loginname").val()).length<4){
		alert("用户名长度不能小于4个字符");
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
	if($.trim($("#password").val())==""){
		alert("密码不能为空,请输入！");
		$("#password").focus();
		return false;
	}
	if($.trim($("#password").val()).length<4){
		alert("密码不能小于4个字符");
		$("#password").focus();
		return false;
	}
	if($("#repassword").val()!=$("#password").val()){
		alert("两次密码输入不一样,请重新输入！");
		$("#repassword").focus();
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
			$("#telephone").focus();
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
		if(status==false){
			alert("您的账号已被使用,每次更改后请先点击按钮查询");
			return false;
		}
		return true;
}
function number(){
	var name=$.trim($("#loginname").val());
	if(name==""){
		alert("用户名不能为空,请先输入！");
		$("#loginname").focus();
		return;
	}
	$.post("user!check.action",{"loginname":name},function(data){
		if(data=="true"){
			$("#tt").val("恭喜您,账号可以使用");
			status = true;
		}else{
			$("#tt").val("很抱歉,账号已经注册");
			status = false;
		}
	});	
	}
function post(){
	var name=$.trim($("#loginname").val());
	$.post("user!check.action",{"loginname":name},function(data){
					if(data=="true"){
						status = true;
					}else{
						status = false;
					}
				});	
}
</script>
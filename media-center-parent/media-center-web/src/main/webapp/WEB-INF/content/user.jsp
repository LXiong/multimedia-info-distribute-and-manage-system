<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<div class="operate">
	<button onclick="showAddDlg();" class="btn_117"><fmt:message key="user.add" /></button>
	<button onclick="showEditDlg();" class="btn_117"><fmt:message key="user.edit" /></button>
	<button onclick="delUser();" class="btn_117"><fmt:message key="user.delete" /></button>
	<button onclick="visitStbPower();" class="btn_117"><fmt:message key="user.visit.stb" /></button>
	<button onclick="userRoleEdit();" class="btn_117"><fmt:message key="user.role.edit" /></button>
</div>
<!-- List -->
<div id="divUsers"></div>
<!-- Add user div -->
<div id="divAddUser" >
	<div id="addMsg" align="left" style="color: red; font-size: 12px" ></div>
	<table id="tblAddUser" style="width: 100%">
		<tr><td><fmt:message key='user.id' /></td>
			<td><input id="newId" name="newId" onchange="newUserIdCheck();" /></td>
			<td><div id="divUserIdTip">For login, you cannot change it after register.</div></td>
		</tr>
		<tr><td><fmt:message key='user.password' /></td>
			<td><input type="password" id="newPW" name="newPW" /></td>
		</tr>
		<tr><td><fmt:message key='user.password.again' /></td>
			<td><input type="password" id="newPW1" name="newPW1" /></td>
		</tr>
		<tr><td><fmt:message key='user.name' /></td>
			<td><input id="newName" name="newName" /></td>
		</tr>
		<tr><td><fmt:message key='user.tele' /></td>
			<td><input id="newTele" name="newTele" /></td>
		</tr>
		<tr><td><fmt:message key='user.email' /></td>
			<td><input id="newEmail" name="newEmail" /></td>
		</tr>
	</table>
</div>
<!-- Edit user div -->
<div id="divEditUser" >
	<div id="msg" align="left" style="color: red; font-size: 12px" ></div>
	<table id="tblEditUser" style="width: 100%">
		<tr><td><fmt:message key='user.id' /></td>
			<td><input id="loginName" name="loginName" readonly="readonly" /></td>
		</tr>
		<tr><td><fmt:message key='user.name' /></td>
			<td><input id="userName" name="userName" /></td><!-- onblur="check('userName', 'msg');" -->
		</tr>
		<tr><td><fmt:message key='user.tele' /></td>
			<td><input id="telephone" name="telephone" /></td>
		</tr>
		<tr><td><fmt:message key='user.email' /></td>
			<td><input id="email" name="email" /></td>
		</tr>
	</table>
</div>
<script type="text/javascript">
var flag = false; // the value is true if the new user id is valid.

$(document).ready(function() {
	// add new user dialog
	$("#divAddUser").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true,
		title: "<fmt:message key='user.add' />", 
		buttons: [
	  		{
		  		text: "<fmt:message key='Submit' />", 
		  		click: function() { if(addCheck()){addNewUser();} }
	  		}, 
	  		{
		  		text: "<fmt:message key='Cancel' />", 
		  		click: function() { $('#divAddUser').dialog('close'); }
	  		}
	  		]
		}
	);
	// Edit dialog
	$("#divEditUser").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true,
		title: "<fmt:message key='user.edit' />", 
		buttons: [
	  		{
		  		text: "<fmt:message key='save' />", 
		  		click: function() { if(editCheck()){updateUser();} }
	  		}, 
	  		{
		  		text: "<fmt:message key='Cancel' />", 
		  		click: function() { $('#divEditUser').dialog('close'); }
	  		}
	  		]
		}
	);
	// users list
	pageInit();
});

var grid = null;
/**
 * user list init
 */
function pageInit() {
	var jsonObj = {};
	jsonObj.Rows = ${jsonStr};
	grid = $("#divUsers").ligerGrid(
		{
			url : "user.action",
			columns : [
					{
						display : "<fmt:message key='user.id' />",
						width : 80,
						name : 'loginName',
						align : 'left'
					},
					{
						display : "<fmt:message key='user.name' />",
						minWidth : 120,
						name : 'userName',
						align : 'left'
					},
					{
						display : "<fmt:message key='user.tele' />",
						name : 'telephone',
						align : 'left',
						minWidth : 200
					},
					{
						display : "<fmt:message key='user.email' />",
						width : 120,
						name : 'email',
						align : 'left'
					},
					{
						display : "<fmt:message key='user.last.login.time' />",
						width : 120,
						name : 'lastLoginTime',
						align : 'left',
						render: function (row)
						{
							//var time = new Date(row.createTime);
							//dateFormat(time, "yyyy-MM-dd hh:mm");
							return new Date(row.lastLoginTime);
						}
					} ],
			data : jsonObj,
			pageSize : 20,
			checkbox : false,
			dataAction : 'server',
			sortName : 'loginname',
			width : '100%',
			height : '100%'
		}
	);
}

/**
 * show add dialog
 */
function showAddDlg() {
	$("#addMsg").empty();
	$("#divAddUser").dialog( "open" );
	$("#divAddUser").dialog( "option", "height", 270 );
	$("#divAddUser").dialog( "option", "width", 520 );
}

/**
 * add a new user
 */
function addNewUser() {
	var url = "user!create.action";
	var params = {"loginName":$("#newId").val(), "userName":$("#newName").val(), 
			"password":$("#newPW").val(), "telephone":$("#newTele").val(), 
			"email":$("#newEmail").val()};
	$.post(url, params, function() {
		$("#divAddUser").dialog( "close" );
		window.location = "user.action";
	});
}

/**
 * check the new user id
 */
function newUserIdCheck() {
	if($.trim($("#newId").val())==""){
		alert("用户名不能为空,请输入！");
		$("#newId").focus();
		return false;
	}
	if($.trim($("#newId").val()).length < 4){
		alert("用户名长度不能小于4个字符");
		$("#newId").focus();
		return false;
	}
	$.post("user!check.action", {"loginName":$("#newId").val()}, function(data){
		if(data == "false"){
			$("#divUserIdTip").empty();
			$("#divUserIdTip").append("很抱歉,账号已经注册");
			flag = false;
		} else {
			$("#divUserIdTip").empty();
			$("#divUserIdTip").append("For login, you cannot change it after register.");
			flag = true;
		}
	});
	return true;
}

/**
 * check input
 */
function addCheck() {
	// check the user login name
	if(newUserIdCheck()) {
		if(!flag) {
			return false;
		}
	} else {
		return false;
	}
	// check the password
	if($.trim($("#newPW").val())==""){
		alert("密码不能为空,请输入！");
		$("#newPW").focus();
		return false;
	}
	if($.trim($("#newPW").val()).length < 4){
		alert("密码不能小于4个字符");
		$("#newPW").focus();
		return false;
	}
	if($("#newPW1").val() != $("#newPW").val()){
		alert("两次密码输入不一样,请重新输入！");
		$("#newPW1").focus();
		return false;
	}
	/* if($.trim($("#newName").val())==""){
		alert("实名不能为空,请输入！");
		$("#newName").focus();
		return false;
	} */
	if($.trim($("#newName").val()).length<2){
		alert("实名不能小于两个字符！");
		$("#newName").focus();
		return false;
	}
	var charMatch = $.trim($("#newName").val()).match(/[\u4e00-\u9fa5]/g);
	var charNum = charMatch==null ? 0 :charMatch.length;
	if(charNum==0){
		alert("实名请输入汉字");
		$("#newName").focus();
		$("#newName").val("");
		return false;
	}
	/*if($.trim($("#newTele").val())==""){
		alert("联系方式不能为空,请输入！");
		$("#newTele").focus();
		return false;
	}*/
	if($.trim($("#newTele").val()).match(/^[0-9]*$/)==null){
			alert("联系方式请输入数字");
			$("#newTele").val("");
			$("#newTele").focus();
			return false;
	}
	/* if($.trim($("#newEmail").val())==""){
		alert("邮箱不能为空,请输入！");
		$("#newEmail").focus();
		return false;
	} */
	if($("#newEmail").val().match(/^\w+[@]\w+[.]\w+$/)==null){
        alert("您的电子邮件格式错误,请重新输入！");
        $("#newEmail").focus();
		 return false;
	}
	return true;
}

/**
 * show edit user dialog
 */
function showEditDlg() {
	var row = grid.getSelectedRow();
	if(row == null) {
		alert("Please select a user for edit!");
		return;
	}
	$("#msg").empty();
	$("#divEditUser").dialog( "open" );
	$("#divEditUser").dialog( "option", "height", 200 );
	$("#divEditUser").dialog( "option", "width", 400 );
	
	$("#loginName").val(row.loginName);
	$("#userName").val(row.userName);
	$("#telephone").val(row.telephone);
	$("#email").val(row.email);
}

/**
 * update user
 */
function updateUser() {
	var url = "user!update.action";
	var params = {"loginName":$("#loginName").val(), "userName":$("#userName").val(), 
			"telephone":$("#telephone").val(), "email":$("#email").val()};
	$.post(url, params, function() {
		$("#divEditUser").dialog( "close" );
		window.location = "user.action";
	});
}

/**
 * new value check
 */
function editCheck() {
	/* if($.trim($("#userName").val())==""){
		alert("实名不能为空,请输入！");
		$("#userName").focus();
		return false;
	} */
	if($.trim($("#userName").val()).length<2){
		alert("实名不能小于两个字符！");
		$("#userName").focus();
		return false;
	}
	var charMatch = $.trim($("#userName").val()).match(/[\u4e00-\u9fa5]/g);
	var charNum = charMatch==null ? 0 :charMatch.length;
	if(charNum==0){
		alert("实名请输入汉字");
		$("#userName").focus();
		$("#userName").val("");
		return false;
	}
	if($.trim($("#telephone").val()).match(/^[0-9]*$/)==null){
		alert("联系方式请输入数字");
		$("#telephone").val("");
		$("#telephone").focus();
		return false;
	}
	/* if($.trim($("#email").val())==""){
		alert("邮箱不能为空,请输入！");
		$("#email").focus();
		return false;
	} */
	if($("#email").val().match(/^\w+[@]\w+[.]\w+$/)==null){
	    alert("您的电子邮件格式错误,请重新输入！");
	    $("#email").focus();
		 return false;
	}
	return true;
}

/**
 * delete a user
 */
function delUser() {
	var row = grid.getSelectedRow();
	if(row == null) {
		alert("Please select a user for delete!");
		return;
	}
	if (confirm("You will delete the User which loginName is " + row.loginName
			+ " ?")) {
		var url = "user!del.action";
		var params = {"loginName" : row.loginName};
		$.post(url, params, function(data) {
			alert(data);
			if(data == "<fmt:message key='user.have.been.deleted' />") {
				window.location = "user.action";
			}
		});
	}
}

/**
 * 
 */
function visitStbPower() {
	var row = grid.getSelectedRow();
	if(row == null) {
		alert("Please select a user!");
		return;
	}
	window.location = 'user!visitStbPower.action?userId=' + row.userId;
}

/**
 * 
 */
function userRoleEdit() {
	var row = grid.getSelectedRow();
	if(row == null) {
		alert("Please select a user!");
		return;
	}
	window.location = "user-role!update.action?userId=" + row.userId;
}
</script>
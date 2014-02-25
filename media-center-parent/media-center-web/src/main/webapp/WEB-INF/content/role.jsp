<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<div class="operate">
	<button onclick="showAddDlg();" class="btn_117"><fmt:message key="role.add" /></button>
	<button onclick="showEditDlg();" class="btn_117"><fmt:message key="role.edit" /></button>
	<button onclick="delRole();" class="btn_117"><fmt:message key="role.delete" /></button>
	<button onclick="changeFunction();" class="btn_117" ><fmt:message key="role.function" /></button>
</div>
<!-- List -->
<div id="divRoles"></div>
<!-- Add Role div -->
<div id="divAddRole" >
	<div id="addMsg" align="left" style="color: red; font-size: 12px" ></div>
	<table id="tblAddRole" style="width: 100%">
		<tr><td><fmt:message key='role.name' /></td>
			<td><input id="newName" name="newName" /></td>
			<td><div id="divRoleNameTip">Please make it clearly.</div></td>
		</tr>
		<tr><td><fmt:message key='role.description' /></td>
			<td><input id="newDesp" name="newDesp" /></td>
		</tr>
	</table>
</div>
<!-- Edit role div -->
<div id="divEditRole" >
	<div id="msg" align="left" style="color: red; font-size: 12px" ></div>
	<table id="tblEditRole" style="width: 100%">
		<tr><td><fmt:message key='role.name' /></td>
			<td><input id="roleName" name="roleName" readonly="readonly" /></td>
			<!-- <td><div id="divRoleNameTip2">Please make it clearly.</div></td> -->
		</tr>
		<tr><td><fmt:message key='role.description' /></td>
			<td><input id="describe" name="describe" /></td>
		</tr>
	</table>
</div>
<script type="text/javascript">
var flag = false; // the value is true if the new role name is valid.

$(document).ready(function() {
	// add new role dialog
	$("#divAddRole").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true,
		title: "<fmt:message key='role.add' />", 
		buttons: [
	  		{
		  		text: "<fmt:message key='Submit' />", 
		  		click: function() { if(addCheck()){addNewRole();} }
	  		}, 
	  		{
		  		text: "<fmt:message key='Cancel' />", 
		  		click: function() { $('#divAddRole').dialog('close'); }
	  		}
	  		]
		}
	);
	// Edit dialog
	$("#divEditRole").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true,
		title: "<fmt:message key='role.edit' />", 
		buttons: [
	  		{
		  		text: "<fmt:message key='save' />", 
		  		click: function() { updateRole();/* if(editCheck()){updateRole();} */ }
	  		}, 
	  		{
		  		text: "<fmt:message key='Cancel' />", 
		  		click: function() { $('#divEditRole').dialog('close'); }
	  		}
	  		]
		}
	);
	// role list
	pageInit();
});

var grid = null;
/**
 * role list init
 */
function pageInit() {
	var jsonObj = {};
	jsonObj.Rows = ${jsonStr};
	grid = $("#divRoles").ligerGrid(
		{
			url : "role.action",
			columns : [
					{
						display : "<fmt:message key='role.name' />",
						minWidth : 120,
						name : 'roleName',
						align : 'left'
					},
					{
						display : "<fmt:message key='role.description' />",
						width : 200,
						name : 'describe',
						align : 'left'
					}],
			data : jsonObj,
			pageSize : 20,
			checkbox : false,
			dataAction : 'server',
			sortName : 'roleName',
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
	$("#divAddRole").dialog( "open" );
	$("#divAddRole").dialog( "option", "height", 180 );
	$("#divAddRole").dialog( "option", "width", 520 );
}

/**
 * 
 */
function roleNameCheck(roleName, divId) {
	$.post("role!check.action", {"roleName":roleName}, function(data){
		if(data == "false"){
			$("#" + divId).empty();
			$("#" + divId).append("The role name is already used.");
			flag = false;
		} else {
			$("#" + divId).empty();
			$("#" + divId).append("Please make it clearly.");
			flag = true;
		}
	});
}

/**
 * 
 */
function addCheck() {
	if($.trim($("#newName").val())==""){
		alert("The role name can not be empty.");
		$("#newName").focus();
		return false;
	}
	// check the role name
	roleNameCheck($("#newName").val(), "divRoleNameTip");
	setTimeout("if(!flag) {return false;}", 1000);
	return true;
}

/**
 * add a new role
 */
function addNewRole() {
	var url = "role!create.action";
	var params = {"roleName":$("#newName").val(), "describe":$("#newDesp").val()};
	$.post(url, params, function() {
		$("#divAddRole").dialog( "close" );
		window.location = "role.action";
	});
}

/**
 * show edit user dialog
 */
function showEditDlg() {
	var row = grid.getSelectedRow();
	if(row == null) {
		alert("Please select a role for edit!");
		return;
	}
	$("#msg").empty();
	$("#divEditRole").dialog( "open" );
	$("#divEditRole").dialog( "option", "height", 180 );
	$("#divEditRole").dialog( "option", "width", 520 );
	
	$("#roleName").val(row.roleName);
	$("#describe").val(row.describe);
}

/**
 * update the role infos
 */
function updateRole() {
	var row = grid.getSelectedRow();
	var url = "role!update.action";
	var params = {"roleId":row.roleId, "roleName":$("#roleName").val(), 
			"describe":$("#describe").val()};
	$.post(url, params, function() {
		$("#divEditRole").dialog( "close" );
		window.location = "role.action";
	});
}

/**
 * new value check
 */
function editCheck() {
	if($.trim($("#roleName").val())==""){
		alert("The role name can not be empty.");
		$("#roleName").focus();
		return false;
	}
	// check the role name
	if(roleNameCheck($("#roleName").val(), "divRoleNameTip2")) {
		if(!flag) {
			return false;
		}
	} else {
		return false;
	}
	return true;
}

/**
 * delete a role
 */
function delRole() {
	var row = grid.getSelectedRow();
	if(row == null) {
		alert("Please select a role for delete!");
		return;
	}
	if (confirm("You will delete the Role which roleName is " + row.roleName
			+ " ?")) {
		var url = "role!delRole.action";
		var params = {"roleId" : row.roleId};
		$.post(url, params, function(data) {
			alert(data);
			if(data == "<fmt:message key='role.have.been.deleted' />") {
				window.location = "role.action";
			}
		});
	}
}

/**
 * 
 */
function changeFunction(){
	var row = grid.getSelectedRow();
	if(row == null) {
		alert("Please select a role!");
		return;
	}
	window.location="role!function.action?roleId=" + row.roleId;
}
</script>
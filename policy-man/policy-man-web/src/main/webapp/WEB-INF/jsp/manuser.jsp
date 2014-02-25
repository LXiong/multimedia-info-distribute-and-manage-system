<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="taglib.jsp" %>

<sp:url var="userSaveUrl" value="/manuser/save" />
<sp:url var="saveRoleUrl" value="/manuser/saverole" />
<sp:url var="listRoleUrl" value="/manuser/listrole" />

<sec:authorize url="${saveRoleUrl}">
<button id='btnAdd' class="jqbutton"><fmt:message key="button.add" /></button>
<button id='btnEdit' class="jqbutton"><fmt:message key="button.edit" /></button>
<button id='btnDelete' class="jqbutton"><fmt:message key="button.delete" /></button>
</sec:authorize>
<sec:authorize url="${listRoleUrl}">
<button id='btnRole' class="jqbutton"><fmt:message key="manuser.button.edit.role" /></button>
</sec:authorize>
<table id="userList"></table>
<div id="userPage"></div>
<fmt:message var="colNames" key="manuser.page.colNames" />

<div id="role_dlg" class="hidden">
</div>

<script type="text/javascript">
function booleanFormatter (cellvalue, options, rowObject) {
	if (cellvalue=='true') return '<fmt:message key="option.yes" />';
	else return '<fmt:message key="option.no" />';
}

function unformatboolean ( cellvalue, options, cellobject) {
   if (cellvalue == '<fmt:message key="option.yes" />') return 'true';
   return 'false';
}

function mypasscheck() {
	if ($('#password').val() != '') {
		if ($('#password').val() != $('#password2').val()) {
			return [false,'<fmt:message key="manuser.password.mismatch" />'];
		}
	}
	return [true,''];
}

$(document).ready(function() {
	jQuery("#userList").jqGrid({ 
		url:'<%=request.getContextPath()%>/manuser/grid',
		datatype: "json",
		colNames:[${colNames}],
		colModel:[
				{name:'username',index:'username', width:90, editable:true,
					editoptions:{size:20}, editrules:{required:true} },				
				{name:'dispName',index:'dispName', width:100, editable:true,
					editoptions:{size:20}},
				{name:'email',index:'email', width:200, editable:true,
					editoptions:{size:30}},
				{name:'phone',index:'phone', width:200, editable:true,
					editoptions:{size:20}},
				{name:'enabled',index:'enabled', width:50
						, formatter:booleanFormatter, unformat:unformatboolean
						, editable:true,edittype:"checkbox",
						editoptions:{value:"true:false",defaultValue:'true'}	},
				{name:'lastlogin',index:'lastLogin', width:80},
				{name:'password',index:'password', hidden:true, editable:true,
					edittype:'password',editrules:{edithidden:true}},
				{name:'password2',index:'password2', hidden:true, editable:true,
					edittype:'password',editrules:{edithidden:true,custom:true, custom_func:mypasscheck}},
				],
		rowNum:10,
		rowList:[10,20,30],
		pager: '#userPage',
		autowidth: true, 
		height: "270", 
		sortname: 'username',
		viewrecords: true,
		sortorder: "asc",
		hidegrid: false,
		editurl:'${userSaveUrl}',
		caption:'<fmt:message key="user.list" />' });
	jQuery("#userList").jqGrid('navGrid','#userPage',
			{edit:false, add:false, del:false},
			{width:400, height:300, reloadAfterSubmit:true}, // edit options 
			{width:400, height:300, reloadAfterSubmit:true}, // add options 
			{reloadAfterSubmit:true}, // del options 
			{} // search options 
	);
	
	$(".jqbutton").button();
	$('#btnAdd').click(function() {
		jQuery("#userList").jqGrid('editGridRow', "new", {
			closeAfterAdd:true, 
			afterSubmit: function (response, postdata) {
				if (response.statusCode == 500) {
					return [false, '服务器端出错，请联系管理员'];
				} else if (response.responseText == 'name_exists') {
					return [false, '用户名己经被使用，添加失败'];
				} else if (response.responseText == 'new_missing_passwd') {
					return [false, '新建用户必须输入密码'];
				}
				return [true];
			}, beforeShowForm:function() {
				$("#username").attr('readonly',null);
				$("#username").removeClass('readonly');
			}}); 
	});
	$('#btnEdit').click(function() {
		var gr = jQuery("#userList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			jQuery("#userList").jqGrid('editGridRow', gr, {
				closeAfterEdit:true, 
				beforeShowForm:function() {
					$("#username").attr('readonly','readonly');
					$("#username").addClass('readonly');
				},
				afterSubmit : function (response, postdata) {
					if (response.responseText == "success") {
						return [true,"修改成功"];
					} else {
						return [false,"修改失败"];
					}
				}
				}
			); 
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
	$('#btnDelete').click(function() {
		var gr = jQuery("#userList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			jQuery("#userList").jqGrid('delGridRow',gr,	
					{afterSubmit : function (response, postdata) {	
					if (response.responseText == "success") {
						return [true,"删除成功"];
					} else {
						return [false,"删除失败"];
					}
				}
				}
			);
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
	$('#btnRole').click(function() {
		var gr = jQuery("#userList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			$.get("${listRoleUrl}",{'id':gr},
					function(data) {
				$("#role_dlg").html(data);
				$("#role_dlg").dialog("open");
			});
		}
		else alert('<fmt:message key="alert.select.row" />');
	});
	$('#role_dlg').dialog ({
		autoOpen: false,
		height: 400,
		width: 350,
		modal: true,
		title: '<fmt:message key="manuser.role.title" />',
		buttons: {
			'<fmt:message key="button.save" />' :function() {
				$.post("${saveRoleUrl}",$("#formRole").serialize(),
				function (data) {
					if ("success" == data) {
						alert('<fmt:message key="manuser.user.saverole.success" />');
						$("#role_dlg").dialog("close");
					} else {
						alert('<fmt:message key="manuser.user.saverole.fail" />');
					}
				});
			},
			'<fmt:message key="button.cancel" />': function() {
				$( this ).dialog( "close" );
			}
		}
	});
});
</script>
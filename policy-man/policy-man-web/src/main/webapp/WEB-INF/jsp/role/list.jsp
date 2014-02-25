<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<sp:url value="/role/saveauth" var="saveAuthUrl"/>
<sp:url value="/role/listauth" var="listAuthUrl"/>
<button id='btnAdd' class="jqbutton"><fmt:message key="button.add" /></button>
<button id='btnEdit' class="jqbutton"><fmt:message key="button.edit" /></button>
<button id='btnDelete' class="jqbutton"><fmt:message key="button.delete" /></button>
<button id='btnAuth' class="jqbutton"><fmt:message key="role.button.edit.authority" /></button>
<table id="roleList"></table>
<div id="rolePage"></div>
<script type="text/javascript">
function booleanFormatter (cellvalue, options, rowObject) {
	if (cellvalue=='true') return '<fmt:message key="option.yes" />';
	else return '<fmt:message key="option.no" />';
}

function unformatboolean ( cellvalue, options, cellobject) {
   if (cellvalue == '<fmt:message key="option.yes" />') return 'true';
   return 'false';
}

function showAuthorityDialog() {
	var gr = jQuery("#roleList").jqGrid('getGridParam','selrow');
	if( gr != null ) {
		$.get("${listAuthUrl}",{'id':gr},
				function(data) {
			$("#auth_dig").html(data);
			$(".auth_group").click(function() {
				var checked = $(this).attr('checked');
				var selfId = $(this).attr('id');
				
				$("input").each(function() {
					if ($(this).attr('id').indexOf(selfId+'_')>=0) {
						$(this).attr('checked',checked);							
					}
				});
			});
			$('.authority').click(function() {
				var id = $(this).attr('id');
				var groupId = id.substring(0, id.indexOf('_'));
				var checkedAll = true;
				$('.'+groupId).each(function() {
					if ($(this).attr('checked')!=true) {
						checkedAll = false;
					}
				});
				$('#'+groupId).attr('checked', checkedAll);
			});
			$("#auth_dig").dialog("open");
		});
	} else{
		alert('<fmt:message key="alert.select.row" />');
	}
}

$(document).ready(function() {
	jQuery("#roleList").jqGrid({ 
		url:'<%=request.getContextPath()%>/role/grid',
		datatype: "json",
		colNames:['<fmt:message key="role.thead.name" />','<fmt:message key="role.thead.desc" />', '<fmt:message key="role.thead.enabled" />'],
		colModel:[
				{name:'name',index:'name', width:90, editable:true, editoptions:{size:20}},
				{name:'description',index:'description', width:100, editable: true,edittype:"textarea", editoptions:{rows:"3",cols:"25"}},
				{name:'enabled',index:'enabled', width:60
					, formatter:booleanFormatter, unformat:unformatboolean
					, editable:true,edittype:"checkbox",
					editoptions:{value:"true:false",defaultValue:'true'}					
					},
				],
		rowNum:20,
		rowList:[10,20,30],
		pager: '#rolePage',
		autowidth: true, 
		height: "270", 
		sortname: 'name',
		viewrecords: true,
		sortorder: "asc",
		hidegrid: false,
		editurl:'<%=request.getContextPath()%>/role/save',
		caption:'<fmt:message key="role.list" />' });
	jQuery("#roleList").jqGrid('navGrid', '#rolePage', 
			{edit:false, add:false, del:false},
			{height:220, reloadAfterSubmit:true}, // edit options 
			{height:220, reloadAfterSubmit:true}, // add options 
			{reloadAfterSubmit:true}, // del options 
			{} // search options 
			);
	$(".jqbutton").button();
	$('#btnAdd').click(function() {
		jQuery("#roleList").jqGrid('editGridRow',"new", {
			closeAfterAdd:true, 
			afterSubmit: function (response) {
				if (response.responseText=='success') {
					return [true];
				}
				return [false, response.responseText];
			}}
		); 
	});
	$('#btnEdit').click(function() {
		var gr = jQuery("#roleList").jqGrid('getGridParam','selrow');
		if(gr != null) {
			jQuery("#roleList").jqGrid('editGridRow', gr,  {
				closeAfterEdit:true, 
				afterSubmit: function (response) {
					if (response.responseText=='success') {
						return [true];
					}
					return [false,response.responseText];
				}
			}); 
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
	$('#btnDelete').click(function() {
		var gr = jQuery("#roleList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			var rowData = jQuery("#roleList").jqGrid('getRowData',gr); 
			jQuery("#roleList").jqGrid('delGridRow',gr,	{
				reloadAfterSubmit:true,
				afterSubmit: function (response) {
					if (response.responseText=='success') {
						return [true];
					}
					return [false,response.responseText];
				}});
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
	$('#btnAuth').click(showAuthorityDialog);
	$( "#auth_dig" ).dialog({
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
		buttons: {
			'<fmt:message key="button.save" />' :function() {
				$.post("${saveAuthUrl}",$("#formAuth").serialize(),
				function (data) {
					if ("success" == data) {
						alert('<fmt:message key="role.auth.save.success" />');
						$("#auth_dig").dialog("close");
					} else {
						alert('<fmt:message key="role.auth.save.fail" />');
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

<div id="auth_dig" class="hidden">
</div>

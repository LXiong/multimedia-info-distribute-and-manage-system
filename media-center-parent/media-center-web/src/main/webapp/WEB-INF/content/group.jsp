<%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%><%@include file="taglib.jsp" 
%><link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/dtree.css">
<script type="text/javascript" src="js/dtree.js" ></script>
<script type="text/javascript" >
var count = 0;
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	$("#addGroupDiv").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true, 
		title: "<fmt:message key='add.new.group.dialog.title' />"
		});
	$("#addTypeDiv").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true, 
		title: "<fmt:message key='add.new.type.dialog.title' />", 
		buttons: [
		  		{
			  		text: "<fmt:message key='save' />", 
			  		click: function() { saveNewType(); }
		  		}, 
		  		{
			  		text: "<fmt:message key='Cancel' />",
                  	click: function() { $('#addTypeDiv').dialog('close'); } 
              	} 
              	]});
	$("#msg").dialog({ 
		autoOpen: false, 
		modal: true, 
		buttons: [
		  		{
			  		text: "<fmt:message key='OK' />",
                  	click: function() { $('#msg').dialog('close'); } 
              	} 
              	]});
	/*$("#tbl").hide();
	$("#add").hide();
	$("#delete").hide();
	$("#edit").hide();
	$("#typeSel").change(function(){ getGroup("typeSel", "groupSel") });
	$("#typeSel1").change(function(){ getGroup("typeSel1", "groupSel1") });*/
	// After delete, empty types
	//$("#typeSel").val("");
	//$("#typeSel1").val("");
	if("${userName}" != "admin") {
		$("#addTypeBt").hide();
	}
});
/**
 * 
 */
function addGroup(typeId) {
	$( "#addGroupDiv" ).dialog( "open" );
	$( "#addGroupDiv" ).dialog( "option", "height", 200 );
	$( "#addGroupDiv" ).dialog( "option", "width", 400 );
	$( "#addGroupDiv" ).dialog( {buttons: [
	                      		  		{
	                    			  		text: "<fmt:message key='save' />", 
	                    			  		click: function() { saveNewGroup(typeId); }
	                    		  		}, 
	                    		  		{
	                    			  		text: "<fmt:message key='Cancel' />",
	                                      	click: function() { $('#addGroupDiv').dialog('close'); } 
	                                  	} 
	                                  	]});
}
/**
 * 
 */
function addType() {
	$( "#addTypeDiv" ).dialog( "open" );
	$( "#addTypeDiv" ).dialog( "option", "height", 200 );
	$( "#addTypeDiv" ).dialog( "option", "width", 400 );
}
/**
 * 
 */
function editGroupName(typeId, groupId) {
	var url = path + "/group!editGroupName.action";
	var params = {"typeId":typeId, "groupId":groupId, 
			"groupName":$("#" + groupId).val()};
	$.post(url, params, function(data){
		if(data != null && data != "") {
			$( "#msg" ).empty();
			$( "#msg" ).append(data);
			$( "#msg" ).dialog( "open" );
		}
	});
}
/**
 * 
 */
function editGroupDesp(groupId) {
	var url = path + "/group!editGroupDesp.action";
	var params = {"groupId":groupId, 
			"groupDescription":$.trim($("#" + groupId + "groupDescription").val())};
	$.post(url, params);
}
/**
 * 
 */
function editTypeName(typeId) {
	var url = path + "/group!editTypeName.action";
	var params = {"typeId":typeId, "typeName":$("#" + typeId).val()};
	$.post(url, params, function(data) {
		if(data != null && data != "") {
			$( "#msg" ).empty();
			$( "#msg" ).append(data);
			$( "#msg" ).dialog( "open" );
		}
	});
}
/**
 * 
 */
function editTypeDesp(typeId) {
	var url = path + "/group!editTypeDesp.action";
	var params = {"typeId":typeId, 
			"typeDescription":$.trim($("#" + typeId + "typeDescription").val())};
	$.post(url, params);
}
/**
 * 
 */
function saveNewGroup(typeId) {
	if($("#newGroupName").val() != null && $("#newGroupName").val() != '') {
		var url = path + "/group!addGroup.action";
		var params = {"typeId":typeId, "groupName":$("#newGroupName").val(), 
				"groupDescription":$.trim($("#newGroupDescription").val())};
		$.post(url, params, function(data) {
			if(data != null && data != "") {
				$("#msg1").empty();
				$("#msg1").append(data);
			} else {
				window.location = path + "/group.action";
			}
		});
	}
}
/**
 * 
 */
function saveNewType() {
	if($("#newTypeName").val() != null && $("#newTypeName").val() != '') {
		var url = path + "/group!addType.action";
		var params = {"typeName":$("#newTypeName").val(), 
				"typeDescription":$.trim($("#newTypeDescription").val())};
		$.post(url, params, function(data) {
			if(data != null && data != "") {
				$("#msg2").empty();
				$("#msg2").append(data);
			} else {
				window.location = path + "/group.action";
			}
		});
	}
}
/**
 * 
 */
function deleteByGroupId(obj, groupId) {
	if(confirm("<fmt:message key='Delete' />")) {
		var url = path + "/group!deleteGroup.action";
		var params = {"groupId":groupId};
		$.post(url, params, function(data) {
			if(data != null && data != "") {
				$("#msg").empty();
				$("#msg").append(data);
				$( "#msg" ).dialog( "open" );
			} else {
				var table = obj.parentNode.parentNode.parentNode.parentNode;
				table.deleteRow(obj.parentNode.parentNode.parentNode.rowIndex - 1);
			}
		});
	}
}
/**
 * 
 */
function deleteByTypeId(obj, typeId) {
	if(confirm("<fmt:message key='Delete' />")) {
		var url = path + "/group!deleteType.action";
		var params = {"typeId":typeId};
		$.post(url, params, function(data) {
			if(data != null && data != "") {
				$("#msg").empty();
				$("#msg").append(data);
				$( "#msg" ).dialog( "open" );
			} else {
				var table = obj.parentNode.parentNode.parentNode.parentNode;
				var i = obj.parentNode.parentNode.parentNode.rowIndex;
				table.deleteRow(i - 1);
				table.deleteRow(i - 1);
			}
		});
	}
}
</script>
<div><p style="color: maroon; font-size: 12px; font-style: normal" align="center">
		<fmt:message key="group.manage.tips" /></p></div>
<div>
<table id="table" class="linetable">
	<thead><tr>
		<th width="10%"></th>
		<th style="text-align: left" width="30%"><fmt:message key='group.show' /></th>
		<th style="text-align: left" width="40%"><fmt:message key='Description' /></th>
		<th style="text-align: left" width="10%"><fmt:message key='Operation' /></th>
		<th width="10%"></th>
	</tr></thead>
	<tbody><c:forEach var="item" items="${typeList}" >
		<tr>
			<td></td>
		<c:choose>
		<c:when test="${item.typeId != -1}">
			<td><div align="left">
					<yun:auth functions="CustomerDeviceManage">
						<input type="text" id="${item.typeId }" value="${item.typeName }" 
							onchange="editTypeName('${item.typeId }');" />
					</yun:auth>
					<label style="color: gray" >${item.typeName }</label>
				</div>
			</td>
			<td style="text-align: left"><input id="${item.typeId}typeDescription" 
				value="${item.typeDescription }" 
				onchange="editTypeDesp('${item.typeId }');" size="50" ></input></td>
			<td style="text-align: left">
				<yun:auth functions="CustomerDeviceManage">
				<div><a href="###" onclick="deleteByTypeId(this, '${item.typeId }')">
				<fmt:message key="Delete" /></a></div>
				</yun:auth>
			</td>
		</c:when>
		<c:otherwise>
			<td><div align="left">
				<label style="color: gray" >${item.typeName }</label></div>
			</td>
			<td style="text-align: left"><input id="${item.typeId}typeDescription" 
				value="${item.typeDescription }" onchange="editTypeDesp('${item.typeId }');" 
				disabled="disabled" size="50" ></input></td>
			<td></td>
		</c:otherwise>
		</c:choose>
			<td></td>
		</tr>
		<c:forEach var="group" items="${groupList }" >
			<c:if test="${group.typeId eq item.typeId}"><tr>
				<td></td>
				<td><div align="left">&nbsp;&nbsp;|----&nbsp;
					<input type="text" id="${group.groupId }" value="${group.groupName }" 
					onchange="editGroupName('${group.typeId }', '${group.groupId }');" />
					<label style="color: gray" >${group.groupName }</label>
				</div></td>
				<td style="text-align: left">
					<input id="${group.groupId}groupDescription" value="${group.groupDescription }" 
					onchange="editGroupDesp('${group.groupId }');" size="50" ></input></td>
				<td><div align="left">
					<a href="###" onclick="deleteByGroupId(this, '${group.groupId }');">
					<fmt:message key="Delete" /></a>
				</div></td>
				<td></td>
			</tr></c:if>
		</c:forEach>
		<c:if test="${item.typeId != -1}" >
			<tr><td></td>
				<td><div align="left">&nbsp;&nbsp;\----&nbsp;
					<a href="###" onclick="addGroup('${item.typeId}');">
						<fmt:message key="group.add"/></a>
				</div></td>
				<td></td><td></td><td></td>
			</tr>
		</c:if>
	</c:forEach>
	<yun:auth functions="CustomerDeviceManage">
	<tr><td></td>
		<td><div align="left" id="addTypeBt" >
			<a href="###" onclick="addType();"><fmt:message key="type.add"/></a>
		</div></td>
		<td></td><td></td><td></td>
	</tr>
	<tr><td></td>
		<td><div align="left" id="addTypeBt" >
			<a href="###" onclick="addType();"><fmt:message key="type.add"/></a>
		</div></td>
		<td></td><td></td><td></td>
	</tr>
	</yun:auth>
	<tr><td></td>
		<td><div align="left"><fmt:message key="other.groups" /></div></td>
		<td></td><td></td><td></td>
	</tr>
	<c:forEach var="group" items="${groupsList }" >
		<tr>
			<td></td>
			<td><div align="left">&nbsp;&nbsp;|----&nbsp;
				<input type="text" id="${group.groupId }" disabled="disabled" 
				value="${group.groupName }" onchange="editGroupName('${group.typeId }', 
				'${group.groupId }');" size="50" />
			</div></td>
			<td style="text-align: left"><input id="${group.groupId}groupDescription" 
				value="${group.groupDescription }" disabled="disabled" size="30" 
				onchange="editGroupDesp('${group.groupId }');"></input></td>
			<td><div align="left">
				<!-- <a href="###" onclick="deleteByGroupId(this, '{group.groupId }');">
				<fmt:message key="Delete" /></a> -->
			</div></td>
			<td></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>
<div id="msg" align="center" style="color: red; font-size: 12px" ></div>
<div id="addGroupDiv">
	<table class='linetable'>
		<tr><td style='text-align: left'><label><fmt:message key='new.group.name' />
			</label></td><td style='text-align: left'><input id='newGroupName'/></td></tr>
		<tr><td style='text-align: left'><label><fmt:message key='Description' /></label></td>
			<td style='text-align: left'><input id='newGroupDescription'/>
		</td></tr>
	</table>
	<div id="msg1" align="center" style="color: red; font-size: 12px" ></div>
</div>
<div id="addTypeDiv">
	<table class='linetable'>
		<tr><td style='text-align: left'><label><fmt:message key='new.type.name' />
			</label></td><td style='text-align: left'><input id='newTypeName'/></td></tr>
		<tr><td style='text-align: left'><label><fmt:message key='Description' /></label></td>
			<td style='text-align: left'><input id='newTypeDescription'/>
		</td></tr>
	</table>
	<div id="msg2" align="center" style="color: red; font-size: 12px" ></div>
</div>
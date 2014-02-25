<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<script type="text/javascript" >
$(document).ready( function() {
	$("#addCategoryDiv").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true, 
		title: "<fmt:message key='file.category.add' />"
		});
	$("#msg").dialog({ 
		autoOpen: false, 
		modal: true, 
		buttons: [
		  		{
			  		text: "<fmt:message key='option.ok' />",
                  	click: function() { $('#msg').dialog('close'); } 
              	} 
              	]});
});
/**
 * 
 */
function editCateName(categoryId) {
	var url = "<%=request.getContextPath()%>/videos/category/update";
	var params = {"id":categoryId, "name":$("#" + categoryId).val(), 
			"description":$("#" + categoryId + "description").val()};
	$.post(url, params, function(data){
		if(data != "") {
			$( "#msg" ).empty();
			$( "#msg" ).append("<fmt:message key='category.name.confict' />");
			$( "#msg" ).dialog( "open" );
		}
	});
}
/**
 * 
 */
function showAddDiv(parentId) {
	$( "#addCategoryDiv" ).dialog( "open" );
	$( "#addCategoryDiv" ).dialog( "option", "height", 200 );
	$( "#addCategoryDiv" ).dialog( "option", "width", 400 );
	$( "#addCategoryDiv" ).dialog( {buttons: [
       		  		{
     			  		text: "<fmt:message key='button.save' />", 
     			  		click: function() { addCategory(parentId); }
     		  		}, 
     		  		{
     			  		text: "<fmt:message key='button.cancel' />",
                       	click: function() { $('#addCategoryDiv').dialog('close'); } 
                   	} 
                   	]});
	setTimeout("$( '#newName' ).focus();", 1000);
}
/**
 * 
 */
function addCategory(parentId) {
	var url = "<%=request.getContextPath()%>/videos/category/add";
	var params = {"parentId":parentId, "name":$("#newName").val(), 
			"description":$("#newDescription").val()};
	$.post(url, params, function(data){
		if(data != "") {
			$("#msg1").empty();
			$("#msg1").append("<fmt:message key='category.name.confict' />");
		} else {
			window.location = "<%=request.getContextPath()%>/videos/categories";
		}
	});
}
/**
 * 
 */
function delCategory(id) {
	var url = "<%=request.getContextPath()%>/videos/category/del";
	var params = {"id":id};
	$.post(url, params, function(data){
		if(data != "") {
			$( "#msg" ).empty();
			$( "#msg" ).append( "<fmt:message key='category.can.not.delete' />" );
			$( "#msg" ).dialog( "open" );
		} else {
			window.location = "<%=request.getContextPath()%>/videos/categories";
		}
	});
}
</script>
<div><p style="color: maroon; font-size: 12px; font-style: normal" align="center">
		<fmt:message key="categories.manage.tips" /></p></div>
<div id="category">
<table class="linetable">
<thead><tr>
		<th width="5%"></th>
		<th style="text-align: left" width="30%"><fmt:message key='file.category' /></th>
		<th style="text-align: left" width="40%"><fmt:message key='file.description' /></th>
		<th style="text-align: left" width="10%"></th>
		<th width="10%"></th>
</tr></thead>
<tbody><c:forEach var="parent" items="${categoryMap }">
	<tr><td></td><td style="text-align: left">
		<input type="text" id="${parent.key.id }" value="${parent.key.name }" 
			onchange="editCateName('${parent.key.id}');" />
		<label style="color: gray" >${parent.key.name }</label>
	</td>
	<td style="text-align: left">
		<input id="${parent.key.id}description" value="${parent.key.description }" 
			onchange="editCateName('${parent.key.id}');" size="50" ></input></td>
	<td style="text-align: left">
		<div><a href="###" onclick="delCategory('${parent.key.id}');">
		<fmt:message key="button.delete" /></a></div></td>
	<td></td></tr>
	<c:forEach var="child" items="${parent.value}">
		<tr><td></td>
			<td><div align="left">&nbsp;&nbsp;|----&nbsp;
				<input type="text" id="${child.id }" value="${child.name }" 
					onchange="editCateName('${child.id}');" />
				<label style="color: gray" >${child.name }</label>
			</div></td>
			<td style="text-align: left">
				<input id="${child.id}description" value="${child.description }" 
				onchange="editCateName('${child.id}');" size="50" ></input></td>
			<td style="text-align: left"><div>
				<a href="###" onclick="delCategory('${child.id}');">
				<fmt:message key="button.delete" /></a>
			</div></td>
		<td></td></tr>
	</c:forEach>
	<tr><td></td>
		<td><div align="left">&nbsp;&nbsp;\----&nbsp;
			<a href="###" onclick="showAddDiv('${parent.key.id}');">
				<fmt:message key="file.sub.category.add"/></a>
		</div></td>
		<td></td><td></td><td></td>
	</tr>
</c:forEach>
	<tr><td></td>
		<td><div align="left">
			<a href="###" onclick="showAddDiv(0);">
				<fmt:message key="file.category.add"/></a>
		</div></td>
		<td></td><td></td><td></td>
	</tr>
</tbody>
</table>
</div>
<div id="msg" align="center" style="color: red; font-size: 12px" ></div>
<div id="addCategoryDiv">
	<table class='linetable'>
		<tr><td style='text-align: left'><label><fmt:message key='file.category.name' />
			</label></td>
			<td style='text-align: left'><input type="text" id='newName'/></td>
		</tr>
		<tr><td style='text-align: left'>
			<label><fmt:message key='file.category.description' /></label></td>
			<td style='text-align: left'><input type="text" id='newDescription'/>
		</td></tr>
	</table>
	<div id="msg1" align="center" style="color: red; font-size: 12px" ></div>
</div>
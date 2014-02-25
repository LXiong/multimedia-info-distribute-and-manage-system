<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
<hr>
<form method="post" action="" name="myform" id="myform">
<input type="hidden" value="${userId}" name="userId" readonly="readonly">
<table class="linetable">
<thead>
	<tr>
		<th style="text-align: left;">操作</th>
		<th style="text-align: left;">描述</th>
	</tr>
</thead>
<tbody id="tbody">
	<c:forEach var="type" items="${groupTypeList}">
		<tr>
			<td style="text-align: left">
				<input type="checkbox" class="cheType" value="${type.typeId}" onclick="choose(this)">
				&nbsp;${type.typeName }
			</td>
			<td style="text-align: left">${type.typeDescription }</td>
		</tr>
		<c:forEach var="group" items="${groupsList}"><c:if test="${group.typeId eq type.typeId}">
		<tr>
			<td style="text-align: left">&nbsp;&nbsp;&nbsp;
				<input type="checkbox" class="chkGroup" onclick="choose2(this)" name="${group.typeId }" value="${group.groupId}">
				&nbsp;${group.groupName}
			</td>
			<td style="text-align: left">${group.groupDescription }</td>
		</tr>
		</c:if></c:forEach>
	</c:forEach>
</tbody>
</table>
<hr>
<br>
<div class="btn">
	<input type="button" class="btn_64" value="保存" onclick="save()">
	<input type="button" class="btn_64" value="返回" onclick="javascript:history.back()">
</div>
</form>
<script type="text/javascript">
$(document).ready(function(){
	<c:forEach items="${UserStbList}" var="a">
		if(${a.typeflag}=='4'){
			$("#tbody").find("input[type='checkbox'][value="+${a.typeId}+"]").attr("checked",true);
			$("#tbody").find("input[type='checkbox'][name="+${a.typeId}+"]").attr("checked",true);
		}else{
			$("#tbody").find("input[type='checkbox'][value="+${a.typeId}+"]").attr("checked",true);
		}
	</c:forEach>
})

function save() {
	if(confirm("您确认保存?")) {
		var groupsArray = [];
		var typeArray = [];
		if($(".cheType:checked").size() < 1) {
			$(".chkGroup:checked").each(function() {
				groupsArray.push(this.value);
			});
		} else {											
			var status = true;
			$(".cheType:checked").each(function() {
				typeArray.push(this.value);
			});	
			$(".chkGroup:checked").each(function() {
				var id = $(this).attr("name");
				for(var i=0; i<typeArray.length; i++) {
					if(id == typeArray[i]) {
						status = false;
						break;
					}		
				}
				if(status == true) {
					groupsArray.push(this.value);
				}
				status = true;
			});
		}
		url = 'user!saveStbPower.action?groupsArray=' + groupsArray + '&typeArray=' + typeArray;
		$("#myform").attr("action", url);
		document.myform.submit();
	}
}

function choose(obj) {
	var attr = $(obj).attr("checked");
	var id = obj.value;
	$("#tbody").find("input[type='checkbox'][name="+id+"]").attr("checked", attr);
}

function choose2(obj) {
	var attr = $(obj).attr("checked");
	var id = $(obj).attr("name");
	if(attr!=true) {
		$("#tbody").find("input[type='checkbox'][value="+id+"]").attr("checked", false);
	} else {
		var size = $("#tbody").find("input[type='checkbox'][name="+id+"]").size();
		var size2 =$("#tbody").find("input[type='checkbox'][checked=true][name="+id+"]").size();
		if(size == size2) {
			$("#tbody").find("input[type='checkbox'][value="+id+"]").attr("checked",true);
		}
	}
}
</script>
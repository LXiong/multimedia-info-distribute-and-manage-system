<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %><%@include file="stb-js.jsp" %>
<input type="hidden" value="${id }" id="id">
<table class="linetable">
<thead>
<tr>
	<th>模块</th>
	<th>版本</th>
	<th>意见</th>
	<th>发布时间</th>
	<th>文件路径</th>
	<th>验证码</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="m" items="${list}">
	<tr>
	<td>${m.module }</td>
	<td>${m.version }</td>
	<td>${m.file_comment }</td>
	<td>
		<fmt:formatDate value="${module.releaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	</td>
	<td>${m.filePath }</td>
	<td>${m.verflyCode }</td>
	</tr>
</c:forEach>
</tbody>
</table>
<div id="msg" align="center" style="color: red; font-size: 12px" ></div>
<div id="apply" align="left">
	<button onclick="applyToAddr();" class="btn_117">
		<fmt:message key="config.apply.to.stb"/></button>
	<button onclick="applyToGroup();" class="btn_117">
		<fmt:message key="config.apply.to.group"/></button>
</div>

<div id="addrDiv" style="position:relative;width:100%;text-align:left">
	<ul class="query cf">
		<li><label><fmt:message key="Province" /></label>
			<select id="provinceId" ><option value=""><fmt:message key="Total" /></option></select>
		</li>
		<li><label><fmt:message key="City" /></label>
			<select id="cityId" ><option value=""><fmt:message key="Total" /></option></select>
		</li>
		<li><label><fmt:message key="District" /></label>
			<select id="districtId" ><option value=""><fmt:message key="Total" /></option></select>
		</li>
	</ul>
	<div class="btn" style="position:absolute;top:0px;_top:0px;left:700px">
		<button class="btn1_64" onclick="$('#page').val(1);getStbInfo();"><fmt:message key="Query" /></button>
	</div>
	<div id="tbl">
		<table id="table" class="linetable"></table>
	</div>
	<ul>
		<li><button name="" class="btn_64" onclick="setConfigToAddr();" ><fmt:message key="OK" /></button>
			<button onclick="cancel('addrDiv');" class="btn_64"><fmt:message key='Cancel' /></button>
		</li>
	</ul>
</div>
<div id="groupDiv" style="position:relative;width:100%;text-align:left">
	<ul class="query cf">
		<li><label><fmt:message key="GroupType" /></label>
			<select id="chgType"><option value="" ><fmt:message key="Select" /></option></select>
		</li>
		<li><label><fmt:message key="Group" /></label>
			<select id="chgGroup"><option value="" ><fmt:message key="Select" /></option></select>
		</li>
	</ul>
	<div id="groupTblDiv">
		<table id="groupTable" class="linetable"></table>
	</div>
	<ul>
		<li>
			<button onclick="setConfigToGroup();" class="btn_64"><fmt:message key='OK' /></button>
			<button onclick="cancel('groupDiv');" class="btn_64"><fmt:message key='Cancel' /></button>
		</li>
	</ul>
</div>
<script type="text/javascript">
var rows = 0;
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	$("#addrDiv").hide();
	$("#groupDiv").hide();
	$("#add").hide();
	$("#save").hide();
	$("#provinceId").change(function(){ getCity("cityId", "provinceId", "districtId") });
	$("#cityId").change(function(){ getDistrict("districtId", "cityId") });
	$("#typeSel").change(function(){ getGroup("typeSel", "groupSel") });
	$("#chgType").change(function(){ getGroup("chgType", "chgGroup") });
});

function applyToAddr() {
	getProvince("provinceId");
	$("#addrDiv").show();
	$("#groupDiv").hide();
}
function applyToGroup() {
	$("#msg").empty();
	getType("chgType", "chgGroup");
	$("#groupDiv").show();
	$("#addrDiv").hide();
}
function setConfigToGroup(){
		if( $("#chgGroup").val() == "" ) {
			$("#msg").empty();
			$("#msg").append("<fmt:message key='config.apply.to.null' />.<br/>");
			return ;
		}
		var url = path + "/stb!findByGroupId.action";
		var params = {"groupId":$("#chgGroup").val()};
		var mac;
		$.post(url, params, function(list) {
			var macStr = "";
			for(stb in list['stb']) {
				macStr = macStr + list['stb'][stb]['stbMac'] + "l";
			}
			sendConfig(macStr);
		}, "json");
		$("#groupDiv").hide();
}
function cancelEdit() {
	$("#apply").show();
	$("#save").hide();
	$("#addrDiv").hide();
	$("#groupDiv").hide();
	window.location = path + "/config!confContent.action?confId=${confId}&confName=${confName}&updateTime=${updateTime}";
}
function getStbInfo() {
	$("#tbl").show();
	$("#groupTblDiv").hide();
	$("#msg").empty();
	$("#table").empty();
	$("#table").append("<thead><th><input type='checkbox' id='checkAll' onclick='selectAll();'></th>" + 
			"<th><fmt:message key='Mac' /></th><th><fmt:message key='Addr' /></th>" + 
			"<th><fmt:message key='Status' /></th><th><fmt:message key='Operation' /></th>" + 
			"</thead><tbody>");
	var url = path + "/stb!stbList.action";
	var params = {"provinceId":$("#provinceId").val(), "cityId":$("#cityId").val(), 
			"districtId":$("#districtId").val(), "stbStatus":$("#stbStatus").val(), 
			"pageSize":$("#pageSize").val(), "page":$("#page").val() };
	$.post(url, params, function(data) {
		var mac;
		var provinceId, cityId, districtId;
		var provinceName, cityName, districtName;
		var addrDetail, groupId, groupName;
		var stbStatus, msg;
		for(i in data['stb']) {
			mac = data['stb'][i]['stbMac'];
			provinceId = data['stb'][i]['provinceId'];
			cityId = data['stb'][i]['cityId'];
			districtId = data['stb'][i]['districtId'];
			provinceName = data['stb'][i]['provinceName'];
			cityName = data['stb'][i]['cityName'];
			districtName = data['stb'][i]['districtName'];
			addrDetail = (data['stb'][i]['addrDetail']).replace(/[ ]/g, "");
			groupId = data['stb'][i]['groupId'];
			groupName = data['stb'][i]['groupName'];
			stbStatus = data['stb'][i]['stbStatus'];
			msg = {"online":"<fmt:message key='online'/>", "offline":"<fmt:message key='offline'/>", 
					"active":"<fmt:message key='active'/>", "pending":"<fmt:message key='pending'/>",
					"refused":"<fmt:message key='refused'/>"};
			$("#table").append("<tr><td><input type='checkbox' id='" + mac + "' class='chk'></td><td>" + 
					mac + 
					"</td>" + 
					"<td>" + provinceName + cityName + districtName + addrDetail + "</td>" + 
					"<td>" + msg[stbStatus] + "</td><td>" + 
					"<fmt:message key='Edit' />" + 
					"</td></tr>");
		}
		pageNums = parseInt(data['pageNums']);
		var page = data['page'];
		var previous = parseInt(page) - 1;
		var next = parseInt(page) + 1;
		$("#table").append("</tbody><tfoot id='tfoot'><tr><td colspan='5'><div class='dd' align='center' >" + page + 
				"/" + pageNums + " <fmt:message key='Page' />&nbsp;&nbsp;<a href='javascript:;' onclick=page(1)>" + 
				"<fmt:message key='First' /></a>" + 
				"<a href='javascript:;' onclick=page(" + previous + ")><fmt:message key='Previous' /></a>" + 
				"<a href='javascript:;' onclick=page(" + next + ")><fmt:message key='Next' /></a>" + 
				"<a href='javascript:;' onclick=page(" + pageNums + ")><fmt:message key='Last' /></a>" + 
				"&nbsp;&nbsp;<fmt:message key='EveryPage' /><select id='size' onchange=setPagesize('getStbInfo()');>" + 
				"<option value='10'>10</option><option value='20'>20</option>" + 
				"<option value='30'>30</option></select><fmt:message key='Item' />" + 
				"</div></td></tr></tfoot>");
		$("#size").val($("#pageSize").val());
	}, "json");
}
function setConfigToAddr(){
	var arry = [];
	var id = $("#id").val();
		if($(".chk:checked").size()<1){
				alert("请选择先!");
				return;
		}
		$(".chk:checked").each(function(){
				arry.push($(this).attr("id"));
			});
		url='box-package!updateStbPackageId.action?arry='+arry+'&id='+id;
		alert(url);
		location=url;
}
</script>
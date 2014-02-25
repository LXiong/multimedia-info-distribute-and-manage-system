<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%><%@include file="stb-js.jsp" %>
<div class="operate">
	<button onclick="showAddDlg();" class="btn_117"><fmt:message key="packages.add" /></button>
	<button onclick="showEditDlg();" class="btn_117"><fmt:message key="packages.edit" /></button>
	<button onclick="delPkg();" class="btn_117"><fmt:message key="packages.delete" /></button>
</div>
<!-- List -->
<div id="divPackages"></div>
<!-- Add package div -->
<div id="divAddPkg" >
	<div id="addMsg" align="left" style="color: red; font-size: 12px" ></div>
	<table id="tblAddPkg" style="width: 100%">
		<tr><td><fmt:message key='packages.version' /></td>
			<td><input id="newVersion" name="newVersion" /></td>
		</tr>
		<tr><td><fmt:message key='packages.name' /></td>
			<td><input id="newName" name="newName" /></td>
		</tr>
		<tr><td><fmt:message key='packages.illustrate' /></td>
			<td><input id="newIllustrate" name="newIllustrate" /></td>
		</tr>
	</table>
</div>
<!-- Edit package div -->
<div id="divEditPkg" >
	<div id="msg" align="left" style="color: red; font-size: 12px" ></div>
	<table id="tblEditPkg" style="width: 100%">
		<tr><td><fmt:message key='packages.version' /></td>
			<td><input id="version" name="version" disabled="disabled"/></td>
		</tr>
		<tr><td><fmt:message key='packages.name' /></td>
			<td><input id="name" name="name" onblur="check('name', 'msg');" /></td>
		</tr>
		<tr><td><fmt:message key='packages.illustrate' /></td>
			<td><input id="illustrate" name="illustrate" /></td>
		</tr>
	</table>
</div>
<!-- send package to STBs -->
<div id="msg" align="center" style="color: red; font-size: 12px" ></div>
<div id="apply" align="left">
	<button onclick="applyToAddr();" class="btn_117">
		<fmt:message key="package.deliver.to.stb"/></button>
	<button onclick="applyToGroup();" class="btn_117">
		<fmt:message key="package.deliver.to.group"/></button>
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
		<li><button name="" class="btn_64" onclick="deliverPkgToAddr();" ><fmt:message key="OK" /></button>
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
			<button onclick="deliverPkgToGroup();" class="btn_64"><fmt:message key='OK' /></button>
			<button onclick="cancel('groupDiv');" class="btn_64"><fmt:message key='Cancel' /></button>
		</li>
	</ul>
</div>
<script type="text/javascript">
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	// add new package dialog
	$("#divAddPkg").dialog({ 
		autoOpen: false, 
		show: "blind", 
		title: "<fmt:message key='packages.add' />", 
		buttons: [
	  		{
		  		text: "<fmt:message key='Submit' />", 
		  		click: function() { if(addCheck()){addNewPkg();} }
	  		}, 
	  		{
		  		text: "<fmt:message key='Cancel' />", 
		  		click: function() { $('#divAddPkg').dialog('close'); }
	  		}
	  		]
		}
	);
	// Edit dialog
	$("#divEditPkg").dialog({ 
		autoOpen: false, 
		show: "blind", 
		title: "<fmt:message key='packages.edit' />", 
		buttons: [
	  		{
		  		text: "<fmt:message key='save' />", 
		  		click: function() { updatePackage(); }
	  		}, 
	  		{
		  		text: "<fmt:message key='Cancel' />", 
		  		click: function() { $('#divEditPkg').dialog('close'); }
	  		}
	  		]
		}
	);
	// package list
	pageInit();
	
	$("#addrDiv").hide();
	$("#groupDiv").hide();
	$("#provinceId").change(function(){ getCity("cityId", "provinceId", "districtId") });
	$("#cityId").change(function(){ getDistrict("districtId", "cityId") });
	$("#typeSel").change(function(){ getGroup("typeSel", "groupSel") });
	$("#chgType").change(function(){ getGroup("chgType", "chgGroup") });
});

var grid = null;
/**
 * package list init
 */
function pageInit() {
	var jsonObj = {};
	jsonObj.Rows = ${jsonStr};
	grid = $("#divPackages").ligerGrid(
		{
			url : "packages.action",
			columns : [
					/*{
						display : "<fmt:message key='packages.id' />",
						width : 80,
						name : 'id',
						align : 'left',
						isAllowHide: 'true'
					},*/
					{
						display : "<fmt:message key='packages.version' />",
						width : 80,
						name : 'version',
						align : 'left'
					},
					{
						display : "<fmt:message key='packages.name' />",
						minWidth : 120,
						name : 'name',
						align : 'left'
					},
					{
						display : "<fmt:message key='packages.illustrate' />",
						name : 'illustrate',
						align : 'left',
						minWidth : 200
					},
					{
						display : "<fmt:message key='packages.create.time' />",
						width : 120,
						name : 'createTime',
						align : 'left',
						render: function (row)
						{
							//var time = new Date(row.createTime);
							//dateFormat(time, "yyyy-MM-dd hh:mm");
							return new Date(row.createTime);
						}
					},
					{
						display : "<fmt:message key='packages.update.time' />",
						width : 120,
						name : 'updateTime',
						align : 'left',
						render: function (row)
						{
							return new Date(row.updateTime);
						}
					}/*,
					{
						display : "<fmt:message key='packages.operation' />",
						align : 'left',
						minWidth : 100,
						render : function(row) {
							var html = "<a href='###' onclick=delPackage('"
									+ row.version
									+ "'); ><fmt:message key='packages.delete' /></a>";
							return html;
						}
					}*/ ],
			data : jsonObj,
			pageSize : 20,
			checkbox : false,
			dataAction : 'server',
			sortName : 'version',
			width : '100%',
			height : '90%'
		}
	);
}

/**
 * show edit package dialog
 */
function showEditDlg() {
	var row = grid.getSelectedRow();
	if(row == null) {
		alert("Please select a package for edit!");
		return;
	}
	$("#msg").empty();
	$("#divEditPkg").dialog( "open" );
	$("#divEditPkg").dialog( "option", "height", 200 );
	$("#divEditPkg").dialog( "option", "width", 400 );
	
	$("#version").val(row.version);
	$("#name").val(row.name);
	$("#illustrate").val(row.illustrate);
}

/**
 * update package
 */
function updatePackage() {
	var url = "packages!update.action";
	var params = {"version":$("#version").val(), 
			"name":$("#name").val(), "illustrate":$("#illustrate").val()};
	$.post(url, params, function() {
		$("#divEditPkg").dialog( "close" );
		window.location = "packages.action";
	});
}

/**
 * show add dialog
 */
function showAddDlg() {
	$("#addMsg").empty();
	$("#divAddPkg").dialog( "open" );
	$("#divAddPkg").dialog( "option", "height", 200 );
	$("#divAddPkg").dialog( "option", "width", 400 );
}

/**
 * add a new package
 */
function addNewPkg() {
	//var manager = $("#divPackages").ligerGetGridManager();
	//manager.addRow();
	//grid.addRow();
	var url = "packages!create.action";
	var params = {"version":$("#newVersion").val(), 
			"name":$("#newName").val(), "illustrate":$("#newIllustrate").val()};
	$.post(url, params, function() {
		$("#divAddPkg").dialog( "close" );
		window.location = "packages.action";
	});
}

/**
 * delete a package
 */
function delPkg() {
	var row = grid.getSelectedRow();
	if (confirm("You will delete the Package which version is " + row.version
			+ " ?")) {
		var url = "packages!del.action";
		var params = {"version" : row.version};
		$.post(url, params, function() {
			window.location = "packages.action";
		});
	}
}

/**
 * check input value
 */
function check(id, msg) {
	var val = $("#" + id).val();
	$("#" + msg).empty();
	if(val==null || val=="") {
		$("#" + msg).append("The package " + id + " can not be null!");
		$("#" + msg).focus();
	}
}

/**
 * check the input
 */
function addCheck() {
	var version = $("#newVersion").val();
	var name = $("#newName").val();
	if(version==null || version=="") {
		$("#addMsg").empty();
		$("#addMsg").append("Please input the version of the New Package!");
		$("#version").focus();
		return false;
	}
	if(name==null || name=="") {
		$("#addMsg").empty();
		$("#addMsg").append("Please input the name of the New Package!");
		$("#name").focus();
		return false;
	}
	return true;
}

/**
 * deliver package to STBs
 */
function applyToAddr() {
	getProvince("provinceId");
	$("#addrDiv").show();
	$("#groupDiv").hide();
}

/**
 * deliver package to STBs
 */
function applyToGroup() {
	$("#msg").empty();
	getType("chgType", "chgGroup");
	$("#groupDiv").show();
	$("#addrDiv").hide();
}

/**
 * 
 */
function deliverPkgToAddr(){
	var row = grid.getSelectedRow();
	if(row == null) {
		alert("Please select a package!");
		return;
	}
	//var id = $("#id").val();
	var arry = [];
	if($(".chk:checked").size() < 1){
		alert("请选择先!");
		return;
	}
	$(".chk:checked").each(function(){
		arry.push($(this).attr("id"));
	});
	url='packages!updateStbPackageVersion.action?arry=' + arry + '&version=' + 
			row.version + '&packageId=' + row.id;
	//alert(url);
	location=url;
}

/**
 * 
 */
function deliverPkgToGroup(){
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
		deliverPkg(macStr);
	}, "json");
	$("#groupDiv").hide();
}

/**
 * 
 */
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
</script>

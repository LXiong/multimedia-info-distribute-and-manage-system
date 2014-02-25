<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %><%@include file="stb-js.jsp" 
%>
<div>
	<div align="left">
		<label><fmt:message key="config.file.name"/></label>
		<input id="fileName" onchange="checkFileName();" disabled="disabled" value="${confName }"></input>
		<label><fmt:message key="config.applied.to.group"/>:&nbsp;<c:forEach var="group" varStatus="listStatus" 
			items="${groupList}">${group.groupName}<c:if test="${!listStatus.last}">,</c:if>
		</c:forEach></label>
	</div>
	<table id="configPropts" class="linetable">
		<thead><tr>
			<td style='text-align: left'><label><fmt:message key="config.property.key"/></label></td>
			<td style='text-align: left'><label><fmt:message key="config.property.value"/></label></td>
		</tr></thead>
		<c:forEach var="config" items="${configs }">
			<tr>
				<td style='text-align: left'><label><fmt:message key="${config.configKey }"/></label></td>
				<td style='text-align: left'><input id="${config.configId }" disabled="disabled" 
					value="${config.configValue }"></input></td>
				<!--<td></td>-->
			</tr>
		</c:forEach>
	</table>
	<!--<table id="addPropts">
	</table>-->
</div>
<div id="msg" align="center" style="color: red; font-size: 12px" ></div>
<div align="right">
	<div id="editConfigBt">
	<button onclick="editConfig();" class="btn_64"><fmt:message key="Edit"/></button>
	</div>
	<button onclick="back();" class="btn_64"><fmt:message key="back"/></button>
</div>
<div id="save" align="left">
	<button onclick="if(checkConfig()){saveEdit();}" class="btn_64"><fmt:message key="save"/></button>
	<button onclick="cancelEdit();" class="btn_64"><fmt:message key="Cancel"/></button>
</div>
<div id="apply" align="left">
	<button onclick="applyToAddr();" class="btn_117">
		<fmt:message key="config.apply.to.stb"/></button>
	<button onclick="applyToGroup();" class="btn_117">
		<fmt:message key="config.apply.to.group"/></button>
</div>
<hr></hr>
<div id="addrDiv" style="position:relative;width:100%;text-align:left">
	<ul class="query cf">
		
		<li><label><fmt:message key="top.level" /></label>
			<select id="typeId" name="typeId"><option value="" ><fmt:message key="Total" /></option></select>
		</li>
		<li><label><fmt:message key="second.level" /></label>
			<select id="groupId" name="groupId"><option value="" ><fmt:message key="Total" /></option></select>
		</li>
		<li><label>MAC</label>
			<input id="stbMac" name="stbMac" value="" size="15px">
		</li>
	</ul>
	<div class="btn" style="position:absolute;top:0px;_top:0px;left:700px">
		<input type="hidden" id="page" value=""></input>
		<button class="btn1_64" onclick="getPage(0);"><fmt:message key="Query" /></button>
	</div>
	
	<div id="tbl">
		<div class="operate" id="pageDiv1">
		</div>
		<table id="table" class="linetable"></table>
		<div class="operate" id="pageDiv2">
		</div>
	</div>
	<ul>
		<li><button name="" class="btn_64" onclick="setConfigToAddr();" ><fmt:message key="OK" /></button>
			<button onclick="cancel('addrDiv');" class="btn_64"><fmt:message key='Cancel' /></button>
		</li>
	</ul>
</div>
<div id="groupDiv" style="position:relative;width:100%;text-align:left">
	<ul>
		<li><label><fmt:message key="GroupType" /></label>
			<select id="chgType"><option value="" ><fmt:message key="Select" /></option></select>
			<label><fmt:message key="Group" /></label>
			<select id="chgGroup"><option value="" ><fmt:message key="Select" /></option></select>
		</li>
	</ul>
	<ul>
		<li>
			<button onclick="setConfigToGroup();" class="btn_64"><fmt:message key='OK' /></button>
			<button onclick="cancel('groupDiv');" class="btn_64"><fmt:message key='Cancel' /></button>
		</li>
	</ul>
</div>
<script type="text/javascript">
//var rows = 0;
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	$("#addrDiv").hide();
	$("#groupDiv").hide();
	$("#add").hide();
	$("#save").hide();
	$("#tbl").hide();
	
	//$("#provinceId").change(function(){ getCity("cityId", "provinceId", "districtId") });
	//$("#cityId").change(function(){ getDistrict("districtId", "cityId") });
	$("#typeSel").change(function(){ getGroup("typeSel", "groupSel") });
	$("#chgType").change(function(){ getGroup("chgType", "chgGroup") });
	getType("typeId", "groupId");
	$("#typeId").change(function(){ getGroup("typeId", "groupId") });
	
	if("${userName}" != "admin") {
		$("#editConfigBt").hide();
	}
});
/**
 * 
 */
function getPage(page) {
	$("#page").val(page);
	getStbInfo();
}
/**
 * 
 */
function back() {
	window.location = path + "/conf!execute.action";
}
/**
 * 
 */
function applyToAddr() {
	$("#msg").empty();
	getProvince("provinceId");
	$("#addrDiv").show();
	$("#groupDiv").hide();
}
/**
 * 
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
function setConfigToAddr(){
	var macStr = "";
	
	if( $(".chk").is(':checked') ) {
		$(".chk").each(function(index) {
			if($(this).is(':checked')) {
				macStr = macStr + $(this).attr("id") + "l";
			}
		});
		sendConfig(macStr);
	} else {
		$("#msg").empty();
		$("#msg").append("<fmt:message key='config.apply.to.null' />.<br/>");
	}
	//}
}
/**
 * 
 */
function setConfigToGroup(){
	//if( !$(".chk").is(':checked') ) {
		if( $("#chgGroup").val() == "" ) {
			$("#msg").empty();
			$("#msg").append("<fmt:message key='config.apply.to.null' />.<br/>");
			return ;
		}
		var url = path + "/stb!stbList.action";
		var params = {"groupId":$("#chgGroup").val()};
		var mac;
		$.post(url, params, function(list) {
			var macStr = "";
			for(stb in list['stb']) {
				macStr = macStr + list['stb'][stb]['stbMac'] + "l";
			}
			//$("#msg").empty();
			var configUrl = path + "/stb!sendConfig.action";
			var configParams = {"macStr":macStr, "confId":${confId }, "updateTime":${updateTime}};
			$.post(configUrl, configParams, function(data) {
				var groupUrl = path + "/group!update.action";
				var groupParams = {"groupId":$("#chgGroup").val(), "confId":${confId }};
				$.post(groupUrl, groupParams, function(){
					window.location = path + "/config!confContent.action?confId=${confId}" + 
						"&confName=${confName}&updateTime=${updateTime}";
					if(data != "") {
						$("#msg").append(data);
					}
				});
				//setTimeout("getStbInfo()", 1000);
			});
			//sendConfig(macStr);
		}, "json");
		
}
/**
 * send commend
 */
function sendConfig(macStr) {
	$("#msg").empty();
	var url = path + "/stb!sendConfig.action";
	var params = {"macStr":macStr, "confId":${confId }, "updateTime":${updateTime}};
	$.post(url, params, function(data) {
		if(data != "") {
			$("#msg").append(data);
		} else {
			$("#msg").append("<fmt:message key='Success' />");
		}
		$("#groupDiv").hide();
		$("#addrDiv").hide();
		//setTimeout("getStbInfo()", 1000);
	});
}
/**
 * 
 */
function editConfig() {
	$("#apply").hide();
	$("#save").show();
	$("#addrDiv").hide();
	$("#groupDiv").hide();
	//$("#add").show();
	$("input").each( function() {
		if( $(this).is(':disabled') ) {
			$(this).removeAttr("disabled");
		}
	});
}
/**
 * 
 */
function saveEdit() {
	var url = path + "/conf!edit.action";
	var params = {"confId":"${confId}", "confName":$("#fileName").val()};
	$.post(url, params, function(exist) {
		$("#msg").empty();
		if(exist != "") {
			$("#msg").append("<fmt:message key='config.file.name.exist' />.<br/>");
			return;
		}
		
		url = path + "/config!edit.action";
		<c:forEach var="config" items="${configs }" >
			if( $("#${config.configId }").val() != "" ) {
				params = {"configId":"${config.configId}", "configValue":$("#${config.configId }").val()};
				$.post(url, params);
			}
		</c:forEach>
		$("input").each( function() {
			$(this).attr("disabled", true);
		});
		// window.location = path + "/conf!execute.action";
		$("#msg").append("<fmt:message key='config.file.save.success' />.<br/>");
	});
}
/**
 * 
 */
function cancelEdit() {
	$("#apply").show();
	$("#save").hide();
	$("#addrDiv").hide();
	$("#groupDiv").hide();
	window.location = path + "/config!confContent.action?confId=${confId}&confName=${confName}&updateTime=${updateTime}";
}
/**
 * list stbs
 */
function getStbInfo() {
	$("#tbl").show();
	$("#msg").empty();
	$("#table").empty();
	$("#table").append("<thead><th><input type='checkbox' id='checkAll' onclick='selectAll();'></th>" + 
			"<th><fmt:message key='Mac' /></th><th><fmt:message key='Addr' /></th>" + 
			"<th><fmt:message key='Status' /></th></thead><tbody>");
	var url = path + "/stb!stbList.action";
	var params = {"provinceId":$("#provinceId").val(), "cityId":$("#cityId").val(), 
			"districtId":$("#districtId").val(), "typeId":$("#typeId").val(), 
			"groupId":$("#groupId").val(), "stbStatus":$("#stbStatus").val(), 
			"pageSize":$("#pageSize").val(), "page":$("#page").val() };
	var mac;
	var provinceId, cityId, districtId;
	var provinceName, cityName, districtName;
	var addrDetail;//, groupId, groupName;
	var msg;
	$.post(url, params, function(list) {
		var pageNums = parseInt(list['pageNums']);
		var page = parseInt(list['page']);
		var previous = parseInt(page) - 1;
		var next = parseInt(page) + 1;
		var item = 0;
		var str = "";
		str = str + "<div class='page'>" + page + "/" + pageNums + "&nbsp;";
		if(page > 3 && pageNums < 5) {
			str = str + "<a href='###' onclick='getPage(${1});'> |&lt; </a>" + 
				"<a href='###' onclick='getPage(" + (page -1) + ");'> &lt; </a>";
		}
		for(i in list['pageArr']) {
			item = parseInt(list['pageArr'][i]);
			if( page == item ) {
				str = str + "<span>" + item + "</span>";
			} else if(item > 0 && item <= pageNums) {
				str = str + "<a href='###' onclick='getPage(" + item + ");'>" + item + "</a>";
			}
		}
		if(page + 2 < pageNums && pageNums > 5) {
			str = str + "<a href='###' onclick='getPage(" + (page + 1) + " );'> &gt; </a>" +
	    		"<a href='###' onclick='getPage(" + pageNums + ");'> &gt;| </a></div>";
		}
		$("#pageDiv1").empty();
		$("#pageDiv1").append("<table width='100%'><tr><td align='left'></td><td>" + str 
				+ "</td></tr></table>");
		for(i in list['stb']) {
			mac = list['stb'][i]['stbMac'];
			provinceId = list['stb'][i]['provinceId'];
			cityId = list['stb'][i]['cityId'];
			districtId = list['stb'][i]['districtId'];
			provinceName = list['stb'][i]['provinceName'];
			cityName = list['stb'][i]['cityName'];
			districtName = list['stb'][i]['districtName'];
			addrDetail = (list['stb'][i]['addrDetail']).replace(/[ ]/g, "");
			//groupId = list['stb'][i]['groupId'];
			//groupName = list['stb'][i]['groupName'];
			msg = {"online":"<fmt:message key='online'/>", "offline":"<fmt:message key='offline'/>", 
					"active":"<fmt:message key='active'/>", "pending":"<fmt:message key='pending'/>",
					"refused":"<fmt:message key='refused'/>"};
			$("#table").append("<tr><td><input type='checkbox' id='" + mac + "' class='chk'></td><td>" + 
					mac + "</td><td>" + provinceName + cityName + districtName + addrDetail + "</td>" + 
					"<td>" + msg[list['stb'][i]['stbStatus']] + "</td></tr>");
		}
		$("#table").append("</tbody></table>");
		$("#pageDiv2").empty();
		$("#pageDiv2").append(str);
	}, "json");
}
</script>

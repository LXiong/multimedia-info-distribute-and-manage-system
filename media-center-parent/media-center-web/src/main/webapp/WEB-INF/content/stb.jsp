<%@page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%><%@include file="taglib.jsp" %><%@include file="stb-js.jsp" 
%><%@include file="query.jsp"  %>
<div id="msg" align="center" style="color: red; font-size: 12px" ></div>
<div class="operate">
	<table><tr>
		<td align='left'><div class="btn">
			<yun:auth functions="CustomerDeviceManage" >
			<button id="btPause" disabled="disabled" onclick="playerOperation('pause');" class="btn_64d"><fmt:message key='Pause' /></button>
			<button id="btResume" disabled="disabled" onclick="playerOperation('resume');" class="btn_64d"><fmt:message key='Resume' /></button>
			<!--<button onclick="if(check()){shutdown();}"><fmt:message key='Shutdown' /></button>-->
			<button id="btRestart" disabled="disabled" onclick="restart();" class="btn_64d"><fmt:message key='Restart' /></button>
			<button id="btDelete" disabled="disabled" onclick="stbDelete();" class="btn_64d"><fmt:message key='Delete' /></button>
			</yun:auth>
			<button id="btUploadLog" disabled="disabled" onclick="uploadLog();" class="btn_117d"><fmt:message key='upload.log' /></button>
			<%-- <button id="btScreenShot" disabled="disabled" onclick="playerOperation('screenshot');" class="btn_64d"><fmt:message key='screen.shot' /></button> --%>
			&nbsp;||&nbsp;<button onclick="exportData();" class="btn_64"><fmt:message key='export.data' /></button>
		</div></td>
		<!-- <td><div class="page">
		    <yun:pageLink totalPage="${pageNums}" currentPage="${page}" 
		    dispCount="5" link="###" middle="true" onclick="getPage({p})"/>
		</div></td> -->
	</tr></table>
</div>
<!-- List -->
<div id="tbl"></div>
<!-- detail infos -->
<div id="infoDiv">
	<table id="infoTbl" class="linetable" style="text-align: left"></table>
	<input id="policyKey" type="hidden" value=""></input>
	<!--<input id="policyVersion" type="hidden" value=""></input>-->
</div>
<!-- Edit -->
<div id="stb">
	<table class="linetable">
		<tr><td style="text-align: left"><fmt:message key="Mac" /></td>
			<td style="text-align: left"><label id="mac" ></label></td></tr>
		<tr>
			<td style="text-align: left"><fmt:message key="Addr" /></td>
			<td style="text-align: left">
				<div id="setAddr">
					<select id="selprov" ><option value=""><fmt:message key="Select" /></select>
					<select id="selcity" ></select>
					<select id="seldist" ></select>
				</div>
			</td>
		</tr>
		<tr><td style="text-align: left"><fmt:message key="Address" /></td>
			<td style="text-align: left"><input id="addrDetail"></input></td></tr>
		<tr><td style="text-align: left"><fmt:message key="shop.no" /></td>
			<td style="text-align: left"><input id="eShopNo"></input></td></tr>
		<tr><td style="text-align: left"><fmt:message key="shop.name" /></td>
			<td style="text-align: left"><input id="eShopName"></input></td></tr>
		<tr><td style="text-align: left"><fmt:message key="stb.contacts" /></td>
			<td style="text-align: left"><input id="contacts"></input></td></tr>
		<tr><td style="text-align: left"><fmt:message key="stb.phone" /></td>
			<td style="text-align: left"><input id="phone"></input></td></tr>
		<tr><td style="text-align: left"><fmt:message key="Group" /></td>
			<td style="text-align: left"><div id="selGroup">
			<fmt:message key="top.level" />:&nbsp;
			<select id="typeSel"><option value="" ><fmt:message key="Select" /></option></select>
			<fmt:message key="second.level" />:&nbsp;
			<select id="groupSel"><option value="" ><fmt:message key="Select" /></option></select>
		</div></td></tr>
		<tr>
			<yun:auth functions="CustomerDeviceManage">
			<td style="text-align: left"><div id="editStatus" >
				<fmt:message key="status.change" /></div></td>
			<td style="text-align: left"><div id="editStatus1" >
				<select id="stbStatusEdit" name="stbStatusEdit" >
					<option value='-1'  selected='selected'><fmt:message key='no.change' /></option>
					<option value='notinstalled' ><fmt:message key='notinstalled' /></option>
					<option value='nointernet' ><fmt:message key='nointernet'/></option>
				</select></div>
			</td></yun:auth>
		</tr>
	</table>
</div>
<!-- manager screenshot -->
<div id="screenshot">
	<table id="picTbl" class="linetable">
	</table>
</div>
<!-- manager media files -->
<div id="mediaFiles">
	<input type="hidden" id="filesKey" name="filesKey" value="" ></input>
	<table id="fileTbl" class="linetable">
	</table>
</div>
<script type="text/javascript">
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	// STB infos  div
	$("#infoDiv").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true,
		position: "center", 
		title: "<fmt:message key='stb.infos.show.dialog.title' />", 
		buttons: [
		  		{
                   	text: "<fmt:message key='close' />", 
                   	click: function() { $('#infoDiv').dialog('close'); }
               	}
               	] 
               	});
	// Edit div
	$("#stb").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true,
		position: "center",
		title: "<fmt:message key='stb.infos.modify.dialog.title' />", 
		buttons: [
		  		{
			  		text: "<fmt:message key='Submit' />", 
			  		click: function() { updateStbInfos(); }
		  		}, 
		  		{
			  		text: "<fmt:message key='Cancel' />", 
			  		click: function() { $('#stb').dialog('close'); }
		  		}
		  		]
			});
	// screenshot manage div
	$("#screenshot").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true,
		position: "center",
		title: "<fmt:message key='screen.pic' />", 
		buttons: [
			  		{
				  		text: "<fmt:message key='Delete' />", 
				  		click: function() { deletePics('${stb.stbMac}'); }
			  		}, 
			  		{
				  		text: "<fmt:message key='Cancel' />", 
				  		click: function() { $('#screenshot').dialog('close'); }
			  		}
			  		]
			});
	// screenshot manage div
	$("#mediaFiles").dialog({ 
		autoOpen: false, 
		show: "blind", 
		modal: true,
		position: "center",
		title: "<fmt:message key='file.list' />", 
		buttons: [
			  		/* {
				  		text: "<fmt:message key='Delete' />", 
				  		click: function() { deleteFiles('${stb.stbMac}'); }
			  		}, */ 
			  		{
				  		text: "<fmt:message key='Cancel' />", 
				  		click: function() { $('#mediaFiles').dialog('close'); }
			  		}
			  		]
			});
	//getProvince("provinceId", "cityId", "districtId", "${provinceId}", "${cityId}", "${districtId}");
	//$("#provinceId").change(function(){ getCity("cityId", "provinceId", "districtId") });
	//$("#cityId").change(function(){ getDistrict("districtId", "cityId") });
	$("#selprov").change(function(){ getCity("selcity", "selprov", "seldist") });
	$("#selcity").change(function(){ getDistrict("seldist", "selcity") });
	getProvince("selprov");

	$("#typeSel").change(function(){ getGroup("typeSel", "groupSel") });
	//$("#chgType").change(function(){ getGroup("chgType", "chgGroup") });
	$("#typeId").change(function(){ getGroup("typeId", "groupId", ${groupId}) });
	getType("typeId", "groupId", ${typeId}, ${groupId});
	//getStbInfo(${page});

	$("#stbStatus").val('${stbStatus}');
	
	pageInit();
});

/**
 * 
 */
function getFiles(stbMac) {
	$("#msg").empty();
	$("#fileTbl").empty();
	$("#mediaFiles").dialog( "open" );
	$("#mediaFiles").dialog( "option", "height", 430 );
	$("#mediaFiles").dialog( "option", "width", 700 );
	var url = path + "/stb!getFiles.action";
	var params = {"stbMac":stbMac};
	$.post(url, params, function(data) {
		if(data == "<fmt:message key='connect.control.server.failure' />") {
			$("#msg").append("<fmt:message key='connect.control.server.failure' />");
			$('#mediaFiles').dialog('close');
			return;
		} else if(data == "<fmt:message key='stb.offline' />") {
			$("#msg").append("<fmt:message key='stb.offline' />");
			$('#mediaFiles').dialog('close');
			return;
		}
		//$("#policyVer").empty();
		//$("#policyVer").append("<fmt:message key='policy.searching' />");
		$("#filesKey").val(data);
		setTimeout("getFilesByKey(3)", 2000);
	});
}

/**
 * get file list
 */
function getFilesByKey(i) {
	$("#msg").empty();
	$("#fileTbl").empty();
	var url = path + "/stb!getFilesByKey.action";
	var params = {"filesKey":$("#filesKey").val()};
	$.post(url, params, function(data) {
		if(data == "<fmt:message key='connect.control.server.failure' />") {
			if(i == 1) {
				$("#msg").append("<fmt:message key='connect.control.server.failure' />");
				$('#mediaFiles').dialog('close');
				//$("#policyVer").empty();
			}
		} else if(data == "<fmt:message key='stb.file.list.get.failed' />") {
			if(i == 1) {
				$("#msg").append("<fmt:message key='stb.file.list.get.failed' />");
				$('#mediaFiles').dialog('close');
				//$("#policyVer").empty();
			}
		} else {
			//$("#policyVer").empty();
			//$("#policyVer").append(data);
			$("#fileTbl").append("<thead><tr><th><input type='checkbox' id='fileChkAll' " + 
					"onclick='selectAllFiles();'></th><th><fmt:message key='file.align' />" + 
					"</th><th><fmt:message key='file.name' /></th></tr></thead><tbody>");
			for(i in data) {
				$("#fileTbl").append("<tr><td><input type='checkbox' id='" + data[i] + "' " +
						"class='fileChk'></td><td>" + i + "</td><td>" + data[i] + "</td></tr>");
			}
			$("#fileTbl").append("</tbody>");
			return;
		}
		if(i>0) {
			setTimeout("getFilesByKey(" + (i-1) + ")", 3000);
		}
	}, "json");
}

/**
 * checkbox
 */
function selectAllFiles() {
	var att =  $("#fileChkAll").attr("checked");
	$(".fileChk").attr("checked", att);
}

/**
 * delete the selected files 
 */
function deleteFiles(stbMac) {
	if( confirm("You will delete these meidas ?") ) {
		var url = path + "/stb!deleteFiles.action";
		var fileStr = "";
		$(".fileChk").each(function(index) {
			if($(this).is(':checked')) {
				fileStr = fileStr + $(this).attr("id") + ",";
			}
		});
		$("#mediaFiles").dialog( "close" );
		var params = {"fileStr":fileStr, "stbMac":stbMac};
		$.post(url, params);//, function() {
			//setTimeout("getPage(${page});", 1000);
			//$("#fileTbl").empty();
			//$("#mediaFiles").dialog( "close" );
		//});
	}
}

/**
 * 
 */
function getPage(page) {
	$("#page").val(page);
	$("#queryForm").attr('action','stb.action');
	$("#queryForm").submit();
}
/**
 * STB detail infos
 */
function getInfo(mac, provinceName, cityName, districtName, addrDetail, 
		stbStatus, groupName, configFile, activePolicySuccessNum, activePolicy, 
		activePolicyFailedNum, packageName, contacts, phone, cpu, nmem, disk) {
	$("#msg").empty();
	$("#stb").dialog( "close" );
	$("#infoDiv").dialog( "open" );
	$("#infoDiv").dialog( "option", "height", 430 );
	$("#infoDiv").dialog( "option", "width", 700 );
	$("#infoTbl").empty();
	if(!isNaN(cpu) && cpu > 0) {
		cpu = cpu + "%";
	} else {
		cpu = "";
	}
	if(!isNaN(nmem) && nmem > 0) {
		nmem = nmem + "%";
	} else {
		nmem = "";
	}
	if(!isNaN(disk) && disk > 0) {
		disk = disk + "%";
	} else {
		disk = "";
	}
	$("#infoTbl").append("<tr><td style='text-align: left'><fmt:message key='Mac' />" + 
			"</td><td style='text-align: left'>" + mac + "</td></tr><tr>" + 
			"<td style='text-align: left'><fmt:message key='Address' /></td>" + 
			"<td style='text-align: left'>" + provinceName + cityName + 
			districtName + unescape(addrDetail) + "</td></tr><tr>" + 
			"<td style='text-align: left'><fmt:message key='stb.contacts' /></td>" + 
			"<td style='text-align: left'>" + contacts + "</td></tr><tr>" + 
			"<td style='text-align: left'><fmt:message key='stb.phone' /></td>" + 
			"<td style='text-align: left'>" + phone + "</td></tr><tr>" + 
			"<td style='text-align: left'><fmt:message key='config.file.name' />" + 
			"</td><td style='text-align: left'>" + configFile + "</td></tr>" + 
			//"<tr><td><fmt:message key='policy.version' /></td><td><div id='policyVer'>" + 
			//"<a href='javascript:;' onclick=getPolicyVer('" + mac + 
			//"')><fmt:message key='Query' /></a></div></td></tr>" + 
			"<tr><td style='text-align: left'>" + 
			"<fmt:message key='policy.media.download.success.num' /></td>" + 
			"<td style='text-align: left'>" + activePolicySuccessNum + "</td></tr>" +
			"<tr><td style='text-align: left'>" + 
			"<fmt:message key='policy.media.download.failure.num' /></td>" + 
			"<td style='text-align: left'>" + activePolicyFailedNum + "</td>" + 
			"</tr><tr><td style='text-align: left'><fmt:message key='package.name' />" + 
			"</td><td style='text-align: left'>" + packageName + "</td></tr>" + 
			"</tr><tr><td style='text-align: left'><fmt:message key='cup.use' />" + 
			"</td><td style='text-align: left'>" + cpu + "</td></tr>" + 
			"</tr><tr><td style='text-align: left'><fmt:message key='mem.use' />" + 
			"</td><td style='text-align: left'>" + nmem + "</td></tr>" + 
			"</tr><tr><td style='text-align: left'><fmt:message key='disk.use' />" + 
			"</td><td style='text-align: left'>" + disk + "</td></tr>");
}
/**
 * Edit STB infos
 */
function edit(mac, provinceId, cityId, districtId, addrDetail, typeId, 
		groupId, shopNo, shopName, contacts, phone, stbStatus) {
	if("online"!=stbStatus && "pause"!=stbStatus) {
		$("#editStatus").show();
		$("#editStatus1").show();
	} else {
		$("#editStatus").hide();
		$("#editStatus1").hide();
	}
	$("#msg").empty();
	$("#infoDiv").dialog( "close" );
	$("#stb").dialog( "open" );
	$("#stb").dialog( "option", "height", 360 );
	$("#stb").dialog( "option", "width", 700 );
	$("#mac").text(mac);
	$("#addrDetail").val(unescape(addrDetail));
	$("#eShopNo").val(shopNo);
	$("#eShopName").val(shopName);
	$("#contacts").val(contacts);
	$("#phone").val(phone);
	$("#selprov").val(provinceId);
	$("#typeSel").val(typeId);
	// load cities
	getCity("selcity", "selprov", "seldist", cityId, districtId);
	// $("#selcity").val(cityId);
	
	getType("typeSel", "groupSel", typeId, groupId);
	
}
/**
 * post new infos
 */
function updateStbInfos() {
	var url = path + "/stb!updateStb.action";
	var params = {"stbMac":$("#mac").text(), "provinceId":$("#selprov").val(), 
			"cityId":$("#selcity").val(), "districtId":$("#seldist").val(), 
			"addrDetail":$("#addrDetail").val(), "typeId":$("#typeSel").val(), 
			"groupId":$("#groupSel").val(), "shopNo":$("#eShopNo").val(), 
			"shopName":$("#eShopName").val(), "contacts":$("#contacts").val(), 
			"phone":$("#phone").val(), "stbStatusEdit":$("#stbStatusEdit").val() };
	$.post(url, params, function() {
		$("#stb").dialog( "close" );
		getPage(${page});
	});
}
/**
 * 
 */
function stbDelete() {
	$("#msg").empty();
	//$("#chgDiv").hide();
	if( confirm("You will delete these STBs ?") ) {
		var url = path + "/stb!deleteStbs.action";
		var macStr = "";
		$(".chk").each(function(index) {
			if($(this).is(':checked')) {
				macStr = macStr + $(this).attr("id") + "l";
			}
		});
		var params = {"macStr":macStr};
		$.post(url, params, function() {
			setTimeout("getPage(${page});", 3000);
			//getPage(${page});
		});
	}
}

/**
 * 
 */
function getPic(stbMac) {
	$("#picTbl").empty();
	$("#screenshot").dialog( "open" );
	$("#screenshot").dialog( "option", "height", 430 );
	$("#screenshot").dialog( "option", "width", 700 );
	var url = path + "/stb!getPic.action";
	var params = {"stbMac":stbMac};
	$.post(url, params, function(data) {
		$("#picTbl").append("<thead><tr><th><input type='checkbox' id='picChkAll' " + 
				"onclick='selectAllPic();'></th><th><fmt:message key='screen.pic' />" + 
				"</th></tr></thead><tbody>");
		for(i in data) {
			$("#picTbl").append("<tr><td><input type='checkbox' id='" + i + "' " +
					"class='picChk'></td><td><a href='###' " + 
					"onclick='showPic(\"" + data[i] + "\",\"" + stbMac + "\");'>" + 
					data[i] + "</a></td></tr>");
		}
	}, 'json');
	$("#picTbl").append("</tbody>");
}

/**
 * Select All
 */
function selectAllPic() {
	var att =  $("#picChkAll").attr("checked");
	$(".picChk").attr("checked", att);
}

/**
 * delete pictures
 */
function deletePics(stbMac) {
	if( confirm("You will delete these pictures ?") ) {
		var url = path + "/stb!deletePics.action";
		var picStr = "";
		$(".picChk").each(function(index) {
			if($(this).is(':checked')) {
				picStr = picStr + $(this).attr("id") + ",";
			}
		});
		var params = {"picStr":picStr, "stbMac":stbMac};
		$.post(url, params, function() {
			//setTimeout("getPage(${page});", 1000);
			$("#picTbl").empty();
			$("#screenshot").dialog( "close" );
		});
	}
}

/**
 * show picture
 */
function showPic(pic, stbMac) {
	window.open("stb!showPicture.action?stbMac=" + stbMac + "&picture=" + pic);
}

function total(mac){
	location='stb!standAlone.action?mac='+mac;
}

// stb list init
pageInit = function() {
	var jsonObj = {};
	jsonObj.Rows = [${stbListJson }];
	var statusArr = {"online":"<fmt:message key='online'/>", "offline":"<fmt:message key='offline'/>", 
			"active":"<fmt:message key='active'/>", //"pending":"<fmt:message key='pending'/>",
			"pause":"<fmt:message key='Pause'/>", //"refused":"<fmt:message key='refused'/>", 
			"notinstalled":"<fmt:message key='notinstalled'/>", //"updating":"<fmt:message key='updating'/>", 
			"nointernet":"<fmt:message key='nointernet'/>"};
	var downStatusArr = {"updating":"<fmt:message key='stb.media.download.part'/>", 
			"updated":"<fmt:message key='stb.media.download.total'/>"};
	// do init
	//$.ligerDefaults.Grid.root="stbs";
	//$.ligerDefaults.Grid.record="total";
	grid = $("#tbl").ligerGrid({
		url: "stb.action",
		columns: [
		  	{ width: 30, isAllowHide: false, name: 'checkbox', isSort: false,
				render: function (row){
					var html = "<input type='checkbox' id='" + row.stbMac + "' class='chk' onclick=checkStatus('btPause','btResume','btRestart','btScreenShot','btGroup');>";
					return html;
				},
				headerRender: function (column){
					var html = "<input type='checkbox' id='checkAll' onclick=selectAll('btPause','btResume','btRestart','btScreenShot','btGroup');>";
					return html;
				}
			},
          { display: "<fmt:message key='Mac' />", minWidth: 100, 
        	  render: function (row){
        	  var html = "<a href='###' onclick=getInfo(\'" + row.stbMac + "\',\'" + 
        	  row.provinceName + "\',\'" + row.cityName + "\',\'" + row.districtName + "\',\'" + 
        	  escape(row.addrDetail) + "\',\'" + row.stbStatus + "\',\'" + row.groupName + "\',\'" + 
	       	  row.confName + "\',\'" + row.activePolicySuccessNum + "\',\'" + row.activePolicy + "\',\'" + 
	       	  row.activePolicyFailedNum + "\',\'" + row.packageName + "\',\'" + row.contacts + "\',\'" + 
	       	  row.phone + "\',\'" + row.cpu + "\',\'" + row.nmem + "\',\'" + row.disk + 
	       	  "\')>" + row.stbMac + "</a>";
        	  return html;
        	  	/*getInfo(row.stbMac, row.provinceName, row.cityName, row.districtName, 
                	  	row.addrDetail, row.stbStatus, row.groupName, row.confName, 
                	  	row.activePolicySuccessNum, row.activePolicy, 
                	  	row.activePolicyFailedNum, row.packageName, row.contacts, 
                	  	row.phone, row.cpu, row.nmem, row.disk);*/
        	  }, align:'left'
    	  },
          { display: "<fmt:message key='top.level' />", name: 'typeName', minWidth: 60, align:'left' }, 
          { display: "<fmt:message key='second.level' />", name: 'groupName', minWidth: 60, align:'left' }, 
          { display: "<fmt:message key='shop.no' />", name: 'shopNo', minWidth: 60, align:'left' }, 
          { display: "<fmt:message key='shop.name' />", name: 'shopName', minWidth: 60, align:'left' }, 
          { display: "<fmt:message key='PolicyVersion' />", name: 'activePolicy', minWidth: 90, align:'left' }, 
          { display: "<fmt:message key='terminal.ip' />", name: 'terminalIp', minWidth: 90, align:'left' }, 
          { display: "<fmt:message key='Status' />", name: 'stbStatus', minWidth: 60, align:'left', 
        	  render: function(row) {
        	  	var sts = statusArr[row.stbStatus];
				var html = "<label id='ol_status_" + row.stbMac + "'>" + sts + "</label>";
				return html;
    		}
  		  }, 
          { display: "<fmt:message key='stb.media.download.status' />", 
  			  name: 'downloadStatus', minWidth: 80, align:'left', 
  			  render: function(row) {
	       	  	var sts = downStatusArr[row.downloadStatus];
	       	  	if(typeof(sts) == "undefined") {
	       	  		sts = "未知"
	       	  	}
				var html = "<label>" + sts + "</label>";
				return html;
    		  }
  		  }, 
          { display: "<fmt:message key='stb.policy.integrity' />", name: 'policyIntegrity', 
  			  minWidth: 100, align:'left' }, 
          { display: "<fmt:message key='stb.last.offline.time' />", name: 'lastOfflineTime', 
              	minWidth: 80, align:'left'/*,
              	render: function (row)
				{
					return new Date(row.lastOfflineTime);
				}*/
          }, 
          { display: "<fmt:message key='Operation' />", minWidth: 150, align:'left', 
				render: function(row) {
					var html = "<yun:auth functions='CustomerDeviceEdit' ><a href='###' onclick=edit('" + 
						row.stbMac + "','" + row.provinceId + "','" + row.cityId + "','" + 
	  					row.districtId + "','" + escape(row.addrDetail) + "','" + row.typeId + "','" + 
	  					row.groupId + "','" + row.shopNo + "','" + row.shopName + "','" + 
	  					row.contacts + "','" + row.phone + "','" + row.stbStatus + 
	  					"'); ><fmt:message key='Edit' /></a></yun:auth>" + 
	  					//"&nbsp;&nbsp;<a href='###' onclick=getPic('" + row.stbMac + 
	  					//"') ><fmt:message key='screen.pic' /></a>" + 
	  					"&nbsp;&nbsp;<a href='###' " + 
	  					"onclick=getFiles('" + row.stbMac + "') ><fmt:message key='file.list' /></a>";
					return html;
          		}
          }], pageSize: 20, 
          data: jsonObj,
          dataAction: 'server', 
          checkbox : false,
          sortName: 'stbStatus',
          width: '100%',height:'100%'/*,
          onAfterShowData: function () {
        	  Yun.resolveTargets("#tbl");
          }*/
	});
	//Yun.data['tab'] = window.parent.tab;
}

/**
 * export STB infos
 */
function exportData() {
	var url = path + "/upload!exportData.action";
	$('#queryForm').attr('action',url);
	$('#queryForm').submit();
}
/**
 * 

function page(page) {
	location = "stb.action?page=" + page;
} */
</script>
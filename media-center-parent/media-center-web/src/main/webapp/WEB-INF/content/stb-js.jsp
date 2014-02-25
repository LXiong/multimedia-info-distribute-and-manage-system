<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><script type="text/javascript">
// Global
var path;
//var pageNums = 0;
/**
 * Set path
 * @param param
 * @return
 */
function setPath(param) {
	path = param;
}
/**
 * load provinces
 */
function getProvince(provSelId, citySelId, distSelId, provinceId, cityId, districtId) {
	var url = path + "/province!listProvince.action";
	$.post(url, function(data){
		for(i in data) {
			$("#" + provSelId).append("<option value='" + i + "'>" + data[i] + "</option>");
		}
		if(provinceId != null && provinceId != "") {
			$("#" + provSelId).val(provinceId);
			getCity(citySelId, provSelId, distSelId, cityId, districtId);
		}
	}, "json");
};
/**
 * load cities by province ID
 * cityId is the value
 * districtId is the value
 */
function getCity(citySelId, provSelId, distSelId, cityId, districtId) {
	$("#" + citySelId).empty();
	$("#" + distSelId).empty();
	$("#" + citySelId).append("<option value=''><fmt:message key='Select' /></option>");
	$("#" + distSelId).append("<option value=''><fmt:message key='Select' /></option>");
	var url = path + "/city!listCity.action";
	var params = {"provinceId":$("#" + provSelId).val()};
	$.post(url, params, function(data) {
		for(i in data) {
			$("#" + citySelId).append("<option value='" + i + "'>" + data[i] + "</option>");
		}
		if(cityId != null && cityId != "") {
			$("#" + citySelId).val(cityId);
			// load districts
			getDistrict(distSelId, citySelId, districtId);
			// $("#seldist").val(districtId);
		}
	}, "json");
}
/**
 * load districts by city ID
 * districtId is the value
 */
function getDistrict(distSelId, citySelId, districtId) {
	$("#" + distSelId).empty();
	$("#" + distSelId).append("<option value=''><fmt:message key='Select' /></option>");
	var url = path + "/district!listDistrict.action";
	var params = {"cityId":$("#" + citySelId).val()};
	$.post(url, params, function(data) {
		for(i in data) {
			$("#" + distSelId).append("<option value='" + i + "'>" + data[i] + "</option>");
		}
		if(districtId != null && districtId != "") {
			$("#" + distSelId).val(districtId);
		}
	}, "json");
}
/**
 * cancel notice
 */
function cancel() {
	$("#msg").empty();
	$("#msg").append("<fmt:message key='nothing.has.changed' />");
	for(var i=0; i<arguments.length; i++){
		$("#" + arguments[i]).hide();
	}
}
/**
 * 
 */
function setPagesize(fun) {
	var size = $("#size").val();
	$("#pageSize").val(size);
	eval(fun);
}
/**
 * 
 */
function isAllNotOnline() {
	var flag = false;
 	var tid;
 	var count = 0;
 	//$("input[type=\"checkbox\"]").each(
 	$(".chk").each(
 			function () {
				if( $(this).is(':checked') ) {
	 				tid = $(this).attr("id");
	 				if ($("#ol_status_" + tid).text() != "<fmt:message key='online'/>" && 
	 						$("#ol_status_" + tid).text() != "<fmt:message key='Pause'/>") {
	 					flag = true;
	 				} else {
						count = count + 1;
	 				}
				}
 			});
	if( count != 0 ) {
		flag = false;
	}
 	return flag;
}
/**
 * Select All
 * params are the id of buttons
 */
function selectAll() {
	var att =  $("#checkAll").attr("checked");
	$(".chk").attr("checked", att);
	$("#msg").empty();
	if( isAllOnline() ) {
 		for(var i=0; i<arguments.length; i++){
			$("#" + arguments[i]).removeAttr("disabled");
			$("#" + arguments[i]).attr("class", "btn_64");
		}
 		$("#btUploadLog").removeAttr("disabled");
		$("#btUploadLog").attr("class", "btn_117");
 		$("#btDelete").attr("disabled", true);
	 	$("#btDelete").attr("class", "btn_64d");
	} else {
 		for(var i=0; i<arguments.length; i++){
			$("#" + arguments[i]).attr("disabled", true);
			$("#" + arguments[i]).attr("class", "btn_64d");
			$("#" + arguments[i]).val("");
		}
 		$("#btUploadLog").attr("disabled", true);
		$("#btUploadLog").attr("class", "btn_117d");
		$("#btUploadLog").val("");
 		//$("#chgDiv").hide();
 	 	if( isAllNotOnline() ) {
 	 	 	$("#btDelete").removeAttr("disabled");
 	 	 	$("#btDelete").attr("class", "btn_64");
 	 	} else {
 	 	 	$("#btDelete").attr("disabled", true);
 	 	 	$("#btDelete").attr("class", "btn_64d");
 	 	}
 	}
}
/**
 * 
 */
function checkStatus() {
	$("#msg").empty();
 	if( isAllOnline() ) {
 		for(var i=0; i<arguments.length; i++){
			$("#" + arguments[i]).removeAttr("disabled");
			$("#" + arguments[i]).attr("class", "btn_64");
		}
 		$("#btUploadLog").removeAttr("disabled");
		$("#btUploadLog").attr("class", "btn_117");
 		$("#btDelete").attr("disabled", true);
	 	$("#btDelete").attr("class", "btn_64d");
 	} else {
 		for(var i=0; i<arguments.length; i++){
			$("#" + arguments[i]).attr("disabled", true);
			$("#" + arguments[i]).attr("class", "btn_64d");
			$("#" + arguments[i]).val("");
		}
 		$("#btUploadLog").attr("disabled", true);
		$("#btUploadLog").attr("class", "btn_117d");
		$("#btUploadLog").val("");
 		//$("#chgDiv").hide();
 		if( isAllNotOnline() ) {
 	 	 	$("#btDelete").removeAttr("disabled");
 	 	 	$("#btDelete").attr("class", "btn_64");
 	 	} else {
 	 	 	$("#btDelete").attr("disabled", true);
 	 		$("#btDelete").attr("class", "btn_64d");
 	 	}
 	}
 }
/**
 * check the status of the selected stbs 
 */
function isAllOnline() {
 	var flag = false;
 	var tid;
 	var count = 0;
 	//$("input[type=\"checkbox\"]").each(
 	$(".chk").each(function () {
		if( $(this).is(':checked') ) {
			tid = $(this).attr("id");
			if ($("#ol_status_" + tid).text() == "<fmt:message key='online'/>" || 
					$("#ol_status_" + tid).text() == "<fmt:message key='Pause'/>") {
				flag = true;
			} else {
				count = count + 1;
				flag = false;
			}
		}
	});
	if( count != 0 ) {
		flag = false;
	}
 	return flag;
}

/**
 * Restart the STB
 */
function restart() {
	if(window.confirm("<fmt:message key='sure.do.the.operation' />")) {
		$("#msg").empty();
		var url = path + "/stb!restart.action";
		var macStr = "";
		$(".chk").each(function(index) {
			if($(this).is(':checked')) {
				macStr = macStr + $(this).attr("id") + "l";
			}
		});
		var params = {"macStr":macStr};
		$.post(url, params, function(data) {
			$("#msg").append(data);
			setTimeout("getPage(${page});checkStatus();", 1000);
		});
	}
}
/**
 * 
 */
function uploadLog() {
	$("#msg").empty();
	var url = path + "/stb!uploadLog.action";
	var macStr = "";
	$(".chk").each(function(index) {
		if($(this).is(':checked')) {
			macStr = macStr + $(this).attr("id") + "l";
		}
	});
	var params = {"macStr":macStr};
	$.post(url, params, function(data) {
		$("#msg").append(data);
		setTimeout("getPage(${page});checkStatus();", 1000);
	});
}
/**
 * Control the STB
 */
function playerOperation(operation) {
	if(window.confirm("<fmt:message key='sure.do.the.operation' />")) {
		$("#msg").empty();
		var url = path + "/stb!playerOperation.action";
		var macStr = "";
		$(".chk").each(function(index) {
			if($(this).is(':checked')) {
				macStr = macStr + $(this).attr("id") + "l";
			}
		});
		var params = {"macStr":macStr, "playerOperation":operation};
		$.post(url, params, function(data) {
			$("#msg").append(data);
			setTimeout("getPage(${page});checkStatus();", 1000);
		});
	}
}

/**
 * get group infos
 */
function getGroup(type, group, groupId) {
	$("#" + group).empty();
	$("#" + group).append("<option value=''><fmt:message key='Total' /></option>");
	var url = path + "/group!getGroup.action";
	var params = {"typeId":$("#" + type).val()};
	var sel = "";
	$.post(url, params, function(data) {
		for(i in data) {
			if(i == groupId) {
				sel = i;
				//$("#" + group).append("<option selected='selected' value='" + i + "'>" + data[i] + "</option>");
			}
			$("#" + group).append("<option value='" + i + "'>" + data[i] + "</option>");
		}
		$("#" + group).val(sel);
	}, "json");
}
/**
* Delay for a number of milliseconds
*/
function sleep(delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}
/**
 * get group type
 */
function getType(type, group, typeId, groupId) {
	$.post(path + "/group!getType.action", function(data) {
		$("#" + type).empty();
		$("#" + type).append("<option value='' ><fmt:message key='Total' /></option>");
		$("#" + group).empty();
		$("#" + group).append("<option value='' ><fmt:message key='Total' /></option>");
		var sel = "";
		for(i in data) {
			if(i == typeId) {
				sel = i;
				//$("#" + type).append("<option selected='selected' value='" + i + "'>" + data[i] + "</option");
			}
			$("#" + type).append("<option value='" + i + "'>" + data[i] + "</option");
		}
		$("#" + type).val(sel);
		getGroup(type, group, groupId);
	}, "json");
}
/**
 * 
 */
function checkConfig() {
	$("#msg").empty();
	var flag = true;
	var pattern;
	var x;
	x = $("#fileName").val();
	pattern = /^[A-Za-z0-9_]{1,30}$/;
	if(x == "") {
		flag = false;
		$("#msg").append("<fmt:message key='config.file.name.null' />.<br/>");
	} else if( !pattern.test(x) ) {
		flag = false;
		$("#msg").append("<fmt:message key='config.file.name.wrong' />.<br/>");
	}
	return flag;
}
/**
 * 
 */
function checkFileName() {
	var url = path + "/conf!getConf.action";
	var params = {"confName":$("#fileName").val()};
	$.post(url, params, function(data) {
		if(data != null && data != "") {
			$("#msg").empty();
			$("#msg").append("<fmt:message key='config.file.name.exist' />.<br/>");
			return false;
		}
		return true;
	});
}
/**
 * 
 */
function checkGroup() {
	if ($("#chgGroup").val() != "") {
		return true;
	} else {
		return false;
	}
}

/**
 * set stb volumm
 */
function setVolumn(mac, status){
	$("#volumn_mac").val(mac);
	if("online" != status && "pause" != status){
		alert("机顶盒不在线， 无法设置音量！");
		return;
	}
	var dialog = $("#set_volumn_dialog");
	dialog.dialog({
			buttons:{"OK":function(){
					
					dialog.dialog("destroy");
				}, 
					"CANCEL":function(){
					dialog.dialog("destroy");
				}
			}
					
		}
	);
	var slider = $("#volumn_slider");
	slider.slider({
			max: 10,
			value: 5,
			slide: function(event, ui){
				slider.val(ui.value)
			}
		}
	);
}
function changeVolumn(text_input){
	var value = $(text_input).val();
	if(!(/^\d*$/.test(value))){
		alert("请输入0至10之间的数字");
		$("#volumn_slider").slider("value", 5);
		$(text_input).val("5");
		return;
	}
	if(value > 10){
		$("#volumn_slider").slider("value", 10);
		$(text_input).val("10");
	}else if(value < 0){
		$("#volumn_slider").slider("value", 0);
		$(text_input).val("0");
	}	
}
</script>

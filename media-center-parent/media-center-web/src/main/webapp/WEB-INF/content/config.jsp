<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %><%@include file="stb-js.jsp" 
%>
<div id="msg" align="center" style="color: red; font-size: 12px" ></div>
<div>
	<div align="left">
		<label><fmt:message key="config.file.name"/></label>
		<input id="fileName" onchange="checkFileName();"></input>
	</div>
	<table id="configPropts" class="linetable">
		<thead><tr>
			<td style='text-align: left'><fmt:message key="config.property.key"/></td>
			<td style='text-align: left'><fmt:message key="config.property.value"/></td>
		</tr></thead>
		<c:forEach var="property" items="${properties }">
			<tr id="id${property }">
				<td style='text-align: left'><label><fmt:message key="${property }"/></label></td>
				<td style='text-align: left'><input id="${property }" onchange="checkConfigInput('${property }');"></input></td>
				<!--<td><button onclick="deleteProperty('id${property }');"><fmt:message key="config.property.delete"/></button></td>-->
			</tr>
		</c:forEach>
	</table>
	<!--<table id="addPropts" class="linetable">
	</table>-->
</div>
<div align="right">
	<button onclick="if(checkConfig()){saveAdd();}" class="btn_64"><fmt:message key="save"/></button>
	<button onclick="back();" class="btn_64"><fmt:message key="back"/></button>
</div>
<!--<div align="left">
	<a href="javascript:;" onclick="addProperty();"><fmt:message key="config.property.add"/></a>
</div>-->
<script type="text/javascript">
var rows = 0;
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
});
/**
 * 
 */
function addProperty() {
	rows = rows + 1;
	$("#configPropts").append("<tr id='" + rows + "' ><td><fmt:message key='config.property.key'/></td>" + 
			"<td><input id='key" + rows + "' /></td><td><fmt:message key='config.property.value'/></td>" + 
			"<td><input id='value" + rows + "' /></td>" + 
			"<td><button onclick='deleteProperty(" + rows + ");'><fmt:message key='config.property.delete'/>" + 
			"</button></td></tr>");
}
/**
 * 
 */
function deleteProperty(row) {
	$("#" + row).remove();
}
/**
 * 
 */
function saveAdd() {
	var url = path + "/conf!add.action";
	var params = {"confName":$("#fileName").val()};
	$.post(url, params, function(exist) {
		$("#msg").empty();
		if(exist != "") {
			$("#msg").append("<fmt:message key='config.file.name.exist' />.<br/>");
			return;
		}
		var confId;
		url = path + "/conf!getConf.action";
		params = {"confName":$("#fileName").val()};
		$.post(url, params, function(data) {
			confId = data;
			url = path + "/config!add.action";
			/*for(var i=1; i<=rows; i++) {
				// $("#" + i).constructor.toString().indexOf("Object") != -1
				if( typeof($("#" + i)) != "undefined" ) {
					params = {"confId":confId, "configKey":$("#key" + i).val(), 
							"configValue":$("#value" + i).val()};
					$.post(url, params, function(data) {
						$("#msg").append(data);
					});
				}
			}*/
			<c:forEach var="property" items="${properties }" >
				if( $("#id${property }") != "undefined" ) {
					if( $("#" + "${property }").val() != "" ) {
						params = {"confId":confId, "configKey":"${property }", 
								"configValue":$("#${property }").val() };
						$.post(url, params, function(data) {
							$("#msg").append(data);
						});
					}
				}
			</c:forEach>
			//window.location = path + "/conf.action";
			$("#msg").append("<fmt:message key='config.file.add.success' />.<br/>");
		});
	});
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
function checkConfigInput(proty) {
	var flag = true;
	var pattern;
	var x;
	switch(proty) {
	case "AuthenIp": 
		/*$("#msg").empty();
		pattern = /^(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])\.(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])\.(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])\.(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])$/;
		x = $("#AuthenIp").val();
		if(x == "") {
			$("#msg").append("<fmt:message key='ip.null' />.<br/>");
			flag = false;
		} else if( !pattern.test(x) ) {
			flag = false;
			$("#msg").append("<fmt:message key='ip.wrong' />.<br/>");
		}*/
		break;
	case "AuthenPort":
		// port less than 65536
		pattern = /^[1-9]\d*$/;
		x = $("#AuthenPort").val();
		if(x == "") {
			$("#msg").append("<fmt:message key='port.null' />.<br/>");
			flag = false;
		} else if( !pattern.test(x) ) {
			$("#msg").append("<fmt:message key='port.wrong' />.<br/>");
			flag = false;
		} else if(parseInt(x) > 65535) {
			$("#msg").append("<fmt:message key='port.wrong' />.<br/>");
			flag = false;
		}
		break;
	case "onlineReport_interval":
		break;
	case "perfStatistics_interval":
		break;
	case "authenticate_interval":
		break;
	case "ftp_username":
		break;
	case "ftp_password":
		break;
	case "max_ftp_try_time":
		break;
	case "max_download_thread":
		break;
	case "log_upload_path":
		break;
	case "log_upload_username":
		break;
	case "log_upload_password":
		break;
	case "log_upload_interval":
		break;
	case "log_upload_begin_at":
		break;
	}
	return flag;
}
</script>
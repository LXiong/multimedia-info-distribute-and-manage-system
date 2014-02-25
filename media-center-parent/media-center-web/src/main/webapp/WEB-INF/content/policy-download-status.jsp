<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" 
%><script type="text/javascript">
$(document).ready(function() {
	$("#downDetailsDiv").dialog({ 
		autoOpen: false, 
		show: "blind", 
		position: 'top', 
		title: "<fmt:message key='policy.media.download.status' />", 
		buttons: [
               	{
                   	text: "<fmt:message key='close' />", 
                   	click: function() { 
                       	$('#downDetailsDiv').dialog('close'); 
                       	}
               	}
               	] });
});

/**
 * 
 */
function page(page) {
	window.location = "policy-download-status.action?policyNumber=${policyNumber }" + 
		"&policyId=${policyId}&isFinished=" + $("#isFinished").val();
}

/**
 * 
 */
function back() {
	window.location = "policy.action";
}
/**
 * 
 */
function check() {
	if( $("#isFinished").is(':checked') ) {
		$("#isFinished").val("N");
	} else {
		$("#isFinished").val("");
	}
}

/**
 * 
 */
function downDetails(mac, policyNumber) {
	// get down details
	var url = "policy-download-status!mediaDownDetail.action";
	var params = {"mac":mac, "policyNumber":policyNumber };
	$.post(url, params, function(data) {
		$("#detailsTbl").empty();
		var str = "<tr><th style='text-align: left'><fmt:message key='file.name' /></th>" + 
				"<th style='text-align: left'><fmt:message key='download.is.finished' /></th>";
		for(i in data) {
			str = str + "<tr><td style='text-align: left'>" + data[i]["videoName"] + 
			"</td><td style='text-align: left'>" + data[i]["isFinished"] + "</td></tr>";
		}
		$("#detailsTbl").append(str);
		// open dialog
		$("#downDetailsDiv").dialog( "open" );
		$("#downDetailsDiv").dialog( "option", "height", 600 );
		$("#downDetailsDiv").dialog( "option", "width", 400 );
	}, "json");
}
</script>
<div align="left">
	<fmt:message key="policy.version" /><label>${policyNumber }</label>
	<fmt:message key="policy.file.item.num" /><label>${mediaNum }</label><p>
	<input type="checkbox" id="isFinished" value="" onchange="javascript:check();" ></input>
	<fmt:message key="show.not.finish.stb.only" />
	<button class="btn_64" onclick="javascript:page(0);" >
		<fmt:message key="condition.filt" /></button>
</div>

<div>
	<table class="linetable">
		<thead><tr>
			<th><fmt:message key='stb.mac' /></th>
			<th><fmt:message key='top.level' /></th><th><fmt:message key='second.level' /></th>
			<th><fmt:message key='shop.no' /></th><th><fmt:message key='shop.name' /></th>
			<th><fmt:message key='policy.media.download.finished.num' /></th>
			<th><fmt:message key='policy.media.download.not.finished.num' /></th>
		</tr></thead>
		<tbody><c:forEach var="stb" items="${downStatus}" ><tr>
			<td><a href="###" onclick="downDetails('${stb.stbMac }', 
				'${policyNumber }');" >${stb.stbMac }</a></td>
			<td>${stb.typeName }</td><td>${stb.groupName }</td>
			<td>${stb.shopNo }</td><td>${stb.shopName }</td>
			<td>${stb.finishedNum }</td>
			<td>${stb.notFinishedNum }</td>
		</tr></c:forEach></tbody>
	</table>
</div>
<div id="downDetailsDiv">
	<table id="detailsTbl" class="linetable" style="text-align: left"></table>
</div>
<div>
	<input type="button" class="btn_64" value="<fmt:message key='back' />" 
		onclick="back();" ></input>
</div>
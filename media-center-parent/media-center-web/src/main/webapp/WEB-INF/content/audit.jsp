<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp"%><%@include file="stb-js.jsp" 
%>
<script type="text/javascript">
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	getType("typeId", "groupId", '${typeId}', '${groupId}');
	$("#typeId").change(function(){ getGroup("typeId", "groupId", '${groupId}') });
	//getUnauditStb();
});
/**
 * 
 */
function getPage(page) {
	var params = "?page=" + page + "&typeId=" + $("#typeId").val() + 
		"&groupId=" + $("#groupId").val() + "&stbStatus=" + $("#status").val();
	window.location = "audit.action" + params;
}
/**
 * 
 */
function auditChkAll() {
	var att =  $("#checkAll").attr("checked");
	$(".chk").attr("checked", att);
	auditChkStatus();
}
/**
 * 
 */
function auditChkStatus() {
	if( $(".chk").is(':checked') ) {
		if($("#status").val() == 'pending' || $("#status").val() == '' 
			|| $("#status").val() == 'installed_pending') {
			$("#btAudit").removeAttr("disabled");
			$("#btAudit").attr("class", "btn_64");
			$("#btRefuse").removeAttr("disabled");
			$("#btRefuse").attr("class", "btn_64");
		} else {
			$("#btAudit").removeAttr("disabled");
			$("#btAudit").attr("class", "btn_64");
			$("#btRefuse").attr("disabled", true);
			$("#btRefuse").attr("class", "btn_64d");
		}
	} else {
		$("#btAudit").attr("disabled", true);
		$("#btAudit").attr("class", "btn_64d");
		$("#btRefuse").attr("disabled", true);
		$("#btRefuse").attr("class", "btn_64d");
		$("#checkAll").removeAttr("checked");
	}
}
/**
 * Audit
 */
function audit() {
	if(confirm("<fmt:message key='stb.audit.active.tips' />")){
		var url = path + "/audit!audit.action";
		var macStr = "";
		$(".chk").each(
	 			function () {
					if( $(this).is(':checked') ) {
		 				macStr = macStr + $(this).attr("id") + "l";
					}
	 			});
		var params = {"macStr":macStr };
		$.post(url, params, function() {
			setTimeout("getPage(0);", 1000);
		});
	} else {
		$("#msg").empty();
		$("#msg").append("<fmt:message key='nothing.has.changed' />");
	}
}
/**
 * Refuse
 */
function refuse() {
	if(confirm("<fmt:message key='stb.audit.refuse.tips' />")){
		var url = path + "/audit!refuse.action";
		var macStr = "";
		$(".chk").each(
	 			function () {
					if( $(this).is(':checked') ) {
		 				macStr = macStr + $(this).attr("id") + "l";
					}
	 			});
		var params = {"macStr":macStr };
		$.post(url, params, function() {
			setTimeout("getPage(0);", 1000);
		});
	} else {
		$("#msg").empty();
		$("#msg").append("<fmt:message key='nothing.has.changed' />");
	}
}
</script>
<div id="msg" align="center" style="color: red; font-size: 12px" ></div>
<div style="position:relative;width:99%;text-align:left">
	<ul class="query cf">
		<li><label><fmt:message key="top.level" /></label>
			<select id="typeId" name="typeId"><option value="" ><fmt:message key="Total" /></option></select>
		</li>
		<li><label><fmt:message key="second.level" /></label>
			<select id="groupId" name="groupId"><option value="" ><fmt:message key="Total" /></option></select>
		</li>
		<li><label><fmt:message key="Status" /></label>
			<select id="status" onchange="javascript:getPage(0);" >
				<c:choose>
					<c:when test="${stbStatus == 'pending' }" >
						<option value="" ><fmt:message key="Total" /></option>
						<option value="pending" selected="selected"><fmt:message key="stb.status.pendding" /></option>
						<option value="refused"><fmt:message key="stb.status.refused" /></option>
						<option value="installed_pending"><fmt:message key="stb.status.installed.pendding" /></option>
					</c:when>
					<c:when test="${stbStatus == 'refused' }" >
						<option value="" ><fmt:message key="Total" /></option>
						<option value="pending"><fmt:message key="stb.status.pendding" /></option>
						<option value="refused" selected="selected"><fmt:message key="stb.status.refused" /></option>
						<option value="installed_pending"><fmt:message key="stb.status.installed.pendding" /></option>
					</c:when>
					<c:when test="${stbStatus == 'installed_pending' }" >
						<option value="" ><fmt:message key="Total" /></option>
						<option value="pending"><fmt:message key="stb.status.pendding" /></option>
						<option value="refused"><fmt:message key="stb.status.refused" /></option>
						<option value="installed_pending" selected="selected">
							<fmt:message key="stb.status.installed.pendding" />
						</option>
					</c:when>
					<c:otherwise>
						<option value="" selected="selected"><fmt:message key="Total" /></option>
						<option value="pending"><fmt:message key="stb.status.pendding" /></option>
						<option value="refused"><fmt:message key="stb.status.refused" /></option>
						<option value="installed_pending"><fmt:message key="stb.status.installed.pendding" /></option>
					</c:otherwise>
				</c:choose>
			</select>
		</li>
		<li><label>MAC</label>
			<input id="stbMac" name="stbMac" value="" size="15px">
		</li>
	</ul>
	<div class="btn" style="position:absolute;top:0px;_top:0px;left:700px">
    	<button id="getStbBt" onclick="getPage(0);" class="btn1_64"><fmt:message key="Query" /></button>
    </div>
</div>

<div>
	<div class="operate">
		<table style="width:100%"><tr>
			<td align='left'><div class="btn">
				<button id="btAudit" disabled="disabled" onclick="audit();" class="btn_64d"><fmt:message key='Check' /></button>
				<button id="btRefuse" disabled="disabled" onclick="refuse();" class="btn_64d"><fmt:message key='refused' /></button>
			</div></td>
			<td><div class="page">
				<yun:pageLink totalPage="${pageNums}" currentPage="${page}" 
			    dispCount="5" link="###" middle="true" onclick="getPage({p})"/>
			</div></td>
		</tr></table>
	</div>
	<table id="table" class="linetable">
		<thead><tr>
			<th><input type='checkbox' id='checkAll' onclick="auditChkAll();"></th>
			<th><fmt:message key='Mac' /></th><th><fmt:message key='Addr' /></th>
			<th><fmt:message key='Status' /></th>
		</tr></thead>
		<tbody><c:forEach var="stb" items="${stbs}" ><tr>
			<td><input type='checkbox' id="${stb.stbMac }" class='chk' onclick="auditChkStatus();" ></td>
			<td>${stb.stbMac }</td>
			<td>${stb.provinceName }${stb.cityName }${stb.districtName }${stb.addrDetail }</td>
			<td><fmt:message key="${stb.stbStatus }" /></td>
		</tr></c:forEach></tbody>
	</table>
	<div class="operate">
		<div class="page">
			<yun:pageLink totalPage="${pageNums}" currentPage="${page}" 
			    dispCount="5" link="###" middle="true" onclick="getPage({p})"/>
		</div>
	</div>
</div>

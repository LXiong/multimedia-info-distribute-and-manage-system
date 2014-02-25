<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %><%@include file="stb-js.jsp" %>
<hr>
<form action="box-package!save.action" onsubmit="check()" method="post" >
<input type="hidden" value="${id }" id="id" name="id">
	<table class="linetable" style="width:300px;">
	  	<tr>
	  		<td>软件包名称</td>
	  		<td><input type="text" name="vo.boxname" id="boxname" value="${box.name }"></td>
	  	</tr>
	  	<tr>
	  		<td>软件包描述:</td>
	  		<td><textarea name="vo.description" id="description" >${box.description }</textarea></td>
	  	</tr>
	  	<tr>
	  		<td>
	  			<input type="submit" value="保存" class="btn_64">
	  		</td>
	  		<td>
	  			<input type="reset" class="btn_64">
	  		</td>
	  	</tr>
</table>
<br>
<p style="color: blue">已有的模块文件 ↓</p>
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
					<fmt:formatDate value="${m.releaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${m.filePath }</td>
				<td>${m.verflyCode }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</form>
<hr>
<p style="color: blue">关联操作 ↓</p>
<div id="apply" align="left">
	<button onclick="applyToAddr();" class="btn_117">
		<fmt:message key="config.apply.to.stb"/></button>
	<button onclick="applyToGroup();" class="btn_117">
		<fmt:message key="config.apply.to.group"/></button>
</div>
<!-- ------------- -->
<div id="addrDiv" style="position:relative;width:100%;text-align:left;">
	<div style="position:relative;width:99%;text-align:left">
		<form id="queryForm" method="post" action="" >
				<ul class="query cf">
					<li><label><fmt:message key="top.level" /></label>
						<select id="typeId" name="typeId"><option value="" ><fmt:message key="Total" /></option></select>
					</li>
					<li><label><fmt:message key="second.level" /></label>
						<select id="groupId" name="groupId"><option value="" ><fmt:message key="Total" /></option></select>
					</li>
					<li><label><fmt:message key="Status" /></label>
						<select id="stbStatus" name="stbStatus" >
							<option value="" ><fmt:message key="Total" /></option>
							<c:forEach var="item" items="${mapStatus }" >
								<c:choose>
									<c:when test="${item.key == stbStatus }">
										<option selected="selected" value="${item.key }" ><fmt:message key="${item.value }" /></option>
									</c:when>
									<c:otherwise>
										<option value="${item.key }" ><fmt:message key="${item.value }" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</li>
				</ul>
				<ul class="query cf">
					<li><label><fmt:message key='PolicyVersion' /></label>
						<input id="activePolicy" name="activePolicy" value="${activePolicy }" size="15px">
					</li>
					<li><label><fmt:message key='shop.no' /></label>
						<input id="shopNo" name="shopNo" value="${shopNo }" size="15px">
					</li>
					<li><label><fmt:message key='shop.name' /></label>
						<input id="shopName" name="shopName" value="${shopName }" size="15px">
					</li>
					<li><label>MAC</label>
						<input id="stbMac" name="stbMac" value="${stbMac }" size="15px">
					</li>
				</ul>
				<div class="btn" style="position:absolute;top:0px;_top:0px;left:700px">
			    	<button class="btn1_64" onclick="getPage(0);"><fmt:message key="Query" /></button>
			    	<p style="color: maroon; font-size: 12px; font-style: normal" align="center">
					<fmt:message key="search.tips" /></p>
			    </div>
			<input type="hidden" id="page" name="page" value="0" ></input>
		</form>
	</div>
	<div id="tbl">
		<div class="operate">
			<table style="width:100%"><tr>
				<td align='left'></td>
				<td><div class="page">
					${page }/${pageNums }&nbsp;
				    <c:if test="${page > 3 && pageNums > 5}">
				    	<a href="###" onclick="getPage(${1});"> |&lt; </a>
						<a href="###" onclick="getPage(${page-1});"> &lt; </a>
					</c:if>
					<c:forEach var="i" items="${pageArr}" >
						<c:choose>
							<c:when test="${page == i }" ><span>${i}</span></c:when>
							<c:when test="${i > 0 && i <= pageNums }" ><a href="###" onclick="getPage(${i });">${i }</a></c:when>
						</c:choose>
					</c:forEach>
					<c:if test="${page+2 < pageNums && pageNums > 5 }">
				    	<a href="###" onclick="getPage(${page+1});"> &gt; </a>
				    	<a href="###" onclick="getPage(${pageNums});"> &gt;| </a>
				    </c:if>
				</div></td>
			</tr></table>
		</div>
		<table id="table" class="linetable">
			<thead><tr>
			<th><input type='checkbox' id='checkAll' onchange="selectAll('btPause','btResume','btRestart','btGroup');"></th>
			<th><fmt:message key='Mac' /></th>
			<th><fmt:message key='top.level' /></th><th><fmt:message key='second.level' /></th>
			<th><fmt:message key='shop.no' /></th><th><fmt:message key='shop.name' /></th>
			<th><fmt:message key='PolicyVersion' /></th><th><fmt:message key='terminal.ip' /></th>
			<th><fmt:message key='Status' /></th><th><fmt:message key='stb.media.download.status' /></th>
			<th><fmt:message key='stb.last.offline.time' /></th>
		</tr></thead>
		<tbody><c:forEach var="stb" items="${stbs}" ><tr>
			<td><input type='checkbox' id="${stb.stbMac }" class='chk' onchange="checkStatus('btPause', 
				'btResume', 'btRestart', 'btGroup');"></td>
			<td><a href="###" onclick="getInfo('${stb.stbMac }', '${stb.provinceName }', 
				'${stb.cityName }', '${stb.districtName }', '${stb.addrDetail }', '${stb.stbStatus }', 
				'${stb.groupName }', '${stb.confName }', '${stb.activePolicySuccessNum }', 
				'${stb.activePolicy }', '${stb.activePolicyFailedNum }', '${stb.packageName }', 
				'${stb.contacts }', '${stb.phone }');" >${stb.stbMac }</a></td>
			<!--<td>${stb.provinceName }${stb.cityName }${stb.districtName }${stb.addrDetail }</td>-->
			<td>${stb.typeName }</td><td>${stb.groupName }</td><td>${stb.shopNo }</td><td>${stb.shopName }</td>
			<td>${stb.activePolicy }</td><td>${stb.terminalIp }</td>
			<td><label id="ol_status_${stb.stbMac }" ><fmt:message key="${stb.stbStatus }" /></label></td>
			<td><c:choose><c:when test="${stb.activePolicyFailedNum > 0 }">
					<label><fmt:message key="stb.media.download.part" /></label>
				</c:when>
				<c:otherwise>
					<label><fmt:message key="stb.media.download.total" /></label>
				</c:otherwise>
			</c:choose></td>
			<td><fmt:formatDate value="${stb.lastOfflineTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr></c:forEach></tbody>
		</table>
			<div class="operate">
				<div class="page">
					${page }/${pageNums }&nbsp;
				    <c:if test="${page > 3 && pageNums > 5}">
				    	<a href="###" onclick="getPage(${1});"> |&lt; </a>
						<a href="###" onclick="getPage(${page-1});"> &lt; </a>
					</c:if>
					<c:forEach var="i" items="${pageArr}" >
						<c:choose>
							<c:when test="${page == i }" ><span>${i}</span></c:when>
							<c:when test="${i > 0 && i <= pageNums }" ><a href="###" onclick="getPage(${i });">${i }</a></c:when>
						</c:choose>
					</c:forEach>
					<c:if test="${page+2 < pageNums && pageNums > 5 }">
				    	<a href="###" onclick="getPage(${page+1});"> &gt; </a>
				    	<a href="###" onclick="getPage(${pageNums});"> &gt;| </a>
				    </c:if>
				</div>
		</div>
	</div>
</div>
<!-- ------------- -->
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
</div>
<!-- ------------- -->
<button name="" class="btn_64" onclick="setConfigToAddr();" ><fmt:message key="OK" /></button>
<button onclick="javascript:history.back()" class="btn_64">返回</button>
<script type="text/javascript">
var rows = 0;
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
	if('${show}'=='0'){
		$("#addrDiv").hide();
	}
	$("#groupDiv").hide();
	$("#add").hide();
	$("#save").hide();
	$("#chgType").change(function(){ getGroup("chgType", "chgGroup") });
	$("#infoDiv").dialog({autoOpen: false, show: "blind"});
	$("#stb").dialog({autoOpen: false, show: "blind"});
	getProvince("selprov");
	$("#selprov").change(function(){ getCity("selcity", "selprov", "seldist") });
	$("#selcity").change(function(){ getDistrict("seldist", "selcity") });
	getType("typeId", "groupId", ${typeId}, ${groupId});
	$("#typeSel").change(function(){ getGroup("typeSel", "groupSel") });
	$("#typeId").change(function(){ getGroup("typeId", "groupId", ${groupId}) });
});

function applyToAddr() {
	$("#addrDiv").show();
	$("#groupDiv").hide();
}
function applyToGroup() {
	getType("chgType", "chgGroup");
	$("#groupDiv").show();
	$("#addrDiv").hide();
}

function setConfigToAddr(){
	var id = $("#id").val()
	var arry = [];
	var url=null;
	if($("#groupDiv").css("display")=='none' && $("#addrDiv").css("display")=='none'){
				alert("请先选择需要关联的设备");
				return;
	}
	if($("#addrDiv").css("display")=='block'){
			if($(".chk:checked").size()<1){
					alert("请选择先");
					return;
			}
			$(".chk:checked").each(function(){
				arry.push($(this).attr("id"));
				});
			url='box-package!updateStbPackageId.action?arry='+arry+'&id='+id;
	}else{
		var chgGroup = $("#chgGroup>option:selected").val();
		if(chgGroup==""){
			alert("请先选择分组");
			return;		
		}else{
			url='box-package!updateStbPackageId.action?id='+id+'&groupid='+chgGroup;
		}
	}
	    location=url;
}
function check(){
	var boxname = $.trim($("#boxname").val())
	if(boxname==""){
		alert("软件包名称不能为空,请输入！");
		$("#boxname").focus();
		return false;
	}
	var description = $.trim($("#description").val())
	if(description==""){
		alert("软件包描述不能为空,请输入！");
		$("#description").focus();
		return false;
	}
	return true;
}
function getPage(page) {
	$("#page").val(page);
	var id = $("#id").val();
	$("#queryForm").attr("action","box-package!edit.action?show=1&id="+id);
	
	$("#queryForm").submit();
}
</script>
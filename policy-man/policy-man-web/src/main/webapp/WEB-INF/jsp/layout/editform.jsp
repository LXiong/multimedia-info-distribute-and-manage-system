<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<div id="#log"></div>
<form:form action="/layout/edit" onsubmit="return checkLayout()" method="post" commandName="layout">
<div>
<form:hidden path="id"/>
<table>
<tr>
<td valign="top">
	<table class="linetable" style="width:300px;" id="edittbl">
	<tr><th colspan="2">布局属性</th></tr>
	  	<tr>
	  		<td>布局名称:</td>
	  		<td>
	  		<form:input path="name" />
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>百分比模式：</td>
	  		<td><form:radiobutton path="usePercent" id="percent_yes" value="true" onclick="updatePercentMode()"/>
	  		<label for="percent_yes"><fmt:message key="option.yes" /></label>
	  		<form:radiobutton path="usePercent" id="percent_no" value="false" onclick="updatePercentMode()"/>
	  		<label for="percent_no"><fmt:message key="option.no" /></label></td>
	  	</tr>
	  	<tr>
	  		<td>大小:</td>
	  		<td>
	  		<label>宽</label>
	  		<form:input path="width" id="width" readonly="${layout.usePercent}" size="5" onchange="refreshArea()"/>
	  		<label>高</label>
	  		<form:input path="height" id="height" readonly="${layout.usePercent}" size="5" onchange="refreshArea()"/>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>注释:</td>
	  		<td><form:textarea path="lcomment" id="lcomment"/></td>
	  	</tr>
	  	<tr>
	  		<td>
	  			区域:
	  		</td>
	  		<td>
	  			<input type="button" value='<fmt:message key="button.add" />' onclick="addArea()" />
	  			<input type="button" value='<fmt:message key="button.edit" />' onclick="editArea()" />
	  			<input type="button" value='<fmt:message key="button.delete" />' onclick="deleteArea()" />
	  			<br />
	  			<select id="areaList" size="6" onclick="fillAreaValues()">
	  			<c:forEach var="area" items="${layout.areas }">
	  			<option value="${area.id }">${area.name }</option>
	  			</c:forEach>
	  			</select>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td colspan="2">
	  			<input type="button" value='<fmt:message key="button.save" />' onclick="saveLayout()">
	  			<input type="button" value="返回" onclick="javascript:history.back()">
	  		</td>
	  	</tr>
</table>
</td><td valign="top">
<input type="hidden" id="areaIndex" class="areaField" />
<input type="hidden" id="areaId" class="areaField" />
	<table class="linetable" style="width:300px;" id="areaTable">
	<tr><th colspan="2">区域属性</th></tr>
		<tr>
	  		<td>区域名称:</td>
	  		<td><input type="text" name="name" id="areaName" class="areaField" disabled="disabled"></td>
	  	</tr>
	  	<tr>
	  		<td>类型:</td>
	  		<td>
	  			<select name="type" id="areaType" class="areaField" disabled="disabled">
	  				<option value="0">--请选择--</option>
	  				<option value="1">视频</option>
	  				<option value="2">图片</option>
	  				<option value="3">文字</option>
	  				<option value="4">时间</option>
	  				<option value="5">天气</option>
	  			</select>
	  		</td>
	  	</tr>
	  	<tr id="time_format_container" style="display:none;">
	  		<td><label>时间格式:</label></td>
	  		<td>
	  		<input type="text" name="left" id="areaTimeFormat" class="areaField" disabled="disabled">
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>偏移:</td>
	  		<td>
	  		<label>左</label>
	  		<input type="text" name="left" id="areaLeft" class="areaField" size="5" disabled="disabled">
	  		<label>上</label>
	  		<input type="text" name="top" id="areaTop" class="areaField" size="5" disabled="disabled">
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>大小:</td>
	  		<td>
	  		<label>宽</label>
	  		<input type="text" name="width" id="areaWidth" class="areaField" disabled="disabled" size="5">
	  		<label>高</label>
	  		<input type="text" name="height" id="areaHeight" class="areaField" disabled="disabled" size="5">
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>注释:</td>
	  		<td>
	  		<textarea rows="3" cols="20" name="lcomment" id="areaLcomment" class="areaField" disabled="disabled"></textarea>
	  		</td>
	  	</tr>
	  	
	  	<tr>
	  		<td colspan="2">
	  			<input type="button" value='<fmt:message key="button.save" />' onclick="saveArea()" id="areaSave" disabled="disabled">
	  			<input type="button" value='<fmt:message key="button.cancel" />' onclick="cancelArea()" id="areaCancel" disabled="disabled">
	  		</td>
	  	</tr>
	</table>
</td>
<td valign="top">
<div id="areaShow" style="position:absolute; width:200px; height:200px; background-color: red;">
</div>
</td></tr>
</table>

</div>
</form:form>


<c:set var="jsAreaList">
<c:forEach var="area" items="${layout.areas }" varStatus="areaStatus">
{id:'${area.id }',name:'${area.name }',left:'${area.left }',top:'${area.top }',
type:'${area.type}',width:'${area.width }',height:'${area.height }',
timeFormat:'${area.timeformatJs}',lcomment:'${area.lcomment }'}<c:if test="${not areaStatus.last}">,</c:if>
</c:forEach>
</c:set>
<sp:url var="saveLayoutUrl" value="/layout/save" />
<sp:url var="layoutListUrl" value="/layout" />
<script type="text/javascript">
var areaArr = [${jsAreaList}];

function showHide() {
	if ($('#areaType').val() == 4) {
		$('#time_format_container').show();
		if ($('#areaTimeFormat').val() =="") {
			$('#areaTimeFormat').val('YYYY年MM月DD日\\n星期WK hh:mm:ss');
		}
	} else {
		$('#time_format_container').hide();
	}
	if ($('#areaType').val() == 5) {
		$('#weather_format_container').show();
	} else {
		$('#weather_format_container').hide();
	}
}

function fillAreaValues() {
	var areaIndex = $("#areaList").attr('selectedIndex');
	result = false;
	if (areaIndex>-1 && areaIndex<areaArr.length) {
		var area = areaArr[areaIndex];
		// set value to area form
		$('#areaIndex').val(areaIndex);
		$('#areaId').val(area.id);
		$('#areaName').val(area.name);
		$('#areaLeft').val(area.left);
		$('#areaTop').val(area.top);
		$('#areaWidth').val(area.width);
		$('#areaHeight').val(area.height);
		$('#areaTimeFormat').val(area.timeFormat);
		$('#areaLcomment').val(area.lcomment);
		result = true;
		setTimeout(function() {
			$('#areaType').val(area.type);
			showHide();
		},1);
	}
	return result;
}
function editArea() {
	clearArea();
	if (fillAreaValues()) {
		enableArea();
	} else {
		alert("请选择一个 区域");
	}
}
function deleteArea() {
	var areaIndex = $("#areaList").attr('selectedIndex');
	result = false;
	if (areaIndex>-1 && areaIndex<areaArr.length) {
		if (confirm("确认要删除选中的区域吗？")) {
			areaArr.splice(areaIndex, 1);
			clearArea();
			refreshAreaList();
		}
	}
}
function addArea() {
	clearArea();
	$('#areaId').val('-1');
	$('#areaIndex').val(areaArr.length);
	var no = areaArr.length+1;
	$('#areaName').val('区域 '+no);
	$('#areaTimeFormat').val('');
	setTimeout(function () {
		$('#areaType').val('1');
		showHide();
	},1);
	enableArea();
}
function enableArea() {
	$('input', '#areaTable').removeAttr('disabled');
	$('select', '#areaTable').removeAttr('disabled');
}
function disableArea() {
	$('input', '#areaTable').attr('disabled','disabled');
	$('select', '#areaTable').attr('disabled','disabled');
};
function clearArea() {
	$("input[type!='button']", '#areaTable').val('');
	setTimeout(function() {
		$('#areaType').val(1);
		showHide();
	},1);
}
function saveArea() {
	if (!checkArea()) {
		return;
	}
	var areaIndex = parseInt($('#areaIndex').val());
	var obj = {
			id: $('#areaId').val(),
			name: $('#areaName').val(),
			type: $('#areaType').val(),
			left: $('#areaLeft').val(),
			top: $('#areaTop').val(),
			width: $('#areaWidth').val(),
			height: $('#areaHeight').val(),
			timeFormat: $('#areaTimeFormat').val(),
			lcomment: $('#areaLcomment').val(),
		};
	if (areaArr.length<=areaIndex) {
		areaArr.length=1+areaIndex;
	}
	areaArr[areaIndex] = obj;
	refreshAreaList();
	disableArea();
	clearArea();
}
function cancelArea() {
	disableArea();
	clearArea();
}
function refreshAreaList() {
	// refresh select[areaArr]
	$('#areaList').empty();
	for(areaKey in areaArr) {
		var area = areaArr[areaKey];
		$("<option value='"+area.id+"'>"+area.name+"</option>").appendTo("#areaList");
	}
	refreshArea();
}
function refreshArea() {
	$('#areaShow').empty();
	var width=parseFloat($('#width').val());
	var height=parseFloat($('#height').val());
	
	if (width>height) {
		xwidth=200;
		xheight=height/width*200;
	} else if (width<height){
		xwidth=width/height*200;
		xheight=200;
	} else {
		xwidth=200;
		xheight=200;
	}
	$('#areaShow').css('width',(xwidth+1)+'px');
	$('#areaShow').css('height',(xheight+1)+'px');
	for(areaKey in areaArr) {
		var area = areaArr[areaKey];
		var left = area.left / width * xwidth;
		var top = area.top / height * xheight;
		
		awidth = Math.floor(parseFloat(area.width) / width *xwidth);
		aheight = Math.floor(parseFloat(area.height) / height*xheight);
		$('<div class="box" style="position:absolute;left:'+left+'px; top:'+top+'px; width:'+awidth
			+'px;height:'+aheight+'px;" >'+area.name+'</div>').appendTo("#areaShow");
	}
}
$(document).ready(function(){
	showHide();
	$('#areaType').change(showHide);
	refreshArea();
});
function ensure(id, message) {
	if ($.trim($(id).val())=="") {
		alert(message);
		$(id).focus();
		return false;
	}
	return true;
}
function ensureNumber(id, message) {
	var val = $.trim($(id).val());
	if (val!="") {
		if(val.match(/^[0-9]*$/)==null){
			alert(message);
			$(id).val("");
			$(id).focus();
			return false;
		}
	}
	return true;
}
function checkLayout(){
	var flag = ensure("#name", "布局名称不能为空,请输入！")
		&& ensure('#width', "宽度不能为空,请输入！")
		&& ensureNumber('#width', "宽度请输入数字")
		&& ensure('#height', "高度不能为空,请输入！")
		&& ensureNumber('#height', "高度请输入数字");
	return flag;
}

function saveLayout() {
	if (checkLayout()) {
		$.ajax( {
			contentType: 'application/json',
		    data: JSON.stringify({
		    	'id':$('#id').val(),
				'name':$('#name').val(),
				'width':$('#width').val(),
				'height':$('#height').val(),
				'lcomment':$('#lcomment').val(),
				'usePercent':$('#percent_yes').attr('checked')?'true':'false',
				'areas': areaArr
		    }),
		    dataType: 'json',
		    success: function(data){
		    	if ('success'== data.flag) {
		    		alert("保存成功");
		    		location.href="${layoutListUrl}";
		    	}
		    },
		    processData: false,
		    type: 'POST',
		    url: '${saveLayoutUrl}'
		});
	}
}

function checkArea(){
	var usePercent = $('#percent_yes').attr('checked');
	var name = $.trim($("#areaName").val());
	if(name==""){
		alert("名称不能为空,请输入！");
		$("#areaName").focus();
		return false;
	}
	var type = $("#areaType>option:selected").val();
	if(type=='0'){
		alert("请选择一种类型");
		$("#areaType").focus();
		return false;
	}
	if (type == '4' && $('#areaTimeFormat').val() == '') {
		alert('请指定时间格式');
		$('#areaTimeFormat').focus();
		return false;
	}
	var left = $.trim($("#areaLeft").val());
	if(left==""){
		alert("左偏移量不能为空,请输入！");
		$("#areaLeft").focus();
		return false;
	}
	if($.trim($("#areaLeft").val()).match(/^[0-9]*$/)==null){
		alert("左偏移量请输入数字");
		$("#areaLeft").val("");
		$("#areaLeft").focus();
		return false;
	}
	var top = $.trim($("#areaTop").val());
	if(top==""){
		alert("上偏移量不能为空,请输入！");
		$("#areaTop").focus();
		return false;
	}
	if($.trim($("#areaTop").val()).match(/^[0-9]*$/)==null){
		alert("上偏移量请输入数字");
		$("#areaTop").val("");
		$("#areaTop").focus();
		return false;
	}
	var width = $.trim($("#areaWidth").val());
	if(width==""){
		alert("宽度不能为空,请输入！");
		$("#areaWidth").focus();
		return false;
	}
	if($.trim($("#areaWidth").val()).match(/^[0-9]*$/)==null){
		alert("宽度请输入数字");
		$("#areaWidth").val("");
		$("#areaWidth").focus();
		return false;
	}
	if (usePercent && parseInt($('#areaWidth').val())>100) {
		alert("使用百分比时，宽度不能超过100");
		$("#areaWidth").focus();
		return false;
	}
	
	var height = $.trim($("#areaHeight").val());
	if(height==""){
		alert("高度不能为空,请输入！");
		$("#areaHeight").focus();
		return false;
	}
	if($.trim($("#areaHeight").val()).match(/^[0-9]*$/)==null){
		alert("高度请输入数字");
		$("#areaHeight").val("");
		$("#areaHeight").focus();
		return false;
	}
	if (usePercent && parseInt($('#areaHeight').val())>100) {
		alert("使用百分比时，高度不能超过100");
		$("#areaHeight").focus();
		return false;
	}
	/*
	var zindex = $.trim($("#areaZindex").val());
	if(zindex==""){
		alert("zindex不能为空,请输入！");
		$("#areaZindex").focus();
		return false;
	}
	if($.trim($("#areaZindex").val()).match(/^[0-9]*$/)==null){
		alert("zindex请输入数字");
		$("#areaZindex").val("");
		$("#areaZindex").focus();
		return false;
	}
	*/
	/*
	var lcomment = $.trim($("#areaLcomment").val());
	if(lcomment==""){
		alert("注释不能为空,请输入！");
		$("#areaLcomment").focus();
		return false;
	}
	*/
	return true;
}
function updatePercentMode() {
	var usePercent = $('#percent_yes').attr('checked');
	if (usePercent) {
		$('#width').val('100');
		$('#height').val('100');
		$('#width').attr('readonly','readonly');
		$('#height').attr('readonly','readonly');
	} else {
		$('#width').removeAttr('readonly');
		$('#height').removeAttr('readonly');
	}
}
</script>

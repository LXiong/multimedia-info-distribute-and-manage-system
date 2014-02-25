<%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%><%@include file="taglib.jsp" 
%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-ui-timepicker-addon.js"></script>

<div id="Notification"></div>

<div id="box">
<s:url id="streamConfigUrl" action="stream" method="config"></s:url>
<form method="post" action="${streamConfigUrl }" id="configForm">
<input type="hidden" name="postBack" value="1" />
<label for="ip_box">流媒体服务器地址：</label><input type="text" id="ip_box" name="ip" /> <br />
<label>开始时间:</label>
<input type="text" name="startTime" id="startTime" readonly="readonly" value=""/>
<label>结束时间:</label>
<input type="text" name="endTime" id="endTime" readonly="readonly" value=""/>
<br />
<label>选择设备分组：</label>
<ul>
<c:forEach var="group1" items="${groupTypeList }">
	<li id="gr1_${group1.typeId }" class="group">
	<input type="checkbox" name="group1" class="group1" id="group1_${group1.typeId }" value="${group1.typeId }">
	<label for="group1_${group1.typeId }">${group1.typeName }</label>
	<ul>
	<c:forEach var="group2" items="${group1.groups }">
	<li class="group"><input type="checkbox" name="group2" class="gr2_${group1.typeId }" 
		id="gr_${group1.typeId }_${group2.groupId}" value="${group2.groupId}">
	<label for="gr_${group1.typeId }_${group2.groupId}">${group2.groupName }</label></li>
	</c:forEach>
	</ul>
	</li>
</c:forEach>
</ul>
<input type="button" class="btn_64" onclick="$('#configForm').submit()" value="设定" />
</form>
<script type="text/javascript">
<!--
function doConfig() {
	//$('#configForm').submit();
	var checked = false;
	$('input[type="checkbox"]').each (function () {
		if ($(this).attr('checked')) checked = true;
	});
	if (!checked ) {
		$('#Notification').jnotifyAddMessage({
			text: "请至少选择一个分组",
			permanent: false});
		return;
	}
	$.post("${streamConfigUrl}", $('#configForm').serialize(), function (data) {
		if (data.flag == 'true') {
			$('#Notification').jnotifyAddMessage({
				text: "指令发送成功",
				permanent: false});
		} else if (data.code == 'missing-param') {
			$('#Notification').jnotifyAddMessage({
				text: "指令发送失败, 缺少参数",
				permanent: false});
		} else {
			$('#Notification').jnotifyAddMessage({
				text: "指令发送失败: " + data.errorMsg,
				permanent: false});
		}
	},'json');
}

function setupValidation() {
	$('#configForm').validate({
		rules: {
			ip: { required : true },
			startTime: { required : true },
			endTime: { required : true }
		},
		submitHandler : doConfig
	});
}
function initDoc() {
	var loader = jQuery('<div id="loader"><img src="<%=request.getContextPath()%>/static/loading.gif" alt="loading..." /></div>')
		.css({position: "relative", top: "1em", left: "25em"})
		.appendTo("body")
		.hide();
	jQuery().ajaxStart(function() {
		loader.show();
	}).ajaxStop(function() {
		loader.hide();
	}).ajaxError(function(a, b, e) {
		throw e;
	});
	setupValidation();
	$('#Notification')
         .jnotifyInizialize({
             oneAtTime: false
             // ,appendType: 'append'
         })
         /*
         .css({ 'position': 'absolute',
             'marginTop': '20px',
             'right': '20px',
             'width': '250px',
             'z-index': '9999'
         })
         */
         ;
	
	$(".group1").click(function () {
		var isChecked = $(this).attr("checked");
		var id=$(this).val();
		$('input[name="group2"]','#gr1_'+id).attr("checked", isChecked);
	});
	
	$.datepicker.regional['zh-CN'] = {  
		    closeText: '关闭',  
		    prevText: '<上月',  
		    nextText: '下月>',  
		    currentText: '今天',  
		    monthNames: ['一月','二月','三月','四月','五月','六月',  
		    '七月','八月','九月','十月','十一月','十二月'],  
		    monthNamesShort: ['一','二','三','四','五','六',  
		    '七','八','九','十','十一','十二'],  
		    dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		    dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		    dayNamesMin: ['日','一','二','三','四','五','六'],  
		    weekHeader: '周',  
		    dateFormat: 'yy-mm-dd',
		    timeFormat: 'hh:mm',
		    firstDay: 1,  
		    isRTL: false,  
		    showMonthAfterYear: true,  
		    yearSuffix: '年'
		    };
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
	$.timepicker.regional['zh-CN'] = {
			currentText: '现在',
			closeText: '确定',
			ampm: false,
			timeFormat: 'hh:mm',
			timeOnlyTitle: '选择时间',
			timeText: '时间',
			hourText: '小时',
			minuteText: '分钟',
			secondText: '秒',
			timezoneText: '时区'
	};
	$.timepicker.setDefaults($.timepicker.regional['zh-CN']);
	
		
	var pickerOption = {
			showOn: "button",
			buttonImage: "images/calendar.gif",
			buttonImageOnly: true,
			dateFormat: 'yy-mm-dd',
			timeFormat: 'hh:mm'
		};
	$( "#startTime" ).datetimepicker(pickerOption);
	$( "#endTime" ).datetimepicker(pickerOption);
	
	//$( "#dateinput" ).val("<fmt:formatDate value='${dateinput}' pattern='MM/dd/yyyy'/>");
	//$( "#dateinput2" ).val("<fmt:formatDate value='${dateinput2}' pattern='MM/dd/yyyy'/>");
}
$(document).ready(initDoc());
//-->
</script>

</div>
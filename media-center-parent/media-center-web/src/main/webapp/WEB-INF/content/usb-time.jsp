<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>

<div class="midpanel">
	<form id="timeform" method="post" target="hiddenframe" enctype="multipart/form-data">
	<p>
	日期<input type="text" readonly="readonly" id="dateinput" name="dateinput" class="date" size="14" value="${currentDate }"></input> 
	时间<input type="text" id="hour" name = "hour" size="2" maxlength="2" value="${currentHour }"></input>:
	<input type="text" id="minute" name="minute" size="2" maxlength="2" value="${currentMinute }"></input>
	<br/>
	<a href="###" onclick="down_config()"><fmt:message key="usb.time.conffile" /></a> 
	<a href="###" onclick="down_verify()"><fmt:message key="usb.time.verifyfile" /></a></p>
	</form>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$("#dateinput").datepicker({
		showOn: "button",
		buttonImage: "images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "yy-mm-dd"
	});
});
function checkNum(){
	if($.trim($("#hour").val())=="" || $.trim($("#minute").val())==""){
		alert("时间不能为空");
		return false;
	}
	if($.trim($("#hour").val()).length==2){
			if($.trim($("#hour").val()).substring(0,1)>2){
				alert("小时输入格式不正确");
				$("#hour").val("");
				$("#hour").focus();
				return false;
			}if($.trim($("#hour").val()).substring(0,1)==2 && $.trim($("#hour").val()).substring(1)>=4){
				alert("小时输入格式不正确");
				$("#hour").val("");
				$("#hour").focus();
				return false;	
			}
	}
	if($.trim($("#minute").val()).length==2){
		if($.trim($("#minute").val()).substring(0,1)>=6){
				alert("分钟输入格式不正确");
				$("#minute").val("");
				$("#minute").focus();
				return false;
		}
  	}
	if($.trim($("#hour").val()).match(/^[0-9]*$/)==null){
		alert("小时请输入数字");
		$("#hour").val("");
		$("#hour").focus();
		return false ;  
	}
	if($.trim($("#minute").val()).match(/^[0-9]*$/)==null){
		alert("分钟请输入数字");
		$("#minute").val("");
		$("#minute").focus();
		return false;
	}
	return true;	
}
function down_config() {
	if(checkNum()){
		$("#timeform").attr("action","usb!downtime.action");       
		$("#timeform").submit();   
	}  
}
function down_verify() {
	if(checkNum()){
		$("#timeform").attr("action","usb!downtimeverify.action");
		$("#timeform").submit();
	}
}
</script>
<iframe id="hiddenframe" name="hiddenframe" width="0" height="0" frameborder="0" scrolling=no marginheight="0" marginwidth="0"></iframe>

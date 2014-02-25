<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<script type="text/javascript">
<!--
function compareTime(s1, s2) {
	var c1 = s1.split(':');
	var c2 = s2.split(':');
	var t1 = parseInt(c1[0])*100+parseInt(c1[1]);
	var t2 = parseInt(c2[0])*100+parseInt(c2[1]);
	if (t1 == t2) return 0;
	else if (t1<t2) return -1;
	return 1;
}
function checkTime(hourId, minId, message) {
	var hour = parseInt($(hourId).val());
	var minute = parseInt($(minId).val());
	var hourFlag = false;
	var minFlag = false;
	if (hour <0 || hour>23) {
		//alert("小时应该是0到23");
		hourFlag = true;
	}
	if (minute<0 || minute>59) {
		//alert("分钟应该是0到59");
		minFlag = true;
	}
	if (hourFlag || minFlag ) {
		alert(message +" " +(hourFlag ?"小时应该是0到23 ":'') +(minFlag?"分钟应该是0到59":''));
		return false;
	}
	return true;
}
function getTime(hourId, minId) {
	var hour = $(hourId).val();
	var minute = $(minId).val();
	return hour+":"+minute;
}
function setTime(hourId, minId, timeStr) {
	if (timeStr == null || timeStr == '') {
		$(hourId).val('');
		$(minId).val('');
		return;
	}
	var t = timeStr.split(':');
	$(hourId).val(t[0]);
	$(minId).val(t[1]);
}
//-->
</script>
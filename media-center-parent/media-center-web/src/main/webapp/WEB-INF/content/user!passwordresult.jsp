<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<div align="center" style="margin-top: 100x; font-size:14px ;color: red">
	<span id="ss">4</span>秒后自动跳转
</div>
<script>
setInterval("window.location.href='user!list.action'",3000);
show();
function show(){
	document.getElementById("ss").innerHTML=(document.getElementById("ss").innerHTML)-1;
	clearTimeout(aa);
	var aa = setTimeout("show()",1000);
}
</script>
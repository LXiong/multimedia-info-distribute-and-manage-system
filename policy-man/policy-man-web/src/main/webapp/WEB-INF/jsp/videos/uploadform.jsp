<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%><%@include 
	file="../taglib.jsp"%>
<script type="text/javascript">
<!--
	function ExtendCheck()
	{
		var pathname = $('#mfile').val();
		if (pathname == "")
		{
			alert("请先选择要上传的文件！");
			return false;
		}
		else
		{
			var num = pathname.lastIndexOf(".");
			var exname = pathname.substr(num + 1, pathname.length - num)
			exname = exname.toLowerCase();
			switch (exname)
			{
			//此处修改允许的文件类型，必须是小写
			// case "avi": case "mpg": case "mp4": // for videos
			case "txt": // allow text file
			
			// allow image files
			case "jpg":	case "gif":	case "bmp":	case "jpge":
				return true;
				break;
			default:
				alert("文件类型不正确");
				return false;
			}
			return false;
		}
	}
//-->
</script>
<div id="Notification"></div>

<sp:url var="uploadUrl" value="/videos/uploadform"></sp:url>
<form action="${uploadUrl}" method="post" enctype="multipart/form-data" onsubmit="return ExtendCheck()">
<input type="hidden" name="postBack" value="1" >
	媒体文件 <input type="file" name="file" id="mfile"/><br />
	可以上传jpg bmp　图片文件和txt文本文件<br />
<input type="submit" value='<fmt:message key="button.upload" />' class="btn_64">
</form>
<c:if test="${notifyKey != null }"><fmt:message var="notification" key="${notifyKey}" /></c:if>
<script type="text/javascript">
<!--
var notification = "${notification}";
function initDoc() {
	$('#Notification').jnotifyInizialize({
		onAtTime: true
	});
	if (notification != "") {
		$('#Notification').jnotifyAddMessage({
			text:notification,
			permanent : false
		});
	}
}
$(document).ready(initDoc);
//-->
</script>
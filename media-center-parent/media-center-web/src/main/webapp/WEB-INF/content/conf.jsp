<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %><%@include file="stb-js.jsp" 
%><script type="text/javascript">
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
});
/**
 * Add a new config file
 */
function configAdd() {
	window.location = path + "/config!getProperty.action";
}
/**
 * show the details of the config file
 */
function detail(confId, confName) {
	window.location = path + "/config!confContent.action?confId=" + confId + 
	"&confName=" + confName + "&updateTime=" + $("#" + confId + "updateTime").val();
}
/**
 * 
 */
function deleteConfig(confId) {
	if(confirm("<fmt:message key='config.file.delete' />")) {
		var url = path + "/conf!deleteConf.action"; //path + "/config!deleteConfig.action";
		var params = {"confId":confId };
		$.post(url, params, function() {
			/*url = path + "/conf!deleteConf.action";
			$.post(url, params);*/
			alert("<fmt:message key='config.file.delete.success' />");
			window.location.reload();
		});
	}
}
</script>
<div align="right"><button class="btn_64" onclick="configAdd();"><fmt:message key="config.file.add" /></button></div>
<!-- policy file list -->
<div><table class="linetable">
	<thead><tr>
		<th><fmt:message key="config.file.name"/></th>
		<!--<th><fmt:message key="config.file.version"/></th>-->
		<th><fmt:message key="config.file.create.time"/></th>
		<th><fmt:message key="config.file.update.time"/></th>
		<th><fmt:message key="Operation"/></th>
	</tr></thead>
	<tbody><c:forEach var="conf" items="${confs }"><tr>
		<td>${conf.confName }</td>
		<!--<td>${conf.confVersion }</td>-->
		<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${conf.createTime }" /></td>
		<td>
			<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${conf.updateTime }" />
			<input id="${conf.confId }updateTime" type="hidden" value="<fmt:formatDate pattern='yyyyMMddHHmmss' value='${conf.updateTime }' />"></input>
		</td>
		<td><a href="javascript:;" onclick="detail('${conf.confId }','${conf.confName }');">
			<fmt:message key="Details"/></a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="deleteConfig('${conf.confId }');">
			<fmt:message key="Delete"/></a>
		</td>
	</tr></c:forEach></tbody>
</table>
</div>

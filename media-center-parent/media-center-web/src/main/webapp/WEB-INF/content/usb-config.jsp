<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %><%@include file="stb-js.jsp" 
%><script type="text/javascript">
$(document).ready(function() {
	setPath("<%=request.getContextPath() %>");
});
/**
 * show the details of the config file
 */
function detail(confId, confName) {
	window.location = path + "/usb!confContent.action?confId=" + confId + "&confName=" + confName;
}
</script>
<!-- policy file list -->
<s:url var="configUrl" action="usb" method="config"><s:param name="page">{p}</s:param></s:url>
<div class="operate">
	<yun:pageLink totalPage="${pageBean.totalPage}" currentPage="${pageBean.curPage}"
		link="${configUrl}"></yun:pageLink>
</div>
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
		<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${conf.updateTime }" /></td>
		<td><a href="javascript:;" onclick="detail('${conf.confId }', '${conf.confName }');">
			<fmt:message key="Details"/></a>
		</td>
	</tr></c:forEach></tbody>
</table>
</div>
<div class="operate">
	<yun:pageLink totalPage="${pageBean.totalPage}" currentPage="${pageBean.curPage}"
		link="${configUrl}"></yun:pageLink>
</div>

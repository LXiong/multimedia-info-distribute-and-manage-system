<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" 
%><script type="text/javascript">
/**
 * 
 */
function cancel() {
	window.location = "module-file.action?page=0";
}
</script>
<!-- module file list -->
<div><form action="module-file!addModuleFile.action" method="post" >
	<table class="linetable">
		<tr><td><fmt:message key="module.file.name"/></td>
			<td><select id="module" name="module">
				<c:forEach var="item" items="${module }" >
					<option value="${item.key }" >${item.value }</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr><td><fmt:message key="module.file.version"/></td>
			<td><input id="version" name="version"></input></td>
		</tr>
		<tr><td><fmt:message key="module.file.comment"/></td>
			<td><input id="comment" name="comment"></input></td>
		</tr>
		<tr><td><fmt:message key="module.file.path"/></td>
			<td><input id="filePath" name="filePath"></input></td>
		</tr>
		<tr><td><fmt:message key="module.file.verify.code"/></td>
			<td><input id="verifyCode" name="verifyCode"></input></td>
		</tr>
	</table>
	<button type="submit" class="btn_64" ><fmt:message key='save'/></button>
	<input type="button" class="btn_64" value="<fmt:message key='Cancel'/>" onclick="cancel();" />
</form></div>
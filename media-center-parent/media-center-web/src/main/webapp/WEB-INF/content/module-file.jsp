<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" 
%><script type="text/javascript">
/**
 * 
 */
function getPage(page) {
	location = "module-file.action?page=" + page;
}
/**
 * 
 */
function moduleFileEdit(id, module, version, comment, filePath, verifyCode) {
	$("#msg").empty();
	if($("#" + arguments[1]).attr("disabled") == true) {
		$("#" + id).text("<fmt:message key='Cancel' />");
		for(var i=1; i<arguments.length; i++){
			$("#" + arguments[i]).removeAttr("disabled");
		}
		$("#" + id + "Save").removeAttr("disabled");
		$("#" + id + "Save").attr("class", "btn_64");
	} else {
		window.location = "module-file.action?page=${page}";
	}
}
/**
 * 
 */
function moduleFileSave(id, module, version, comment, filePath, verifyCode) {
	var url = "module-file!edit.action";
	var params = {"id":id, "module":$("#" + module).val(), "version":$("#" + version).val(), 
			"comment":$("#" + comment).val(), "filePath":$("#" + filePath).val(), 
			"verifyCode":$("#" + verifyCode).val()};
	$.post(url, params, function() {
		location = "module-file.action?page=${page}";
	});
}
/**
 * 
 */
function moduleFileDel(fileId) {
	var url = "module-file!del.action";
	var params = {"id":fileId};
	$.post(url, params, function(data) {
		if(data != null && data != "") {
			$("#msg").empty();
			$("#msg").append(data);
		} else {
			getPage(${page});
		}
	});
}
</script>
<div id="msg" align="center" style="color: red; font-size: 12px" ></div>
<div class="operate">
	<div class="page">
		${page }/${pageNums }&nbsp;
	    <c:if test="${page > 3 && pageNums > 5}">
	    	<a href="#" onclick="getPage(${1});"> |&lt; </a>
			<a href="#" onclick="getPage(${page-1});"> &lt; </a>
		</c:if>
		<c:forEach var="i" items="${pageArr}" >
			<c:choose>
				<c:when test="${page == i }" ><span>${i}</span></c:when>
				<c:when test="${i > 0 && i <= pageNums }" ><a href="#" onclick="getPage(${i });">${i }</a></c:when>
			</c:choose>
		</c:forEach>
		<c:if test="${page+2 < pageNums && pageNums > 5 }">
	    	<a href="#" onclick="getPage(${page+1});"> &gt; </a>
	    	<a href="#" onclick="getPage(${pageNums});"> &gt;| </a>
	    </c:if>
	</div>
</div>
<!-- module file list -->
<div><table class="linetable">
	<thead><tr>
		<th><fmt:message key="module.file.name"/></th>
		<th><fmt:message key="module.file.version"/></th>
		<th><fmt:message key="module.file.comment"/></th>
		<th><fmt:message key="module.file.release.time"/></th>
		<th><fmt:message key="module.file.path"/></th>
		<th><fmt:message key="module.file.verify.code"/></th>
		<th><fmt:message key="Operation"/></th>
	</tr></thead>
	<tbody><c:forEach var="moduleFile" items="${moduleFiles }"><tr>
		<td><select id="${moduleFile.id }module" disabled="disabled" >
			<c:forEach var="item" items="${module }" >
				<c:choose>
					<c:when test="${moduleFile.module == item.key}" >
						<option selected="selected" value="${item.key }" >${item.value }</option>
					</c:when>
					<c:otherwise>
						<option value="${item.key }" >${item.value }</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select></td>
		<td><input id="${moduleFile.id }version" disabled="disabled" value="${moduleFile.version }"></input></td>
		<td><input id="${moduleFile.id }comment" disabled="disabled" value="${moduleFile.file_comment }"></input></td>
		<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${moduleFile.releaseTime }" /></td>
		<td><input id="${moduleFile.id }filePath" disabled="disabled" value="${moduleFile.filePath }"></input></td>
		<td><input id="${moduleFile.id }verifyCode" disabled="disabled" value="${moduleFile.verflyCode }"></input></td>
		<td><button id="${moduleFile.id }" class="btn_64" onclick="moduleFileEdit('${moduleFile.id }', 
				'${moduleFile.id }module', '${moduleFile.id }version', '${moduleFile.id }comment', 
				'${moduleFile.id }filePath', '${moduleFile.id }verifyCode');" ><fmt:message key='Edit'/></button>
			<button id="${moduleFile.id }Save" class="btn_64d" disabled="disabled" 
				onclick="moduleFileSave('${moduleFile.id }', '${moduleFile.id }module', 
				'${moduleFile.id }version', '${moduleFile.id }comment', '${moduleFile.id }filePath', 
				'${moduleFile.id }verifyCode');" ><fmt:message key="save"/></button>
			<button class="btn_64" onclick="moduleFileDel('${moduleFile.id }');" ><fmt:message key="Delete"/></button>
		</td>
	</tr></c:forEach></tbody>
</table>
</div>
<div class="operate">
	<div class="page">
		${page }/${pageNums }&nbsp;
	    <c:if test="${page > 3 && pageNums > 5}">
	    	<a href="#" onclick="getPage(${1});"> |&lt; </a>
			<a href="#" onclick="getPage(${page-1});"> &lt; </a>
		</c:if>
		<c:forEach var="i" items="${pageArr}" >
			<c:choose>
				<c:when test="${page == i }" ><span>${i}</span></c:when>
				<c:when test="${i > 0 && i <= pageNums }" ><a href="#" onclick="getPage(${i });">${i }</a></c:when>
			</c:choose>
		</c:forEach>
		<c:if test="${page+2 < pageNums && pageNums > 5 }">
	    	<a href="#" onclick="getPage(${page+1});"> &gt; </a>
	    	<a href="#" onclick="getPage(${pageNums});"> &gt;| </a>
	    </c:if>
	</div>
</div>
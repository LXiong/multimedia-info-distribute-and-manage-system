<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<div class="operate">
<button id="btnEdit"><fmt:message key="button.edit" /></button>
</div>
<sp:url value="/personal/edit" var="personalEditUrl" />
<script type="text/javascript">
$(document).ready(
		function () {
			$('button').button();
			$('#btnEdit').click(function() {
				location.href="${personalEditUrl}";
			});
		}
		);
</script>
<table class="linetable">
<tr><th>属性</th><th>值</th></tr>
<tbody>
<tr>
<td>用户名</td><td>${user.username }</td>
</tr>
<tr>
<td>显示名</td><td>${user.dispName }</td>
</tr>
<tr>
<td>电子邮件</td><td>${user.email }</td>
</tr>
<tr>
<td>电话</td><td>${user.phone }</td>
</tr>
</tbody>
</table>
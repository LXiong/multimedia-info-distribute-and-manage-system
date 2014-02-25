<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<s:url action="personal" var="infoUrl" />
<script type="text/javascript">
function backToInfo () {
	location.href="${infoUrl}";
}
</script>
<s:form action="personal!save" cssClass="linetable" cssStyle="width:300px;">
	<s:hidden name="userInfo.userId" />
	<s:textfield name="userInfo.username" label="用户名" ></s:textfield>
	<s:textfield name="userInfo.telephone" label="电话号"></s:textfield>
	<s:textfield name="userInfo.email" label="电子邮件" ></s:textfield>
	<tr>
	<td></td>
	<td>
	<s:submit value="提交" theme="simple"></s:submit>
	<input type="button" value="<fmt:message key="Cancel" />" onclick="backToInfo()">
	</td>
	</tr>
</s:form>

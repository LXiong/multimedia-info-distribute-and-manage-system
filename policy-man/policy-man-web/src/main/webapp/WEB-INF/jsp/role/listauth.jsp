<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<div id="authority">
<form id="formAuth" method="post">
<input type="hidden" name="id" value="${roleId}" />
<input type="hidden" name="authority" value="" />
<ul class="group_list">
	<c:forEach var="authGroup" items="${authGroupList }">
	<li><input type="checkbox" id="authGroup${authGroup.id }" class="auth_group" ${authGroup.checkAll?'checked':'' }/>
	<label for="authGroup${authGroup.id }">${authGroup.localName }</label></li>
	<li><ul class="group_child">
		<c:forEach var="auth" items="${authGroup.authorities }">
			<li><input type="checkbox" id="authGroup${authGroup.id }_auth${auth.id }" 
				class="authority authGroup${authGroup.id }" value="${auth.id }"
				name="authority" ${auth.granted?'checked':'' } />
			<label for="auth${auth.id }">${auth.localName }</label>
			</li>
		</c:forEach>
	</ul>
	</li>
	</c:forEach>
</ul>
</form>
</div>

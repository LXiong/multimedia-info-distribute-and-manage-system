<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp" %>

<div class="operate">
    <div><fmt:message key="Role" />: ${role.rolename}</div>
</div>
<c:if test="${flash_message != null}"><div class="info"><b>${flash_message}</b></div></c:if>
<style type="text/css">
.scrolly {overflow:scroll; width:300px; height:300px;}
</style>
<form action="role!saveauth" method="POST">
<input type="hidden" name="roleid" value="${role.roleId }" />

<div >
<table style="width:300px">
<thead>
  <tr>
    <th><fmt:message key="Authority"/></th>
  </tr>
 </thead>
 <tbody id="tbody" class="scrolly">
 <c:forEach var="auth" items="${authList}">
  <tr>
  	<td>
  	<input type="checkbox" name="auth" class="rad" id="auth${auth.id }" 
  	value="${auth.id }" <c:if test="${auth.granted}">CHECKED</c:if> />
  	<label for="auth${auth.id }">${auth.localName }</label>
  	</td>
  </tr>
 </c:forEach>
</tbody>
</table>
</div>
<div class="operate">
    <div class="btn">
    	<div class="btn">
		    <input type="button" name="" value="<fmt:message key="Submit" />" class="btn_64" onclick="submit()" />
		    <input type="button" name="" value="<fmt:message key="Cancel" />" class="btn_64" onclick="backtorolelist()" />
	    </div>
    </div>
</div>
</form>
<s:url id="roleListUrl" action="role" method="list" />
<script type="text/javascript">
$(document).ready(function(){
	show();
});

function show(){
var tbody = $("#tbody>tr").size();
for(var i=0;i<tbody;i++){
$("#tbody>tr").eq(i).mouseover(function(){$(this).css("background-color","pink")});
$("#tbody>tr").eq(i).mouseout (function(){$(this).css("background-color","white")});
	}
}
function backtorolelist() {
	window.location="${roleListUrl}";
}
</script>
	

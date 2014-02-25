<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%> <%@include file="taglib.jsp" %>
<div class="operate">
    <div class="btn">
    <input type="button" name="" value="<fmt:message key="admin.user-role.edit" />" class="btn_117" onclick="check()" />
    </div>
    <div class="page">
	    ${map.page }/${map.count }
	    <c:if test="${map.page == 1 }"><span class="disabled"> |&lt; </span>
	    <span class="disabled"> &lt; </span></c:if>
	    <c:if test="${map.page gt 1 }">
	    <a href="javascript:page(1)"> |&lt; </a>
	    <a href="javascript:page(${map.page-1})"> &lt; </a>
	    </c:if>
	    <yun:pageNumbers varIsCurrent="isCur" varPage="curPage" totalPage="${map.count}" currentPage="${map.page}">
	    	<c:if test="${isCur}"><span>${curPage}</span></c:if>
	    	<c:if test="${not isCur}"><a href="javascript:page(${curPage})">${curPage}</a></c:if>
	    </yun:pageNumbers>
	    <c:if test="${map.page == map.count }"><span class="disabled"> &gt; </span>
	    <span class="disabled"> &gt;| </span></c:if>
	    <c:if test="${map.page lt map.count }">
	    <a href="javascript:page(${map.page+1})"> &gt; </a>
	    <a href="javascript:page(${map.count})"> &gt;| </a>
	    </c:if>
    </div>                                
</div>	
<table  class="linetable">
<thead>
<tr>
	<th>选择</th>
	<th>用户名</th>
	<th>真名</th>
	<th>电话</th>
	<th>Email</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="user" items="${map.list}">
	<tr>
	<td><input type="radio"  name="rad" id="rad" value="${user.userId }" class="rad"></td>
	<td>${user.loginName }</td>
	<td>${user.userName }</td>
	<td>${user.telephone }</td>
	<td>${user.email }</td>
	</tr>
</c:forEach>
</tbody>
</table>
<div class="operate">

<div class="btn">
    </div>
    <div class="page">
	    ${map.page }/${map.count }
	    <c:if test="${map.page == 1 }"><span class="disabled"> |&lt; </span>
	    <span class="disabled"> &lt; </span></c:if>
	    <c:if test="${map.page gt 1 }">
	    <a href="javascript:page(1)"> |&lt; </a>
	    <a href="javascript:page(${map.page-1})"> &lt; </a>
	    </c:if>
	    <yun:pageNumbers varIsCurrent="isCur" varPage="curPage" totalPage="${map.count}" currentPage="${map.page}">
	    	<c:if test="${isCur}"><span>${curPage}</span></c:if>
	    	<c:if test="${not isCur}"><a href="javascript:page(${curPage})">${curPage}</a></c:if>
	    </yun:pageNumbers>
	    <c:if test="${map.page == map.count }"><span class="disabled"> &gt; </span>
	    <span class="disabled"> &gt;| </span></c:if>
	    <c:if test="${map.page lt map.count }">
	    <a href="javascript:page(${map.page+1})"> &gt; </a>
	    <a href="javascript:page(${map.count})"> &gt;| </a>
	    </c:if>
    </div>                                
</div>	
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

function check(){
		if($(".rad:checked").size()<1){
				alert("请选择先！");
				return;
		}
		var userId=$(".rad:checked").val();

		location="user-role!update.action?userId="+userId;
	
}

function page(page){
	location="user-role.action?page="+page;
}
</script>

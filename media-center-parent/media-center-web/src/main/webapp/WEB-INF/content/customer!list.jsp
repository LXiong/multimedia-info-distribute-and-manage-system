<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
<div class="operate">
    <div class="btn">
    <input type="button" name="" value="<fmt:message key="admin.customer.add" />" class="btn_64" onclick="add()" />
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
<table class="linetable">
<thead>
<tr>
	<th>客户名字</th>
	<th>客户地址</th>
	<th>联系方式</th>
	<th>操作</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="customers" items="${map.list}">
	<tr>
	<td>${customers.custname}</td>
	<td>${customers.address}</td>
	<td>${customers.telephone}</td>
	<td><a href="javascript:edit('${customers.custId}')" >编辑</a> | <a href="javascript:del('${customers.custId}')" >删除</a></td>
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
			show()
});
function show(){
	var tbody = $("#tbody>tr").size();
	for(var i=0;i<tbody;i++){
		$("#tbody>tr").eq(i).mouseover(function(){$(this).css("background-color","pink")});
		$("#tbody>tr").eq(i).mouseout (function(){$(this).css("background-color","white")});
		}
}
function del(id){
	if(confirm("您真的要删除吗?"))
	  {
		location='customer!delById.action?custid='+id;
	  }	
}
function edit(userid){
	location='customer!edit.action?custid='+userid;					
}
function add(){
	location='customer!add.action';
}
function page(page){
	location='customer!list.action?page='+page;
}
</script>			
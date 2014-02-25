<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
<div class="operate">
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
	<th>软件包名称</th>
	<th>软件包说明</th>
	<th>更新时间</th>
	<th>操作</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="box" items="${map.list}">
	<tr>
	<td>${box.name }</td>
	<td>${box.description }</td>
	<td>
		<fmt:formatDate value="${box.updateAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
	</td>
		<td>
		<a href="javascript:edit('${box.id }')">软件包详情</a>
		
		<a href="javascript:add('${box.id }','${box.name}')">模块文件添加</a>
		
		<a href="javascript:del('${box.id }')">删除软件包</a>
		</td>
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

function page(page){
		location="box-package!list.action?page="+page;
}
function edit(id){
		location='box-package!edit.action?id='+id;
}
function add(id,name){
		url='box-package!add.action?id='+id+'&boxname='+name;
		location=url;
}
function see(id){
	location='box-package!see.action?id='+id;
}
function del(id){
	if(confirm("您确认删除?")){
		location='box-package!delBoxPackageById.action?id='+id;
	}
}
</script>

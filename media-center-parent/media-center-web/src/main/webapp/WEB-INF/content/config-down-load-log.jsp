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
	<th>下载时间</th>
	<th>下载文件</th>
	<th>用户名</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="downloadlog" items="${map.list}">
	<tr>
	<td>
		<fmt:formatDate value="${downloadlog.downloadtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
	</td>
	<td>${downloadlog.downloaditem }</td>
	<td>${downloadlog.userName }</td>
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
		location="config-down-load-log!list.action?page="+page;
}

</script>

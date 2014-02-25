<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<div class="operate">
    <div class="btn">
    <input type="button" name="" value="<fmt:message key="admin.role.add" />" class="btn_64" onclick="add()" />
    <input type="button" name="" value="<fmt:message key="admin.role.function" />" class="btn_117" onclick="changeFunction()" />
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
  	<th>选择</th>
    <th>角色名称</th>
    <th>角色描述</th>
    <th>操作</th>
  </tr>
 </thead>
 <tbody id="tbody" >
 <c:forEach var="role" items="${map.list}">
  <tr>
  	<td><input type="radio" name="rad" id="rad" class="rad"  value="${role.roleId }"></td>
    <td>${role.rolename }</td>
    <td>${role.describe }</td>
    <td><a href="javascript:edit('${role.roleId}')" >编辑</a> | <a href="javascript:del('${role.roleId}')" >删除</a></td>
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

function add(){
		location='role!add.action';
	}

function edit(roleid){
		location="role!edit.action?roleid="+roleid;
}

function del(roleid){
		if(confirm("真的要删除吗?")){
			location='role!del.action?roleid='+roleid;
		}
}
function changeFunction(){
	if($(".rad:checked").size()<1){
		alert("请选择先！");
		return;
}
	var roleid=$(".rad:checked").val();

	location="role!function.action?roleid="+roleid;
}
function page(page){
	location="role!list.action?page="+page;
}
</script>
	

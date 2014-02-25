<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
<hr>
<p style="font-size:15px;color: red;margin-left: 500px" >当前软件包的名称:${boxname}</p>
<p style="color: blue">已有的模块文件</p>
<table class="linetable">
<thead>
<tr>
	<th>模块</th>
	<th>版本</th>
	<th>意见</th>
	<th>发布时间</th>
	<th>文件路径</th>
	<th>验证码</th>
	<th>操作</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="m" items="${list}">
	<tr>
	<td>${m.module }</td>
	<td>${m.version }</td>
	<td>${m.file_comment }</td>
	<td>
		<fmt:formatDate value="${m.releaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	</td>
	<td>${m.filePath }</td>
	<td>${m.verflyCode }</td>
	<td><a href="javascript:del('${m.id }')" >去除</a></td>
	</tr>
</c:forEach>
</tbody>
</table>
<br>
<p style="color: blue">所有的模块文件</p>
 <form action="box-package!add.action?boxname=${boxname }"  method="post" >
 <input type="hidden" name="id"  id = "id" value="${id}">
<div class="operate">
 <div class="btn">
	<s:select cssStyle="margin-right:20px"  headerKey="0" headerValue="请选择"  label="模块" id="module" name="module"
		listKey="key" listValue="value" list="#request.optionMap"
		></s:select>
	版本:<input type="text" onkeyup="checkNum(this)" value="${version }" id="version" name="version">
	<span style="font-size: 15px">发布时间</span>
	<input type="text" readonly="readonly"  value="<fmt:formatDate value='${dateinput}' pattern='yyyy-MM-dd'/>" id="dateinput" name="dateinput" class="date dateinput" size="14" ></input>
		----
	<input type="text" readonly="readonly" value="<fmt:formatDate value='${dateinput2}' pattern='yyyy-MM-dd'/>" id="dateinput2" name="dateinput2" class="date dateinput" size="14" ></input>
	<input type="submit" value="<fmt:message key="module.file.filter" />" class="btn_64" />
    </div>
</div>  
</form>                              
<table class="linetable">
<thead>
<tr>
	<th><input type="checkbox" onclick="selectAll()" id="first_chk"></th>
	<th>模块</th>
	<th>版本</th>
	<th>意见</th>
	<th>发布时间</th>
	<th>文件路径</th>
	<th>验证码</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="module" items="${map.list}">
	<tr>
	<td><input type="checkbox" id="chk" value="${module.id}" class="chk"></td>
	<td>${module.module }</td>
	<td>${module.version }</td>
	<td>${module.file_comment }</td>
	<td>
		<fmt:formatDate value="${module.releaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	</td>
	<td>${module.filePath }</td>
	<td>${module.verflyCode }</td>
	</tr>
</c:forEach>
</tbody>
</table>
 <div class="btn">
    <input type="button" name="" value="<fmt:message key="module.file.add" />" class="btn_64" 
    	onclick="add()" />
    	<input type="button" class="btn_64"  onclick="javascirpt:location='box-package!list.action'" value="返回">
    </div>
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
$(document).ready(function() {
	$(".dateinput").datepicker({
		showOn: "button",
		buttonImage: "images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "yy-mm-dd"
	});
});
function selectAll(){
	var checked = $("#first_chk").attr("checked");
	$(".chk").attr("checked",checked);
}
function add(){
	var tbd = $("#tbody>tr");
	var module ="";
	$(".chk:checked").each(function(){
		module=module+","+$(this).parent().next("td").html();
	});
	url='box-package!addFile.action?id='+$("#id").val()+'&module='+module+'&boxname='+'${boxname}'+'&ids=-999';
	if($(".chk:checked").size()<1){
		alert("请选择先！");
		return;		
	}
	if(confirm("确认添加吗?")){
	$(".chk:checked").each(function(){
				url = url+","+$(this).val();		
		});
		location=url;
	}
}
function del(id){
	if(confirm("真的要去除吗?")){
		url='box-package!del.action?fileid='+id+'&boxname='+'${boxname}'+'&id='+$('#id').val();
		location=url;
	}
}
function checkNum(obj){
		if(isNaN($(obj).val())){
			alert("请输入一个数字");
			$(obj).focus();
			$(obj).val("");
		}
}
function page(page){
	location="box-package!list.action?page="+page;
}
</script>
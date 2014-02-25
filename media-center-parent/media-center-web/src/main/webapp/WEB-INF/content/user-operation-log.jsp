<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
<%@include file="inc-checkdate-js.jsp" %>
<div class="operate">
<form action="user-operation-log.action" METHOD="post" onsubmit="return check_filter_date('from','to')">
起止时间
<input type="text" id="from" name="from" class="date dateinput" size="14" ></input>
		--
<input type="text" id="to" name="to" class="date dateinput" size="14" ></input>
是否成功<s:select  headerKey="" headerValue="请选择" name="isok" list="#{'Y':'成功','N':'失败'}" id="isok"></s:select>
<input type="submit" value="查询" class="btn_64"   />
</form>
    <div class="page">
    	<yun:pageLink totalPage="${pageBean.totalPage}" currentPage="${pageBean.curPage}"
    	link="javascript:page({p})"></yun:pageLink>
    </div>                                
</div>

<table class="linetable">
<thead>
<tr>
	<th>操作时间</th>
	<th>操作者</th>
	<th>执行的动作</th>
	<th>被执行对象</th>
	<th>对象ID</th>
	<th>是否成功</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="operationlog" items="${logList}">
	<tr>
		<td>
			<fmt:formatDate value="${operationlog.operationtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
		<td>${operationlog.userName }</td>
		<td>${operationlog.action }</td>
		<td>${operationlog.updateobject }</td>
		<td>${operationlog.objectid }</td>
		<td>${operationlog.issuccess }</td>
	</tr>
</c:forEach>
</tbody>
</table>

<div class="operate">
    <div class="btn">
    </div>
    <div class="page">
	    <yun:pageLink totalPage="${pageBean.totalPage}" currentPage="${pageBean.curPage}"
    	link="javascript:page({p})"></yun:pageLink>
    </div>                                
</div>
<script type="text/javascript">
$(document).ready(function() {
	/* $(".dateinput").datepicker({
		showOn: "button",
		buttonImage: "images/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "yy-mm-dd"
	}); */
	$( "#from" ).datepicker({ dateFormat: 'yy-mm-dd' });
	$( "#to" ).datepicker({ dateFormat: 'yy-mm-dd' });
	$( "#from" ).val("${from}");
	$( "#to" ).val("${to}");
	$( "#isok" ).val("${isok}");
});
function page(page){
	url=null;
	if($("#dateinput").val()=="" && $("#dateinput2").val()==""){
		url="user-operation-log!list.action?page="+page+'&isok='+$("#isok").val();
	}else if($("#dateinput").val()==""){
		url="user-operation-log!list.action?page="+page+'&isok='+$("#isok").val()+'&dateinput2='+$("#dateinput2").val();
	}else if($("#dateinput2").val()==""){
		url="user-operation-log!list.action?page="+page+'&isok='+$("#isok").val()+'&dateinput='+$("#dateinput").val();
	}else{
		"user-operation-log!list.action?page="+page+'&isok='+$("#isok").val()+'&dateinput='+$("#dateinput").val()+'&dateinput2='+$("#dateinput2").val();
	}
	location=url;
}

</script>
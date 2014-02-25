<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
<%@include file="inc-checkdate-js.jsp" %>
<div class="operate">
<form action="login-log.action" method="post" onsubmit="return check_filter_date('from','to')" >
起止时间
<input type="text" id="from" name="from" class="date dateinput" size="14" ></input>
		--
<input type="text" id="to" name="to" class="date dateinput" size="14" ></input>
是否成功
<s:select headerKey="" headerValue="请选择" name="isok" list="#{'Y':'成功','N':'失败'}" id="isok" >
</s:select>
<input type="submit" value="查询" class="btn_64"  />
</form>
    <div class="page">
    	<yun:pageLink totalPage="${pageBean.totalPage}" currentPage="${pageBean.curPage}" 
    		link="javascript:page({p})"></yun:pageLink>
    </div>                                
</div>

<table class="linetable">
<thead>
<tr>
	<th>登录时间</th>
	<th>登录用户</th>
	<th>登录IP</th>
	<th>是否成功</th>
</tr>
</thead>
<tbody id="tbody">
<c:forEach var="loginlog" items="${logList}">
	<tr>
	<td>
		<fmt:formatDate value="${loginlog.logintime }" pattern="yyyy-MM-dd HH:mm:ss"/>
	</td>
	<td>${loginlog.userName }</td>
	<td>${loginlog.remoteIp }</td>
	<td>${loginlog.issuccess }</td>
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
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : "mm/dd/yy"
		}); */
		$( "#from" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#to" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#from" ).val("${from}");
		$( "#to" ).val("${to}");
		$( "#isok" ).val("${isok}");
	});
	
	function page(page) {
		url = null;
		if ($("#dateinput").val() == "" && $("#dateinput2").val() == "") {
			url = "login-log!list.action?page=" + page + '&isok='
					+ $("#isok").val();
		} else if ($("#dateinput").val() == "") {
			url = "login-log!list.action?page=" + page + '&isok='
					+ $("#isok").val() + '&dateinput2='
					+ $("#dateinput2").val();
		} else if ($("#dateinput2").val() == "") {
			url = "login-log!list.action?page=" + page + '&isok='
					+ $("#isok").val() + '&dateinput=' + $("#dateinput").val();
		} else {
			url = "login-log!list.action?page=" + page + '&isok='
					+ $("#isok").val() + '&dateinput=' + $("#dateinput").val()
					+ '&dateinput2=' + $("#dateinput2").val();
		}
		location = url;
	}
</script>

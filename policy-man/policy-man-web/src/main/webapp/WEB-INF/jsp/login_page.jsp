<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/css/global.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
function check(){
	if($("#loginname").val()==""){alert("请输入您的登录名"); return false};
	if($("#password" ).val()==""){alert("请输入您的密码");return false};
	return true;
}
</script>
</head>
<body>
<div id="login" class="shadow" style="text-align:center;">
    <div class="innerDiv">
    	<form method="post" action="j_spring_security_check" onsubmit="return check()">
        <div class="item_list" >
            <div class="pic"><img src="<%=request.getContextPath() %>/static/images/logo.jpg" /></div>
            <c:if test='${!(empty param["login_error"])}'>
	        	<div>
	        	<font color="red">
	        		<fmt:message key="login.failed" /><br />
	        		<c:if test='${!empty SPRING_SECURITY_LAST_EXCEPTION && SPRING_SECURITY_LAST_EXCEPTION.message=="Bad credentials" }'>
	        			<fmt:message key="login.bad.credential" />
	        		</c:if>
	        	</font>
	        	</div>
	        </c:if>
            <ul class="cf">
                <li>
                <label for="username" style="width:200px"><fmt:message key="login.label.login_id" /></label>
                <input style="left:200px;" type="text" name="j_username" id="username" class="input1" />
                </li>
                <li>
                <label for="password" style="width:200px"><fmt:message key="login.label.password" /></label>
                <input style="left:200px;" type="password" value="" id="password" name="j_password" class="input1">
                </li>
             </ul>
            <div class="btn" style="text-align:center;margin-left:22px;">
            <input type="submit" id="submit" class="btn1_64" value="<fmt:message key="Login" />"/>
            </div>                                      
        </div>
        </form>
    </div>
</div>

</body>
</html>
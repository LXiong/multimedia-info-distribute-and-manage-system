<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/global.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
function check(){
	if($("#loginname").val()==""){alert("请输入您的登录名"); return false};
	if($("#password" ).val()==""){alert("请输入您的密码");return false};
	return true;
}
</script>
</head>
<body>
<div id="login" class="shadow">
    <div class="innerDiv">
    	<s:form method="post" action="login!login" onsubmit="return check()" id="login-form" theme="simple">
        <div class="item_list" style="position:relative">
        
            <div class="pic"><img src="<%=request.getContextPath() %>/images/logo.jpg" /></div>
            <ul class="cf">
                <li>
                <label><s:text name="Loginame" /></label>
                <input type="text" value="" id="loginname" name="loginname" class="input1">
                </li>
                <li>
                <label><s:text name="login.password" /></label>
                <input type="password" value="" id="password" name="password" class="input1">
                </li>
             </ul>
            <div class="btn" style="text-align:center;margin-left:22px;">
            <s:submit key="Login" cssClass="btn1_64" theme="simple"></s:submit>
            </div>                                      
        </div>
        </s:form>
    </div>
</div>

</body>
</html>
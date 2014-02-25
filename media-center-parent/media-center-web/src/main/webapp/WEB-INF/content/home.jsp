<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@include file="taglib.jsp" %>
<p>
<fmt:message key="home.welcome" />
${userName }
<br />
<fmt:message key="home.lastlogin" />
${lastLoginTime }
</p>
<c:if test="${status eq false }">
<table class="linetable">
<tbody id="tab">
<tr><th><fmt:message key="home.server.status" /></th></tr>
<tr><th><fmt:message key="home.server.auth" /></th></tr>
<tr>
<td>
	<table class="linetable">
		<tr>
			<td>成功认证播放盒数量</td><td>需要审核播放盒数量</td><td>暂停使用播放盒数量</td><td>认证失败的次数</td>
		</tr>
		<tr>
			<td>${map.normal }</td><td>${map.waitauth }</td><td>${map.blocked }</td><td>${map.fail }</td>
		</tr>
	</table>
</td>
</tr>
<tr><th><fmt:message key="home.server.watch" /></th></tr>
<tr>
<td>
	<table class="linetable">
		<tr>
			<th>监控的stb数量</th><th>Ip地址</th><th>端口</th><th>开启的时间</th>
		</tr>
		<c:forEach  var="watch" items="${list }">
		<tr>
			<td>${watch.stbCount }</td><td>${watch.watcherIp }</td><td>${watch.watchPort }</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${watch.startUpTime }"/></td>
		</tr>
		</c:forEach>
	</table>
</td>
</tr>
</tbody>
</table>
</c:if>
<span style="color: red">
<c:if test="${status eq true}">
	 服务器无法连接，请稍后再试!!
</c:if>
</span>
<table class="linetable">
	<caption style="text-align:center;">机顶盒最近未处理告警信息</caption>
	<thead>
		<tr>
			<th>MAC地址</th>
			<th>告警类型</th>
			<th>告警时间</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${infos}" var="info">
			<tr class="severity-${info.severity }">
				<td>${info.stbMac }</td>
				<td>${info.warningTypeCn }</td>
				<td><fmt:formatDate value="${info.createdAt }" pattern="yyyy年MM月dd日 HH时mm分"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
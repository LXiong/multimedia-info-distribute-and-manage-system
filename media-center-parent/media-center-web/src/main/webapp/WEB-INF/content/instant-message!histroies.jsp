<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%>
<table class="linetable">
<thead>
	<tr>
		<th>发送时间</th>
		<th>消息内容</th>
		<th>播放次数</th>
		<th>接收终端</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${messages }" var="msg">
	<tr>
		<td>${msg.sendTime }</td>
		<td><a href="<%=request.getContextPath() %>/instant-message!show?message.fileName=${msg.fileNameWithoutExtentionName}">${msg.shortContent }</a></td>
		<td>${msg.playTimes }</td>
		<td>${msg.macJoinShort }</td>
	</tr>
</c:forEach>
</tbody>
</table>
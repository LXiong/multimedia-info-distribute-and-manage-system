<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%>
<table class="linetable">
	<caption>即时消息播放终端</caption>
	<thead>
		<tr>
			<th width="20%" style="text-align:center;"><label>MAC地址</label></th>
			<th width="20%" style="text-align:center;"><label>一级分组</label></th>
			<th width="20%" style="text-align:center;"><label>二级分组</label></th>
			<th width="20%" style="text-align:center;"><label>自编号</label></th>
			<th width="20%" style="text-align:center;"><label>车编号</label></th>
		</tr>
	</thead>
	<c:forEach items="${stbs }" var="stb">
		<tr>
			<td width="20%" style="text-align:center;">${stb.stbMac }</td>
			<td width="20%" style="text-align:center;">${stb.typeName }</td>
			<td width="20%" style="text-align:center;">${stb.groupName }</td>
			<td width="20%" style="text-align:center;">${stb.shopName }</td>
			<td width="20%" style="text-align:center;">${stb.shopName }</td>
		</tr>
	</c:forEach>
</table>
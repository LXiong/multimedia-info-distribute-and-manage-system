<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%>
<table class="linetable text-left">
	<tr>
		<td width="25%"><label>发送时间</label></td>
		<td width="75%">${message.sendTime }</td>
	</tr>
	<tr>
		<td><label>消息内容</label></td>
		<td><textarea readonly="readonly">${message.messageContent }</textarea></td>
	</tr>
	<tr>
		<td><label>播放次数</label></td>
		<td>${message.playTimes }</td>
	</tr>
	<tr>
		<td><label>背景色</label></td>
		<td><div style="background-color:${message.backgroundColorValue }">${message.backgroundColor }</div></td>
	</tr>
	<tr>
		<td><label>文本颜色</label></td>
		<td><div style="background-color:${message.textColorValue }">${message.textColor }</div></td>
	</tr>
	<tr>
		<td><label>字体</label></td>
		<td><fmt:message key="font.${message.textFont }"></fmt:message></td>
	</tr>
	<tr>
		<td>播放区域</td>
		<td>
		<label>起点</label><div>[${message.startX }, ${message.startY }]</div>
		<label>终点</label><div>[${message.endX }, ${message.endY }]</div>
		</td>
	</tr>
	<tr>
		<td>播放终端</td>
		<td><a href="<%=request.getContextPath() %>/instant-message!terminals?message.fileName=${message.fileNameWithoutExtentionName}">点此查看接收终端</a></td>
		<!-- td>
			<c:choose>
				<c:when test="${fn:length(message.stbMac) > 2}">
					<a href="#" onclick="showDialog()">show</a>
				</c:when>
				<c:otherwise>
					${message.macJoin }
				</c:otherwise>
			</c:choose>
			<c:if test=""></c:if>
			${message.macJoin }
		</td-->
	</tr>
	<!--tr>
		<td colspan="2">
			<table class="linetable">
				<thead>
					<tr>
						<th width="20%" style="text-align:center;"><label>MAC地址</label></th>
						<th width="20%" style="text-align:center;"><label>一级分组</label></th>
						<th width="20%" style="text-align:center;"><label>二级分组</label></th>
						<th width="20%" style="text-align:center;"><label>自编号</label></th>
						<th width="20%" style="text-align:center;"><label>车编号</label></th>
					</tr>
				</thead>
				<tbody id="stbs_container" style="height:250px;">
					<c:forEach items="${stbs}" var="stb">
						<tr>
							<td width="20%" style="text-align:center;">${stb.stbMac }</td>
							<td width="20%" style="text-align:center;">${stb.typeName }</td>
							<td width="20%" style="text-align:center;">${stb.groupName }</td>
							<td width="20%" style="text-align:center;">${stb.shopName }</td>
							<td width="20%" style="text-align:center;">${stb.shopName }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</td>
	</tr-->
</table>
<script type="text/javascript">
var data = [];
$(function(){
	
})
</script>
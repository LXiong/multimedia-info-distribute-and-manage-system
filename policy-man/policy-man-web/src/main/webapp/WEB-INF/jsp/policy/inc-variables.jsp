<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<c:set var="layoutList">
<c:forEach var="layout" items="${layoutList }" varStatus="layoutStatus">
<c:if test="${!layoutStatus.first}">,</c:if>
{
	"id": "${layout.id }","name":"${layout.name}",
	"layoutId": "${layout.layoutId }",
	"lcomment": "${layout.lcomment }",
	"startTime": "${layout.startTime }",
	"endTime": "${layout.endTime }",
	"areas": [
	<c:forEach var="area" items="${layout.areas }" varStatus="areaStatus">
	<c:if test="${!areaStatus.first }">,</c:if>
		{"id":"${area.id }","name":"${fn:escapeXml(area.name) }","type":"${area.type }",
		 "font":"${area.font}", "color":"${area.color}","bgcolor":"${area.bgcolor }",
			"lcomment":"${fn:escapeXml(area.lcomment) }",
			"plList":[
				<c:forEach var="pl" items="${area.playLists }" varStatus="plStatus">
				<c:if test="${!plStatus.first }">,</c:if>
				{"id":"${pl.id }","type":"${pl.type }",
				"startTime":"${pl.startTime }",
				"endTime":"${pl.endTime }",
				"loop": ${pl.loop },
				"content":"${fn:escapeXml(pl.content) }",
				"timedMedias":[
					<c:forEach items="${pl.medias}" var="media">
					
					</c:forEach>
				],}
				</c:forEach>
			]
		}
	</c:forEach>
	]
}
</c:forEach>
</c:set>
<c:set var="mediaList">
<c:forEach var="media" items="${mediaList }" varStatus="mediaStatus">
<c:if test="${!mediaStatus.first }">,</c:if>
{"name":"${media.name }", "type":"${media.type }",
"content":"${media.content }", "playCount":"${media.playCount }"}
</c:forEach>
</c:set>

<fmt:message key="policy.sel.layout.colNames" var="layoutColNames"/>
<fmt:message key="policy.sel.media.colNames" var="mediaColNames"/>

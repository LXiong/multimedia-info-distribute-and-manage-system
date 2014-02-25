<%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%><%@include file="taglib.jsp" 
%>
<!-- list -->
<div>
	<table id="table" class="linetable">
		<thead><tr>
			<th><fmt:message key='GroupType' /></th>
			<th><fmt:message key='Status' /></th>
			<th><fmt:message key='Counts' /></th>
		</tr></thead>
		<tbody>
			<c:forEach var="group" items="${mapStatistics}" >
				<tr><td>${group.key }</td><td></td><td></td></tr>
				<c:forEach var="map" items="${group.value}" >
					<tr><td></td>
						<td><fmt:message key="${map.key}"/></td><td>${map.value}</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
</div>
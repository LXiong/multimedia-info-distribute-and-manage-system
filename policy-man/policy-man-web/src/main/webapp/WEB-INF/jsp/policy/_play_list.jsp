<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="play_list_panel">
	<label><fmt:message key="policy.play_list_play_mode"/></label>
	<span class="common_field play_mode">
		<select name="play_mode" class="select_play_mode">
			<option value="timing">TIMING</option>
			<option value="loop">LOOP</option>
		</select>
	</span>
	<%@include file="/WEB-INF/jsp/partials/start_and_end_time.jsp" %>
	<span class="media_files">
		<c:choose>
			<c:when test="${is_text }">
				<label><fmt:message key="media_file.input_text"/></label>
				<textarea cols="30" rows="5"></textarea>
			</c:when>
			<c:otherwise>
				<label><fmt:message key="media_file.choose_media_files"/></label>
				<c:forEach items="${videos}" var="item">
					<label><input type="checkbox" name="media_files" value="${item.code}">${item.fileName }</label>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<c:forEach items=""></c:forEach>
	</span>
</div>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div>
	<c:forEach items="${areas}" var="item">
		<div class="area">
			<input type="hidden" name="area_id" value="${item.id }"/>
			<input type="hidden" name="media_type" value="${item.mediaType }"/>
			<label><fmt:message key="policy.area.support_media_type"/><fmt:message key="media_file.${item.mediaType}"/></label>
			<button class="add_play_list_btn"><fmt:message key="policy.add_play_list"/></button>
			<div class="play_list_tabs_container">
				<ul class="play_list_tab"></ul>
			</div>
		</div>
	</c:forEach>
</div>

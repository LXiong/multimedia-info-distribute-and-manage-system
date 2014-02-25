<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<input name="time_bucket_id" value="${time_bucket_id}" type="hidden" class="time_bucket_id"></input>
<div class="layout_type">
	<label><fmt:message key="policy.layout_type"/></label>
	<select name="layout_mode">
		<c:forEach items="${layout_types }" var="item">
			<option value="${item.key }">${item.value }</option>
		</c:forEach>
	</select>
	<%@include file="/WEB-INF/jsp/partials/start_and_end_time.jsp" %>
</div>
<div class="layouts_container">
	
</div>
<!-- when time-bucket`s content too big, the parent div should auto expend -->
<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
<script type="text/javascript">

</script>
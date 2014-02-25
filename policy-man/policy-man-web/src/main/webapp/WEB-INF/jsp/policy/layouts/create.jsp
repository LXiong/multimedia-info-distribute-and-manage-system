<input name="layout_id" value="${layout_id}" type="hidden"></input>
<div class="layout_type">
	<input type="hidden" name="change_layout_url" value="${change_area_path}"></input>
	<label>LayoutType</label><select name="layout_mode" onchange="load_area()">
		<option value="fuck">FUCK</option>
		<option value="fucking">FUCKING</option>
		<option value="fucked">FUCKED</option>
	</select>
	<label>StartTime</label>
	<%@include file="../time_select.jsp" %>
	<label>EndTime</label>
	<%@include file="../time_select.jsp" %>
</div>
<div class="layouts_container">
	<a>fuckyou</a>
</div>
<!-- when time-bucket`s content too big, the parent div should auto expend -->
<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
<script type="text/javascript">

</script>
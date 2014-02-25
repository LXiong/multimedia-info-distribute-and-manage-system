<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 
prefix="fmt"%>
<style>
#dialog label,#dialog input {
	display: block;
}

#dialog label {
	margin-top: 0.5em;
}

#dialog input,#dialog textarea {
	width: 95%;
}

#layouts {
	margin-top: 1em;
}

#layouts li .ui-icon-close {
	float: left;
	margin: 0.4em 0.2em 0 0;
	cursor: pointer;
}

#add_tab {
	cursor: pointer;
}

div .right {
	float: left
}
#policy #tabs_container li{
	background-color: red;
	margin: 1px 0;
	padding: 0 1px 0 1px;
}
#policy #tabs_container li a{
	margin: 1px 0;
	padding: 0 1px 0 1px;
}
#policy .play_list_tab li{
	background-color: green;
	margin: 1px 0;
	padding: 0 1px 0 1px;
}
#policy .play_list_tab li a{
	margin: 1px 0;
	padding: 0 1px 0 1px;
}
.error_red{
	padding: 5px 5px 5px 5px;
	border-color: red;
}
</style>
<script type="text/javascript">

var site_root = "<%=request.getContextPath()%>"

$(function(){Init("<%=request.getContextPath()%>")})


</script>

<div id="policy"><input class="policy_id" type="hidden"
	name="policy_id" value='${policy_id }' />
<div class="common_fields" id="policy_common_fields">
<div id="file_dialog"><textarea rows="20" cols="30"
	style="display: none" id="file_paths"></textarea>
<form>
	<fieldset id="files_box">
		<legend>SelectMediaFiles</legend>
		<table id="media_file_list"></table>
		<div id="media_file_pager"></div>
	</fieldset>
</form>
</div>
<div>
	<%@include
	file="/WEB-INF/jsp/partials/start_and_end_time.jsp"%>
	<label for="policy_name"><fmt:message key="policy.policy_number"/></label>
	<input name="policy_number" id="policy_number" type="text" />
	<input name="select_file" type="button" value='<fmt:message key="policy.add_file_btn"/>'
	id="global_play_list">
	<input name="save_policy" type="button"
	value='<fmt:message key="policy.save_policy"/>' id="save_policy" />
</div>
<span id="media_files_area" style="display: none"></span></div>
<div id="add_tab">Add tab</div>
<div id="layouts">
<ul id="tabs_container">
</ul>
</div>
</div>
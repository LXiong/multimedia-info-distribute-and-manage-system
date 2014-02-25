<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@include file="../taglib.jsp"%>
<sp:url var="savePolicyUrl" value="/policy/save" />
<c:if test="${policy.status == 'rejected' || policy.status=='normal'}">
	<sp:url var="listPolicyUrl" value="/policy/list" />
</c:if>

<c:if test="${policy.status != 'rejected' && policy.status!='normal'}">
	<sp:url var="listPolicyUrl" value="/policy/status/${currentStatus }" />
</c:if>

<sp:url var="submitPolicyUrl" value="/policy/submit" />
<sp:url var="passPolicyUrl" value="/policy/passaudit" />
<sp:url var="publishPolicyUrl" value="/policy/publish" />
<sp:url var="reviewPolicyUrl" value="/policy/review" />
<sp:url var="republishPolicyUrl" value="/policy/republish" />
<sp:url var="rejectPolicyUrl" value="/policy/rejectaudit" />
<sp:url var="listPassedUrl" value="/policy/listpassed" />
<sp:url var="listSubmittedUrl" value="/policy/listsubmitted" />
<sp:url var="stbGroupListUrl" value="/stb_group/aj_list" />
<sp:url var="copyPolicyUrl" value="/policy/copy_new" />
<sp:url var="policyEditUrl" value="/policy/edit" />

<form:form action="/policy/mod" onsubmit="return checkLayout()"
	method="post" commandName="policy">
	<div class="cf"><form:hidden path="id" />
	<div id="policy_property" class="clr">
	<table>
		<tr>
			<td><label for="policy_name">策略名称：</label> <form:input
				path="name" id="policy_name" readonly="readonly" disabled="true" />
			<br />
			<label for="policy_start">起始时间：</label> <form:input
				path="fmtStartTime" id="policy_start" readonly="readonly"
				disabled="true" /> <label for="policy_end">结束时间：</label> <form:input
				path="fmtEndTime" id="policy_end" readonly="readonly"
				disabled="true" /></td>
			<td><c:if test='${policy.status == "normal"}'>
				<input type="button"
					value='<fmt:message key="policy.submit.for.aduit" />'
					id="btnSubmit" class="jbutton">
			</c:if> 
			<sec:authorize url="/policy/passaudit">
				<c:if test='${policy.status == "submitted" }'>
					<input type="button"
						value='<fmt:message key="policy.publish.review" />' id="btnReview"
						class="jbutton">
					<input type="button"
						value='<fmt:message key="policy.audit.pass" />' id="btnPass"
						class="jbutton">
					<input type="button"
						value='<fmt:message key="policy.audit.reject" />' id="btnReject"
						class="jbutton">
				</c:if>
			</sec:authorize> 
			<sec:authorize url="/policy/publish">
				<c:if test='${policy.status == "passed" }'>
					<input type="button" value='<fmt:message key="policy.publish" />'
						id="btnPublish" class="jbutton">
					<input type="button" value='<fmt:message key="policy.unpublish" />'
						id="btnUnpublish" class="jbutton">
				</c:if>
			</sec:authorize>
			<sec:authorize url="/policy/republish">
				<c:if test='${policy.status == "published" }'>
				<input type="button" value='<fmt:message key="policy.republish" />'
					id="btnRepublish" class="jbutton"></c:if>
			</sec:authorize>
			<sec:authorize url="/policy/copy_new">
				<c:if test='${policy.status == "published" }'>
				<input type="button" value='<fmt:message key="policy.copy_new" />'
					id="btnCopyNew" class="jbutton"></c:if>
			</sec:authorize>
			<input type="button"
				value='<fmt:message key="policy.backto.list" />' id="btnCancel"
				class="jbutton"></td>
		</tr>
</table>
</div>
<div class="clr">&nbsp;</div>
<div id="tabs" class="tabs">
	<ul>
		<li><a href="#mediaDiv">媒体资源</a></li>
		<li><a href="#policyDiv">策略</a></li>
		<li><a href="#commentsDiv">备注</a></li>
		<c:if test="${ policy.status == 'rejected' }">
		<li><a href="#rejectDiv">原因</a></li>
		</c:if>
	</ul>
	
	<div id="policyDiv" class="hidden">
		<div id="layoutDiv" style="float: left; width: 130px; overflow: auto;">
		<h3>布局</h3>
		<ul id="layoutUl" class="vlist">
		</ul>
	</div>

	<div id="areaDiv" class="hidden-false"
		style="float: left; margin-left: 3px; width: 130px; border-left: 1px solid #33ddee;">
	<h3>区域列表</h3>
	<ul id="areaUl" class="vlist">
	</ul>
		<div id="area_prop" style="display:none">
			<div><label>字体</label>
			<select id="area_font" name="area_font" disabled=disabled>
				<option></option>
				<option value="wenquanyi">文泉驿</option>
				<option value="fangsong">仿宋</option>
				<option value="lishu">隶书</option>
			</select>
			</div>
			<div><label>字体颜色</label><br />
				<input type="text" size="6" id="area_color" name="area_color" disabled=disabled />
				<span id="area_color_disp" style="border:1px solid;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
			<div><label>背景颜色</label><br />
				<input type="text" size="6" id="area_bgcolor" name="area_bgcolor" disabled=disabled />
				<span id="area_bgcolor_disp" style="border:1px solid;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
		</div>
	</div>

		<div id="itemListDiv"
			style="float: left; margin-left: 3px; border-left: 1px solid #33ddee;">
			<h3>播放项目</h3>
			<table id="itemTable"></table>
			</div>
			</div>
			<div id="mediaDiv">
			<table id="mediaTable"></table>
		</div>
		<div id="commentsDiv"><form:textarea path="comments"
			id="policy_comments" rows="5" cols="60" readonly="readonly"
			disabled="true" />
		</div>
		
		<div id="rejectDiv" class="hidden">
		<label>被打回时间:<fmt:formatDate value="${policy.rejectedAt }" pattern="yyyy-MM-dd HH:mm:ss"/></label><br />
			<textarea cols="80" rows="5" readonly="readonly">${policy.reason }</textarea>
		</div>
	</div>
	

	</div>
</form:form>

<div id="mediaTextDlg" class="hidden"><input type="hidden"
	name="media_text_no" id="media_text_no" />
<table>
	<tr>
		<td>名称</td>
		<td><input type="text" name="media_text_name"
			id="media_text_name" /></td>
	</tr>
	<tr>
		<td>循环次数</td>
		<td><input type="text" name="play_count" id="play_count" /></td>
	</tr>
	<tr>
		<td>文字</td>
		<td><textarea id="media_text" name="media_text" rows="3"
			cols="30"></textarea></td>
	</tr>
</table>
</div>

<div id="publishGroupDlg" class="hidden okCancelDlg" style="width:400,height:400;" title="选择发布区域">
<form id="publishForm" action="" method="post"><input
	type="hidden" name="policy_id" value="${policy.id }">
<div id="stbGroupList1">
</div>
<div>
	<input type="checkbox" name="isTimingPublish" id="is-timing-publish" value="true"><label for="is-timing-publish">是否定时发布?</label>
	<input type="text" name="publishTime" id="publish-time"> 
</div>
</form>
</div>

<div id="republishGroupDlg" class="hidden okCancelDlg" title="重新发布策略文件" style="width:300,height:300;">
<form id="republishForm" action="" method="post">
<input type="hidden" name="policy_id" value="${policy.id }" >
<label>新策略文件名</label><input type="text" id="new_policy_name" name="policy_name" value="${policy.name }-再发布" ><br />
<label>选择区域:</label>
<ul id="stbGroupList2">
</ul>
</form>
</div>

<div id="copyNewDlg" class="hidden okCancelDlg" title="导出为新策略 - 请输入新策略名" style="width:400,height:150;">
<form id="copyNewForm" action="" method="post">
<input type="hidden" name="policy_id" value="${policy.id }" >
<label>新策略文件名</label>
<input type="text" id="new_policy_name" name="policy_name" value="${policy.name } - new" ><br />
</form>
</div>

<div id="reviewPublishGroupDlg" class="hidden okCancelDlg" style="width:300,height:300;" title="测试发布-选择发布区域" >
<form id="reviewPublishForm" action="" method="post"><input
	type="hidden" name="policy_id" value="${policy.id }">
<ul id="testGroup"></ul>
</form>
</div>
<!-- init variables in javascript form -->
<%@include file="inc-variables.jsp"%>
<%@include file="inc-init-dialog.jsp"%>
<%@include file="inc-init-grid.jsp"%>
<div id="rejectDialog" class="hidden">拒绝原因:<br />
<form id="rejectForm"><textarea rows="7" cols="43" name="reason"
	id="reason"></textarea></form>
</div>
<div id="unpublishDialog" class="hidden">不发布原因:<br />
<form id="unpublishForm">
<textarea rows="5" cols="70"
	name="reason" id="unpublish_reason"></textarea></form>
</div>
<!-- Javascript codes -->
<script type="text/javascript">
var layoutList = [${layoutList}];
var mediaList = [${mediaList}];
var layoutSelList = {};
var layoutIndex = -1;
var areaIndex = -1;
var playListIndex = -1;

function refreshLayout() {
	$('#layoutUl').empty();
	layoutList.eachWithIndex(function(layout, index){
		var ele = '<li>';
		ele += '<div class="layoutButton ui-widget ui-state-default" ';
		ele += ' onclick="setLayout(this, '+index+')">'+layout.name+'<br />'+
			layout.startTime+"~"+layout.endTime+'</div>';
		ele += '<div id="lbtnDiv'+index+'" class="lbtnDiv">';
		ele += '</div>';
		ele += '</li>';
		$(ele).appendTo($('#layoutUl'));
	});
	$('.layoutButton').hover(function() {$(this).addClass("ui-state-hover");},
			function() {$(this).removeClass("ui-state-hover")});
	$('.jbutton').button();
	$('.lbtnDiv').hide();
	layoutIndex = -1;
	refreshArea();
}

function setLayout(src, index) {
	if (index == layoutIndex) return;
	$('.layoutButton').removeClass('ui-state-active');
	$(src).addClass("ui-state-active");
	layoutIndex = index;
	$('.lbtnDiv').hide();
	$('#lbtnDiv'+index).show();
	refreshArea();
	selectArea('.areaButton:first', 0);
}

function refreshArea() {
	$('#areaUl').empty();
	if (layoutIndex > -1) {
		if (layoutIndex <layoutList.length) {
			var layout = layoutList[layoutIndex];
			layout.areas.eachWithIndex(function(aArea, index){
				$('<li class="areaButton ui-widget ui-state-default" onclick="selectArea(this, '
						+index+')">'
							+aArea.name+':'+media_type_formatter2(aArea.type,null,null)+ '</li>').appendTo($('#areaUl'));
			});
			$('.areaButton').hover(function() {$(this).addClass("ui-state-hover");},
					function() {$(this).removeClass("ui-state-hover")});
		} else {
			layoutIndex = -1;
		}
	}
	areaIndex = -1;
	refreshItemList();
}

function selectArea(src, index) {
	if (layoutIndex < 0) return;
	if (index >= layoutList[layoutIndex].areas.length) return;
	if (areaIndex != index) {
		$('.areaButton').removeClass('ui-state-active');
		$(src).addClass("ui-state-active");
		areaIndex = index;
		refreshItemList();
		areaType = layoutList[layoutIndex].areas[areaIndex].type;
		if (areaType == '3' || areaType == '4' ) {
			var area = layoutList[layoutIndex].areas[areaIndex];
			$('#area_prop').show();
			setTimeout(function () {$('#area_font').val(area.font);},1);
			$('#area_color').val(area.color);
			if (area.color && area.color!='') $('#area_color').next().css('background-color','#'+area.color);
			else $('#area_color').next().css('background-color','');
			$('#area_bgcolor').val(area.bgcolor);
			if (area.bgcolor && area.bgcolor!='') $('#area_bgcolor').next().css('background-color','#'+area.bgcolor);
			else $('#area_bgcolor').next().css('background-color','');
		} else {
			$('#area_prop').hide();
		}
	}
}

function refreshItemList() {
	$('#listDesc').text('');
	$('#itemTable').jqGrid('clearGridData');
	if (layoutIndex < 0 || areaIndex < 0) return;
	var curpl = layoutList[layoutIndex].areas[areaIndex].plList[0];
	
	if (curpl == null || curpl == undefined || curpl.content == undefined) {
		return;
	}
	
	var mediaArr = curpl.content.split('\t');
	mediaArr.eachWithIndex(function(content, index){
		if (content != ''){
			var row = {"name": content};
			$('#itemTable').jqGrid('addRowData', index, row);
		}
	});
}

function refreshMediaList() {
	jQuery('#mediaTable').jqGrid('clearGridData');
	mediaList.eachWithIndex(function(mitem, mkey){
		var rowdata = {'type':mitem.type,'name':mitem.name,
				'playCount':mitem.playCount,'content':mitem.content};
		jQuery('#mediaTable').jqGrid('addRowData',mkey,rowdata);
	});
}

function media_type_formatter(cellvalue, options, rowObject) {
	if (cellvalue=='video' || cellvalue=='1') return '视频';
	if (cellvalue=='image' || cellvalue=='2') return '图片';
	if (cellvalue=='3') return '文字';
	return '';
}

function media_type_formatter2(cellvalue, options, rowObject) {
	if (cellvalue=='1') return '视频';
	if (cellvalue=='2') return '图片';
	if (cellvalue=='3') return '文字';
	if (cellvalue=='4') return '时间';
	if (cellvalue=='5') return '天气';
	return '';
}

function checkPlayList() {
	// TODO
	layoutList.each(function(aLayout){
		if(aLayout.areas != null && aLayout.areas != undefined){
			aLayout.areas.each(function(aArea){
				var pl = aArea.plList;
				if (pl == null || pl == undefined || pl[0]==null || pl[0]== undefined || $.trim(pl[0].content)=='') {
					alert("警告，有播放列表为空");
				}
			});
		}
	});
	return true;
}

function showPublishDialog() {
	var treeBranch = "<div class='typeContainer'><input type='checkbox' class='parent' id='{id}' name='stb_group' value='{value}'/><label for='{id}'>{name}</label>{ul}</div>";
	var leaf = "<ul class='groupContainer'>{lis}</ul>";
	var item = "<li><input type='checkbox' name='group_level_two' class='son' value='{value}' id='{id}'/><label for='{id}'>{name}</label></li>";
	$.get("${stbGroupListUrl}", function(groupTypes){
		$("#stbGroupList1").empty();
		var branches = groupTypes.filter(function(group){
			return (group['typeId'] != -1) && (group['typeName'] != 'Test')
		}).collect(function(group){
			var leaves = group["groupLevelTwo"].collect(function(groupTwo){
				return item
				.replace("{value}", groupTwo["groupId"])
				.replace("{id}", "groupTwo"+groupTwo["groupId"])
				.replace("{id}", "groupTwo"+groupTwo["groupId"]).replace("{name}", groupTwo["groupName"]);
			});
			var lis = "";
			leaves.each(function(li){
				lis += li;
			});
			var ul = leaf.replace("{lis}", lis);
			return treeBranch
			.replace("{id}", "stbGroup"+group["typeId"])
			.replace("{id}", "stbGroup"+group["typeId"])
			.replace("{value}", group["typeId"])
			.replace("{name}", group["typeName"])
			.replace("{ul}", ul);
			//.replace("{ul}", leaf.replace("{lis}", ul));

			//end collect
		});
		var tree = "";
		branches.each(function(branch){
			tree += branch;
		});
		//$(tree).appendTo('#stbGroupList');
		$(tree).appendTo('#stbGroupList1');
		initialTimingPublish();
	}, 'json');

	/*
	$.get("${stbGroupListUrl}", function (data) {
		$("#stbGroupList1").empty();
		data.each(function(groupData){
			if (groupData['typeId'] != -1){
				if(groupData['typeName'] != "Test") {
					
					var content = '<li><input type="checkbox" name="stb_group" value="' +groupData['typeId']+
						'" id="stbGroup'+groupData['typeId']+'"><label for="stbGroup'+groupData['typeId']+'">'
						+groupData['typeName']+'</label></li>';
					$(content).appendTo('#stbGroupList');
					$(content).appendTo('#stbGroupList1');
				}
			}
		});
		
		// timing publish
		
		initialTimingPublish();
	},'json')*/
	$(".parent").live("change", function(){
		
		if($(this).attr("checked")){
			//$("ul", $(this).closest("div")).show();
			$("input", $(this).closest("div")).attr("checked", true);
		}else{
			//$("ul", $(this).closest("div")).hide();
			$("input", $(this).closest("div")).attr("checked", false);
		}
	});
	//checkbox
	$(".son").live("change", function(){
		if(!$(this).attr("checked")){
			$(".parent", $(this).closest("div")).attr("checked", false);
		}else{
			var inputs = $("input", $(this).closest("ul"));
			for(var i = 0; i < inputs.length; i++){
				if(!$(inputs[i]).attr("checked")){
					$(".parent", $(this).closest("div")).attr("checked", false);
					return ;
				}
			}
			$(".parent", $(this).closest("div")).attr("checked", true);
		}
	});
	$( "#publishGroupDlg" ).dialog({width:480});
	$( "#publishGroupDlg" ).dialog( "open" );
}

function publishGroupDlgOk() {
	$.post('${publishPolicyUrl}', $('#publishForm').serialize(),function(data) {
		if (data.flag == "success") {
			alert("发布策略成功");
			location.href="${listPolicyUrl}";
		} else if (data.flag == "not_found") {
			alert("没有找到 相应的策略文件");
		} else if (data.flag == "wrong_status") {
			alert("发布策略，需要策略的状态处于审核通过状态");
		} else if (data.flag == "not_stb_group") {
			alert("未指定发布区域");
		} else {
			var msg = "发布失败";
			if (data.message != null && data.message != undefined ) {
				msg +="\n错误信息："+data.message;
			}
			alert(msg);
		}
	},'json');
}

function docReady() {
	initGrid();
	initDialog();
	
	$('#policy_start').datetime({format: "yy-mm-dd hh:ii:00"});
	$('#policy_end').datetime({format: "yy-mm-dd hh:ii:00"});
	$('.jbutton').button();
	
	$('#tabs').tabs();
	
	$('#btnCancel').click(function() {
		location.href="${listPolicyUrl}";
	});
	$('#btnSubmit').click(function() {
		if (checkPlayList()) {
			if (confirm("确认提交该播放策略吗?")) {
				$.post('${submitPolicyUrl}',{"id":$('#id').val()}, function(data) {
					if (data.flag == "success") {
						alert("提交策略审核成功");
						location.href="${listPolicyUrl}";
					} else if (data.flag == "not_found") {
						alert("没有找到 相应的策略文件");
					} else if (data.flag == "wrong_status") {
						alert("提交审核策略，需要策略的状态处于normal状态");
					} else {
						alert("提交审核失败，请尝试刷新页面");
					}
				},'json');
			}
		}
	});
	
	$('#btnPass').click(function() {
		if (confirm("确认通过该播放策略吗?")) {
			$.post('${passPolicyUrl}',{"id":$('#id').val()}, function(data) {
				if (data.flag == "success") {
					alert("通过策略审核成功");
					location.href="${listSubmittedUrl}";
				} else if (data.flag == "not_found") {
					alert("没有找到 相应的策略文件");
				} else if (data.flag == "wrong_status") {
					alert("通过审核策略，需要策略的状态处于submitted状态");
				} else {
					alert("通过审核失败，请尝试刷新页面");
				}
			},'json');
		}
	});
	$("#unpublishDialog").dialog({
		autoOpen: false,
		height: 250,
		width: 250,
		modal: true,
		buttons: {
			"不发布":function(){
				$.post('${rejectPolicyUrl}',{"id":$('#id').val(), "reason":$("#unpublish_reason").val()}, function(data) {
					if (data.flag == "success") {
						alert("打回该策略成功");
						location.href="${listSubmittedUrl}";
					} else if (data.flag == "not_found") {
						alert("没有找到相应的策略文件");
					}else {
						alert("拒绝通过审核失败，请尝试刷新页面");
					}
					$( this ).dialog( "close" );
				},'json');
			},
			"取消":function(){
				$(this).dialog("close");
			}
		}
	});
	$('#rejectDialog').dialog({
		autoOpen: false,
		height: 300,
		width: 400,
		modal: true,
		buttons: {
			"不通过": function() {
				$.post('${rejectPolicyUrl}',{"id":$('#id').val(), "reason":$("#reason").val()}, function(data) {
					if (data.flag == "success") {
						alert("拒绝通过该策略完成成功");
						location.href="${listSubmittedUrl}";
					} else if (data.flag == "not_found") {
						alert("没有找到 相应的策略文件");
					} else if (data.flag == "wrong_status") {
						alert("拒绝通过审核策略，需要策略的状态处于submitted状态");
					} else {
						alert("拒绝通过审核失败，请尝试刷新页面");
					}
					//$("#reason").val("");
					$( this ).dialog( "close" );
				},'json');
			},
			Cancel: function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});
	
	$('#btnReject').click(function() {
		$( "#rejectDialog" ).dialog( "open" );		
	});
	
	$('#btnPublish').click(function() {
		showPublishDialog();
	});
	
	$('#btnRepublish').click(function() {
		showRepublishDialog();
	});
	
	$('#btnCopyNew').click(function() {
		showCopyNewDialog();
	});

	$('#btnReview').click(function() {
		showReviewPublishDialog();
	});
	
	$('#btnUnpublish').click(function() {
		$( "#unpublishDialog" ).dialog( "open" );
	});
	refreshLayout();
	refreshMediaList();
}
$(document).ready(docReady);
</script>

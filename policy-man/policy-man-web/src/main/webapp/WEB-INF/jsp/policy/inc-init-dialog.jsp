<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<script type="text/javascript">
function validateTime(id) {
	var val = $.trim($(id).val());
	if (val == '') {
		return false;
	}
	cols = val.split(':');
	if (cols.length != 2) {
		return false;
	}
	h = parseInt(cols[0]);
	if (h<0 || h>24) {
		return false;
	}
	m = parseInt(cols[1]);
	if (m<0 || m>59) {
		return false;
	}
	return true;
}

function initialTimingPublish(){
	$("#publish-time").attr("disabled", true);
	$("#is-timing-publish").live("change", function(){
		if($(this).attr("checked")){
			$("#publish-time").attr("disabled", false);
			$("#publish-time").datetime({format: "yy-mm-dd hh:ii:00"});
		}else{
			$("#publish-time").val("");
			$("#publish-time").attr("disabled", true);
		}
	});
}

function showReviewPublishDialog() {
	var treeBranch = "<div class='typeContainer'><input type='checkbox' class='parent' id='{id}' name='stb_group' value='{value}'/><label for='{id}'>{name}</label>{ul}</div>";
	var leaf = "<ul class='groupContainer'>{lis}</ul>";
	var item = "<li><input type='checkbox' name='group_level_two' class='son' value='{value}' id='{id}'/><label for='{id}'>{name}</label></li>";
	$.get("${stbGroupListUrl}", function(groupTypes){
		$("#testGroup").empty();
		var branches = groupTypes.filter(function(group){
			return (group['typeName'] == 'Test')
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
		$(tree).appendTo('#testGroup');
		//initialTimingPublish();
	}, 'json');
	/* $.get("${stbGroupListUrl}", function (data) {
		$("#testGroup").empty();
		data.each(function(groupData){
			if (groupData['typeId'] != -1){
				if(groupData['typeName'] == "Test") {
					var content = '<li><input type="checkbox" name="stb_group" value="' +groupData['typeId']+
						'" id="stbGroup'+groupData['typeId']+'"><label for="stbGroup'+groupData['typeId']+'">'
						+groupData['typeName']+'</label></li>';
					$(content).appendTo('#testGroup');
				}
			}
		});
	},'json') */
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
	$( "#reviewPublishGroupDlg" ).dialog( "open" );
}

function reviewPublishGroupDlgOk() {
	$.post('${reviewPolicyUrl}', $('#reviewPublishForm').serialize(), function(data) {
		if (data.flag == "success") {
			alert("发布策略成功");
			location.href="${listPolicyUrl}";
		} else if (data.flag == "not_found") {
			alert("没有找到 相应的策略文件");
		}  else if (data.flag == "not_stb_group") {
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

function showRepublishDialog() {
	var treeBranch = "<div class='typeContainer'><input type='checkbox' class='parent' id='{id}' name='stb_group' value='{value}'/><label for='{id}'>{name}</label>{ul}</div>";
	var leaf = "<ul class='groupContainer'>{lis}</ul>";
	var item = "<li><input type='checkbox' name='group_level_two' class='son' value='{value}' id='{id}'/><label for='{id}'>{name}</label></li>";
	$.get("${stbGroupListUrl}", function(groupTypes){
		$("#stbGroupList2").empty();
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
		$(tree).appendTo('#stbGroupList2');
		initialTimingPublish();
	}, 'json');
	/* $.get("${stbGroupListUrl}", function (data) {
		$("#stbGroupList2").empty();
		data.each(function(groupData){
			if (groupData['typeId'] != -1){
				var content = '<li><input type="checkbox" name="stb_group" value="' +groupData['typeId']+
				'" id="stbGroup'+groupData['typeId']+'"><label for="stbGroup'+groupData['typeId']+'">'
				+groupData['typeName']+'</label></li>';
				$(content).appendTo('#stbGroupList2');
			}
		});
	},'json') */
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
	$( "#republishGroupDlg" ).dialog({width:480});
	$( "#republishGroupDlg" ).dialog( "open" );
}

function republishGroupDlgOk() {
	if($('#new_policy_name').val() =='') {
		alert("新策略文件名不能为空");
		$('#new_policy_name').focus();
		return;
	}
	$.post('${republishPolicyUrl}', $('#republishForm').serialize(),function(data) {
		if (data.flag == "success") {
			alert("重新发布策略成功");
			location.href="${listPolicyUrl}";
		} else if (data.flag == "not_found") {
			alert("没有找到 相应的策略文件");
		} else if (data.flag == "wrong_status") {
			alert("重新发布策略，需要策略的状态处于已发布状态状态");
		} else if (data.flag == "copy_fail") {
			alert("复制策略失败:"+data.message);
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

function showCopyNewDialog() {
	$( "#copyNewDlg" ).dialog( "open" );
}

function copyNewDlgOk() {
	$.post('${copyPolicyUrl}', $('#copyNewForm').serialize(),function(data) {
		if (data.flag == "success") {
			alert("复制策略成功");
			location.href="${policyEditUrl}?id="+data.value;
		} else if (data.flag == "not_found") {
			alert("没有找到 相应的策略文件");
		} else if (data.flag == "wrong_status") {
			alert("需要策略的状态处于已发布状态");
		} else {
			var msg = "导出失败";
			if (data.message != null && data.message != undefined ) {
				msg +="\n错误信息："+data.message;
			}
			alert(msg);
		}
	},'json');
}

function initDialog() {
	$('.okCancelDlg').each(function () {
		$(this).dialog({autoOpen:false,modal:true,title:$(this).attr('title'),
			width:$(this).attr('dwidth'), height:$(this).attr('dheight'),
			buttons: {
				'<fmt:message key="option.ok" />': function () {
					eval($(this).attr('id')+'Ok()');
				},
				'<fmt:message key="button.cancel" />':function() {
					$( this ).dialog( "close" );
				}
			}});
	});
	
	$('#mediaTextDlg').dialog ({
		autoOpen: false,
		height: 300,
		width: 400,
		modal: true,
		title: '设定文字',
		buttons: {
			'<fmt:message key="button.save" />' :function() {
				var media_no = parseInt($('#media_text_no').val());
				var media_name = $('#media_text_name').val();
				mediaList.each(function(media){
					if (media_name == media.name) {
						alert('资源名称不能重复');
						return;
					}
				});
				
				var media_text = {
					"name":media_name,
					"type":"3",
					"content":$('#media_text').val(),
					"playCount":$('#play_count').val()
				}
				if (media_no == -1) {
					mediaList.length++;
					mediaList[mediaList.length-1]=media_text;
				} else {
					mediaList[media_no]=media_text;
				}
				refreshMediaList();
				$('#mediaTextDlg').dialog('close');
			},
			'<fmt:message key="button.cancel" />': function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	$('#randomPlDlg').dialog ({
		autoOpen: false,
		height: 200,
		width: 200,
		modal: true,
		title: '定时播放列表',
		buttons: {
			'<fmt:message key="button.save" />' :function() {
				/*
				if (!validateTime('#pl_start_time') || !validateTime('#pl_end_time')) {
					alert("时间格式不对");
					return;
				}
				*/
				if (!checkTime('#pl_start_hour','#pl_start_end','开始时间不对,')
						||
					!checkTime('#pl_end_hour','#pl_end_end','结束时间不对, ')) {
					return;
				}
				var plIndex = $('#plIndex').val();
				var startTime = getTime('#pl_start_hour','#pl_start_min');
				var endTime = getTime('#pl_end_hour','#pl_end_min');
				var layout = layoutList[layoutIndex];
				if (compareTime(startTime, layout.startTime)<0) {
					alert("开始时间不能小于布局开始时间");
					return ;
				}
				if (compareTime(layout.endTime, endTime)<0) {
					alert("结束时间不能大于布局结束时间");
					return;
				}
				if (layoutList[layoutIndex].areas[areaIndex].plList == undefined) {
					layoutList[layoutIndex].areas[areaIndex].plList = [];
				}
				if (plIndex == -1) {
					len = layoutList[layoutIndex].areas[areaIndex].plList.length;
					if (len ==0 ) {
						layoutList[layoutIndex].areas[areaIndex].plList.length = 2;
						len=1;
					} else {
						layoutList[layoutIndex].areas[areaIndex].plList.length++;
					}
					var ele = {"id":$('#plId').val(), "startTime":startTime,
							"endTime":endTime,"content":'',"loop":false};
					layoutList[layoutIndex].areas[areaIndex].plList[len] = ele;
				} else {
					layoutList[layoutIndex].areas[areaIndex].plList[plIndex].startTime = startTime;
					layoutList[layoutIndex].areas[areaIndex].plList[plIndex].endTime = endTime;
				}
				refreshPlayList();
				$('#randomPlDlg').dialog('close');
			},
			'<fmt:message key="button.cancel" />': function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
}

</script>
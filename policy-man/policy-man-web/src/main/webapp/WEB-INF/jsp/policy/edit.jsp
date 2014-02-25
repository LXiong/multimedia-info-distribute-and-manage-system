<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<sp:url var="savePolicyUrl" value="/policy/save" />
<sp:url var="listPolicyUrl" value="/policy/list" />
<sp:url var="listLayoutUrl" value="/layout/sellist" />

<script type="text/javascript">
/// define ok button action for OkCancel dialog
function imageTimeDlgOk() {
	var media_no = $('#imageResIndex').val();
	var imageTime = $('#imageTime').val();
	var media = mediaList[media_no];
	media.playCount = imageTime;
	mediaList[media_no]=media;
	refreshMediaList();
	$( '#imageTimeDlg' ).dialog( "close" );
}
function layoutSelDlgOk() {
	var gr = $("#layoutSelTable").jqGrid('getGridParam','selrow');
	if (!checkTime('#layoutStartHour','#layoutStartMinute', '开始时间有错')
			|| !checkTime('#layoutEndHour','#layoutEndMinute', '结束时间有错')) {
		return;
	}
	if( gr != null ) {
		$.getJSON("<%=request.getContextPath()%>/layout/jdetail",
				{"id":gr},function (data) {
			startTime = getTime('#layoutStartHour', '#layoutStartMinute');
			endTime = getTime('#layoutEndHour', '#layoutEndMinute');
			layoutList.push({
				'id':-1,
				'layoutId':data.id,
				'name':data.name,
				'startTime':startTime,
				'endTime':endTime,
				'areas':data.areas
			});
			$( '#layoutSelDlg' ).dialog( "close" );
			refreshLayout();
		});
	} else {
		alert('<fmt:message key="alert.select.row" />');
	}
}

function layoutEditDlgOk() {
	lindex= $('#edit_layout_index').val();
	layoutList[lindex].startTime=
		 getTime('#edit_layout_start_hour', '#edit_layout_start_minute');
	layoutList[lindex].endTime=
		getTime('#edit_layout_end_hour', '#edit_layout_end_minute');
	$( this ).dialog( "close" );
	refreshLayout();
}

function mediaAddDlgOk() {
	var gr = $("#mediaSelTable").jqGrid('getGridParam','selarrrow');
	if( gr != null && gr.length>0) {
		gr.each(function(rowid){
			var rowdata = $('#mediaSelTable').jqGrid('getRowData', rowid);
			// Check wether code exists
			var exists = false;
			mediaList.each(function(emedia){
				if (emedia.content == rowdata.code) {
					exists = true;
					return;
				}
			});
			if (!exists){
				var len = mediaList.length; mediaList.length++;
				mediaType = '0';
				playCount = rowdata.playTime;
				if (rowdata.type == 'video') {
					mediaType = '1';
				} else if (rowdata.type=='image') {
					playCount=10;
					mediaType = '2';
				} else if (rowdata.type=='text'){
					mediaType = '3';
				}
				mediaList[len] = {
					"name":rowdata.name,
					"type":mediaType,
					"playCount":playCount,
					"content":rowdata.code
				};
			}
		});
		refreshMediaList();
		$( this ).dialog( "close" );
	} else {
		alert('<fmt:message key="alert.select.row" />');
	}
}
</script>

<form:form action="/policy/save" onsubmit="return checkLayout()" method="post" 
	commandName="policy">
<div class="cf">
<form:hidden path="id"/>
<div id="policy_property" class="clr">
<table><tr><td>
<label for="policy_name">策略名称：</label>
<form:input path="name" id="policy_name"/> 
<br />
<label for="policy_start">起始时间：</label>
<form:input path="fmtStartTime" id="policy_start" />
<label for="policy_end">结束时间：</label>
<form:input path="fmtEndTime" id="policy_end"  />
</td><td>
<input type="button" value='<fmt:message key="button.save" />' id="btnSave" class="jbutton">
<input type="button" value='<fmt:message key="button.cancel" />' id="btnCancel" class="jbutton">
</td></tr>
</table>
</div>
<div id="tabs" class="tabs">
	<ul>
		<li><a href="#mediaDiv">媒体资源&nbsp;&gt;&gt;</a></li>
		<li><a href="#policyDiv">策略&nbsp;&gt;&gt;</a></li>
		<li><a href="#commentsDiv">备注</a></li>
	</ul>
	<div id="mediaDiv">
		<input type="button" value="添加文件资源" id="btnAddRes" class="jbutton"/>
		<input type="button" value="设定图片播放时间 " id="btnImageTime" class="jbutton"/>
		<!-- 
		<input type="button" value="添加文本 " id="btnAddText" class="jbutton"/>
		<input type="button" value="编辑文本 " id="btnEditText" class="jbutton"/>-->
		 
		<input type="button" value="删除资源 " id="btnDeleteRes" class="jbutton"/>
		<table id="mediaTable"></table>
	</div>
	
	<div id="policyDiv">
		<div id="layoutDiv" style="float:left; width:130px; height:100%; overflow: auto;">
		<h3>布局</h3>
		<input type="button" class="jbutton" id="btnAddLayout" value="添加布局" />
		<ul id="layoutUl" class="vlist">
		</ul>
		</div>
		
		<div id="areaDiv" style="float:left; margin-left:3px; height:100%; width:130px; border-left: 2px solid #33ddee;">
			<h3>区域</h3>
			<ul id="areaUl" class="vlist">
			</ul>
			<div id="area_prop" style="display:none">
			<div><label>字体</label>
			<select id="area_font" name="area_font">
				<option value="wenquanyi">文泉驿</option>
				<option value="fangsong">仿宋</option>
				<option value="lishu">隶书</option>
			</select>
			</div>
			<div><label>字体颜色</label><br />
				<input type="text" size="6" id="area_color" name="area_color" />
				<span id="area_color_disp" style="border:1px solid;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
			<div><label>背景颜色</label><br />
				<input type="text" size="6" id="area_bgcolor" name="area_bgcolor" />
				<span id="area_bgcolor_disp" style="border:1px solid;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
			</div>
		</div>
		
		<div id="areaMediaDiv" style="float:left; margin-left:3px; height:100%; width:190px; border-left: 2px solid #33ddee;">
		<h3>可用媒体资源</h3>
		<table id="areaMediaTable"></table>
		</div>
		
		<div id="playListDiv" style="float:left; width:50px margin-left:3px;border-left: 1px solid #33ddee;">
		<br />
		<br />
		<input type="button" class="jbutton" id="btnAddItem" value="添加&gt;&gt;" />
		<br />
		<br />
		<input type="button" class="jbutton" id="btnRemoveItem" value="移除&lt;&lt;" />
		</div>
		<div id="itemListDiv" style="float:left; margin-left:3px; border-left: 1px solid #33ddee;">
		<h3>播放列表</h3>
		<table id="itemTable"></table>
		</div>
	</div>
	<div id="commentsDiv">
		<form:textarea path="comments" id="policy_comments" rows="5" cols="60"/>
	</div>
</div>


</div>
</form:form>

<div id="layoutSelDlg" class="hidden okCancelDlg" title="添加布局 - 请选择布局" dwidth="320" dheight="400">
起止时间：
<input type="text" name="layoutStartHour" id="layoutStartHour" size="2" maxlength="2"/>
:
<input type="text" name="layoutStartMinute" id="layoutStartMinute" size="2" maxlength="2"/>
~
<input type="text" name="layoutEndHour" id="layoutEndHour" size="2" maxlength="2"/>
:
<input type="text" name="layoutEndMinute" id="layoutEndMinute" size="2" maxlength="2"/>
<br />
<table id="layoutSelTable"></table>
<div id="layoutSelPage"></div>
<br />
</div>

<div id="layoutEditDlg" class="hidden okCancelDlg" title="编辑布局时间" dwidth="350" dheight="80">
<input type="hidden" id="edit_layout_index" />
起止时间：
<input type="text" name="edit_layout_start_hour" id="edit_layout_start_hour" size="2" />
:
<input type="text" name="edit_layout_start_minute" id="edit_layout_start_minute" size="2" />
~
<input type="text" name="edit_layout_end_hour" id="edit_layout_end_hour" size="2" />
:
<input type="text" name="edit_layout_end_minute" id="edit_layout_end_minute" size="2"/>
<br />
</div>

<div id="mediaAddDlg" class="hidden okCancelDlg" title="添加媒体文件" dwidth="700" dheight="500">
	<div>
		<label><fmt:message key="file.category" /></label>
		<select id="searchCate" name="searchCate" >
			<option value=""><fmt:message key="media.all" /></option>
			<option value="video"><fmt:message key="media.video" /></option>
			<option value="image"><fmt:message key="media.pic" /></option>
			<option value="text"><fmt:message key="media.text" /></option>
		</select>
		<label><fmt:message key="file.alias" /></label>
		<input type="text" id="name" name="name" onkeydown="doSearch(arguments[0]||event)" />
		&nbsp;&nbsp;&nbsp;
		<button id='btnSearch' class="jqbutton" ><fmt:message key="button.search" /></button>&nbsp;
	</div>
	<table id="mediaSelTable"></table>
	<div id="mediaSelPage"></div>
</div>

<div id="mediaTextDlg" class="hidden">
	<input type="hidden" name="media_text_no" id="media_text_no" />
	<table>
		<tr><td>名称</td><td><input type="text" name="media_text_name" id="media_text_name" /></td></tr>
		<tr><td>循环次数</td><td><input type="text" name="play_count" id="play_count" /></td></tr>
		<tr><td>文字</td><td><textarea id="media_text" name="media_text" rows="3" cols="30"></textarea></td></tr>
	</table>
</div>

<div id="randomPlDlg" class="hidden">
	<input type="hidden" id="plIndex" />
	<input type="hidden" id="plId" />
	<table>
		<tr><td>开始时间：</td>
		<td><input type="text" name="pl_start_hour" id="pl_start_hour" size="2" maxlength="2" />
		:
		<input type="text" name="pl_start_min" id="pl_start_min" size="2" maxlength="2"/>
		</td></tr>
		<tr><td>结束时间：</td>
		<td><input type="text" name="pl_end_hour" id="pl_end_hour" size="2" maxlength="2"/>
		:
		<input type="text" name="pl_end_min" id="pl_end_min" size="2" maxlength="2"/></td></tr>
	</table>
</div>

<div id="imageTimeDlg" class="hidden okCancelDlg" title="设定图片播放时间" dwidth="300" dheight="200">
	<input type="hidden" name="imageResIndex" id="imageResIndex" />
	播放时间(秒)<input type="text" name="imageTime" id="imageTime" />
</div>

<!-- init variables in javascript form -->
<%@include file="inc-variables.jsp" %>
<%@include file="inc-init-dialog.jsp" %>
<%@include file="inc-init-grid.jsp" %>

<%@include file="inc-time-functions.jsp" %>

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
	layoutList.eachWithIndex(function(layout, lkey){
		//var layout = layoutList[lkey];
		var ele = '<li>';
		ele += '<div class="layoutButton ui-widget ui-state-default" ';
		ele += ' onclick="setLayout(this, '+lkey+')">'+layout.name+'<br />'+
			layout.startTime+"~"+layout.endTime+'</div>';
		ele += '<div id="lbtnDiv'+lkey+'" class="lbtnDiv">';
		ele += '<div class="jbutton" onclick="editLayout('+lkey+')">编辑</div>';
		ele += '<div class="jbutton" onclick="removeLayout('+lkey+')">删除</div>';
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

function editLayout(lkey) {
	if (lkey>-1 && lkey<layoutList.length) {
		var layout = layoutList[lkey];
		$('#edit_layout_index').val(lkey);
		setTime('#edit_layout_start_hour', '#edit_layout_start_minute', layout.startTime);
		setTime('#edit_layout_end_hour', '#edit_layout_end_minute', layout.endTime);
		$("#layoutEditDlg").dialog("open");
	}
}
function removeLayout(lkey) {
	var layout = layoutList[lkey];
	if (confirm('确认删除布局:"'+layout.name+'"吗?')) {
		layoutList.splice(lkey,1);
		layoutIndex = -1;
		refreshLayout();
	}
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
			layout.areas.eachWithIndex(function(area, akey){
				$('<li class="areaButton ui-widget ui-state-default" onclick="selectArea(this, '
						+akey+')">'
							+area.name+':'+media_type_formatter2(area.type,null,null)+ '</li>').appendTo($('#areaUl'));
			});
			$('.areaButton').hover(function() {$(this).addClass("ui-state-hover");},
					function() {$(this).removeClass("ui-state-hover")});
		} else {
			layoutIndex = -1;
		}
	}
	areaIndex = -1;
	refreshAreaMediaList();
	refreshItemList()
}

function selectArea(src, index) {
	if (layoutIndex < 0) return;
	if (index >= layoutList[layoutIndex].areas.length) return;
	if (areaIndex != index) {
		$('.areaButton').removeClass('ui-state-active');
		$(src).addClass("ui-state-active");
		areaIndex = index;
		refreshAreaMediaList();
		refreshItemList();
		areaType = layoutList[layoutIndex].areas[areaIndex].type;
		if (areaType == '3' || areaType == '4' ) {
			$("#area_font").trigger("change");
			var area = layoutList[layoutIndex].areas[areaIndex];
			$('#area_prop').show();
			setTimeout(function () {$('#area_font').val(area.font);},1);
			//$('#area_color').val(area.color);
			if (area.color && area.color!=''){ 
				$('#area_color').next().css('background-color','#'+area.color);
			}else{ 
				$('#area_color').val('#0000ff');
				area.color = "0000ff";
				$('#area_color').next().css('background-color','#0000ff');
				}
			//$('#area_bgcolor').val(area.bgcolor);
			if (area.bgcolor && area.bgcolor!='') {
				$('#area_bgcolor').next().css('background-color','#'+area.bgcolor);
			}else {
				$('#area_bgcolor').val('#ffffff');
				area.bgcolor = "ffffff";
				$('#area_bgcolor').next().css('background-color','#ffffff');
				}
		} else {
			$('#area_prop').hide();
		}
	}
}
function refreshAreaMediaList() {
	$('#areaMediaTable').jqGrid('clearGridData');
	$('#cb_areaMediaTable').attr('checked', false);
	if (areaIndex<0) return;
	
	mediaType = layoutList[layoutIndex].areas[areaIndex].type;
	mediaList.each(function(media){
		//var media = index;//mediaList[index];
		var rowdata = {'name':media.name};
		if (mediaType == media.type) {
			jQuery('#areaMediaTable').jqGrid('addRowData',rowdata.name,rowdata);
		}
	});
}

function refreshItemList() {
	$('#itemTable').jqGrid('clearGridData');
	$('#cb_itemTable').attr('checked', false);
	if (layoutIndex < 0 || areaIndex < 0 || layoutList[layoutIndex].areas[areaIndex].plList == undefined) return;
	var curpl = layoutList[layoutIndex].areas[areaIndex].plList[0];
	if (curpl == null || curpl == undefined) {
		return;
	}
	/*
	var mediaArr = curpl.content.split('\t');
	mediaArr.eachWithIndex(function(content, mkey){
		if (content != null && content != ''){
			var row = {"name": content};
			$('#itemTable').jqGrid('addRowData', mkey, row);
		}
	});
	*/
	if(!curpl.loop){
		var layoutStartTime = layoutList[layoutIndex].startTime;
		while(layoutStartTime.split(":").length < 3){
			layoutStartTime += ":0";
		}
		curpl.timedMedias.foldLeft(layoutStartTime, updateMediaStartTime);
		curpl.timedMedias.eachWithIndex(function(m,i){
			if(m != null && m != ''){
				var row = {"name": m.name, "playCount": m.startTime}
				$('#itemTable').jqGrid('addRowData', i, row);
			}
		});
	}else{
		curpl.timedMedias.eachWithIndex(function(m,i){
			if(m != null && m != ''){
				var row = {"name": m.name, "playCount": m.playCount}
				$('#itemTable').jqGrid('addRowData', i, row);
			}
		});
	}
	
}

function refreshMediaList() {
	jQuery('#mediaTable').jqGrid('clearGridData');
	mediaList.eachWithIndex(function(mitem, mkey){
		var rowdata = {'type':mitem.type,'name':mitem.name,
				'playCount':mitem.playCount,'content':mitem.content};
		jQuery('#mediaTable').jqGrid('addRowData',mkey,rowdata);
	});
}

function checkValues() {
	if(layoutList.length == 0){
		alert("策略文件中不能没有布局，请添加");
		return false;
	}

	layoutList.each(function(layout){
		try{
			var localAreas = layout.areas;
			localAreas.length;
		}catch(e){
			alert("布局：" + layout.name + " 中不能没有屏幕区域，请添加。");
			return false;
		}
		localAreas.each(function(area){
			if(area.type < 4){
				try{
					var localPlayList = area.plList;
					localPlayList.length;
				}catch(e){
					alert("布局：" + layout.name + " 中的区域：" + area.name + " 中不能没有播放列表，请添加。");
					return false;
				}
				localPlayList.each(function(playList){
					try{
						var content = playList.content;
						if(content == ''){
							alert("布局：" + layout.name + " 中的区域：" + area.name + " 中的播放列表中没有媒体文件，请添加。");
							return false;
						}
					}catch(e){
						alert("布局：" + layout.name + " 中的区域：" + area.name + " 中的播放列表中没有媒体文件，请添加。");
						return false;
					}
					
				});
			}
		});
		
	});
	if ($.trim($('#policy_name').val())=='') {
		alert("策略名称不能为空");
		return false;
	}
	return true;
}

function docReady() {
	initGrid();
	initDialog();
	$('#area_font').change(function() {
		if (areaIndex>-1 && areaIndex<layoutList[layoutIndex].areas.length) {
			layoutList[layoutIndex].areas[areaIndex].font = $(this).val();
		} 
	});
	$('#area_color').change(function() {
		if (areaIndex>-1 && areaIndex<layoutList[layoutIndex].areas.length) {
			layoutList[layoutIndex].areas[areaIndex].color = $(this).val();
		} 
	});
	$('#area_bgcolor').change(function() {
		if (areaIndex>-1 && areaIndex<layoutList[layoutIndex].areas.length) {
			layoutList[layoutIndex].areas[areaIndex].bgcolor = $(this).val();
		}
	});
	// init color picker
	[{id:"#area_color", color:"#0000ff"}, {id:"#area_bgcolor", color:"#ffffff"}].each(function(el){
			$(el.id).ColorPicker({
				color: el.color,
				onShow: function (colpkr) {
					$(colpkr).css('z-index',50);
					return true;
				},
				onSubmit: function(hsb, hex, rgb, el) {
					$(el).val(hex);
					$(el).ColorPickerHide();
					$(el).next().css('background-color','#'+hex);
					if (areaIndex>-1 && areaIndex<layoutList[layoutIndex].areas.length) {
						layoutList[layoutIndex].areas[areaIndex].color = $('#area_color').val();
						layoutList[layoutIndex].areas[areaIndex].bgcolor = $('#area_bgcolor').val();
					}
				},
				onBeforeShow: function () {
					$(this).ColorPickerSetColor(this.value);
				}
			}).bind('keyup', function(){
				$(this).ColorPickerSetColor(this.value);
			});
		});
	/*
	$('#area_color, #area_bgcolor').ColorPicker({
		onShow: function (colpkr) {
			$(colpkr).css('z-index',50);
			return true;
		},
		onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$(el).ColorPickerHide();
			$(el).next().css('background-color','#'+hex);
			if (areaIndex>-1 && areaIndex<layoutList[layoutIndex].areas.length) {
				layoutList[layoutIndex].areas[areaIndex].color = $('#area_color').val();
				layoutList[layoutIndex].areas[areaIndex].bgcolor = $('#area_bgcolor').val();
			}
		},
		onBeforeShow: function () {
			$(this).ColorPickerSetColor(this.value);
		}
	})
	.bind('keyup', function(){
		$(this).ColorPickerSetColor(this.value);
	});
	$('#area_color').ColorPicker({color:"#ffffff"});
	$("#area_bgcolor").ColorPicker({color: "#0000ff"});
	*/
	$('#policy_start').datetime({format: "yy-mm-dd hh:ii:00"});
	$('#policy_end').datetime({format: "yy-mm-dd hh:ii:00"});
	$('.jbutton').button();
	
	$('#btnAddLayout').click(function() {
		initGridLayout();
		$('#layoutSelTable').trigger('reloadGrid');
		// init start and end time
		var startH = 0;
		var startM = 0;
		layoutList.each(function(curLayout){
			if (curLayout.endTime != '') {
				tcols = curLayout.endTime.split(':');
				if (startH < tcols[0] && startM <tcols[1]) {
					startH = tcols[0];
					startM = tcols[1];
				}
			}
		});
		setTime('#layoutStartHour', '#layoutStartMinute', startH+":"+startM);
		setTime('#layoutEndHour', '#layoutEndMinute', '23:59');
		$('#layoutSelDlg').dialog('open');
	});
	$('#btnAddRes').click(function () {
		initMediaAddGrid();
		$('#mediaSelTable').trigger('reloadGrid');
		$('#mediaAddDlg').dialog('open');
	});
	$('#btnImageTime').click(function () {
		var gr = $("#mediaTable").jqGrid('getGridParam','selrow');
		if (gr != null) {
			var media = mediaList[gr];
			if (media.type != '2') {
				alert("请先选 择图片资源");
				return;
			}
			$('#imageResIndex').val(gr);
			$('#imageTime').val(media.playCount);
			$('#imageTimeDlg').dialog('open');
		}
	});
	$('#btnAddText').click(function () {
		$('#media_text_no').val('-1');
		$('#media_text_name').val('');
		$('#play_count').val('1');
		$('#media_text').val('');
		$('#mediaTextDlg').dialog('open');
		
	});
	$('#btnEditText').click(function () {
		var gr = $("#mediaTable").jqGrid('getGridParam','selrow');
		if (gr != null) {
			var media = mediaList[gr];
			if (media.type != '3') {
				alert("请先选 择文本资源");
				return;
			}
			$('#media_text_no').val(gr);
			$('#media_text_name').val(media.name);
			$('#play_count').val(media.playCount);
			//$('#play_count').val(1);
			$('#media_text').val(media.content);
			$('#mediaTextDlg').dialog('open');
		} else {
			alert("请先选 择一项文本资源");
		}
	});
	$('#btnDeleteRes').click(function () {
		var gr = $("#mediaTable").jqGrid('getGridParam','selrow');
		if (gr != null) {
			if (confirm('确认要删除选中的资源吗?')) {
				mediaList.splice(parseInt(gr), 1);
				refreshMediaList();
			}
		} else {
			alert("请先选 择一项资源");
		}
	});
	
	$('#btnAddItem').click(function () {
		var gr = $("#areaMediaTable").jqGrid('getGridParam','selarrrow');
		if (gr != null && gr.length>0) {
			// add selected media to play list
			if (layoutIndex < 0 || areaIndex < 0) return;
			if (layoutList[layoutIndex].areas[areaIndex].plList ==null 
					|| layoutList[layoutIndex].areas[areaIndex].plList == undefined
					|| layoutList[layoutIndex].areas[areaIndex].plList.length == 0) {
				layoutList[layoutIndex].areas[areaIndex].plList = [{"content":'',"loop":true, 'timedMedias':[]}];
			}
			var timedMedias = layoutList[layoutIndex].areas[areaIndex].plList[0].timedMedias;
			var content = layoutList[layoutIndex].areas[areaIndex].plList[0].content;
			if (content == null || content == undefined) content = '';
			gr.each(function(name){
				//var rowdata = $('#areaMediaTable').jqGrid('getRowData', rowid);
				if (name != null && name != undefined) {
					if (content != '') content+='\t';
					content+=name;
					var m = mediaList.findBy("name", name);
					if(m.startTime == null){
						m.startTime = "0";
					}
					timedMedias.push(m);
				}
			});
			layoutList[layoutIndex].areas[areaIndex].plList[0].content = content;
			layoutList[layoutIndex].areas[areaIndex].plList[0].timedMedias = timedMedias;
			refreshItemList();
		} else {
			alert("请先选 择一项资源");
		}
	});
	$('#tabs').tabs({
		select:function(event, ui) {
			$( ".selector" ).tabs( "option", "selected", 2 );
			refreshAreaMediaList();
			// refresh playlist
		}
	});
	$('#btnSave').click(function() {
		if(!checkValues()){
			return false;
		}else{
			if(!window.confirm("确认保存吗？")){
				return false;
			}
		}
		//if (!checkValues()) return false;
		// TODO submit policy content to server
		var data = JSON.stringify({
	    	'id':$('#id').val(),
			'name':$('#policy_name').val(),
			'fmtStartTime':$('#policy_start').val(),
			'fmtEndTime':$('#policy_end').val(),
			'comments':$('#policy_comments').val(),
			'layoutList': layoutList,
			'mediaList': mediaList
	    });
		$.ajax( {
			contentType: 'application/json',
		    data: data,
		    dataType: 'json',
		    success: function(data){
		    	if ('success'== data.flag) {
		    		alert("保存成功");
		    		location.href="${listPolicyUrl}";
		    	} else if ('wrong_status' == data.flag) {
		    		alert("该播放策略不在可编辑状态中");
		    	} else if ('not_found' == data.flag) {
		    		alert("该播放策略不存在");
		    	}
		    },
		    processData: false,
		    type: 'POST',
		    url: '${savePolicyUrl}'
		});
	});
	$('#btnCancel').click(function() {
		location.href="${listPolicyUrl}";
	});
	
	$('#btnRemoveItem').click(function() {
		var selrows = $('#itemTable').jqGrid('getGridParam', 'selarrrow');
		if (selrows!=null && selrows!=undefined && selrows.length>0) {
			if (confirm('确认删除所选播放项吗?')) {
				$('#itemTable').jqGrid('clearGridData');
				if (layoutIndex < 0 || areaIndex < 0 || layoutList[layoutIndex].areas[areaIndex].plList == undefined) return;
				var curpl = layoutList[layoutIndex].areas[areaIndex].plList[0];
				if (curpl == null || curpl == undefined) {
					return;
				}
				var timedMedias = curpl.timedMedias;
				var newMedias = [];
				timedMedias.eachWithIndex(function(m, i){
					if(!selrows.contains(i)){
						newMedias.push(m);
					}
				});
				layoutList[layoutIndex].areas[areaIndex].plList[0].timedMedias = newMedias;
				var mediaArr = curpl.content.split('\t');
				var newcon = '';
				mediaArr.eachWithIndex(function(content, mkey){
					if (content != null && content != ''){
						var notdel = !selrows.contains(mkey);
						/*notdel = 
						selrows.each(function(selrow){
							if (mkey == selrow) {
								notdel = false;
								return;
							}
						});*/
						if (notdel) {
							var row = {"name": content};
							$('#itemTable').jqGrid('addRowData', mkey, row);
							if (newcon != '') newcon +="\t";
							newcon +=content;
						}
					}
					
				});
				layoutList[layoutIndex].areas[areaIndex].plList[0].content = newcon;
				refreshItemList();
				//saveItemGridToArr();
			}
		}
	});
	refreshLayout();
	refreshMediaList();
}
function saveItemGridToArr() {
	var griddata = $('#itemTable').jqGrid('getGridParam','data');
	var content = '';
	griddata.each(function(data){
		if (content != '') content+='\t';
		content += data.name;
	});
	layoutList[layoutIndex].areas[areaIndex].plList[0].content=content;
}

$(document).ready(docReady);

</script>

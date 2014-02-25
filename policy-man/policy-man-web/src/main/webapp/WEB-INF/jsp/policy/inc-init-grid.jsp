<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<script type="text/javascript">

function media_type_formatter(cellvalue, options, rowObject) {
	if (cellvalue=='video' || cellvalue=='1') return '视频';
	if (cellvalue=='image' || cellvalue=='2') return '图片';
	if (cellvalue=='text' || cellvalue=='3') return '文字';
	if (cellvalue=='time' || cellvalue=='4') return '时间';
	if (cellvalue=='weather' || cellvalue=='5') return '天气';
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
function initGrid() {
	var withToolBar = [true, 'top'];
	var withoutToolBar = [false, 'top'];
	jQuery("#itemTable").jqGrid({ 
		datatype: "local",
		colNames:['名称','播放时间'],
		colModel:[
				//{name:'type',index:'type', width:100, formatter: media_type_formatter},
				{name:'name',index:'name', width:100, sortable:false},
				{name:'playCount', index: 'playCount', width: 100, sortable: false}
				],
		rowNum:30,
		rowList:[30],
		width: 400,
		autowidth: false,
		viewrecords: true,
		sortable: false,
		hidegrid: false,
		multiselect: ${inEdit?'true':'false'},
		caption:'项目列表${inEdit?"(可拖动排序)":""}',
		toolbar: ${inEdit? '[true, "top"]' : '[false, "top"]'} });
	/*
	** add a button to full the area`s play time interval.
	*/
	var needInsertButton = ${inEdit ? "true" : "false"};
	if(needInsertButton){
		/*
		$("#t_itemTable").append("<input id='fullAreaTimeInterval' value='转成定时播放列表' type='button' class='jbutton'/>"
				+"<input id='toLoopPlayList' value='转成循环播放列表' type='button' class='jbutton'/>");
		*/
		$("#fullAreaTimeInterval").live("click", function(){
			try{
				var content = layoutList[layoutIndex].areas[areaIndex].plList[0].content;
				
				var areaMediaType = layoutList[layoutIndex].areas[areaIndex].type;
				if(areaMediaType != "1"){
					alert("只支持视频媒体!");
					return;
				}
				if(content == null || content == ""){
					alert("请先选择视频文件到列表中!");
					return;
				}
				var layoutStartTime = layoutList[layoutIndex].startTime;
				var layoutEndTime = layoutList[layoutIndex].endTime;
				var timedMedias = layoutList[layoutIndex].areas[areaIndex].plList[0].timedMedias;
				
				timedMedias = fillFullLayout(layoutStartTime, layoutEndTime, timedMedias);
				
				while(layoutStartTime.split(":").length < 3){
					layoutStartTime += ":0";
				}
				timedMedias.foldLeft(layoutStartTime, updateMediaStartTime);
				//clear
				$('#itemTable').jqGrid('clearGridData');
				//add new data
				timedMedias.eachWithIndex(function(m, i){
					var row = {"name":m.name, "playCount":m.startTime};
					$('#itemTable').jqGrid('addRowData', i, row);
				});
				layoutList[layoutIndex].areas[areaIndex].plList[0].timedMedias = timedMedias;
				layoutList[layoutIndex].areas[areaIndex].plList[0].loop = false;
				
			}catch(e){
				alert("出错了，你是不是还没有选择布局或区域?");
				return ;
			}
		});
		$("#toLoopPlayList").live("click", function(){
			
		});
	}
	
	/**
	*make it sortable
	*/
	if (${inEdit?'true':'false'}) {
		jQuery("#itemTable").jqGrid("sortableRows", {update:function() {
			var content = '';
			var dataIds = $('#itemTable').jqGrid('getDataIDs');
			var layoutStartTime = layoutList[layoutIndex].startTime;
			while(layoutStartTime.split(":").length < 3){
				layoutStartTime += ":0";
			}
			// get medias 
			timedMedias = dataIds.collect(function(id){
				if (content != '') content+='\t';
				var rowData = $("#itemTable").jqGrid("getRowData", id);
				content += rowData.name;
				return mediaList.findBy("name", rowData.name);
			});
			layoutList[layoutIndex].areas[areaIndex].plList[0].content=content;
			//update start time
			timedMedias.foldLeft(layoutStartTime, updateMediaStartTime);
			
			$('#itemTable').jqGrid('clearGridData');
			timedMedias.eachWithIndex(function(m, i){
				var row = {"name": m.name, "playCount":m.startTime};
				$('#itemTable').jqGrid('addRowData', i, row);
			});
			layoutList[layoutIndex].areas[areaIndex].plList[0].timedMedias = timedMedias;
			/*
			var content = '';
			dataIds.each(function(id){
				if (content != '') content+='\t';
				rowdata = $('#itemTable').jqGrid('getRowData', id);
				content += rowdata.name;
			});
			layoutList[layoutIndex].areas[areaIndex].plList[0].content=content;
			*/
		}});
	}
	
	jQuery("#areaMediaTable").jqGrid({ 
		datatype: "local",
		colNames:['名称'],
		colModel:[ {name:'name',index:'name', width:180},
		           ],
		rowNum:30,
		rowList:[30],
		width: 180,
		autowidth: false,
		viewrecords: true,
		sortable: false,
		hidegrid: false,
		multiselect: true,
		caption:'可用媒体文件' });
	
	jQuery("#mediaTable").jqGrid({ 
		//url:null,
		datatype: "local",
		colNames:['类型','名称','播放时长或次数','内容'],
		colModel:[
				{name:'type',index:'type', width:75, formatter: media_type_formatter},
				{name:'name',index:'name', width:100},
				{name:'playCount',index:'playCount', width:80},
				{name:'content',index:'content', width:200}
				],
		rowNum:30,
		rowList:[30],
		width: 600,
		height: 240,
		autowidth: false,
		sortname: 'name',
		viewrecords: true,
		sortorder: "asc",
		hidegrid: false,
		caption:'资源列表' });
}

function initMediaAddGrid() {
	jQuery("#mediaSelTable").jqGrid({ 
		url:'<%=request.getContextPath()%>/videos/jselect',
		datatype: "json",
		colNames:[${mediaColNames}],
		colModel:[
				{name:'type',index:'type', width:50},
				{name:'name',index:'name', width:200},
				{name:'code',index:'status'},
				{name:'playTime',index:'playTime'},
				],
		rowNum:10,
		rowList:[10,20,30],
		width: 670,
		height: 260,
		pager: '#mediaSelPage',
		autowidth: false,
		sortname: 'id',
		viewrecords: true,
		sortorder: "asc",
		hidegrid: false,
		multiselect: true,
		caption:'<fmt:message key="policy.media.list" />' });

	$(".jqbutton").button();
	
	$('#btnSearch').click(function() {
		gridReload();
	});
}

function initGridLayout() {
	jQuery("#layoutSelTable").jqGrid({
		url:'<%=request.getContextPath()%>/layout/sellist',
		datatype: "json",
		colNames:[${layoutColNames}],
		colModel:[
				{name:'name',index:'name', width:100},
				{name:'areaCount',index:'status', width:200}
				],
		rowNum:10,
		rowList:[10,20,30],
		pager: '#layoutSelPage',
		autowidth: false,
		sortname: 'id',
		viewrecords: true,
		sortorder: "asc",
		hidegrid: false,
		caption:'<fmt:message key="layout.list" />' });
}

var timeoutHnd;
var flAuto = true;

function doSearch(ev) {
	if(!flAuto) {
		return;
	}
//	var elem = ev.target||ev.srcElement;
	if(timeoutHnd) {
		clearTimeout(timeoutHnd);
	}
	timeoutHnd = setTimeout(gridReload, 500)
}

function gridReload(){
	jQuery("#mediaSelTable").jqGrid('setGridParam',{mtype:"POST", url:"<%=request.getContextPath()%>/videos/jselect", 
		//url:"<=request.getContextPath()%>/videos/jselect?searchCate=" + 
		//jQuery("#searchCate").val() + "&name=" + jQuery("#name").val(), 
		postData:{'searchCate':jQuery("#searchCate").val(), 'name':jQuery("#name").val()},
		page:1}).trigger("reloadGrid");
}

var updateMediaStartTime = function(currentStartTime, media){
	media.startTime = currentStartTime;
	var hourMinSec = currentStartTime.split(":");
	var sumSeconds = hourMinSec[0]*3600 + hourMinSec[1]*60 + hourMinSec[2]*1 + media.playCount*1;
	var hour = Math.floor(sumSeconds/3600);
	var min = Math.floor((sumSeconds - hour*3600)/60);
	var seconds = sumSeconds%60;
	return hour + ":" + min + ":" + seconds;
}

var fillFullLayout = function(start, end, medias){
	var results = [];
	var sumPlayCounts = 0;
	medias.each(function(m){
		sumPlayCounts += (m.playCount*1);
	});
	var layoutPlayCounts = getSumSeconds(start, end);
	var loopTimes = Math.floor(layoutPlayCounts/sumPlayCounts);
	var remaindeTime = layoutPlayCounts%sumPlayCounts;
	for(var i = 0; i < loopTimes; i++){
		results.push(medias);
	}
	for(var i = 0; i < medias.length; i++){
		if(remaindeTime <= 0){
			break;
		}
		results.push(medias[i]);
		remaindeTime -= (medias[i].playCount*1);
	}
	return results;
}

var distinctMedias = function(start, end, medias){
	var results = [];
	var codes = [];
	medias.each(function(m){
		if(!codes.contains(m.code)){
			results.push(m);
		}
	});
	return results;
}
var getSumSeconds = function(start, end){
	var s = start.split(":");
	var e = end.split(":");
	var h = e[0] - s[0];
	var m = e[1] - s[1];
	var se = e[2] - s[2];
	return h*3600 + m * 60 + se;
}
</script>
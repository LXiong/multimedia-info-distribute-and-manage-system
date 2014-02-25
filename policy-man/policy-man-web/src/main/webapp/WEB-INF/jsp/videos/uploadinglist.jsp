<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<sp:url var="confirmUploadedUrl" value="/videos/confirmuploaded" />
<sp:url var="delUploadingUrl" value="/videos/del-uploading" />
<script type="text/javascript">
$(document).ready(function() {
	var lastsel;
	jQuery("#uploadingList").jqGrid({ 
		url:'uploading',
		datatype: "json",
		colNames:["id", "code", "<fmt:message key='file.name' />", "初始文件名", 
		          "<fmt:message key='uploaded.size' />", 
		          "最后更新时间", //"<fmt:message key='expected.size' />", 
		          "uploaded"], 
		colModel:[
		        {name:'id', index:'id', hidden:true},
		        {name:'code', index:'code', hidden:true},
				{name:'fileName',index:'fileName', hidden: true},
				{name:'originName', index:'originName'},
				{name:'currentSize',index:'currentSize'},
				//{name:'expectedSize',index:'expectedSize', hidden: true},//, editable:true
				{name:'lastModifiedTime', index:'lastModifiedTime'},
				{name:'uploaded', index:'uploaded', hidden: true},
				],
		rowNum:10,
		rowList:[10,20,30],
		height: "300",
		pager: '#pager',
		autowidth: true, 
		sortname: 'fileName',
		viewrecords: true,
		sortorder: "asc", 
		multiselect: true, 
		/* onSelectRow: function(id){
			if(id && id !== lastsel){
				jQuery('#uploadingList').jqGrid('restoreRow', lastsel);
				jQuery('#uploadingList').jqGrid('editRow', id, true);
				lastsel = id;
			}
		}, */
		//editurl: 'video/edit', 
		caption:"<fmt:message key='file.uploading.list' />" 
	});
	
	jQuery("#uploadingList").jqGrid('navGrid', '#pager', 
		{edit:false, add:false, del:false}, //options
		{height:300, reloadAfterSubmit:false}, // edit options
		{}, // add options
		{}, // del options
		{} // search options
	);
	
	$('.jbutton').button();
	$('#btnConfirm').click(function() {
		var gr = $('#uploadingList').jqGrid("getGridParam", 'selrow');
		if (gr != null) {
			if (confirm("确认选中的文件上传完成了吗？")) {
				//var rowdata = $('#uploadingList').jqGrid('getRowData', gr);
				var rowIds = jQuery("#uploadingList").jqGrid('getGridParam','selarrrow');
				$.post("${confirmUploadedUrl}", {'ids':(""+rowIds)}, function(data) { //{'id':rowdata['id']}
					if (data == 'success') {
						alert("处理完成");
					} else if (data == 'notfound' ) {
						alert("没找到指定的记录");
					} else if (data == 'file_miss' ) {
						alert("没找到指定的文件");
					} else if (data == 'miss_match' ) {
						alert("文件长度与记录中的不一致");
					} else if (data == "failed-md5") {
						alert("计算文件的md5时发生错误");
					} else {
						alert("操作失败");
					}
					$('#uploadingList').trigger("reloadGrid");
				});
			}
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
	$('#btnDelete').click(function() {
		var gr = $('#uploadingList').jqGrid("getGridParam", 'selrow');
		if (gr != null) {
			if (confirm("确认删除选中的文件吗？")) {
				//var rowdata = $('#uploadingList').jqGrid('getRowData', gr);
				var rowIds = jQuery("#uploadingList").jqGrid('getGridParam','selarrrow');
				$.post('${delUploadingUrl}', {'ids':(""+rowIds)}, function(data) {//rowdata['id']
					if (data == 'success') {
						alert("删除完成");
					} else if (data == 'failed-delete-file') {
						alert("删除文件失败");
					} else if (data == 'failed-delete-record') {
						alert("删除记录失败");
					}
					$('#uploadingList').trigger("reloadGrid");
				});
			}
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
	
	/* jQuery("#test").click( function() {
		var rowIds = jQuery("#uploadingList").jqGrid('getGridParam','selarrrow');
		var idsArr = (""+rowIds).replace(",", "x");
		alert(idsArr);
	}); */
});
</script>
<sec:authorize url="/videos/confirmuploaded" >
<input type="button" class="jbutton" id="btnConfirm" value="确认上传完成">
</sec:authorize>
<sec:authorize url="/videos/del-uploading" >
<input type="button" class="jbutton" id="btnDelete" value='<fmt:message key="button.delete" />'>
</sec:authorize>
<!--<button id='btnDelete' class="jqbutton"><fmt:message key="button.delete" /></button>-->
<table id="uploadingList"></table>
<div id="pager"></div>
<!-- <input type="button" id="test"  value="testet"> -->
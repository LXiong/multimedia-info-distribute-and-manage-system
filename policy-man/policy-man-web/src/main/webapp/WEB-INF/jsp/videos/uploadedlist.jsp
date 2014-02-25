<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%><%@include file="../taglib.jsp" %>
<fmt:message var="colNames" key="file.uploaded.page.colNames" />
<sp:url var="mediaInfoUrl" value="/videos/getinfo"></sp:url>
<sec:authorize url="/videos/impt">
<button id='btnEdit' class="jqbutton"><fmt:message key="button.import" /></button>
<button id='btnDelete' class="jqbutton"><fmt:message key="button.delete" /></button>
</sec:authorize>
<table id="uploadedList"></table>
<div id="pager"></div>

<script type="text/javascript">
$(document).ready(function() {
	var lastsel;
	jQuery("#uploadedList").jqGrid({ 
		url:'uploaded',
		datatype: "json",
		colNames:[${colNames}], 
		colModel:[
		        {name:'id', index:'id', editable:false, hidden:true}, 
		        {name:'code', index:'code', editable:false}, 
				{name:'fileName',index:'fileName', editable:false, hidden:true},
				{name:'originName', index:'originName', editable:false},
				{name:'currentSize',index:'currentSize'},
				{name:'expectedSize',index:'expectedSize', hidden:true},
				{name:'lastModifiedTime', index:'lastModifiedTime'}, 
				{name:'uploaded', index:'uploaded', hidden:true}, 
				{name:'name',index:'name', editable:true, hidden:true, 
					editrules:{edithidden:true}}, 
				{name:'tag',index:'tag', editable:true, hidden:true, 
					editrules:{edithidden:true}}, 
				{name:'description',index:'description', editable:true, 
						hidden:true, editrules:{edithidden:true}}, 
				{name:'status',index:'status', hidden:true}, 
				{name:'liveTimeStart',index:'liveTimeStart', editable:true, 
						hidden:true, editrules:{required:true, edithidden:true}, 
						edittype: 'text', label: 'Date', 
						editoptions: {size: 11, maxlengh: 11, 
							dataInit: function(element) {
					        	$(element).datepicker({dateFormat: 'yy-mm-dd'});
					      	}
				      	} }, 
				{name:'liveTimeEnd',index:'liveTimeEnd', editable:true, 
						hidden:true, editrules:{required:true, edithidden:true}, 
						edittype: 'text', label: 'Date', 
						editoptions: {size: 11, maxlengh: 11, 
							dataInit: function(element) {
					        	$(element).datepicker({dateFormat: 'yy-mm-dd'});
					      	}
				      	}}, 
				{name:'width', index:'width', editable:true, hidden:true, 
					editrules:{edithidden:true}}, 
				{name:'height', index:'height', editable:true, hidden:true, 
					editrules:{edithidden:true}}, 
				{name:'playTime',index:'playTime', editable:true, 
						hidden:true, editrules:{edithidden:true}}, 
				{name:'discrim', index:'discrim', editable:false, hidden:true, 
					editrules:{edithidden:true}}, 
				{name:'mediaType', index:'mediaType', editable:true, 
						hidden:true, edittype:'select', 
						editoptions:{ value: "video:视频;image:图片;text:文字" }, editrules:{edithidden:true}}, 
				{name:'mediaSize', index:'mediaSize', hidden:true}, 
				], 
		rowNum:10, 
		rowList:[10,20,30],
		height: "300", 
		pager: '#pager',
		autowidth: true, 
		sortname: 'fileName',
		viewrecords: true,
		sortorder: "asc",
		editurl: "<%=request.getContextPath()%>/videos/impt", 
		caption:"<fmt:message key='file.reviewed.list' />"
	});
	
	jQuery("#uploadedList").jqGrid('navGrid', '#pager', {edit:false, add:false, del:false}); 
	$(".jqbutton").button();
	$('#btnEdit').click(function() {
		var gr = jQuery("#uploadedList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			//jQuery("#uploadedList").jqGrid('setRowData', gr,
			//		{"liveTimeStart":(new Date()).format('yy-mm-dd')});
			//var rowData = jQuery("#uploadedList").jqGrid('getRowData',gr); 
			$.post("${mediaInfoUrl}",{'id':gr},function(data) {
				if (!data.exist) {
					alert('文件不存在');
					return;
				}
				jQuery("#uploadedList").jqGrid('editGridRow', gr, 
						{width:400, height:400, reloadAfterSubmit:true, 
					closeAfterEdit:true, 
					afterShowForm: function () {
						var gr = jQuery("#uploadedList").jqGrid('getGridParam','selrow');
						var selectedRow = jQuery('#uploadedList').jqGrid('getRowData',gr);
						now = new Date();
						oname = selectedRow['originName'];
						oname = oname.substr(0,oname.lastIndexOf('.'));
						$('#name').val(oname);
						$('#width').val(data.width);
						$('#height').val(data.height);
						$('#playTime').val(Math.round(data.duration));
						if (data.nbStream>1) $('#mediaType').val('video');
						if (data.format == 'image2') {
							$('#mediaType').val('image');
							$('#playTime').val('');
						}
						if (data.format == 'text') {
							$('#mediaType').val('text');
							$('#playTime').val('1');
						}
						$('#liveTimeStart').val( $.datepicker.formatDate('yy-mm-dd', now) );
						later = new Date(now.getTime() + 180*24*60*60*1000);
						$('#liveTimeEnd').val($.datepicker.formatDate('yy-mm-dd', later));
					},
					afterSubmit: function (response, postdata) {
						if (response.responseText == 'code_exist') {
							alert("有相同md5文件存在，文件可能已经入库");
							return [false];
						}
						if (response.responseText=="failed_delete") {
							alert("删除失败");
							return [false];
						}
						return [true];
					}
				}); 
			});
			
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
	$('#btnDelete').click(function() {
		var gr = jQuery("#uploadedList").jqGrid('getGridParam','selrow');
		if( gr != null ) { 
			var rowData = jQuery("#uploadedList").jqGrid('getRowData',gr);
			jQuery("#uploadedList").jqGrid('delGridRow', gr, {reloadAfterSubmit:false});
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
});

</script>

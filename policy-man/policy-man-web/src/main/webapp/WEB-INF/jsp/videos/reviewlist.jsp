<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%><%@include file="../taglib.jsp" %>
<fmt:message var="colNames" key="file.page.colNames" />
<script type="text/javascript">
$(document).ready(function() {
	var lastsel;
	jQuery("#reviewList").jqGrid({ 
		url:'reviews',
		datatype: "json",
		colNames:[${colNames}], 
		colModel:[
				{name:'code',index:'code', editable:false}, 
				{name:'originName',index:'originName'}, 
				{name:'fileName',index:'fileName', editable:false, 
					editrules:{required:true, edithidden:true}}, 
				{name:'name',index:'name', editable:true}, 
				{name:'tag', index:'tag', hidden:true, editable:true, 
					editrules:{edithidden:true}, edittype:"textarea", 
					editoptions:{rows:"2", cols:"25"}}, 
				{name:'description', index:'description', hidden:true, 
					editable:true, editrules:{edithidden:true}, 
					edittype:"textarea", editoptions:{rows:"3", cols:"25"}}, 
				{name:'status',index:'status'}, 
				{name:'liveTimeStart',index:'liveTimeStart', editable:true, 
					hidden:true, editrules:{required:true, edithidden:true}, 
					edittype: 'text', label: 'Date', 
					editoptions: {size: 11, maxlengh: 11, 
						dataInit: function(element) {
				        	$(element).datepicker({dateFormat: 'yy-mm-dd'});
				      	}
				  	}}, 
				{name:'liveTimeEnd',index:'liveTimeEnd', editable:true, 
					hidden:true, editrules:{required:true, edithidden:true}, 
					edittype: 'text', label: 'Date', 
					editoptions: {size: 11, maxlengh: 11, 
						dataInit: function(element) {
				        	$(element).datepicker({dateFormat: 'yy-mm-dd'});
				      	}
				  	}}, 
				{name:'submitter',index:'submitter'}, 
				{name:'submitAt',index:'submitAt'}, 
				{name:'auditor',index:'auditor'}, 
				{name:'auditAt',index:'auditAt'}, 
				{name:'width',index:'width', editable:true, hidden:true}, 
				{name:'height',index:'height', editable:true, hidden:true}, 
				{name:'playTime',index:'playTime', editable:true}, 
				{name:'discrim',index:'discrim'}, 
				{name:'mediaType',index:'mediaType', editable:true, edittype:'select', 
					editoptions:{ value: "video:视频;image:图片;text:文字" }}, 
				{name:'mediaSize',index:'mediaSize'}, 
				],
		rowNum:10, 
		rowList:[10,20,30],
		height: "300", 
		pager: '#pager',
		autowidth: true, 
		sortname: 'fileName',
		viewrecords: true,
		sortorder: "asc",
		editurl: "<%=request.getContextPath()%>/videos/video/review", 
		caption:"<fmt:message key="file.uploaded.list" />" 
	});
	
	jQuery("#reviewList").jqGrid('navGrid', '#pager', {edit:false, add:false, del:false}); 
	$(".jqbutton").button();
	
	$('#btnView').click(function() {
		var gr = jQuery("#reviewList").jqGrid('getGridParam','selrow');
		if( gr != null ) { 
			var rowData = jQuery("#reviewList").jqGrid('getRowData',gr); 
			var regVideo = new RegExp(/\.avi|wmv|mpg$/i);
			if(rowData.fileName.search(regVideo) != -1) {
				alert("<fmt:message key='media.can.not.preview' />");
			}
			var regText = new RegExp(/\.txt$/i);
			if(rowData.fileName.search(regText) != -1) {
				var getTextUrl = "<%=request.getContextPath() %>/videos/textpreviewpre";
				var getTextParams = {"originName":rowData.originName};
				$.post(getTextUrl, getTextParams, function(data){
					$("#divTxt").empty();
					$("#divTxt").append(data);
					$('#divTextValue').dialog('open');
					$("#divTextValue").dialog( "option", "height", 400 );
					$("#divTextValue").dialog( "option", "width", 600 );
				});
			}
			var regPic = new RegExp(/\.(jpg|png|bmp)$/i);
			if(rowData.fileName.search(regPic) != -1) {
				//var getPicUrl = "<=request.getContextPath() >/videos/picpreview";
				//var getPicParams = {"originName":rowData.originName};
				//$.post(getPicUrl, getPicParams);
				window.open("picpreviewpre?originName=" + rowData.originName);
			}
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
	
	$('#btnEdit').click(function() {
		var gr = jQuery("#reviewList").jqGrid('getGridParam','selrow');
		if( gr != null ) { 
			jQuery("#reviewList").jqGrid('editGridRow', gr, 
					{width:400, height:600, reloadAfterSubmit:true, 
				closeAfterEdit:true}); 
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});

	$('#btnDelete').click(function() {
		var gr = jQuery("#reviewList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			var rowData = jQuery("#reviewList").jqGrid('getRowData',gr); 
			if (confirm('<fmt:message key="file.delete.confirm" />:"' + rowData.fileName + '"?')) { 
				jQuery("#reviewList").jqGrid('delGridRow', gr, {reloadAfterSubmit:false});
			} 
		} else alert('<fmt:message key="alert.select.row" />');
	});
	
	// show text div
	$("#divTextValue").dialog({ 
		autoOpen: false, 
		show: "blind", 
		title: "<fmt:message key='media.text.txt' />",
		buttons: [
		  		{
                   	text: "<fmt:message key='button.close' />", 
                   	click: function() { $('#divTextValue').dialog('close'); }
               	}
               	] 
     });
});

</script>
<button id='btnView' class="jqbutton"><fmt:message key="button.view" /></button>
<button id='btnEdit' class="jqbutton"><fmt:message key="button.review" /></button>
<button id='btnDelete' class="jqbutton"><fmt:message key="button.delete" /></button>
<table id="reviewList"></table>
<div id="pager"></div>
<div id="divTextValue"><div id="divTxt"></div></div>
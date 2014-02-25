<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="../taglib.jsp" %>
<sp:url var="delVideoUrl" value="/videos/del-video" />
<fmt:message var="colNames" key="file.page.colNames" />
<script type="text/javascript">
$(document).ready(function() {
	jQuery("#videoList").jqGrid({ 
		url:"grid",
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
		editurl: "video/save", 
		caption:"<fmt:message key='file.list' />" 
	});

	$( "#from" ).datepicker({dateFormat: 'yy-mm-dd'});
	$( "#from" ).datepicker("option", "showAnim", "slideDown");
	$( "#to" ).datepicker({dateFormat: 'yy-mm-dd'});
	$( "#to" ).datepicker("option", "showAnim", "slideDown");
	
	jQuery("#videoList").jqGrid('navGrid','#pager',{edit:false,add:false,del:false}); 

	$(".jqbutton").button();
	
	$('#btnView').click(function() {
		var gr = jQuery("#videoList").jqGrid('getGridParam','selrow');
		if( gr != null ) { 
			var rowData = jQuery("#videoList").jqGrid('getRowData',gr); 
			var regVideo = new RegExp(/\.avi|wmv|mpg$/i);
			if(rowData.fileName.search(regVideo) != -1) {
				alert("<fmt:message key='media.can.not.preview' />");
			}
			var regText = new RegExp(/\.txt$/i);
			if(rowData.fileName.search(regText) != -1) {
				var getTextUrl = "<%=request.getContextPath() %>/videos/textpreview";
				var getTextParams = {"fileName":rowData.fileName};
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
				window.open("picpreview?fileName=" + rowData.fileName);
			}
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});
	
	$('#btnEdit').click(function() {
		var gr = jQuery("#videoList").jqGrid('getGridParam','selrow');
		if( gr != null ) { 
			jQuery("#videoList").jqGrid('editGridRow', gr, 
					{width:400, height:430, reloadAfterSubmit:true, 
				closeAfterEdit:true}); 
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});

	$('#btnDelete').click(function() {
		var gr = jQuery("#videoList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			var rowData = jQuery("#videoList").jqGrid('getRowData',gr); 
			if (confirm('<fmt:message key="file.delete.confirm" />:"' + rowData.fileName + '"?')) { 
				//jQuery("#videoList").jqGrid('delGridRow', gr, {reloadAfterSubmit:false});
				
				//var rowIds = jQuery("#videoList").jqGrid('getGridParam', 'selarrrow');
				//$.post('${delVideoUrl}', {'ids':(""+rowIds)}, function(data) {//rowdata['id']
				$.post('${delVideoUrl}', {'code':(""+rowData['code'])}, function(data) {
					if (data == 'success') {
						alert("删除完成");
					} else if (data == 'failed-delete-file') {
						alert("删除文件失败");
					} else if (data == 'failed-delete-record') {
						alert("删除记录失败");
					}
					$('#videoList').trigger("reloadGrid");
				});
			} 			
		} else {
			alert('<fmt:message key="alert.select.row" />');
		}
	});

	$('#btnSearch').click(function() {
		/*var params = "?name=" + $("#name").val() + "&searchCate=" + 
			$("#searchCate").val() + "&from=" + $("#from").val() + 
			"&to=" + $("#to").val();
		window.location = "list" + params;*/
		gridReload();
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
	jQuery("#videoList").jqGrid('setGridParam',{mtype:"POST", url:"grid", 
		//url:"grid?searchCate=" + jQuery("#searchCate").val() + "&name=" + jQuery("#name").val() + 
		//"&from=" + jQuery("#from").val()  + "&to=" + jQuery("#to").val(), 
		postData:{'searchCate':jQuery("#searchCate").val(), 'name':jQuery("#name").val(),'from':jQuery("#from").val(),'to':jQuery("#to").val()},
		page:1}).trigger("reloadGrid");
}
</script>
<div>
	<label><fmt:message key="file.category" /></label>
	<select id="searchCate" name="searchCate" >
		<option value=""><fmt:message key="media.all" /></option>
		<option value="video"><fmt:message key="media.video" /></option>
		<option value="image"><fmt:message key="media.pic" /></option>
		<option value="text"><fmt:message key="media.text" /></option>
	</select>
	<!--<label><fmt:message key="file.sub.category" /></label>
	<select id="searchSubCate" name="searchSubCate" >
	</select>-->
	<label><fmt:message key="file.alias" /></label>
	<input type="text" id="name" name="name" onkeydown="doSearch(arguments[0]||event)" />
	<label><fmt:message key="meida.warehousing" /></label>
	<input id="from" name="from" size="12"></input>&nbsp;to&nbsp;
	<input id="to" name="to" size="12"></input>
	&nbsp;&nbsp;&nbsp;
	<button id='btnSearch' class="jqbutton" ><fmt:message key="button.search" /></button>&nbsp;
	<button id='btnView' class="jqbutton"><fmt:message key="button.view" /></button>
	<button id='btnEdit' class="jqbutton"><fmt:message key="button.edit" /></button>
	<button id='btnDelete' class="jqbutton"><fmt:message key="button.delete" /></button>
</div>
<table id="videoList"></table>
<div id="pager"></div>
<div id="divTextValue"><div id="divTxt"></div></div>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@include file="../taglib.jsp"%>
<sp:url var="policyEditUrl" value="/policy/edit" />
<sp:url var="listPublishedGroupUrl" value="/policy/listPublishedGroup" />
<script type="text/javascript">
var site_root = "<%=request.getContextPath()%>";
var policy_col_names = ["ID", "文件名", "状态"<c:if test="${currentStatus =='published'}">,"发布时间","&nbsp;"</c:if>];
var policy_col_model = [
                        {name:"id", index:"id"},
                        {name:"name", index:"name"},
                        {name:"status", index:"status"}
                        <c:if test="${currentStatus =='published'}">
        				,{name:'publishedAt',index:'publishedAt', width:120}
        				,{name:'viewgroupid',index:'id', width:120, sortable:false, formatter: function (cellvalue, options, row) {
        					return '<a href="###" onclick="showGroup('+row[0]+')">查看发布区域</a>';
        				}}
        				</c:if>
                        ];
var policies_list = {
		url: site_root + "${gridUrl}",
		datatype: "json",
		colNames: policy_col_names,
		colModel: policy_col_model,
		rowNum:10,
		rowList:[10, 20, 30, 40],
		pager: "#policyPager",
		height: 350,
		autowidth: true,
		sortname: "id",
		viewrecords: true,
		sortorder: "asc",
		hidegrid: false,
		caption: "<fmt:message key='${captionKey}'/>"
};
$(function(){
	jQuery("#policyList").jqGrid(policies_list);	
	jQuery("#policyList").jqGrid("navGrid", "#policyPager", {edit:false, add:false, del:false});
	$("#viewButton").button();
	$("#viewButton").click(function(){
			var row_id = jQuery("#policyList").jqGrid("getGridParam", "selrow");
			if(row_id != null){
				var policy = jQuery("#policyList").jqGrid("getRowData", row_id);
				var view_url = site_root + "${viewUrl}?id=" + policy["id"];
				location.href = view_url;
			}
		}
	);
	if ($("#btnEdit")) {
		$("#btnEdit").button();
		$('#btnEdit').click(function() {
			var gr = jQuery("#policyList").jqGrid('getGridParam','selrow');
			if( gr != null ) {
				location.href="${policyEditUrl}?id="+gr;
			}
			else alert('<fmt:message key="alert.select.row" />');
		});
	}
	$('#publishedGroupDlg').dialog({
		autoOpen:false,
		width:400,
		height:300,
		buttons:{
			'<fmt:message key="button.close" />':function () {
				$('#publishedGroupDlg').dialog('close');
			}
		}
	});
});

function showGroup(id) {
	$.post('${listPublishedGroupUrl}', {'id':id}, function (data) {
		if (data.flag == 'success') {
			$('#publishedGroup').empty();
			data.value.each(function(p){
				$('#publishedGroup').append('<li>'+p['typeName']+'</li>');
			});
			$('#publishedGroupDlg').dialog('open');
		} else if (data.flag=='not_found') {
			alert("指定的策略文件不存在");
		} else if (data.flag == 'wrong_status') {
			alert("策略文件不是已发布状态");
		}
	},'json');
}
</script>
<button id="viewButton" class="jqbutton"><fmt:message key="button.view"/></button>
<c:if test="${showEditButton }"><button id='btnEdit' class="jqbutton"><fmt:message key="button.edit" /></button></c:if>
<table id="policyList"></table>
<div id="policyPager"></div>

<div id="publishedGroupDlg" class="hidden">
	<ul id="publishedGroup">
	</ul>
</div>
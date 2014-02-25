<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
<button id='btnAdd' class="jqbutton"><fmt:message key="button.add" /></button>
<button id='btnEdit' class="jqbutton"><fmt:message key="button.edit" /></button>
<button id='btnDelete' class="jqbutton"><fmt:message key="button.delete" /></button>

<table id="layoutList"></table>
<div id="layoutPage"></div>
<sp:url var="editUrl" value="/layout/editform" />
<sp:url var="deleteUrl" value="/layout/delete" />
<script type="text/javascript">
$(document).ready(function() {
	$(".jqbutton").button();
	jQuery("#layoutList").jqGrid({ 
		url:'<%=request.getContextPath()%>/layout/grid',
		datatype: "json",
		colNames:['布局名称','宽度', '高度','注释'],
		colModel:[
				{name:'name',index:'name', width:90},
				{name:'width',index:'width', width:100},
				{name:'height',index:'height', width:100},
				{name:'lcomment',index:'lcomment', width:80},
				],
		rowNum:10,
		rowList:[10,20,30],
		pager: '#layoutPage',
		autowidth: true, 
		sortname: 'name',
		viewrecords: true,
		sortorder: "asc",
		hidegrid: false,
		editurl:'<%=request.getContextPath()%>/layout/update',
		caption:"<fmt:message key="layout.list" />" });
		jQuery("#layoutList").jqGrid('navGrid','#layoutPage',{add:false,edit:false,del:false,search:false})
			.navButtonAdd(
			"#layoutPage",
			{caption:"删除",
				onClickButton: function(){
				var id = jQuery("#layoutList").getGridParam("selrow"); 
				if( id != null ) jQuery("#layoutList").jqGrid('delGridRow',id,{reloadAfterSubmit:true}); 
				else alert("请先选择一行!"); 
			}})
			.navButtonAdd("#layoutPage",{caption:"添加",onClickButton: function(){location='<%=request.getContextPath()%>/layout/addlayout'}})
			.navButtonAdd(
						"#layoutPage",
						{caption:"编辑",
							onClickButton:function(){
								var id = jQuery("#layoutList").getGridParam("selrow");
								if(id!=null&&id!=""){
									location='<%=request.getContextPath()%>/layout/edit?id='+id
								}else{alert("请先选择一行")}
								}
						});
		});
	$('#btnAdd').click(function () {
		location.href='${editUrl}';
	});
	$('#btnEdit').click(function () {
		var id = jQuery("#layoutList").getGridParam("selrow");
		if(id!=null&&id!=""){
			location.href='${editUrl}?id='+id
		}else{alert("请先选择一行")}
	});
	$('#btnDelete').click(function () {
		var id = jQuery("#layoutList").getGridParam("selrow");
		if(id!=null&&id!=""){
			if (confirm('<fmt:message key="layout.delete.confirm" />')) {
				$.post('${deleteUrl}',{"id":id},function(data) {
					if (data.flag =='success') {
						alert('<fmt:message key="delete.completed" />');
						$("#layoutList").trigger("reloadGrid");
					} else {
						alert(data.message);
					}
				},'json');
			}
		}else{
			alert("请先选择一行");
		}
	});
</script>

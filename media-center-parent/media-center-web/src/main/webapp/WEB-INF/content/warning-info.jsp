<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/js/jquery.endless-scroll.js"></script>
<div id="search-warning">
	<form action="<%=request.getContextPath() %>/warning-info!query.action"
		method="get" class="align-left-form" id="query_info_form">
		<fieldset>
			<legend>告警信息查询</legend>
			<label for="stb_mac">机顶盒MAC地址</label> <input type="text"
				name="info.stbMac" id="stb_mac" value="${info.stbMac }"></input> <label
				for="warning_type">告警类型</label> <select name="info.warningType"
				id="warning_type">
				<option value="">所有</option>
				<option value="config-error">配置文件错误</option>
				<option value="disk-lack">硬盘存储空间不足</option>
				<option value="download-failure">下载任务失败</option>
				<option value="mount-failure">SD卡挂载失败</option>
				<option value="md5-wrong">MD5码校验错误</option>
				<option value="play-video-error">视频播放出错</option>
				<option value="play-image-error">图片播放出错</option>
				<option value="illegal-login">非法连接</option>
			</select> <label for="warning_status">状态</label> <select name="info.status"
				id="warning_status">
				<option value="waiting_for_processing">待处理</option>
				<option value="processed">已处理</option>
				<option value="">所有</option>
			</select> <input type="submit" value="查询" id="query" />
		</fieldset>
	</form>
</div>
<hr></hr>
<div class="operate-menus">
	<input type="button" name="delete" value="删除" id="delete-button" /><span></span>
	<input type="button" name="process" value="处理" id="process-button" /><span></span>
</div>
<table class="linetable">
	<thead>
		<tr>
			<th class="row-select"><input type="checkbox" name="check-all"
				id="check-all"></th>
			<th class="same-width">MAC地址</th>
			<th class="same-width">告警类型</th>
			<th class="same-width">告警内容</th>
			<th class="same-width">状态</th>
			<th class="same-width">告警时间</th>
		</tr>
	</thead>
</table>
<div id="info_container" class="scroll-container">
	<table class="linetable">
		<tbody>
		</tbody>
	</table>
</div>
<div id="detail_dialog">
	<table class="linetable">
		<tbody>

		</tbody>
	</table>
</div>
<script type="text/javascript">
Array.prototype.each = function(func){
	return function(array, fun){
		for(var i =0; i < array.length; i++){
			fun(array[i]);
		}
	}(this, func);
}
	var warningType = "${info.warningType}";
	var warningStatus = "${info.status}";
	var root = "<%=request.getContextPath()%>";
	var currentPage = 1;
	var noMoreData = false;
	var datas = [];
	
	var rowTemplate = "<tr class='content-row'><td class='row-select'><input type='checkbox' name='infoId' value='{id}' class='info-row'/></td><td class='same-width'>{macLink}</td><td class='same-width'>{typeLink}</td><td class='same-width'><a onclick='{details}'>详细</a></td><td class='same-width'>{status}</td><td class='same-width'>{date}</td></tr>";
	var macHrefTemplate = "<a href='"+root+"/warning-info!byMac.action?info.stbMac={mac}'>{mac}</a>";
	var typeHrefTemplate = "<a href='"+root+"/warning-info!byType.action?info.warningType={type}'>{typeCn}</a>";

	$(function(){
			$("")
			$("#query").button();
			$("#delete-button").button().live("click", function(){
				$(".info-row").filter(function(){return $(this).attr("checked")}).each(function(e){
					var id = $(this).val();
					var tr = $(this).parent().parent();
					if(confirm("确定删除此条告警信息吗?")) {
						$.ajax({
							url: root+"/warning-info!delete.action?info.id="+id,
							type:"GET",
							success:function(res, status, xhr){
								//remove a row, no reload;
								tr.remove();
							}
						});
					}
				})
				/*reload
				$("#info_container tbody").empty();
				currentPage = 1;
				loadWarningInfos(currentPage++)
				*/
			});
			$("#process-button").button().live("click", function(){
				$(".info-row").filter(function(){return $(this).attr("checked")}).each(function(e){
					var id = $(this).val();
					var tr = $(this).parent().parent();
					$.ajax({
						url: root + "/warning-info!process.action?info.id="+id,
						type: "GET",
						success: function(res, status, xhr){
							var data = eval('('+res+')');
							var newRow = generateRow(data[0]);
							tr.replaceWith(newRow);
							$("#check-all").attr("checked", false);
						}
					});
					/*reload
					$("#info_container tbody").empty()
					currentPage = 1;
					loadWarningInfos(currentPage++);*/
				})
			});
			var typeOptions = $("#warning_type option")
			typeOptions.each(function(index){
				if(typeOptions[index].value == warningType){
					$(typeOptions[index]).attr("selected", "selected");
					return ;
				}
			});
			var statusOptions = $("#warning_status option");
			statusOptions.each(function(index){
				if(statusOptions[index].value == warningStatus){
					$(statusOptions[index]).attr("selected", "selected");
					return ;
				}
			});
			$("#check-all").live("click", function(){
				if($("#check-all").attr("checked")){
					$(".info-row").attr("checked", true);
				}else{
					$(".info-row").removeAttr("checked");
				}
			});
			$(".info-row").live("click", function(e){
				var checkboxNumber = $(".info-row").length;
				var checkedNumber = $(".info-row").filter(function(e){return $(this).attr("checked")}).length;
				if(checkboxNumber == checkedNumber){
					$("#check-all").attr("checked", true);
				}else{
					$("#check-all").removeAttr("checked");
				}
				e.stopPropagation();
			});
			$("#info_container").endlessScroll({
				fireOnce: false,
				bottomPixels: 10,
				insertAfter: "div .operate-menus",
				loader: "<div class='loading'>loading</div>",
				callback: function(i){
					loadWarningInfos(currentPage++);
				}
			});
			loadWarningInfos(currentPage++);
		});
	var loadWarningInfos = function(page){
		var dataurl = root + "/warning-info!data.action?" + $("#query_info_form").serialize() + "&info.page=" + page;
		if(!noMoreData){
			$("#info_container").queue("data-tasks", function(next){
				$.ajax({
					url:dataurl, 
					type: "GET", 
					async: false, 
					success: function(data, status, xhr){
						var array = eval('(' + data + ')');
						//datas = array;
						if(array.length > 0){
							array.each(function(ele){
								datas.push(ele);
									var row = generateRow(ele);
									$("#info_container tbody").append(row);
									console.log("append rows with page : " + currentPage);
							});
							//currentPage++;
						}else{
							noMoreData = true;
							currentPage--;
						}
					}
				});
				next();
			});
			$("#info_container").dequeue("data-tasks")
		}
	}

	var generateRow = function(ele){
		var macHref = macHrefTemplate.replace("{mac}", ele.stbMac).replace("{mac}", ele.stbMac);
		var typeHref = typeHrefTemplate.replace("{type}", ele.warningType).replace("{typeCn}", ele.warningTypeCn);
		var row = rowTemplate.replace("{macLink}", macHref).replace("{typeLink}", typeHref).replace("{details}", "showDetails("+ele.id+")").replace("{date}", ele.createdAt).replace("{id}", ele.id).replace("{status}", ele.statusCn);
		return row;
	}
	var showDetails = function(detail_id){
			var noDetail = true;
			datas.each(function(record){
				if(record.id == detail_id && record.details != ""){
					noDetail = false;
					var pairs = record.details.split("\t");
					$("#detail_dialog > table > tbody").empty();
					pairs.each(function(pair){
						var arr = pair.split("#");
						//lines.push("<label>{key}</label>:<label>{value}</label><p>".replace("{key}", arr[0]).replace("{value}", arr[1]));
						$("#detail_dialog > table > tbody").append(
								"<tr><td><label>{key}:</label></td><td><label>{value}</label></td></tr>".
								replace("{key}", arr[0]).replace("{value}", arr[1]));
					});
					//$("#detail_dialog").append(lines.toString());
					$("#detail_dialog").dialog({title:'告警详细内容 ',close: function(event, ui){$("#detail_dialog > table > tbody").empty()}}).dialog("open");
					return;
				}
			});
			if(noDetail){
				//lines.push();
				$("#detail_dialog tbody").append("<tr><td colspan='2'><label>无内容</label></td></tr>")
				$("#detail_dialog").dialog({modal:true, close: function(event, ui){$("#detail_dialog tbody").empty()}}).dialog("open")
			}
		}
</script>
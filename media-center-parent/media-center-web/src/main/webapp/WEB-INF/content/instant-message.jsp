<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="taglib.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.endless-scroll.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/aries.tiger.js"></script>
<form action="<%=request.getContextPath() %>/instant-message!send.action" method="post">
<table class="linetable text-left">
	<tr>
		<td width="25%"><label for="content">消息内容:</label></td>
		<td width="25%"><textarea name="messageContent" id="content" cols="30" rows="5"></textarea></td>
		<td width="25%"></td>
		<td width="25%"></td>
	</tr>
	<tr>
		<td>
			<label>字体:</label>
		</td>
		<td>
			<select name="textFont" id="text_font">
				<option value="wenquanyi" class="wenquanyi">文泉驿</option>
				<option value="fangsong" class="fangsong">仿宋</option>
				<option value="lishu" class="kaiti">隶书</option>
			</select>
		</td>
		<td></td><td></td>
	</tr>
	
	<tr>
		<td><label>背景色:</label></td>
		<td>
			<div id="background_color" class="color-selector">
				<input type="hidden" name="backgroundColor" value="#00ff00" id="select_background_color"></input>
				<div style="background-color: #00ff00"></div>
			</div>
		</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
	<td><label>文本色:</label></td>
		<td>
			<div id="text_color" class="color-selector">
				<input type="hidden" name="textColor" value="#0000ff" id="select_text_color"></input>
				<div style="background-color: #0000ff"></div>
			</div>
		</td>
		<td></td>
		<td></td>
	</tr>
	<!-- tr>
		<td><label>出入特效:</label></td>
		<td>
			<select id="play_effect" name="play_effect">
				<option value=""></option>
				<option value=""></option>
				<option value=""></option>
			</select>
		</td>
	</tr-->
	<tr>
	<td><label>屏幕尺寸:</label></td>
		<td>
			<select id="screen_size" name="screenSize">
				<option value="720x480">720x480</option>
				<option value="1280x720">1280x720</option>
				<option value="undefined">自定义</option>
			</select>
		</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>
			<label>播放区域:(播放文本区域为绿色，拖拽以改变区域位置和大小。)</label>
		</td>
		<td colspan="3">
			<div class="background-screen" id="background_screen">
				<div id="text_area" class="front-end-area"></div>
			</div>
			<div>起点:[<input type="button" name="start_x" id="start_x">%,<input type="button" name="start_y" id="start_y">%]
				或：[<input class="four-char-wide" type="text" name="start_x_pixels" id="start_x_pixels">px,<input class="four-char-wide" type="text" name="start_y_pixels" id="start_y_pixels">px]
			</div>
			<div>终点:[<input type="button" name="end_x" id="end_x"/>%,<input type="button" name="end_y" id="end_y"/>%]
				或：[<input class="four-char-wide" type="text" name="end_x_pixels" id="end_x_pixels">px,<input class="four-char-wide" type="text" name="end_y_pixels" id="end_y_pixels">px]
			</div>
		</td>
	</tr>
	<tr>
		<td><label for="play_times">播放次数:</label></td>
		<td><input type="text" name="playTimes" id="play_times" /></td>
		<td></td>
		<td><input type="button" value="发送！" id="send_message" class="center"></input></td>
	</tr>
</table>
<table class="linetable five-columns">
	<thead>
		<tr>
			<th><input type="checkbox" id="check_all"></input><label>终端MAC地址</label></th>
			<th><label>一级分组</label><select id="level_one_group"></select></th>
			<th><label>二级分组</label><select id="level_two_group"></select></th>
			<th><label>自编号</label></th>
			<th><label>车编号</label></th>
		</tr>
	</thead>
</table>
<table class="linetable five-columns" id="stbs_table">
	<thead>
		<tr>
			<td width="20%"></td>
			<td width="20%"></td>
			<td width="20%"></td>
			<td width="20%"></td>
			<td width="20%"></td>
		</tr>
	</thead>
	<tbody>
		
	</tbody>
</table>
</form>
<script type="text/javascript">

var site_root = "<%=request.getContextPath()%>";
var current_level_two_group;
var current_stb_addresses;
var currentPage = 1;
var option_reg = "<option value='#value' class='#level'>#text</option>";
var checkbox_reg = "<tr><td style='align=\"left\"'><label id='#id'><input type='checkbox' name='stbMacs' value='#value' class='#class'/>#text<label></td></tr>";
jQuery.ajaxSetup({contentType:"application/x-www-form-urlencoded; charset=UTF-8"});

function colorPickerInit(selector_id, input_id){
	$("#" + selector_id).ColorPicker({
		onShow: function(el){
			$(el).fadeIn(500);
			return false;
		},
		onHide: function(el){
			$(el).fadeOut(500);
			return false;
		},
		onSubmit: function(hsb, hex, rgb, el){
			$("div", el).css("backgroundColor", "#" + hex);
			$("#" + input_id).val("#" + hex);
			changeTextAreaCss();
		}
	});
}
function changeTextAreaCss(){
	$("#content").css("backgroundColor", $("#select_background_color").val());
	$("#content").css("color", $("#select_text_color").val());
}

function refreshMinWidthAndHeight(top, left){
	var text_area = $("#text_area");
	var newHeight = 150 - top;
	
	var newWidth = 200 - left;
	
	$("#text_area").resizable("option", "maxHeight", newHeight);
	$("#text_area").resizable("option", "maxWidth", newWidth);
	$("#text_area").resizable().trigger("start");
	//$("#text_area").draggable({containment: "parent"});
}

function refreshInputCoordinates(){
	var top = $("#text_area").css("top");
	var left = $("#text_area").css("left");
	if(top == "auto"){
		top = "0";
	}
	if(left == "auto"){
		left = "0";
	}
	var start_x = left.replace("px","")/2.0;
	var start_y = top.replace("px","")/1.5;
	var end_x = (left.replace("px", "")*1 + $("#text_area").css("width").replace("px","")*1)/2.0;
	if(end_x > 100){
		end_x = 100;
		//alert((200 - left.replace("px", "")) + "px")
		$("#text_area").width((200 - left.replace("px","")) + "px");
	}
	var end_y = (top.replace("px", "")*1 + $("#text_area").css("height").replace("px","")*1)/1.5;
	if(end_y > 100){
		end_y = 100;
		$("#text_area").height((150 - top.replace("px", "")) + "px");
	}

	$("#start_x").val(start_x);
	$("#start_y").val(start_y);
	$("#end_x").val(end_x);
	$("#end_y").val(end_y);

	var screen_size = $("#screen_size option:selected").val().split("x");
	var screen_width = screen_size[0];
	var screen_height = screen_size[1];
	
	$("#start_x_pixels").val(Math.floor(start_x*screen_width/100));
	$("#start_y_pixels").val(Math.floor(start_y*screen_height/100));
	$("#end_x_pixels").val(Math.floor(end_x*screen_width/100));
	$("#end_y_pixels").val(Math.floor(end_y*screen_height/100));
}

function initCoordinates(){
	$("#start_x, #start_y, #end_x, #end_y").button();
	//$("#start_x, #start_y, #end_x, #end_y, #start_x_pixels, #start_y_pixels, #end_x_pixels, #end_y_pixels").button();
	refreshInputCoordinates();
}

var start_point_relative_position = {"top":0, "left":0, "screen_top":0, "screen_left":0}

var sendMessage = function(){
	var messageContent = $("#content").val();
	var playTimes = $("#play_times").val();
	var groupId = $("#level_two_group").val();
	var textFont = $("#text_font").find("option:selected").val();
	var number_reg = /^[1-9]+\d*$/;
	if(!number_reg.test(playTimes)){
		alert("请输入正确的播放次数，要求是正整数");
		return false;
	}
	var macs = $("[name='stbMacs'][class='stb_row']:checked");
	if(macs.length == 0){
		alert("请选择机顶盒!");
		return false;
	}
	var stbMac = "";
	for(var i = 0; i < macs.length; i++){
		stbMac += macs[i].value;
		stbMac += ",";
		//stbMac.push(macs[i].value);
	}
	stbMac = stbMac.substring(0, stbMac.length - 1);
	var url = site_root + "/instant-message!send.action";
	var params = {
			"message.messageContent":messageContent,
			"message.playTimes": playTimes,
			"message.groupId": groupId,
			"message.stbMac": stbMac,
			"message.textFont": textFont,
			"message.backgroundColor": $("#select_background_color").val(),
			"message.textColor": $("#select_text_color").val(),
			"message.startX": $("#start_x_pixels").val(),
			"message.startY": $("#start_y_pixels").val(),
			"message.endX": $("#end_x_pixels").val(),
			"message.endY": $("#end_y_pixels").val(),
			};
	$.ajax({
			url: url,
			type: "POST",
			data: params,
			success: function(data){
				alert("发送成功！");
			},
			error: function(){
				alert("发送失败，请重试！");
			}
		}
	);
}

var isNumber = function(num){
	return /$\d+/.test(num);
}
// onload
$(function(){

	$("#screen_size").live("change", function(){
		if($("#screen_size option:selected").val() == "undefined"){
			var reg = /^([1-9])+\d*[xX]([1-9])+\d*$/;
			var newSize = "800x600";
			do{
				newSize = window.prompt("请指定屏幕宽高，以x分隔", newSize);
			}while(!reg.test(newSize));
			var lastOption = $("#screen_size option:selected").clone();
			$("#screen_size option:selected").val(newSize).text(newSize).after(lastOption);
		}
		refreshInputCoordinates();
	});
	colorPickerInit("background_color", "select_background_color");
	colorPickerInit("text_color", "select_text_color");
	
	changeTextAreaCss();
	var screen = $("#background_screen");
	$("#text_area").draggable(
		{
			containment:screen,
			stop: function(event, ui){
				refreshMinWidthAndHeight(ui.position.top, ui.position.left)
				start_point_relative_position.top = ui.position.top;
				start_point_relative_position.left = ui.position.left;
				refreshInputCoordinates();
			}
		}
	).resizable(
		{
			//containment: screen,
			minWidth: 10, 
			minHeight: 5, 
			maxWidth: 200, 
			maxHeight:150,
			stop: function(event, ui){
				$("#text_area").css("position", "relative").css("top", start_point_relative_position.top).css("left", start_point_relative_position.left);
				refreshInputCoordinates();
			},
		}
	);
	//$("#text_area").resizable({minWidth: 10, minHeight: 5, containment: "parent"});
	initCoordinates();

	/**dirty and ugly**/
	$("#start_x_pixels").live("change", function(){
		var screenSize = $("#screen_size option:selected").val().split("x");
		var screenWidth = screenSize[0];
		var screenHeight = screenSize[1];
		var value = parseInt($("#start_x_pixels").val());
		if(isNaN(value) || value > screenWidth || value < 0){
			alert("Not a valid number.");
			refreshInputCoordinates();
		}else{
			var newLeft = parseInt(value*200/screenWidth);
			var oldWidth = $("#text_area").css("width").replace("px","");
			$("#text_area").css("left", newLeft+"px");
			$("#start_x").val(value*100/screenWidth);
			if(oldWidth > (200-newLeft)){
				$("#text_area").css("width", (200-newLeft)+"px");
			}
			$("#text_area").resizable("option", "maxWidth", 200-value);
		}
		//refreshInputCoordinates();
	});
	$("#start_y_pixels").live("change", function(){
		var screenSize = $("#screen_size option:selected").val().split("x");
		var screenWidth = screenSize[0];
		var screenHeight = screenSize[1];
		var value = parseInt($("#start_y_pixels").val());
		if(isNaN(value) || value > screenHeight || value < 0){
			alert("Not a valid number.");
			refreshInputCoordinates();
		}else{
			var newTop = parseInt(value*150/screenHeight);
			var oldHeight = $("#text_area").css("height").replace("px", "");
			$("#text_area").css("top", newTop+"px");
			$("#start_y").val(value*100/screenHeight);
			//if(oldHeight > (150-newTop)){
			$("#text_area").css("height", (150-newTop)+"px");
			//}
			$("#text_area").resizable("option", "maxHeight", 150-value);
			//$("#text_area").resizable().trigger("start");
		}
		//refreshInputCoordinates();
	});
	$("#end_x_pixels").live("change", function(){
		var screenSize = $("#screen_size option:selected").val().split("x");
		var screenWidth = screenSize[0];
		var screenHeight = screenSize[1];
		var value = parseInt($("#end_x_pixels").val());
		if(isNaN(value) || value > screenWidth || value < 0){
			alert("Not a valid number.");
			refreshInputCoordinates();
		}else{
			var left = $("#text_area").css("left").replace("px", "");
			$("#text_area").css("width", parseInt(value*200/screenWidth - left)+"px");
			$("#end_x").val(value*100/screenWidth);
		}
		//refreshInputCoordinates();
	});
	$("#end_y_pixels").live("change", function(){
		var screenSize = $("#screen_size option:selected").val().split("x");
		var screenWidth = screenSize[0];
		var screenHeight = screenSize[1];
		var value = parseInt($("#end_y_pixels").val());
		if(isNaN(value) || value > screenHeight || value < 0){
			alert("Not a valid number.");
			refreshInputCoordinates();
		}else{
			var top = $("#text_area").css("top").replace("px", "")
			$("#text_area").css("height", parseInt(value*150/screenHeight - top)+"px");
			$("#end_y").val(value*100/screenHeight);
		}
		//refreshInputCoordinates();
	});
	/*
	$("#font_size").live("change", function(){
			$("#content").css("font-size", $(this).val());
		});*/
	$("#text_font").live("change", function(){
			$("#content").css("font-family", $(this).find("option:selected").text());
		});

	$("#send_message").button().live("click", sendMessage);
	
	$("#level_one_group").live("change", function(){
		$("#level_one_group option").each(function(index, option){
			if($(this).attr("selected")){
				//reloadLevelTwoOptions($(this).val());
				//load level two group.
				var url = site_root + "/group!getGroup.action";
				
				var params = {"typeId":$(this).val()};
				$.ajax({
					url: url,
					type: "POST",
					data: params,
					success: function(jdata, status, xhr){
						var jsonStr = JSON.parse(jdata);
						var options = "<option value='-1'>所有</option>";
						for(key in jsonStr){
							var option = "<option value='"+key+"'>"+jsonStr[key]+"</option>";
							options += option;
						}
						$("#level_two_group").empty();
						$("#level_two_group").append(options);
						$("#level_two_group").trigger("change");
					}
				})
			}
		})
	});
	
		$("#stbs_table body").endlessScroll({
			fireOnce: false,
			bottomPixels: 10,
			loader: "<div class='loading'>loading</div>",
			callback: function(i){
				loadStbs(currentPage++);
			}
		});
		
		$("#level_two_group").live("change", function(){
				$("#stbs_table tbody").empty();
				$("#check_all").removeAttr("checked");
				var currentPage = 1;
				loadStbs(currentPage++);
			}
		);

		$("#check_all").live("click", function(){
			var checkedNumber = $("#stbs_table input:checked").length;
			var checkboxNumber = $("#stbs_table input:checkbox").length;
			if(checkedNumber == checkboxNumber){
				$(".stb_row").removeAttr("checked");
			}else{
				$(".stb_row").attr("checked", true);
			}	
		});
		$(".stb_row").live("click", function(){
			var checkedNumber = $("#stbs_table input:checked").length;
			var checkboxNumber = $("#stbs_table input:checkbox").length;
			if(checkedNumber == checkboxNumber){
				$("#check_all").attr("checked", true);
				return;
			}
			if(checkedNumber == 0){
				$("#check_all").removeAttr("checked");
				return ;
			}
		})
		
		/**
		* init level one group
		*/
		$.ajax({
				url: site_root+"/group!getType.action",
				type: "POST",
				async: false,
				success: function(level_one){
					var options = "";
					var jdata = JSON.parse(level_one);
					for(key in jdata){
						var option = "<option value='"+key+"'>"+jdata[key]+"</option>";
						options += option;
					}
					$("#level_one_group").append(options);
				}
			}
		);
		$("#level_one_group").trigger("change");
		
	}
);


var loadStbs = function(page){
	var level_one_value = $("#level_one_group").val();
	var level_two_value = $("#level_two_group").val();
	
	var url = site_root + "/stb!listOnlinesByType.action";
	var params = {"typeId": level_one_value, "groupId": level_two_value, "page":page};
	// page reset, get first page of stbs
	// render
	$.ajax({
			url: url,
			async: false,
			data: params,
			type: "POST",
			success: function(stb_addresses){
				var jdatas = eval('('+stb_addresses+')');
				var groupOneText = $("#level_one_group option:selected").text();
				var groupTwoText = $("#level_two_group option:selected").text();
				var trs = "";
				jdatas.forEach(function(ele){
					var tdMac = "<td><input type='checkbox' name='stbMacs' class='stb_row' value='"+ele.stbMac+"'><label>"+ele.stbMac+"</lable></td>";
					var tdGroupOne = "<td><label>"+groupOneText+"</label></td>";
					var tdGroupTwo = "<td><label>"+ele.groupName+"</label></td>";
					var tdShopNo = "<td><label>"+ele.shopNo+"</label></td>";
					var tdShopName = "<td><label>"+ele.shopName+"</label></td>";
					var tr = "<tr>"+tdMac+tdGroupOne+tdGroupTwo+tdShopNo+tdShopName+"</tr>";
					trs += tr;
				})
				$("#stbs_table tbody").append(trs);
			}
		}
	);
}

</script>
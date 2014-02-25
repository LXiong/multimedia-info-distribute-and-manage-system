<style>
#dialog label,#dialog input {
	display: block;
}

#dialog label {
	margin-top: 0.5em;
}

#dialog input,#dialog textarea {
	width: 95%;
}

#layouts {
	margin-top: 1em;
}

#layouts li .ui-icon-close {
	float: left;
	margin: 0.4em 0.2em 0 0;
	cursor: pointer;
}

#add_tab {
	cursor: pointer;
}
</style>
<script type="text/javascript">
function Init(){
    //time_bucket tabs
    var tab_counter = 1
	var p_tabs = $("#layouts").tabs({
		tabTemplate: "<li><a href='#{href}'>#{label}</a><span class='ui-icon ui-icon-close'>Remove Tab</span></li>",
		show: function(event, ui){
			if($(ui.panel).children(".layout_type").length == 0){
			    $(ui.panel).load('<%=request.getContextPath()%>/policy/1/layouts/create', {}, function(){load_areas.call(this)})
			}
		},
		remove: function(event, ui){
			//delete time_bucket
		}
	})
	function load_areas(event, req, settings){
		    var select = $(this).children(".layout_type").children("select")
		    var layout_type = select.attr("value")
		    var layout_base_url = select.siblings("input").first().val()
		    var layout_url = layout_base_url + "/areas"
		    
			//load areas
			$(this).children(".layouts_container").first().load(layout_url, {layout_type:layout_type}, function(){load_play_list.call(this)})
			
	}
	function load_play_list(event, req, settings){
		//"this" is the ".layout_container"
		$(".area", this).each(function(){
			$(this).load('<%=request.getContextPath()%>/policies/play_list', {}, function(){fill_media_files.call(this)})
		})
	}
	function fill_media_files(){
		var area_media_files = $(this).children(".play_list").first().children(".media_files").first()
		
		//if(area_media_files.children().length == 0){
		    area_media_files.empty()
			//global media files
			$("#media_files_area").children("label").each(function(){
				var rand = Math.random()
				var name = $(this).text()
				var value = $(this).attr("for")
				var id = value + rand
				var check_box = '<input type="checkbox" name="' + name + '" value="' + value + '" id="' + id + '"></input>'
				var label = "<label for='" + id + "'>" + name + "</label>"
				area_media_files.append(check_box + label)
			})
		//}
	}
	function change_areas(){
		load_areas.call($(this).parent())
	}
	function add_tab(){
	    $("#layouts").tabs("add", "#time-bucket-" + tab_counter, "time-bucket-" + tab_counter)
		tab_counter++
	}
	
	$("#add_tab").button().click(function(){
		add_tab()
	})
		
	$("#layouts span.ui-icon-close").live("click", function(){
		var index = $("li", p_tabs).index($(this).parent());
		$("#layouts").tabs("remove", index)
	})
	
	$("#add_tab").trigger("click")
	$("#layouts").tabs("select", 0)
	
	//$("#policy_common_fields").load('<%=request.getContextPath()%>/policy/1/common_fields');

	//dialog
	var file_dialog = $("#file_dialog").dialog({
	open: function(event, ui){
		$("#files_box").ajaxComplete(function(event, req, settings){
			if(settings.url == '<%=request.getContextPath()%>/policy/1/media_files'){
			    selected_paths = $("#file_paths").text()
				//alert(settings.url)
				$("#files_box input:checkbox").each(function(){
					if(selected_paths.match($(this).attr("value"))){
					    $(this).attr("checked")
					}
				})
			}
		})
		$("#files_box").load('<%=request.getContextPath()%>/policy/1/media_files')
	},
	autoOpen: false,
	buttons: {
		"OK": function(){
	    		$("#media_files_area").empty()
	    		var name_value_pairs = []
				var file_path_text = ""
				$("#files_box input:checkbox[checked]").each(function(){
					var name = $(this).attr("name")
					var value = $(this).attr("value")
					name_value_pairs.push({name:name,value:value})
					var check_box = '<input type="checkbox" name="' + name + '" value="' + value + '" id="' + value + '"></input>'
					var label = '<label for="' + value + '">' + name + '</label>'
					$("#media_files_area").append(label)
				    file_path_text = file_path_text.concat($(this).attr("value"), ",")
				})
				//update all play_list
				$(".media_files").each(function(){
				    $(this).empty()
					var rand = Math.random()
					for(var i = 0; i < name_value_pairs.length; i++){
						var name = name_value_pairs[i].name
						var value = name_value_pairs[i].value
						var in_list_box_and_label = '<input type="checkbox" name="' + name + '" value="' + value + '" id="' + value+rand + '"></input>'
						in_list_box_and_label += '<label for="' + value+rand + '">' + name + '</label>'
						$(this).append(in_list_box_and_label)
					}
				})
				$("#file_paths").text(file_path_text)
				$(this).dialog("close")
			},
		"CANCEL": function(){
			    $(this).dialog("close")
			}
	}
});

$("#global_play_list").button().click(function(){
file_dialog.dialog("open")
})
	
}
$(Init)
function add_play_list(btn){
    var old_play_list = $(btn).siblings(".play_list").first()
    var new_play_list = old_play_list.clone()
    old_play_list.after(new_play_list)
}

</script>

<div id="policy">
	<div class="common_fields" id="policy_common_fields">
		<div id="file_dialog"><textarea rows="20" cols="30"
		style="display: none" id="file_paths"></textarea>
			<form>
			<fieldset id="files_box"></fieldset>
			</form>
		</div>
		<div>
			<label for="policy_name">PolicyName</label>
			<input name="policy_name" id="policy_name" type="text" />
			<input name="select_file" type="button" value="add_file"
			id="global_play_list" />
		</div>
		<span id="media_files_area" style="display: none"></span>
	</div>
	<div id="add_tab">Add tab</div>
	<div id="layouts">
		<ul id="tabs_container">
		</ul>
	</div>
</div>
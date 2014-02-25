var site_root_str;
var tab_counter = 1;
var play_list_tab_counter = 1;
var audio_files = [];
var video_files = [];
var picture_files = [];
function Init(root){
    // time_bucket tabs
    
    var site_root = root;
    site_root_str = root;
    var p_tabs = $("#layouts").tabs({
        tabTemplate: "<li><a href='#{href}'>#{label}</a><span class='ui-icon ui-icon-close time_bucket'>Remove Tab</span></li>",
        show: function(event, ui){
            if ($(ui.panel).children(".layout_type").length == 0) {
                var policy_id = $(this).closest("#policy").children(".policy_id").first().val();
                var create_time_bucket_url = site_root +
                '/policy/' +
                policy_id +
                '/time_buckets/create';
                $(ui.panel).load(create_time_bucket_url, {}, function(){
                    $(".time", this).each(function(){
                        $(this).datetime({
                            format: "yy-mm-dd hh:ii:00"
                        })
                    });
                    $("select[name*='layout_mode']", this).trigger("change");
                });
            }
        },
        remove: function(event, ui){
            // delete time_bucket
            var policy_id = $("#policy").children(".policy_id").first().val();
            var time_bucket_id = $(".time_bucket_id", ui.panel).first().val();
            if (time_bucket_id != undefined) {
                var delete_time_bucket_url = site_root + "/policy/" +
                policy_id +
                "/time_bucket/" +
                time_bucket_id;
                $.ajax({
                    url: delete_time_bucket_url,
                    type: "DELETE"
                });
            }
        }
    });
    
    $("#layouts").tabs("paging", {
        cycle: true,
        follow: true
    });
    
    // function change_areas() {
    // load_areas.call($(this).parent())
    // }
    
    $("#add_tab").button().click(function(){
        add_tab();
    });
    
    // remove time bucket
    $("#layouts span.ui-icon-close.time_bucket").live("click", function(){
        var index = $("li", p_tabs).index($(this).parent());
        $("#layouts").tabs("remove", index);
    });
    
    // change layout
    $(".layout_type [name*='layout_mode']").live("change", function(){
        var policy_id = $("#policy .policy_id").first().val();
        var time_bucket_id = $(this).closest(".layout_type").siblings(".time_bucket_id").first().val();
        var areas_url = site_root + "/policy/" + policy_id +
        "/time_bucket/" +
        time_bucket_id +
        "/areas/create";
        var layout_container = $(this).closest(".layout_type").siblings(".layouts_container").first();
        var layout_type = $(this).val();
        
        layout_container.load(areas_url, {
            layout_type: layout_type
        }, function(){
            load_play_list.call(this);
        });
    });
    
    // change media type: audio, video, picture, text...
    $(".switch_media_type").live("change", function(){
        var media_type = $(this).val();
        if (media_type == "text") {
            $(".media_files", $(this).closest(".area")).each(function(){
                $(this).empty();
                $(this).append("<label>TextMedia:</label><textarea name='media_files' rows='10' cols='20'/>");
            });
        }
        else {
            var files = [];
            if (media_type == "audio") {
                files = audio_files;
            }
            else 
                if (media_type == "video") {
                    files = video_files;
                }
                else 
                    if (media_type == "picture") {
                        files = picture_files;
                    }
            $(".media_files", $(this).closest(".area")).each(function(){
                $(this).empty();
                $(this).append(random_checkbox(files));
            });
        }
        
    });
    
    // remove a play list
    $(".play_list_tab span.ui-icon-close.play_list").live("click", function(){
        var play_list_tabs = $(this).closest(".play_list_tabs_container");
        var index = $("li", play_list_tabs).index($(this).parent());
        play_list_tabs.tabs("remove", index);
    });
    
    // change play mode of playlist
    $(".select_play_mode").live("change", function(){
        if ($(this).val() == "timing") {
            $(this).parent().siblings(".start_and_end").first().attr("style", "display:block");
        }
        else {
            $(this).parent().siblings(".start_and_end").first().attr("style", "display:none");
        }
    })
    
    // save a policy
    $("#save_policy").button().click(function(){
        // alert(time_check())
        var is_valid = common_field_check() && time_check();
        is_valid = true;
        if (is_valid) {
            update_policy();
        }
        else {
            alert("invalid.");
        }
    })
    
    $("#add_tab").trigger("click");
    $("#layouts").tabs("select", 0);
    $(".time").each(function(){
        $(this).datetime({
            format: "yy-mm-dd hh:ii:00"
        });
    });
    
    // dialog
    var file_dialog = $("#file_dialog").dialog({
        width: 500,
        height: 500,
        open: function(event, ui){
            // alert("fuck")
            // var policy_id = $("#policy").children(".policy_id").first().val()
            var media_files_url = site_root + "/videos"
            // jqgrid
            $("#media_file_list").jqGrid({
                url: media_files_url,
                datatype: "json",
                colNames: ["CODE", "FILENAME", "TAG", "DESCRIPTION", "TYPE"],
                colModel: [{
                    name: "code",
                    index: "code",
                    width: 90,
                    search: false
                }, {
                    name: "filename",
                    index: "fileName",
                    width: 90,
                    search: false
                }, {
                    name: "tag",
                    index: "tag",
                    width: 90
                }, {
                    name: "description",
                    index: "description",
                    width: 90,
                    search: false
                }, {
                    name: "type",
                    index: "type",
                    width: 90,
                    search: false
                }],
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: "#media_file_pager",
                autowidth: true,
                sortname: 'filename',
                viewrecords: true,
                sortorder: "asc",
                multiselect: true,
                hidegrid: false,
                editurl: media_files_url,
                catption: "SelectMediaFiles",
                sopt: ["cn"]
            });
            $("#media_file_list").jqGrid('navGrid', '#media_file_pager', {
                edit: false,
                add: false,
                del: false
            }, {}, {}, {}, {
                sopt: ["cn"]
            });
            
        },
        autoOpen: false,
        buttons: {
            "OK": function(){
                var checked_file_codes = classify_media_files();
                report_media_files_used(checked_file_codes);
                $(this).dialog("close");
            },
            "CANCEL": function(){
                $(this).dialog("close");
            }
        }
    });
    function classify_media_files(){
        audio_files = [];
        video_files = [];
        picture_files = [];
        var file_codes = [];
        var ids = $("#media_file_list").jqGrid("getGridParam", "selarrrow")
        for (var i = 0; i < ids.length; i++) {
            var file = $("#media_file_list").jqGrid("getRowData", ids[i]);
            file_codes.push(file["code"]);
            if (file["type"] == "audio") {
                audio_files.push(file);
            }
            else 
                if (file["type"] == "video") {
                    video_files.push(file);
                }
                else 
                    if (file["type"] == "picture") {
                        picture_files.push(file);
                    }
        }
        return file_codes;
    }
    
    function report_media_files_used(file_code_arr){
        var policy_id = $("#policy").children(".policy_id").first().val();
        var report_url = site_root + "/policy/" + policy_id + "/media_file/use";
        $.post(report_url, {
            code: file_code_arr
        }, function(data){
			var play_list_url = site_root + "/policies/play_list";
			$(".play_list_tabs_container").each(function(){
				var area_media_type = $(this).closest(".area").children("[name='media_type']").first().val();
				$(this).children("div").each(function(){
					$(this).load(play_list_url, {media_type: area_media_type}, function(){
					    $(".time", this).each(function(){
						$(this).datetime({
					            format: "yy-mm-dd hh:ii:00"
					        })
					    })
					});
				})
			})
        })
    }
    
    function common_field_check(){
        return true;
    }
    function time_check(){
        if (!all_time_input_filled()) {
            return false;
        }
        var time_bucket_cover_policy = true;
        var play_list_cover_time_bucket = true;
        var policy_start_time_str = $("[name='start_time']", $("#policy_common_fields")).first().val();
        var policy_end_time_str = $("[name='end_time']", $("#policy_common_fields")).first().val();
        
        var policy_time = {
            "start": get_date_from_str(policy_start_time_str),
            "end": get_date_from_str(policy_end_time_str)
        }
        
        var time_buckets_time = [];
        $("[id|='time-bucket']").each(function(){
            var temp = $(".layout_type", this).first();
            var time_bucket_start_str = $("[name='start_time']", temp).first().val();
            var time_bucket_end_str = $("[name='end_time']", temp).first().val();
            var time_bucket_time = {
                "start": get_date_from_str(time_bucket_start_str),
                "end": get_date_from_str(time_bucket_end_str)
            }
            // check each play list of each area
            $(".area", this).each(function(){
                var modes = [];
                var play_lists_time = [];
                
                $("[name='play_mode']", this).each(function(){
                    modes.push($(this).val());
                    var list_temp = $(this).closest(".play_list_panel");
                    var list_start_str = $("[name='start_time']", list_temp).first().val();
                    var list_end_str = $("[name='end_time']", list_temp).first().val();
                    play_lists_time.push({
                        "start": get_date_from_str(list_start_str),
                        "end": get_date_from_str(list_end_str)
                    })
                })
                if (!contains(modes, "loop")) {
                    if (!is_cover(play_lists_time, time_bucket_time)) {
                        play_list_cover_time_bucket = false;
                        // select the
                        // time_bucket
                        var index = $("#layouts").children("div").index($(this).closest("[id|='time-bucket']"));
                        $("#layouts").tabs("select", index);
                        $(this).addClass("error_red");
                        alert("time of play list in this area doesn`t cover this time bucket, please check. ");
                    }
                    else {
                        $(this).removeClass("error_red");
                    }
                }
            })
            
            time_buckets_time.push(time_bucket_time);
        })
        if (play_list_cover_time_bucket) {
            time_bucket_cover_policy = is_cover(time_buckets_time, policy_time);
        }
        return (time_bucket_cover_policy && play_list_cover_time_bucket);
    }
    function contains(set, element){
        for (var i = 0; i < set.length; i++) {
            if (set[i] == element) {
                return true;
            }
        }
        return false;
    }
    function get_date_from_str(date_time_str){
        // alert(date_time_str)
        var date_time_splitor = arguments[1] ? arguments[1] : " ";
        var date_splitor = arguments[2] ? arguments[2] : "-";
        var time_splitor = arguments[3] ? arguments[3] : ":";
        var date_time = date_time_str.split(date_time_splitor);
        var date = date_time[0];
        var yyyy_mm_dd = date.split(date_splitor);
        var y = yyyy_mm_dd[0];
        var m = yyyy_mm_dd[1];
        var d = yyyy_mm_dd[2];
        var time = date_time[1];
        var hh_mm_ss = time.split(time_splitor);
        var h = hh_mm_ss[0];
        var mm = hh_mm_ss[1];
        var s = hh_mm_ss[2];
        return new Date(y, m, d, h, mm, s);
    }
    
    function is_cover(small_time_buckets, big_time_bucket){
        var is_continuou = true;
        if (small_time_buckets.length > 1) {
            for (var i = 0; i < small_time_buckets.length; i++) {
                var time_flag = false;
                var time1 = small_time_buckets[i];
                for (var j = 0; j < small_time_buckets.length; j++) {
                    if (i == j) {
                        continue;
                    }
                    var time2 = small_time_buckets[j]
                    if (!isCross(time1, time2)) {
                        time_flag = true;
                        break;
                    }
                }
                is_continuou = (is_continuou && time_flag);
            }
        }
        var starts = [];
        var ends = [];
        for (var i = 0; i < small_time_buckets.length; i++) {
            starts.push(small_time_buckets[i]["start"].getTime());
            ends.push(small_time_buckets[i]["end"].getTime());
        }
        if (starts.sort()[0] > big_time_bucket["start"].getTime() ||
        ends.sort().pop() < big_time_bucket["end"].getTime()) {
            is_continuou = false;
        }
        return is_continuou;
        
    }
    
    function isCross(a, b){
        return Math.max(a["start"], b["start"]) <= Math.min(a["end"], b["end"])
    }
    function all_time_input_filled(){
        var flag = true;
        $(".time").each(function(){
            var reg = /\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}/
            if (!reg.test($(this).val())) {
                $(this).addClass("error_red");
                flag = false;
            }
            else {
                $(this).removeClass("error_red");
            }
        })
        return flag;
    }
    
    $("#global_play_list").button().click(function(){
        file_dialog.dialog("open");
    })
    
}

function update_policy(){
    alert("update policy");
    var policy_id = $("#policy .policy_id").first().val();
    var update_policy_url = site_root + "/policy/" + policy_id;
    // update policy number and start end time
    var policy_start_time = $("[name='start_time']", $("#policy_common_fields")).first().val();
    var policy_end_time = $("[name='end_time']", $("#policy_common_fields")).first().val();
    var policy_number = $("#policy_number").val();
    $.ajax({url: update_policy_url, data: {
        start_time: policy_start_time,
        end_time: policy_end_time,
        number: policy_number
    }, type: "PUT", success: function(){
        update_every_time_bucket();
    }})
}

function load_play_list(event, req, settings){
    // "this" is the ".layout_container"
    $(".area", this).each(function(){
        $(".play_list_tabs_container", this).first().tabs({
            tabTemplate: '<li><a href="#{href}">#{label}</a><span class="ui-icon ui-icon-close play_list">Remove Tab</span></li>',
            
            add: function(event, ui){
				//if($(ui.panel).children().size == 0){
					var media_type = $(this).closest(".area").children("[name='media_type']").first().val();
					$(ui.panel).load(site_root +
					"/policies/play_list", {media_type: media_type}, function(){
					$(".time", this).each(function(){
							$(this).datetime({format: "yy-mm-dd hh:ii:00"});
						});
					});
				//}
				
            }
        })
        $(".play_list_tabs_container", this).first().tabs("paging", {
            cycle: true,
            follow: true
        })
        $(".add_play_list_btn", this).first().click(function(){
            var tab_id = "#play_list_tab" +
            play_list_tab_counter;
            var tab_title = "播放列表" + play_list_tab_counter;
            $(this).siblings(".play_list_tabs_container").tabs("add", tab_id, tab_title);
            play_list_tab_counter++;
        })
        
        $(".add_play_list_btn", this).first().trigger("click");
    })
}

function fill_media_files(){
    $(".time", this).each(function(){
        $(this).datetime({
            format: "yy-mm-dd hh:ii:00"
        })
    })
    //var area_media_type = $(".switch_media_type", $(this).closest(".area"))
    //.first().val()
    var area_media_type = $("[name='media_type']", $(this).closest(".area")).first().val();
    var files = [];
    if (area_media_type == "text") {
        $(".media_files", this).each(function(){
            $(this).empty();
            $(this).append("<label>播放文本:</label><textarea name='media_files' rows='10' cols='20'/>");
        })
    }
    else {
        if (area_media_type == "audio") {
            files = audio_files;
        }
        else 
            if (area_media_type == "video") {
                files = video_files;
            }
            else 
                if (area_media_type == "picture") {
                    files = picture_files;
                }
        $(".media_files", this).each(function(){
            $(this).empty();
            $(this).append(random_checkbox(files));
        })
    }
}

function fill_media_files_for_each_area(){
	
	// should not use media files in the page straightly,
	// it should get content via ajax request
	
	
    $(".area").each(function(){
        var area_media_file_type = $("[name='media_type']", this).first().val();
        var files = [];
        if (area_media_file_type == "text") {
            $(".media_files", this).each(function(){
                $(this).empty();
                $(this).append("<label>播放文本:</label><textarea name='media_files' rows='10' cols='20'/>");
            })
        }
        else {
            if (area_media_file_type == "audio") {
                files = audio_files;
            }
            else 
                if (area_media_file_type == "video") {
                    files = video_files;
                }
                else 
                    if (area_media_file_type == "picture") {
                        files = picture_files;
                    }
            $(".media_files", this).each(function(){
                $(this).remove("label");
                $(this).append("<label>媒体文件:</label>" + random_checkbox(files));
            })
        }
    })
}

function random_checkbox(files_arr){
    var rand = Math.random();
    var result = "";
    for (var i = 0; i < files_arr.length; i++) {
        var file = files_arr[i];
		result += "<label><input type='checkbox' name='media_files' value='";
		reuslt += file["code"];
		reuslt += "'/>";
		result += file["name"];
		result += "</label>";
    }
    return result;
}

function add_tab(){
    $("#layouts").tabs("add", "#time-bucket-" + tab_counter, "时段" + tab_counter);
    tab_counter++;
}

function update_every_time_bucket(){
	var policy_id = $(".policy_id").first().val();
	var update_time_bucket_url = site_root + "/policy/" + policy_id + "/time_bucket/";
    $("[id|='time-bucket']").each(function(){
		var time_bucket_id = $("[name='time_bucket_id']", this).first().val();
		var time_bucket = this;
		var layout_type = $(".layout_type", this).first();
		var start_time = $("[name='start_time']", layout_type).first().val();
		var end_time = $("[name='end_time']", layout_type).first().val();
		$.ajax({url: update_time_bucket_url + time_bucket_id, type: "POST", data: {start_time: start_time, end_time: end_time}, success: function(){	
				update_every_area.call(time_bucket);
			}
		});
    });
}

function update_every_area(){
	var policy_id = $(".policy_id").first().val();
	var time_bucket_id = $("[name='time_bucket_id']", this).first().val();
	$(".area", this).each(function(){
		var area_id = $("[name='area_id']", this).first().val();
		var update_play_list_url = site_root + "/policy/" + policy_id + "/time_bucket/" + time_bucket_id + "/area/" + area_id
		$(".play_list_panel", this).each(function(){
			var file_codes = [];
			$(".media_files", this).each(function(){
				if($(this).attr("checked")){
					file_codes.push($(this).val());
				}
			})
			var mode = $("[name='play_mode']", this).first().val();
			var start_time = $("[name='start_time']", this).first().val();
			var end_time = $("[name='end_time']", this).first().val();
			$.ajax({url: update_play_list_url,
					data:{"start_time": start_time, "end_time": end_time, "file_codes": file_codes},
					type: "POST",
					success: function(){
						alert("hello");
					}
			});
		});
	})
}

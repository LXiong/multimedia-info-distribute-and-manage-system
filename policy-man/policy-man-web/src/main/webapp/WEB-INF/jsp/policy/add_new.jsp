<script type="text/javascript">
function(){
    var tab_counter = 1
    var time_bucket_tabs = $("#layouts").tabs({
		tabTemplate: "<li><a href='#{href}'>#{label}</a><span class='ui-icon ui-icon-close'>Remove Tab</span></li>",
		add: function(event, ui){

		},
		remove: function(event, ui){
			
		}
	})
	
	$("#layouts span.ui-icon-close").live("click", function(){
		var index = $("li", time_bucket_tabs).index($(this).parent());
		$("#layouts").tabs("remove", index)
	})
}()
</script>

<div class="policy create">
	<div class="common fields">
		<div id="file_dialog">
			<form>
				<fieldset></fieldset>
			</form>
		</div>
		<div>
			<label for="policy_name">PolicyName</label>
			<input name="policy_name" id="policy_name" type="text" />
			<input name="select_file" type="button" value="add_file" id="choose_file_button" />
		</div>
	</div>
	<div class="add_tab" id="add_layout">Add tab</div>
	<div class="layouts" id="layouts">
	</div>
</div>
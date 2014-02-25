<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<script type="text/javascript">
	function checkdatevalue(theDate) {
		var reg = /^\d{4}-((0{0,1}[1-9]{1})|(1[0-2]{1}))-((0{0,1}[1-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))$/;
		var result = true;
		if (!reg.test(theDate))
			result = false;
		else {
			var arr_hd = theDate.split("-");
			var dateTmp;
			dateTmp = new Date(arr_hd[0], parseFloat(arr_hd[1]) - 1,
					parseFloat(arr_hd[2]));
			if (dateTmp.getFullYear() != parseFloat(arr_hd[0])
					|| dateTmp.getMonth() != parseFloat(arr_hd[1]) - 1
					|| dateTmp.getDate() != parseFloat(arr_hd[2])) {
				result = false
			}
		}
		return result;
	}

	function check_filter_date(id1, id2) {
		var result = true;
		var date1 = $('#' + id1).val();
		if (date1 != "" && !checkdatevalue(date1)) {
			alert("起始时间格式不对");
			result = false;
		}
		var date2 = $('#' + id2).val();
		if (date2 != "" && !checkdatevalue(date2)) {
			alert("结束时间格式不对");
			result = false;
		}
		return result;
	}
</script>

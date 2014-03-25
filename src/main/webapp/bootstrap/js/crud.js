function checkAll(checkAllId) {
	if ($(checkAllId).prop("checked") == true) {
		$("input[name='selectFlag']:checkbox").each(function() { // 遍历所有的name为selectFlag的
			// checkbox
			$(this).prop("checked", true);
		});
	} else { // 反之 取消全选

		$("input[name='selectFlag']:checkbox").each(function() { // 遍历所有的name为selectFlag的
			// uncheckbox
			$(this).prop("checked", false);

		});
	}
};

function create(createDiv, createUrl, successCallback) {

	$(createDiv).load(createUrl + '?' + (new Date()).valueOf(), function(data) {
		$(createDiv).bind("evtSuccess", function(evt, data) {
			successCallback();
		});
		$(createDiv).modal();

	});
};

function update(updateDivId, updateUrl, successCallback) {

	$(updateDivId).load(updateUrl, function(data) {
		$(updateDivId).bind("evtSuccess", function(evt, data) {

			successCallback();
		});
		$(updateDivId).modal();

	});
};

function batchDelete(deleteUrl, successCallback) {
	var selctedItems = new Array();
	$("input[name='selectFlag']:checkbox").each(function() { // 遍历所有的name为selectFlag的
		// checkbox
		if ($(this).prop("checked") == true) {
			selctedItems.push($(this).prop("value"));
		}
	});

	$.ajax({
		type : "POST",
		url : deleteUrl,
		data : {
			_method : 'DELETE',
			items : selctedItems
		},
		// contentType : 'application/json',
		dataType : 'json',
		success : function(data) {

			successCallback();

		}
	});

};
Date.prototype.ToString = function(FormatString) {
	// / <summary>
	// / 1: Date.ToString("yyyy-MM-dd") - 日期格式化为字符串。
	// / </summary>
	// / <param name="FormatString" type="string">
	// / 用户格式化日期的字符串 yyyy-MM-dd HH:mm:ss
	// / </param>
	// / <returns type="string" />
	if (typeof this == "string") {
		return "";
	}
	;
	if (FormatString == undefined) {
		FormatString = "yyyy-MM-dd HH:mm:ss";
	}
	;
	if (FormatString == "UTCDateTime") {
		// / /Date(1311821221173+0800)/
		return "/Date(" + (this.AddHour(-8) - "1970-01-01".ToDate())
				+ "+0800)/";
	}
	;
	var yyyy = this.getFullYear();
	var MM = this.getMonth() + 1;
	if (MM < 1) {
		MM = 12;
		yyyy -= 1;
	}
	;
	var yy = yyyy.toString().substring(2, 4);
	MM = MM > 9 ? MM : "0" + MM;
	var dd = this.getDate();
	dd = dd > 9 ? dd : "0" + dd;
	var HH = this.getHours();
	HH = HH > 9 ? HH : "0" + HH;
	var mm = this.getMinutes();
	mm = mm > 9 ? mm : "0" + mm;
	var ss = this.getSeconds();
	ss = ss > 9 ? ss : "0" + ss;
	var ms = 000;
	ms = ms > 99 ? ms : (ms > 9 ? "0" + ms : "00" + ms);
	return FormatString.replace(/yyyy/g, yyyy).replace(/yy/g, yy).replace(
			/MM/g, MM).replace(/dd/g, dd).replace(/HH/g, HH).replace(/mm/g, mm)
			.replace(/ss/g, ss).replace(/ms/g, ms);
};

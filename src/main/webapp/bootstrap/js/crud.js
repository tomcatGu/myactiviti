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


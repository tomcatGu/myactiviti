(function($) {
	$.fn.extend({
		simpleTable : function(dataUrl, rowRender, paginator, callback) {
			var table = this;
			var start = dataUrl.indexOf("&start=");
			var orginDataUrl = dataUrl;
			if (start != -1)
				orginDataUrl = dataUrl.substring(0, start);
			$.ajax({
				url : dataUrl,
				success : function(data) {
					count = Number(data.count);
					start = Number(data.start);
					size = Number(data.size);

					remainder = (count - start) % size;

					var options = {
						currentPage : start / size + 1,
						totalPages : remainder == 0 ? count / size
								: (count - remainder) / size + 1,
						onPageChanged : function(e, oldPage, newPage) {
							table.find("tr").nextAll().remove();

							table.simpleTable(orginDataUrl + "&start="
									+ (newPage - 1) * size, rowRender,
									paginator, callback)

						},
						itemContainerClass : function(type, page, current) {
							return (page === current) ? "active"
									: "pointer-cursor";

						}
					};
					for (i = 0; i < data.records.length; i++) {
						var newRow = rowRender(data.records[i]);
						table.find("tr:last").after(newRow);
						// $("#permissionList tr:last").after(newRow);

					}

					paginator.bootstrapPaginator(options);
				}
			});
			// if (data == undefined)
			// return;

		}
	});
})(jQuery);
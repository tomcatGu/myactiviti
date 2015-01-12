(function($) {
	$.fn
			.extend({
				simpleTable : function(dataUrl, rowRender, paginator, callback,
						type, dataParam) {
					var table = this;
					var start = dataUrl.indexOf("&start=");
					var orginDataUrl = dataUrl;
					if (start != -1)
						orginDataUrl = dataUrl.substring(0, start);
					$
							.ajax({
								url : dataUrl,
								type : type,
								data : dataParam,
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
								dataType:'json',
								success : function(data) {
									count = Number(data.count);
									start = Number(data.start);
									size = Number(data.size);

									remainder = (count - start) % size;

									var options = {
										bootstrapMajorVersion : 3,
										numberOfPages : remainder == 0 ? (count == 0 ? 1
												: count / size)
												: (count - remainder) / size
														+ 1,
										currentPage : start / size + 1,
										totalPages : remainder == 0 ? (count == 0 ? 1
												: count / size)
												: (count - remainder) / size
														+ 1,
										onPageChanged : function(e, oldPage,
												newPage) {
											table.find("tr").nextAll().remove();

											table.simpleTable(orginDataUrl
													+ "&start=" + (newPage - 1)
													* size, rowRender,
													paginator, callback, type,dataParam);

										},
										itemContainerClass : function(type,
												page, current) {
											return (page === current) ? "active"
													: "pointer-cursor";

										}
									};
									for (i = 0; i < data.records.length; i++) {
										var newRow = rowRender(data.records[i]);
										table.find("tbody").append(newRow);
										// $("#permissionList
										// tr:last").after(newRow);

									}

									paginator.bootstrapPaginator(options);

									if (callback != undefined)
										callback(count, start, size);
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									if (XMLHttpRequest.status == 413) {
										// alert("需要登陆。");
										// window.location = "login";
									}
								}
							});
					// if (data == undefined)
					// return;

				},
				simpleTableFromData : function(data, rowRender, paginator,
						callback, type) {
					var table = this;
					table.clearSimpleTable();

					count = Number(data.count);
					start = Number(data.start);
					size = Number(data.size);

					remainder = (count - start) % size;

					var options = {
						bootstrapMajorVersion : 3,
						numberOfPages : remainder == 0 ? (count == 0 ? 1
								: count / size) : (count - remainder) / size
								+ 1,
						currentPage : start / size + 1,
						totalPages : remainder == 0 ? (count == 0 ? 1 : count
								/ size) : (count - remainder) / size + 1,
						onPageChanged : function(e, oldPage, newPage) {
							table.find("tbody").find("tr").nextAll().remove();

							table.simpleTable(orginDataUrl + "&start="
									+ (newPage - 1) * size, rowRender,
									paginator, callback, type)

						},
						itemContainerClass : function(type, page, current) {
							return (page === current) ? "active"
									: "pointer-cursor";

						}
					};
					for (i = 0; i < data.records.length; i++) {
						var newRow = rowRender(data.records[i]);
						table.find("tbody").append(newRow);
						// $("#permissionList
						// tr:last").after(newRow);

					}

					paginator.bootstrapPaginator(options);
					if (callback != undefined)
						callback(count, start, size);

				},
				clearSimpleTable : function() {
					var table = this;
					table.find("tbody").remove();

				}
			});
})(jQuery);
/* Set the defaults for DataTables initialisation */
$
		.extend(
				true,
				$.fn.dataTable.defaults,
				{
					"sDom" : "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
					"sPaginationType" : "bootstrap",
					"oLanguage" : {
						"sLengthMenu" : "_MENU_ records per page"
					}
				});

/* Default class modification */
$.extend($.fn.dataTableExt.oStdClasses, {
	"sWrapper" : "dataTables_wrapper form-inline"
});

/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
	return {
		"iStart" : oSettings._iDisplayStart,
		"iEnd" : oSettings.fnDisplayEnd(),
		"iLength" : oSettings._iDisplayLength,
		"iTotal" : oSettings.fnRecordsTotal(),
		"iFilteredTotal" : oSettings.fnRecordsDisplay(),
		"iPage" : oSettings._iDisplayLength === -1 ? 0 : Math
				.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
		"iTotalPages" : oSettings._iDisplayLength === -1 ? 0 : Math
				.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
	};
};

/* Bootstrap style pagination control */
$.extend($.fn.dataTableExt.oPagination, {
	"bootstrap" : {
		"fnInit" : function(oSettings, nPaging, fnDraw) {
			var oLang = oSettings.oLanguage.oPaginate;
			var fnClickHandler = function(e) {
				e.preventDefault();
				if (oSettings.oApi._fnPageChange(oSettings, e.data.action)) {
					fnDraw(oSettings);
				}
			};

			$(nPaging).addClass('pagination').append(
					'<ul>' + '<li class="prev disabled"><a href="#">&larr; '
							+ oLang.sPrevious + '</a></li>'
							+ '<li class="next disabled"><a href="#">'
							+ oLang.sNext + ' &rarr; </a></li>' + '</ul>');
			var els = $('a', nPaging);
			$(els[0]).bind('click.DT', {
				action : "previous"
			}, fnClickHandler);
			$(els[1]).bind('click.DT', {
				action : "next"
			}, fnClickHandler);
		},

		"fnUpdate" : function(oSettings, fnDraw) {
			var iListLength = 5;
			var oPaging = oSettings.oInstance.fnPagingInfo();
			var an = oSettings.aanFeatures.p;
			var i, ien, j, sClass, iStart, iEnd, iHalf = Math
					.floor(iListLength / 2);

			if (oPaging.iTotalPages < iListLength) {
				iStart = 1;
				iEnd = oPaging.iTotalPages;
			} else if (oPaging.iPage <= iHalf) {
				iStart = 1;
				iEnd = iListLength;
			} else if (oPaging.iPage >= (oPaging.iTotalPages - iHalf)) {
				iStart = oPaging.iTotalPages - iListLength + 1;
				iEnd = oPaging.iTotalPages;
			} else {
				iStart = oPaging.iPage - iHalf + 1;
				iEnd = iStart + iListLength - 1;
			}

			for (i = 0, ien = an.length; i < ien; i++) {
				// Remove the middle elements
				$('li:gt(0)', an[i]).filter(':not(:last)').remove();

				// Add the new list items and their event handlers
				for (j = iStart; j <= iEnd; j++) {
					sClass = (j == oPaging.iPage + 1) ? 'class="active"' : '';
					$('<li ' + sClass + '><a href="#">' + j + '</a></li>')
							.insertBefore($('li:last', an[i])[0]).bind(
									'click',
									function(e) {
										e.preventDefault();
										oSettings._iDisplayStart = (parseInt($(
												'a', this).text(), 10) - 1)
												* oPaging.iLength;
										fnDraw(oSettings);
									});
				}

				// Add / remove disabled classes from the static elements
				if (oPaging.iPage === 0) {
					$('li:first', an[i]).addClass('disabled');
				} else {
					$('li:first', an[i]).removeClass('disabled');
				}

				if (oPaging.iPage === oPaging.iTotalPages - 1
						|| oPaging.iTotalPages === 0) {
					$('li:last', an[i]).addClass('disabled');
				} else {
					$('li:last', an[i]).removeClass('disabled');
				}
			}
		}
	}
});

/*
 * TableTools Bootstrap compatibility Required TableTools 2.1+
 */
if ($.fn.DataTable.TableTools) {
	// Set the classes that TableTools uses to something suitable for Bootstrap
	$.extend(true, $.fn.DataTable.TableTools.classes, {
		"container" : "DTTT btn-group",
		"buttons" : {
			"normal" : "btn",
			"disabled" : "disabled"
		},
		"collection" : {
			"container" : "DTTT_dropdown dropdown-menu",
			"buttons" : {
				"normal" : "",
				"disabled" : "disabled"
			}
		},
		"print" : {
			"info" : "DTTT_print_info modal"
		},
		"select" : {
			"row" : "active"
		}
	});

	// Have the collection use a bootstrap compatible dropdown
	$.extend(true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
		"collection" : {
			"container" : "ul",
			"button" : "li",
			"liner" : "a"
		}
	});
}
/* Table initialisation */
var oTable;
var aSelected = [];
$(document)
		.ready(

				function() {

					if ($('#userList').fnDraw != undefined) {
						return;
					}
					oTable = $('#userList')
							.dataTable(
									{
										"bProcessing" : true,
										"bServerSide" : true,
										"bStateSave" : true,
										"sAjaxSource" : "user/getUsers",
										// /*
										"aoColumns" : [
												{
													"bSortable" : false,
													"sTitle" : "<input type='checkbox' id='selectAll' ></input>",
													"mData" : "id"
												}, {
													"sTitle" : "编号",
													"mData" : "id"
												}, {
													"sTitle" : "姓名",
													"mData" : "name"
												}, {
													"sTitle" : "登录名",
													"mData" : "loginName"
												}, {
													"sTitle" : "移动电话",
													"mData" : "mobile"
												}, {
													"sTitle" : "电子邮件",
													"mData" : "email"
												}, {
													"sTitle" : "角色",
													"mData" : "roles"
												}, {
													"sTitle" : "状态",
													"mData" : "status"
												} ],

										// */
										/*
										 * "fnRowCallback": function( nRow,
										 * aData, iDisplayIndex ) {
										 * 
										 * //if ( aData[4] == "A" ) //{
										 * $('td:eq(4)', nRow).html( '<b>A</b>' );
										 * //} },
										 */
										// /*
										"aoColumnDefs" : [
												{
													"aTargets" : [ 4 ],
													// /*
													"mRender" : function(data,
															type, row) {
														// alert(type);
														// roles=eval(data);
														// alert(roles.length);
														if (data == "[]")
															return "<>";
														else
															return data + ' '
																	+ row[3];
													}
												// */
												/*
												 * "mDataProp" : function( data,
												 * type, val) { if (type ===
												 * 'set') { // Store the base
												 * alert(val); data.roles = val; //
												 * Display is
												 * 
												 * data.roles_display = val;
												 * 
												 * data.roles_filter= val;
												 * return; } else if (type ===
												 * 'display') { return
												 * data.roles_display; } else if
												 * (type === 'filter') { return
												 * data.roles_filter; }
												 * 
												 * return data.roles; }
												 */
												},
												{
													"aTargets" : [ 0 ],
													"mRender" : function(data,
															type, row) {
														// alert(data);
														return '<input type="checkbox" name="check" value="'
																+ data + '"/>';

													}

												} ],
										// */
										"fnServerData" : retrieveData, // 获取数据的处理函数
										"fnServerParams" : customParams,
										"sDom" : "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
										"sPaginationType" : "bootstrap",
										"oLanguage" : {
											"sLengthMenu" : "_MENU_ records per page"
										},
										"fnRowCallback" : function(nRow, aData,
												iDisplayIndex) {
											if (jQuery.inArray(aData.DT_RowId,
													aSelected) !== -1) {
												$(nRow)
														.addClass(
																'row_selected');
											}
										}
									});

					$("#selectAll").click(function() {
						// alert($("input[type='checkbox']"));
						$("input[type='checkbox']").not(this).each(function() {
							this.checked = !this.checked;
						});

					});

				});

function retrieveData(sSource, aoData, fnCallback) {
	// 将客户名称加入参数数组
	// aoData.push( { "name": "customerName", "value": $("#customerName").val()
	// } );

	$.ajax({
		"type" : "POST",
		"contentType" : "application/json",
		"url" : sSource,
		"dataType" : "json",
		"data" : JSON.stringify(aoData), // 以json格式传递
		"success" : function(resp) {
			fnCallback(resp); // 服务器端返回的对象的returnObject部分是要求的格式

			/*
			 * Add a click handler to the rows - this could be used as a
			 * callback
			 */
			$('#userList tbody tr').on('click', function() {
				//alert(this.classname);
				$(this).toggleClass('row_selected');
				var id = this.id;
				var index = jQuery.inArray(id, aSelected);

				if (index === -1) {
					aSelected.push(id);
				} else {
					aSelected.splice(index, 1);
				}

				$(this).toggleClass('row_selected');
			});
		}
	});
};

function customParams(aoData) {
	/*
	 * aoData.push({ "name" : "name#EQ", "value" : "" });
	 */
};


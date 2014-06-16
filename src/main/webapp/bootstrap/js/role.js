function addRole() {
	$("#createRole").load('role/create', function() {
		$("#createRole").bind("evtSuccess", function(evt, data) {

			// alert(data.msg);
			$("#createRole").dialog("close");
			var iCurrentPage = oTable.fnSettings()._iDisplayStart;
			var oSettings = oTable.fnSettings();
			oSettings._iDisplayStart = iCurrentPage;
			oTable.fnDraw(oSettings);
		});
		$("#createRole").dialog({
			show : 'blind',
			modal : true
		});

	});
};
var oTable;

/* Table initialisation */
$(document)
		.ready(
				function() {
					oTable = $('#roleList')
							.dataTable(
									{
										"bProcessing" : true,
										"bServerSide" : true,
										"sAjaxSource" : "role/getRoles",

										"aoColumns" : [ {
											"mData" : "id"
										}, {
											"mData" : "name"
										} ],

										"aoColumnDefs" : [ {
											"aTargets" : [ 1 ],

											"mRender" : function(data, type,
													row) {
												// alert(type);
												// roles=eval(data);
												// alert(roles.length);
												if (data == "[]")
													return "<>";
												else
													return data;
											}

										}

										],

										"fnServerData" : retrieveData, // 获取数据的处理函数
										"fnServerParams" : customParams,
										"sDom" : "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
										"sPaginationType" : "bootstrap",
										"oLanguage" : {
											"sLengthMenu" : "_MENU_ records per page"
										}
									});

					$('#addBtn').bind('click', addRole);
				});
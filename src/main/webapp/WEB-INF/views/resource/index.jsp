<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Resource List</title>
<style>
</style>



<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/css/ui.multiselect.css" />


<script
	src="${pageContext.request.contextPath}/scripts/jquery-1.5.1.min.js"
	type="text/javascript"></script>

<script
	src="${pageContext.request.contextPath}/scripts/jquery-ui-1.8.11.custom.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/scripts/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/scripts/jquery.jqGrid.min.js"
	type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/scripts/json2.js"
	type="text/javascript"></script>



<script type="text/javascript">
	$(document).ready(
			function() {
				function roleFormatter(cellvalue, options, rowObject) {
					//alert(typeof rowObject.roles);
					if (rowObject.roles.length > 0)
						return JSON.stringify(cellvalue);
					else
						return '';

				}
				;

				$("#list").jqGrid(
						{
							url : 'getResources',
							datatype : 'json',
							mtype : 'POST',
							//ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
							colNames : [ '<fmt:message key="resource.id" />',
									'<fmt:message key="resource.name" />',
									'<fmt:message key="resource.value" />',
									'<fmt:message key="resource.type" />',
									'<fmt:message key="resource.roles" />' ],
							colModel : [ {
								name : 'id',
								index : 'id',
								width : 55,
								editable : false,
								editoptions : {
									readonly : true,
									size : 10
								}
							}, {
								name : 'name',
								index : 'name',
								width : 90,
								width : 80,
								editable : true,
								editoptions : {
									size : 10
								}
							}, {
								name : 'value',
								index : 'value',
								width : 80,
								align : 'right',
								editable : true,
								editoptions : {
									size : 10
								}
							}, {
								name : 'type',
								index : 'type',
								width : 80,
								align : 'right',
								editable : true,
								editoptions : {
									size : 10
								}
							}, {
								name : 'roles',
								index : 'roles',
								width : 80,
								align : 'right',
								formatter : roleFormatter
							} ],
							width : 500,
							cache : false,
							pager : '#pager',
							rowNum : 20,
							rowList : [ 10, 20, 30 ],
							sortname : 'name',
							sortorder : 'desc',
							viewrecords : true,
							//height:210,
							editurl : 'resource/edit',
							caption : 'My first grid',
							jsonReader : {
								repeatitems : false
							}

						});
				addFunc = function() {

					var success = function() {
						alert("success");
					}
					jQuery("#createResource").load('add', function() {
						jQuery("#createResource").dialog({
							cache : false,
							show : 'blind',
							buttons : {
								'OK' : function() {

									$('#createResource form').submit();

									$(this).dialog('close');
									jQuery("#list").trigger('reloadGrid');

								},
								'Cancle' : function() {
									$(this).dialog('close');
								}
							}
						});

					});

				};

				editFunc = function(str) {
					jQuery("#createResource").load(str + '/edit', function() {

						jQuery("#createResource").dialog({
							cache : false,
							show : 'blind',
							buttons : {
								'OK' : function() {
									$('#createResource form').submit();
									$("#list").trigger('reloadGrid');

									$(this).dialog('close');

								},
								'Cancle' : function() {
									$(this).dialog('close');
								}
							}
						});
					});

				};

				delFunc = function(str) {

					$.ajax({
						type : "DELETE",
						url : str,
						success : function(msg) {
							alert("Data Saved: " + msg);
						}
					});

					//alert("deleted!");
					jQuery("#list").trigger('reloadGrid');

				};
				jQuery("#list").jqGrid('navGrid', '#pager', {
					addfunc : addFunc,
					editfunc : editFunc,
					delfunc : delFunc
				}, //options
				{
					height : 280,
					reloadAfterSubmit : false
				}, // edit options
				{
					height : 280,
					reloadAfterSubmit : false
				}, // add options
				{
					reloadAfterSubmit : false
				}, // del options
				{} // search options
				);

			});
</script>
</head>
<body>

<h1>Resource List</h1>


<table id="list"></table>
<div id="pager"></div>
<div id="createResource"></div>
</body>



</html>

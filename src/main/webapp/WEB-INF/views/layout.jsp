<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jQuery EasyUI</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/jquery.form.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/json.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/outlook2.js"></script>





<script>
	 var _menus = {"menus":[
						{"menuid":"1","icon":"icon-sys","menuname":"系统管理",
							"menus":[{"menuname":"菜单管理","icon":"icon-nav","url":"http://hxling.cnblogs.com"},
									{"menuname":"添加用户","icon":"icon-add","url":"demo.html"},
									{"menuname":"用户管理","icon":"icon-users","url":"demo2.html"},
									{"menuname":"角色管理","icon":"icon-role","url":"demo2.html"},
									{"menuname":"权限设置","icon":"icon-set","url":"demo.html"},
									{"menuname":"系统日志","icon":"icon-log","url":"demo.html"}
								]
						},{"menuid":"8","icon":"icon-sys","menuname":"员工管理",
							"menus":[{"menuname":"员工列表","icon":"icon-nav","url":"demo.html"},
									{"menuname":"视频监控","icon":"icon-nav","url":"demo1.html"}
								]
						},{"menuid":"56","icon":"icon-sys","menuname":"部门管理",
							"menus":[{"menuname":"添加部门","icon":"icon-nav","url":"demo1.html"},
									{"menuname":"部门列表","icon":"icon-nav","url":"demo2.html"}
								]
						},{"menuid":"28","icon":"icon-sys","menuname":"财务管理",
							"menus":[{"menuname":"收支分类","icon":"icon-nav","url":"demo.html"},
									{"menuname":"报表统计","icon":"icon-nav","url":"demo1.html"},
									{"menuname":"添加支出","icon":"icon-nav","url":"demo.html"}
								]
						},{"menuid":"39","icon":"icon-sys","menuname":"商城管理",
							"menus":[{"menuname":"商品分","icon":"icon-nav","url":"/shop/productcatagory.aspx"},
									{"menuname":"商品列表","icon":"icon-nav","url":"/shop/product.aspx"},
									{"menuname":"商品订单","icon":"icon-nav","url":"/shop/orders.aspx"}
								]
						}
				]};
				
				
				
		$(function(){
			$('#test').datagrid({
				title:'My Title',
				iconCls:'icon-save',
				//width:600,
				//height:350,
				nowrap: false,
				striped: true,
				url:'getall',
				sortName: 'code',
				sortOrder: 'desc',
				idField:'code',
				frozenColumns:[[
	                {field:'ck',checkbox:true},
	                {title:'code',field:'code',width:80,sortable:true}
				]],
				columns:[[
			        {title:'Base Information',colspan:3},
					{field:'opt',title:'Operation',width:100,align:'center', rowspan:2,
						formatter:function(value,rec){
							return '<span style="color:red">Edit Delete</span>';
						}
					}
				],[
					{field:'name',title:'Name',width:120},
					{field:'addr',title:'Address',width:120,rowspan:2,sortable:true},
					{field:'col4',title:'Col41',width:150,rowspan:2}
				]],
				pagination:true,
				rownumbers:true,
				singleSelect:false,
				toolbar:[{
					text:'Add',
					iconCls:'icon-add',
					handler:function(){
						alert('add')
					}
				},{
					text:'Cut',
					iconCls:'icon-cut',
					disabled:true,
					handler:function(){
						alert('cut')
					}
				},'-',{
					text:'Save',
					iconCls:'icon-save',
					handler:function(){
						alert('save')
					}
				}]
			});
			
		//alert($('#myform').formToArray());

		$('#getHtml').bind('click', function() {
		
				$.ajax({
  					url: "../formbuilder/getHtml/abc",
  					cache: false,
  					success: function(html){
    					$("#results").append(html);
  						}
				});
				var a=$('#myform').formToArray();
				//alert(a.toString().toString());
				

				$.ajax({
  					url: "../formbuilder/submitVote",
  					processData:false,
  					type:"POST",
  					data:"answer="+ JSON.stringify(a),
  					error: function(XMLHttpRequest, textStatus, errorThrown){
    					alert(textStatus);
  						},
   					success: function(html){
    					alert(html);
  						}
				});
			
				
				
				
		});		
			

			
			
		});
		function userAdd(){
			$('#tt').tabs('add',{
				title:'New Tab ' + index,
				content:'Tab Body ' + index,
				iconCls:'icon-save',
				closable:true
			});
		};
			
		//}
		
		
		function resize(){
			$('#test').datagrid({
				title: 'New Title',
				striped: true,
				width: 650,
				queryParams:{
					p:'param test',
					name:'My Name'
				}
			});
		}
		function getSelected(){
			var selected = $('#test').datagrid('getSelected');
			alert(selected.code+":"+selected.name);
		}
		function getSelections(){
			var ids = [];
			var rows = $('#test').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].code);
			}
			alert(ids.join(':'))
		}
		function clearSelections(){
			$('#test').datagrid('clearSelections');
		}
		function selectRow(){
			$('#test').datagrid('selectRow',2);
		}
		function selectRecord(){
			$('#test').datagrid('selectRecord','002');
		}
		function unselectRow(){
			$('#test').datagrid('unselectRow',2);
		}
	</script>

</head>
<body>
<div class="easyui-layout" style="width: 100%; height: 400px;">
<div region="north" border="false"
	style="overflow: hidden; height: 60px; background: #A4BED4;">
<h2>Layout in Panel</h2>
</div>
<div region="south" split="true"
	style="height: 50px; background: #efefef;"></div>
<div region="east" icon="icon-reload" title="East" split="true"
	style="width: 180px;">
<div class="easyui-calendar" style="width: 180px; height: 180px;"></div>

</div>
<div region="west" split="true" title="West" style="width: 100px;">
<form id="myform"><input id='A' name="A" type="text" /> <input
	name="A" type="text" /> <input name="B" type="checkbox" value="B1" />
<input name="B" type="checkbox" value="B2" /> <input name="C"
	type="radio" value="C1" /> <input name="C" type="radio" value="C2" /></form>

<button id="getHtml"></button>
<div id="results"></div>

<div class="easyui-accordion" fit="true" border="false"></div>

</div>

<div region="center" title="Main Title" style="background: #eee;">
<div id="centerTab" class="easyui-tabs">
<div title="tabs" style="padding: 2px; display: none;">
<table id="test"></table>
</div>
</div>
</div>
</div>
</body>
</html>
/*
 seajs.config({
 // Sea.js 的基础路径（修改这个就不是路径就不是相对于seajs文件了）
 base: 'sea-modules',
 // 别名配置（用变量表示文件，解决路径层级过深和实现路径映射）
 alias: {
 //'es5-safe': 'gallery/es5-safe/0.9.3/es5-safe',
 //'json': 'gallery/json/1.0.2/json',
 'jquery': 'scripts/jquery-1.10.2.min'
 },
 // 路径配置（用变量表示路径，解决路径层级过深的问题）
 paths: {
 //'gallery': 'https://a.alipayobjects.com/gallery'
 }
 });
 */

seajs.config({
	alias : {
		"jquery" : /* [[@{'/scripts/jquery-1.10.2.min.js'}]] */''

	},
	paths : {
		"bootstrap" : /* [[@{'/bootstrap/js/'}]] */'',
		"scripts" : /* [[@{'/scripts/'}]] */'',

	}
});

var bootstrap = [ 'jquery', 'bootstrap/bootstrap.min.js',
		'bootstrap/respond.min.js', 'bootstrap/prettify.js',
		'bootstrap/jquery.dataTables.js',
		'bootstrap/bootstrap-datetimepicker.min.js',
		'bootstrap/bootstrap-datetimepicker.zh-CN.js',
		'scripts/jquery-ui-1.10.3.custom.min.js',
		'bootstrap/bootstrap-modal.js', 'bootstrap/bootstrap-modalmanager.js',
		'bootstrap/bootstrap-confirm.js', 'bootstrap/bootstrap-paginator.js',
		'bootstrap/jquery.form.js', 'bootstrap/jquery.jqplot.min.js',
		'bootstrap/crud.js', 'bootstrap/jquery.blockUI.js',
		'bootstrap/jquery.simpleTable.js',
		'bootstrap/jquery.bootstrap.teninedialog.min.js',
		'bootstrap/bootstrap-multiselect.js', 'scripts/jquery.validate.js',
		'scripts/jquery.metadata.js', 'scripts/json2.js' ];
seajs.use(bootstrap, function() {
	$.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		complete : function(XMLHttpRequest, textStatus) {
			var sessionstatus = XMLHttpRequest
					.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
			if (sessionstatus == "timeout") {
				// 如果超时就处理 ，指定要跳转的页面
				// alert("会话已经超时，请重新登录。");
				$.teninedialog({
					title : '系统提示',
					content : '会话已经超时，请重新登录。',
					dialogHidden : function() {
						var loginUrl = /* [[@{'/login'}]] */'';
						window.location.replace(loginUrl);
					}
				});

			}
		}
	});
});

//require([ "css/bootstrap.min.css", "css/bootstrap-responsive.min.css",
//		"css/docs.css", "css/prettify.css", "css/bootstrap-modal.css",
//		"css/bootstrap-datetimepicker.min.css", "css/jquery.jqplot.css" ]);

require.config({
	paths : {
		jquery : "../scripts/jquery-1.10.2.min",
		spin : "js/spin.min",
		domReady : "js/domReady"
	}
});
require([ "scripts/jquery-1.10.2.min.js",
		"scripts/jquery-ui-1.10.3.custom.min.js", "scripts/jquery.validate.js",
		"scripts/jquery.metadata.js", "scripts/json2.js" ]);

require([ "bootstrap/js/prettify.js", "bootstrap/js/bootstrap.min.js",
		"bootstrap/js/jquery.dataTables.js",
		"bootstrap/js/bootstrap-datetimepicker.min.js",
		"bootstrap/js/bootstrap-datetimepicker.zh-CN.js",
		"bootstrap/js/bootstrap-modal.js",
		"bootstrap/js/bootstrap-modalmanager.js",
		"bootstrap/js/bootstrap-confirm.js",
		"bootstrap/js/bootstrap-paginator.js", "bootstrap/js/jquery.form.js",
		"bootstrap/js/jquery.jqplot.min.js",
		"bootstrap/js/jquery.simpleTable.js", "bootstrap/js/crud.js",
		"bootstrap/js/jquery.spin.js" ]);
requirejs(['jquery','spin','domReady'], function($) {
   // alert($().jquery);
});
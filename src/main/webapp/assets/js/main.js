var BrowserDetect = {
	init : function() {
		this.browser = this.searchString(this.dataBrowser)
				|| "An unknown browser";
		this.version = this.searchVersion(navigator.userAgent)
				|| this.searchVersion(navigator.appVersion)
				|| "an unknown version";
	},
	searchString : function(data) {
		for (var i = 0; i < data.length; i++) {
			var dataString = data[i].string;
			var dataProp = data[i].prop;
			this.versionSearchString = data[i].versionSearch
					|| data[i].identity;
			if (dataString) {
				if (dataString.indexOf(data[i].subString) != -1)
					return data[i].identity;
			} else if (dataProp)
				return data[i].identity;
		}
	},
	searchVersion : function(dataString) {
		var index = dataString.indexOf(this.versionSearchString);
		if (index == -1)
			return;
		return parseFloat(dataString.substring(index
				+ this.versionSearchString.length + 1));
	},
	dataBrowser : [ {
		string : navigator.userAgent,
		subString : "Chrome",
		identity : "Chrome"
	}, {
		string : navigator.vendor,
		subString : "Apple",
		identity : "Safari",
		versionSearch : "Version"
	}, {
		prop : window.opera,
		identity : "Opera",
		versionSearch : "Version"
	}, {
		string : navigator.userAgent,
		subString : "Firefox",
		identity : "Firefox"
	}, {
		string : navigator.userAgent,
		subString : "MSIE",
		identity : "Explorer",
		versionSearch : "MSIE"
	}, {
		string : navigator.userAgent,
		subString : "Gecko",
		identity : "Mozilla",
		versionSearch : "rv"
	}, { // for older Netscapes (4-)
		string : navigator.userAgent,
		subString : "Mozilla",
		identity : "Netscape",
		versionSearch : "Mozilla"
	} ],
};
BrowserDetect.init();
var url = "http://localhost:8080" + globalUri;
require.config({
	baseUrl : url,
	paths : {
		browserDetect : 'browserDetect',
		domReady : 'domReady',
		html5shiv : 'html5shiv',
		respond : 'respond.min',
		aceExtra : 'ace-extra.min',
		bootstrap : 'bootstrap.min',
		typeaheadBs2 : 'typeahead-bs2.min',
		excanvas : 'excanvas.min',
		juqeryUI : 'jquery-ui-1.10.3.custom.min',
		jqueryUiTouchPunch : 'jquery.ui.touch-punch.min',
		slimscroll : 'jquery.slimscroll.min',
		bootbox : 'bootbox.min',
		sessionTimeout : 'sessionTimeout',
		bootstrapDatepicker : 'date-time/bootstrap-datepicker.min',
		bootstrapDatepickerCN : 'date-time/bootstrap-datepicker.zh-CN',
		aceElements : 'ace-elements.min',
		ace : 'ace.min',
		bootstrapModal : '../../bootstrap/js/bootstrap-modal',
		bootstrapModalmanager : '../../bootstrap/js/bootstrap-modalmanager',
		bootstrapPaginator : '../../bootstrap/js/bootstrap-paginator.min',
		jqueryForm : '../../bootstrap/js/jquery.form',
		jqueryValidate : '../../scripts/jquery.validate',
		json2 : '../../scripts/json2',
		simpleTable : '../../bootstrap/js/jquery.simpleTable',
		crud : '../../bootstrap/js/crud',
		blockUI : '../../bootstrap/js/jquery.blockUI',
		teninedialog : '../../bootstrap/js/jquery.bootstrap.teninedialog.min',
		jstree : '../../jstree/jstree.min'

	},
	shim : {

		'bootstrap' : {
			deps : [ 'browserDetect' ]

		},
		'json2' : {
			deps : [ 'browserDetect' ]

		},
		'juqeryUI' : {
			deps : [ 'browserDetect' ]

		},
		'bootstrapModalmanager' : {
			deps : [ 'browserDetect', 'bootstrap' ]

		},
		'bootstrapModal' : {
			deps : [ 'browserDetect', 'bootstrap' ]

		},
		'typeaheadBs2' : {
			deps : [ 'browserDetect' ]

		},
		'slimscroll' : {
			deps : [ 'browserDetect' ]

		},
		'sessionTimeout' : {
			deps : [ 'browserDetect', 'teninedialog' ]

		},
		'bootbox' : {
			deps : [ 'browserDetect', 'bootstrap' ]

		},
		'bootstrapPaginator' : {
			deps : [ 'browserDetect', 'bootstrap' ]

		},
		'simpleTable' : {
			deps : [ 'browserDetect' ]

		},
		'crud' : {
			deps : [ 'browserDetect' ]

		},
		'jqueryForm' : {
			deps : [ 'browserDetect' ]

		},
		'jqueryValidate' : {
			deps : [ 'browserDetect' ]

		},
		'blockUI' : {
			deps : [ 'browserDetect' ]

		},
		'teninedialog' : {
			deps : [ 'browserDetect', 'bootstrap' ]

		},
		'bootstrapDatepicker' : {
			deps : [ 'browserDetect', 'bootstrap' ]

		},
		'bootstrapDatepickerCN' : {
			deps : [ 'bootstrapDatepicker' ]

		},
		'jqueryUiTouchPunch' : {
			deps : [ 'juqeryUI' ]

		},
		'ace' : {
			deps : [ 'aceElements' ]
		},
		'aceElements' : {
			deps : [ 'aceExtra', 'bootstrap' ],
		},
		'jstree' : {
			deps : [ 'browserDetect' ]

		}
	}
});
if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
	// IE8 and below specific scripts

	require([ 'html5shiv', 'respond', 'excanvas' ], function(ieScript) {
		// ... do stuff
		// alert('lt IE 8');
	});

	// alert($().jquery);

};

require([ 'browserDetect', 'bootstrap', 'typeaheadBs2', 'juqeryUI',
		'jqueryUiTouchPunch', 'slimscroll', 'bootstrapDatepicker',
		'bootstrapDatepickerCN', 'bootbox', 'bootstrapModal',
		'bootstrapModalmanager', 'bootstrapPaginator', 'jqueryForm',
		'jqueryValidate', 'json2', 'simpleTable', 'crud', 'blockUI',
		'teninedialog', 'domReady', 'sessionTimeout', 'jstree' ], function($) {
	return $;

});
require([ 'aceExtra', 'aceElements', 'ace' ], function(isScript) {

});

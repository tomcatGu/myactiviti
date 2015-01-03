$
	.ajaxSetup({
	    contentType : "application/x-www-form-urlencoded;charset=utf-8",
	    complete : function(XMLHttpRequest, textStatus) {
		var sessionstatus = XMLHttpRequest
			.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
		if (sessionstatus == "timeout") {
		    // 如果超时就处理 ，指定要跳转的页面
		    // alert("会话已经超时，请重新登录。");

		    var dialogContent = '<div tabindex="-1" class="modal fade" role="dialog"'
			    + 'data-keyboard="false" data-backdrop="static" style="display: none;">'
			    + '<div class="modal-dialog"><div class="modal-content"><div class="modal-header">'
			    + '	<button type="button" class="close" data-dismiss="modal"'
			    + 'aria-hidden="true">×</button>'
			    + '<h3>消息</h3></div>'
			    + '<div class="modal-body">'
			    + '	<p>会话已经超时，请重新登录。</p>'
			    + '</div>'
			    + '<div class="modal-footer">'
			    + '	<button class="btn btn-warning" type="button" data-dismiss="modal"'
			    + '>关闭</button></div></div></div></div>';
		    var dialog = $(dialogContent);
		    dialog.modal().on('hidden.bs.modal', function(e) {
			var loginUrl = /* [[@{'/login'}]] */'';
			window.location.replace(loginUrl);
		    });

		}
	    }
	});

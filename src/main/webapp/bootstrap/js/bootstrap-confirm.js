(function($) {
	$.fn
			.extend({
				// pass the options variable to the function
				confirm : function(options) {
					var html = '<div class="modal" id="confirmContainer"><div class="modal-header"><a class="close" data-dismiss="modal">×</a>'
							+ '<h3>#Heading#</h3></div><div class="modal-body">'
							+ '#Body#</div><div class="modal-footer">'
							+ '<a href="#" class="btn btn-danger" id="confirmYesBtn">#yesBtn#</a>'
							+ '<a href="#" class="btn" data-dismiss="modal">#noBtn#</a></div></div>';

					var defaults = {
						heading : 'Please confirm',
						body : 'Body contents',
						yesBtn : 'Yes',
						noBtn : 'No',
						callback : null
					};

					var options = $.extend(defaults, options);
					html = html.replace('#Heading#', options.heading).replace(
							'#Body#', options.body).replace('#yesBtn#',
							options.yesBtn).replace('#noBtn#', options.noBtn);
					$(this).html(html);
					$(this).modal('show');
					var context = $(this);
					$('#confirmYesBtn', this).click(function() {
						if (options.callback != null)
							options.callback();
						$(context).modal('hide');
					});
				}
			});

})(jQuery);
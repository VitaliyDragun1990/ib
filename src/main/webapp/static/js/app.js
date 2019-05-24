$(function() {
    var init = function() {
    	$('#search-link').on('click', goToSearch);
    	$('#loadMore').on('click', loadMoreComments);
    	$('.reply').on('click', function() {
    		reply($(this).attr('data-name'));
    	});
    	$('#sendComment').on('click', sendComment);
    	$('.logout').on('click', gpLogout);
    	$('#contactForm').on('submit', contact);
    };
    
    var contact = function(event) {
    	var name = $('input[name="name"]').val();
    	var email = $('input[name="email"]').val();
    	var message = $('textarea[name="message"]').val();
    	var valid = true;
    	
    	if (!validateName(name)) {
    		valid = false;
    		$('.form-group.name').addClass('has-error');
    		$('.form-group.name .help-block').removeClass('hidden');
    	} else {
    		$('.form-group.name').removeClass('has-error');
    		$('.form-group.name .help-block').addClass('hidden');
    	}
    	
    	if (!validateEmail(email)) {
    		valid = false;
    		$('.form-group.email').addClass('has-error');
    		$('.form-group.email .help-block').removeClass('hidden');
		} else {
			$('.form-group.email').removeClass('has-error');
    		$('.form-group.email .help-block').addClass('hidden');
		}
    	
    	if (!validateMessage(message)) {
    		valid = false;
    		$('.form-group.message').addClass('has-error');
    		$('.form-group.message .help-block').removeClass('hidden');
		} else {
			$('.form-group.message').removeClass('has-error');
    		$('.form-group.message .help-block').addClass('hidden');
		}
    	
    	if (valid) {
			$('#contactForm .alert').addClass('hidden');
		} else {
			$('#contactForm .alert').removeClass('hidden');
		}
    	
    	return valid;
    }
    
    var validateEmail = function(email) {
    	 var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    	 return re.test(email);
    }
    
    var validateName = function(name) {
    	return name.trim() !='';
    }
    
    var validateMessage = function(msg) {
    	return msg.trim() !='';
    }
    

    /*
    * This function responseible for animating transition to the search form
    * when user clicks on 'Search' link.
    */
    var goToSearch = function() {
        $('html, body').animate({
                scrollTop: $('#search').offset().top,
            },
            2000);
        $('#search').focus();
    };

    /*
    * This functions responsible for loading more user comments for
    * specific article from the server.
    */
    var loadMoreComments = function() {
    	$('#loadMore').addClass('hidden');
    	$('#loadIndicator').removeClass('hidden');
    	var offset = $('#comments-container .comment-item').length;
    	var articleId = $('#comments-container').attr('data-article-id');
    	
    	$.ajax({
    		url: ctx + '/ajax/html/comments?offset=' + offset + '&articleId=' + articleId,
    		success: function(data) {
    			$('#loadIndicator').addClass('hidden');
    			$('#comments-container').append(data);
        		var actualTotal = $('#comments-container .comment-item').length;
        		var maxTotal = $('#comments-container').attr('data-comments-count');
        		if (actualTotal < maxTotal) {
        			$('#loadMore').removeClass('hidden');
				}
    		},
    		error: function(data) {
    			alert(messages.errorAjax);
    			$('#loadIndicator').addClass('hidden');
    			$('#loadMore').removeClass('hidden');
    		}
    	});
    };

    /*
    * This function responseible for animating transition to comment form
    * when user clicks on 'Reply' link on some other comment.
    */
    var reply = function(name) {
    	$('#commentText').val(name + ', ');
    	$('#commentText').focus();
    	$('html, body').animate({
    		scrollTop : $('#commentText').offset().top
    	}, 2000);
    };
    
    /**
     * This function is responsible for handling 'send new comment' form submition.
     */
    var sendComment = function() {
		if (googleProfile == null) {
			$('#signin-form').modal('show');
		} else {
			var authToken = googleProfile.authToken;
			var articleId = $('#comments-container').attr('data-article-id');
			var content = $('.new-comment #commentText').val();
			var articleUrl = window.location.href;
			var articleTitle = $('#comments-container').attr('data-article-title');
			if (content.trim() != '') {
				$('#commentForm .help-block').addClass('hidden');
				$('#commentForm').removeClass('has-error');
				$('.new-comment #commentText').val('');
				var sendBtn = $('#sendComment');
				convertButtonToLoaderSpinner(sendBtn, 'btn-primary')
				$.ajax({
					url : ctx + '/ajax/html/comment',
					method: 'post',
					data: {
						articleId : articleId,
						authToken : authToken,
						content : content,
						articleUrl : articleUrl,
						articleTitle : articleTitle
					}, 
					success : function(data) {
						$('#comments-container').prepend(data);
						// Increment comments count of the current article
						var commentCount = parseInt($('span.comment-count').text(), 10);
						commentCount += 1;
						$('span.comment-count').html(commentCount.toLocaleString());
						
						convertLoaderSpinnerToButton(sendBtn, 'btn-primary', sendComment);
						// add click listener to reply button on all comments again
						$('.reply').on('click', function() {
				    		reply($(this).attr('data-name'));
				    	});
					},
					error : function(data) {
						alert(messages.errorAjax);
						convertLoaderSpinnerToButton(sendBtn, 'btn-primary', sendComment);
					}
				});
			} else {
				$('#commentForm').addClass('has-error');
				$('#commentForm .help-block').removeClass('hidden');
				$('.new-comment #commentText').val('');
			}
		}
	};
	
	
	 var convertButtonToLoaderSpinner = function(btn, btnClass) {
			btn.removeClass(btnClass);
			btn.removeClass(btn);
			btn.addClass('load-indicator');
			var text = btn.text();
			btn.text('');
			btn.attr('data-btn-text', text);
			btn.off('click');
		};
		
	var convertLoaderSpinnerToButton = function(btn, btnClass, actionClick) {
			btn.removeClass('load-indicator');
			btn.addClass('btn');
			btn.addClass(btnClass);
			btn.text(btn.attr('data-btn-text'));
			btn.removeAttr('data-btn-text');
			btn.click(actionClick);
		};

    init();
});
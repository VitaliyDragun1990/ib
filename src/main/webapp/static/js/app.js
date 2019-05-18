$(function() {
    var init = function() {
    	$('#search-link').on('click', goToSearch);
    	$('#loadMore').on('click', loadMoreComments);
    	$('.reply').on('click', function() {
    		reply($(this).attr('data-name'));
    	});
    };

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
    		url: ctx + '/ajax/comments?offset=' + offset + '&articleId=' + articleId,
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

    init();
});
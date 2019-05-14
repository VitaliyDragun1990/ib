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

    	setTimeout(function() {
    		$('#loadIndicator').addClass('hidden');
    		$('#loadMore').removeClass('hidden');
    	}, 1000);
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
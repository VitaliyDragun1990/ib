 // --------------------------- Google plus integration Start ---------------------------
    var googleProfile = null;
    
    
	
	var gpLogout = function() {
		var auth2 = gapi.auth2.getAuthInstance();
		auth2.signOut();
		googleProfile = null;
		
		$('.new-comment a.logout').addClass('hidden');
		var noAvatarUrl = $('.new-comment img.avatar').attr('data-no-avatar-url');
		$('.new-comment img.avatar').attr('src', noAvatarUrl);
		$('.new-comment img.avatar').attr('alt', messages.anonym);
	}
	
	function onSignIn(googleUser) {
		googleProfile = googleUser.getBasicProfile();
		googleProfile.authToken = googleUser.getAuthResponse().id_token;
		$('#signin-form').modal('hide');
		if (googleProfile.getImageUrl() != null) {
			var noAvatarUrl = $('.new-comment img').attr('src');
			$('.new-comment img.avatar').attr('data-no-avatar-url', noAvatarUrl);
			$('.new-comment img.avatar').attr('src', googleProfile.getImageUrl());
		}
		$('.new-comment img.avatar').attr('alt', googleProfile.getName());
		$('.new-comment a.logout').removeClass('hidden');
	}
	
    // --------------------------- Google plus integration End ------------------------
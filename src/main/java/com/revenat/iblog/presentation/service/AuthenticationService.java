package com.revenat.iblog.presentation.service;

import com.revenat.iblog.application.domain.entity.Account;

public class AuthenticationService {
	private final SocialService socialService;
	private final AvatarService avatarService;
	
	public AuthenticationService(SocialService socialService, AvatarService avatarService) {
		this.socialService = socialService;
		this.avatarService = avatarService;
	}

	public Account authenticate(String authToken) {
		return null;
	}
}

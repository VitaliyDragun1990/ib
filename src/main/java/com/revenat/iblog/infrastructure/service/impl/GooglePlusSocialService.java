package com.revenat.iblog.infrastructure.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.revenat.iblog.infrastructure.exception.AuthenticationException;
import com.revenat.iblog.infrastructure.service.SocialAccount;
import com.revenat.iblog.infrastructure.service.SocialService;

class GooglePlusSocialService implements SocialService {
	private final String googlePlusClientId;
	private final List<String> issuers;
	
	public GooglePlusSocialService(String clientId) {
		this.googlePlusClientId = clientId;
		this.issuers = Arrays.asList("https://accounts.google.com", "accounts.google.com");
	}

	@Override
	public SocialAccount getSocialAccount(String authToken) {
		try {
			return obtainSocialAccount(authToken);
		} catch (GeneralSecurityException | IOException e) {
			throw new AuthenticationException("Error while authenticating via google plus api", e);
		}
	}

	private SocialAccount obtainSocialAccount(String authToken) throws GeneralSecurityException, IOException {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
				.setAudience(Collections.singleton(googlePlusClientId))
				.setIssuers(issuers)
				.build();
		GoogleIdToken idToken = verifier.verify(authToken);
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			return new SocialAccount(payload.getEmail(), (String) payload.get("given_name"), (String) payload.get("picture"));
		} else {
			throw new AuthenticationException("Can not get account by authToken: " + authToken);
		}
	}
}

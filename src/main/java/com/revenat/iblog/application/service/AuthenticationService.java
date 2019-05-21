package com.revenat.iblog.application.service;

import com.revenat.iblog.application.domain.entity.Account;
import com.revenat.iblog.application.infra.exception.AuthenticationException;

public interface AuthenticationService {

	/**
	 * Authenticates user via specified {@code authToken}.
	 * 
	 * @param authToken token using to authenticate user
	 * @return {@link Account} of the authenticated user
	 * @throws @{@link AuthenticationException} if error occurs during
	 *         authentication process.
	 */
	Account authenticate(String authToken);

}
package com.revenat.iblog.application.service;

import com.revenat.iblog.domain.entity.Account;
import com.revenat.iblog.infrastructure.exception.AuthenticationException;

public interface AuthenticationService {

	/**
	 * Authenticates user via specified {@code authToken}.
	 * 
	 * @param authToken token using to authenticate user
	 * @return {@link Account#getId()} of the authenticated user
	 * @throws @{@link AuthenticationException} if error occurs during
	 *         authentication process.
	 */
	long authenticate(String authToken) throws AuthenticationException;

}
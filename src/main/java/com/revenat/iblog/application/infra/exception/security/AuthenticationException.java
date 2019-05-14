package com.revenat.iblog.application.infra.exception.security;

import com.revenat.iblog.application.infra.exception.base.ApplicationException;

/**
 * Signals some kind of error during client authentication
 * process.
 * 
 * @author Vitaly Dragun
 *
 */
public class AuthenticationException extends ApplicationException {
	private static final long serialVersionUID = 25477902049903710L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause, 401);
	}
}

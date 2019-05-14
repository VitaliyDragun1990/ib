package com.revenat.iblog.presentation.infra.exception;

/**
 * Signals that input values specified by user is invalid (wrong format, out of
 * bounds, etc).
 * 
 * @author Vitaly Dragun
 *
 */
public class InputValidationException extends Exception {
	private static final long serialVersionUID = -2077735156808591318L;

	public InputValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InputValidationException(String message) {
		super(message);
	}
}

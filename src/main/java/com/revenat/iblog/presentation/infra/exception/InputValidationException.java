package com.revenat.iblog.presentation.infra.exception;

import com.revenat.iblog.application.infra.exception.base.ApplicationException;

/**
 * Signals that input values specified by user is invalid (wrong format, out of
 * bounds, etc).
 * 
 * @author Vitaly Dragun
 *
 */
public class InputValidationException extends ApplicationException {
	private static final long serialVersionUID = -2077735156808591318L;

	public InputValidationException(String message) {
		super(message, 400);
	}
}

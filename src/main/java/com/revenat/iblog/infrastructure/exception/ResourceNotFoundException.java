package com.revenat.iblog.infrastructure.exception;

import com.revenat.iblog.infrastructure.exception.base.ApplicationException;

/**
 * Signals that requested resource can not be found.
 * @author Vitaly Dragun
 *
 */
public class ResourceNotFoundException extends ApplicationException {
	private static final long serialVersionUID = 8885825981842064855L;

	public ResourceNotFoundException(String message) {
		super(message, 404);
	}
}

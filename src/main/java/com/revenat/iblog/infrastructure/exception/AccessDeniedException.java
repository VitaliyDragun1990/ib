package com.revenat.iblog.infrastructure.exception;

import com.revenat.iblog.infrastructure.exception.base.ApplicationException;

/**
 * Signals that client does not have required permissions to get requested
 * resource.
 * 
 * @author Vitaly Dragun
 *
 */
public class AccessDeniedException extends ApplicationException {
	private static final long serialVersionUID = -3739773835132414549L;

	public AccessDeniedException(String message) {
		super(message, 403);
	}
}

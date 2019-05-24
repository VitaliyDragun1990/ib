package com.revenat.iblog.infrastructure.exception.flow;

import com.revenat.iblog.infrastructure.exception.base.ApplicationException;

/**
 * Signals about exceptional cases in the application logic
 * 
 * @author Vitaly Dragun
 *
 */
public class FlowException extends ApplicationException {
	private static final long serialVersionUID = -1127720904105893710L;

	public FlowException(String message, Throwable cause, int code) {
		super(message, cause, code);
	}
	
	public FlowException(String message, Throwable cause) {
		super(message, cause, 500);
	}

	public FlowException(String message, int code) {
		super(message, code);
	}
}

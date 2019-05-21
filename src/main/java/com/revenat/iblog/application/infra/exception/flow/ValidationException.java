package com.revenat.iblog.application.infra.exception.flow;

/**
 * Raised when attribute values of the object model violates business rules or
 * restrictions
 * 
 * @author Vitaly Dragun
 *
 */
public class ValidationException extends FlowException {
	private static final long serialVersionUID = 6176636234085711406L;

	public ValidationException(String message) {
		super(message, 500);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}

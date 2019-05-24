package com.revenat.iblog.infrastructure.util;

import com.revenat.iblog.infrastructure.exception.ResourceNotFoundException;
import com.revenat.iblog.infrastructure.exception.flow.InvalidParameterException;
import com.revenat.iblog.infrastructure.exception.flow.ValidationException;

public class Checks {

	/**
	 * Verifies that specified check passed and throws exception otherwise
	 * 
	 * @param check   specific check to verify
	 * @param message specific message to pass to posiible exception
	 * @param args    optional arguments to message string
	 * @throws InvalidParameterException
	 */
	public static void checkParam(boolean check, String message, Object... args) throws InvalidParameterException {
		if (!check) {
			throw new InvalidParameterException(String.format(message, args));
		}
	}
	
	/**
	 * Verifies that specified resource not {@code null}
	 * 
	 * @param resource   resource to check for {@code null}
	 * @param message specific message to pass to posiible exception
	 * @param args    optional arguments to message string
	 * @throws ResourceNotFoundException
	 */
	public static void checkResource(Object resource, String message, Object... args) throws ResourceNotFoundException {
		if (resource == null) {
			throw new ResourceNotFoundException(String.format(message, args));
		}
	}

	/**
	 * Verifies that specified condition is reached and throws exception otherwise
	 * 
	 * @param condition specific condition to verify
	 * @param message   specific message to pass to posiible exception
	 * @param args      optional arguments to message string
	 * @throws ValidationException
	 */
	public static void validateCondition(boolean condition, String message, Object... args) throws ValidationException {
		if (!condition) {
			throw new ValidationException(String.format(message, args));
		}
	}

	private Checks() {
	}
}

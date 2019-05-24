package com.revenat.iblog.infrastructure.exception;

import com.revenat.iblog.infrastructure.exception.base.ApplicationException;

/**
 * SIgnals about incorrect configuration settings/parameters
 * 
 * @author Vitaly Dragun
 *
 */
public class ConfigurationException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public ConfigurationException(String message) {
		super(message, 500);
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause, 500);
	}
	
	public ConfigurationException(Throwable cause) {
		super(cause, 500);
	}
}

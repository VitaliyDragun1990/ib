package com.revenat.iblog.application.domain.form;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.revenat.iblog.application.infra.exception.flow.InvalidParameterException;

public class ContactForm extends AbstractForm {
	private String name;
	private String email;
	private String message;

	@Override
	public void validate() throws InvalidParameterException {
		if (!EmailValidator.getInstance().isValid(email)) {
			throw new InvalidParameterException("Email is invalid");
		}
		if (StringUtils.isBlank(name)) {
			throw new InvalidParameterException("Name is required");
		}
		if (StringUtils.isBlank(message)) {
			throw new InvalidParameterException("Message is required");
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

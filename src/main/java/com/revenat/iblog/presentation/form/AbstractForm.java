package com.revenat.iblog.presentation.form;

import java.util.Locale;

import com.revenat.iblog.application.service.I18nService;
import com.revenat.iblog.presentation.infra.exception.InputValidationException;

public abstract class AbstractForm {
	protected Locale locale;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void validate(I18nService i18nService) throws InputValidationException {
		
	}
	
	public abstract void validate() throws InputValidationException;
}

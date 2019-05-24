package com.revenat.iblog.application.form;

import java.util.Locale;

import com.revenat.iblog.application.service.I18nService;
import com.revenat.iblog.infrastructure.exception.flow.InvalidParameterException;

public abstract class AbstractForm {
	protected Locale locale;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void validate(I18nService i18nService) throws InvalidParameterException {
		
	}
	
	public abstract void validate() throws InvalidParameterException;
}

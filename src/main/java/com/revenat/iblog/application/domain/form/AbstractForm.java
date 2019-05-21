package com.revenat.iblog.application.domain.form;

import java.util.Locale;

import com.revenat.iblog.application.infra.exception.flow.InvalidParameterException;
import com.revenat.iblog.application.service.I18nService;

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

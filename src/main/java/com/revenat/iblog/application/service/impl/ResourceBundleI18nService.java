package com.revenat.iblog.application.service.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import com.revenat.iblog.application.service.I18nService;

class ResourceBundleI18nService implements I18nService {

	private final String bundlePath;
	
	public ResourceBundleI18nService(String bundlePath) {
		this.bundlePath = bundlePath;
	}

	@Override
	public String getMessage(String key, Locale locale, Object... args) {
		String value = ResourceBundle.getBundle(bundlePath, locale).getString(key);
		for (int i = 0; i < args.length; i++) {
			value = value.replace("{" + i + "}", args[i].toString());
		}
		return value;
	}
}

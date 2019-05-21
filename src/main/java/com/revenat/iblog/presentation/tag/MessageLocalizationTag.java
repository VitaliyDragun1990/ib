package com.revenat.iblog.presentation.tag;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.revenat.iblog.application.service.I18nService;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;

public class MessageLocalizationTag extends SimpleTagSupport {
	private String key;
	private String args;
	private Locale locale;
	private String lang;
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setArgs(String args) {
		this.args = args;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		I18nService i18n = (I18nService) getJspContext().getAttribute(Attribute.I18N, PageContext.APPLICATION_SCOPE);
		determineLocale();
		
		String value = null;
		if (args != null) {
			String[] arguments = args.split(",");
			value = i18n.getMessage(key, locale, (Object[])arguments);
		} else {
			value = i18n.getMessage(key, locale);
		}
		getJspContext().getOut().print(value);
	}
	
	private void determineLocale() {
		if (locale == null && lang == null) {
			locale = (Locale) getJspContext().getAttribute(Attribute.USER_LOCALE, PageContext.SESSION_SCOPE);
		} else if (locale == null) {
			locale = new Locale(lang);
		}
	}

}

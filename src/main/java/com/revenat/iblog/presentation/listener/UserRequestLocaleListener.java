package com.revenat.iblog.presentation.listener;

import java.util.Locale;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import com.revenat.iblog.presentation.infra.config.Constants.Attribute;

@WebListener
public class UserRequestLocaleListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// do nothing
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
		Locale locale = request.getLocale();
		request.getSession().setAttribute(Attribute.USER_LOCALE, locale);
	}
}

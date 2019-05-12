package com.revenat.iblog.presentation.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.revenat.iblog.presentation.infra.config.Constants;

public class ApplicationSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		session.setMaxInactiveInterval(Constants.SESSION_MAX_INACTIVE_INTERVAL);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	}
}

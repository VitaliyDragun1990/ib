package com.revenat.iblog.application.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.application.service.NotificationService;

/**
 * This implementation of the {@link NotificationService} logs notifications.
 * 
 * @author Vitaly Dragun
 *
 */
class DummyNotificationService implements NotificationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DummyNotificationService.class);

	@Override
	public void sendNotification(String title, String content) {
		LOGGER.info("New comment: title='{}', content='{}'", title, content);

	}

	@Override
	public void shutdown() {
		// do nothing
	}
}
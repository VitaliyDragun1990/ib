package com.revenat.iblog.application.service;

/**
 * This interface represents service responsible for sending notifications.
 * 
 * @author Vitaly Dragun
 *
 */
public interface NotificationService {

	/**
	 * Sends notification with specified {@code title} and {@code content}
	 * 
	 * @param title   title of the notification to send
	 * @param content content of the notification to send
	 */
	void sendNotification(String title, String content);

	/**
	 * Shuts down notification service.
	 */
	void shutdown();
}

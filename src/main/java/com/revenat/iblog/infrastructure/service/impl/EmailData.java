package com.revenat.iblog.infrastructure.service.impl;

/**
 * This value object contains data needed to
 * {@link AsyncEmailNotificationService} to work.
 * 
 * @author Vitaly Dragun
 *
 */
final class EmailData {
	private final String notificationEmail;
	private final String fromEmail;
	private final int tryAttempts;
	private final String smtpServer;
	private final String smtpPort;
	private final String smtpUsername;
	private final String smtpPassword;

	public EmailData(String notificationEmail, String fromEmail, int tryAttempts, String smtpServer, String smtpPort,
			String smtpUsername, String smtpPassword) {
		this.notificationEmail = notificationEmail;
		this.fromEmail = fromEmail;
		this.tryAttempts = tryAttempts;
		this.smtpServer = smtpServer;
		this.smtpPort = smtpPort;
		this.smtpUsername = smtpUsername;
		this.smtpPassword = smtpPassword;
	}

	public String getNotificationEmail() {
		return notificationEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}
	
	public int getTryAttempts() {
		return tryAttempts;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}
}

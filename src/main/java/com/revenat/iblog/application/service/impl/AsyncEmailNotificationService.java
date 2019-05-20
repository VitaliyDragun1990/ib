package com.revenat.iblog.application.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.application.domain.model.AbstractModel;
import com.revenat.iblog.application.service.NotificationService;

class AsyncEmailNotificationService implements NotificationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncEmailNotificationService.class);
	private final ExecutorService executorService;
	private final EmailData emailData;

	public AsyncEmailNotificationService(EmailData emailData) {
		this.executorService = Executors.newCachedThreadPool();
		this.emailData = emailData;
	}

	@Override
	public void sendNotification(String title, String content) {
		executorService.submit(new EmailItem(title, content));
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

	private class EmailItem extends AbstractModel implements Runnable {
		private final String subject;
		private final String content;
		private int tryAttempts;

		public EmailItem(String subject, String content) {
			this.subject = subject;
			this.content = content;
			this.tryAttempts = emailData.getTryAttempts();
		}
		private boolean isValidTry() {
			return tryAttempts > 0;
		}

		@Override
		public void run() {
			try {
				SimpleEmail email = new SimpleEmail();
				email.setCharset("utf-8");
				
				email.setHostName(emailData.getSmtpServer());
				email.setSmtpPort(Integer.parseInt(emailData.getSmtpPort()));
				email.setSSLCheckServerIdentity(true);
				email.setStartTLSEnabled(true);
				
				email.setAuthenticator(new DefaultAuthenticator(emailData.getSmtpUsername(), emailData.getSmtpPassword()));
				email.setFrom(emailData.getFromEmail());
				email.setSubject(subject);
				email.setMsg(content);
				email.addTo( emailData.getNotificationEmail());
				email.send(); // TODO: commented for testing puprose
				LOGGER.info("Email with subject '{}' and content '{}' has been sent to {}",
						subject, content, emailData.getNotificationEmail());
			} catch (EmailException e) {
				LOGGER.error("Can't send email: " + e.getMessage(), e);
				tryAttempts--;
				if (isValidTry()) {
					LOGGER.info("Resend email: {}", this);
					executorService.submit(this);
				} else {
					LOGGER.error("Email has not been sent: limit of try attempts");
				}
			} catch (Exception e) {
				LOGGER.error("Error during sending email: {}",e.getMessage(), e);
			}
		}
	}
}

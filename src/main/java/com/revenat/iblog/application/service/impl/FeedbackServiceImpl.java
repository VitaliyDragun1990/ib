package com.revenat.iblog.application.service.impl;

import com.revenat.iblog.application.form.CommentForm;
import com.revenat.iblog.application.form.ContactForm;
import com.revenat.iblog.application.service.FeedbackService;
import com.revenat.iblog.application.service.I18nService;
import com.revenat.iblog.infrastructure.service.NotificationService;

class FeedbackServiceImpl implements FeedbackService {
	private final I18nService i18nService;
	private final NotificationService notificationService;

	public FeedbackServiceImpl(I18nService i18nService, NotificationService notificationService) {
		this.i18nService = i18nService;
		this.notificationService = notificationService;
	}

	@Override
	public void sendNewCommentNotification(CommentForm form) {
		String title = i18nService.getMessage("notification.newComment.title", form.getLocale(), form.getArticleTitle());
		String content = i18nService.getMessage("notification.newComment.content", form.getLocale(), form.getArticleTitle(),
				form.getArticleUrl(), form.getContent());
		notificationService.sendNotification(title, content);
	}

	@Override
	public void sendContactRequestNotification(ContactForm form) {
		form.validate();
		String title = i18nService.getMessage("notification.contact.title", form.getLocale());
		String content = i18nService.getMessage("notification.contact.content",
				form.getLocale(), form.getName(), form.getEmail(), form.getMessage());
		notificationService.sendNotification(title, content);
	}

}

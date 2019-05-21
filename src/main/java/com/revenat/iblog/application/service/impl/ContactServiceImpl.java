package com.revenat.iblog.application.service.impl;

import com.revenat.iblog.application.domain.form.ContactForm;
import com.revenat.iblog.application.service.ContactService;
import com.revenat.iblog.application.service.I18nService;
import com.revenat.iblog.application.service.NotificationService;
import com.revenat.iblog.presentation.infra.exception.InputValidationException;

public class ContactServiceImpl implements ContactService {
	private final I18nService i18nService;
	private final NotificationService notificationService;
	
	public ContactServiceImpl(I18nService i18nService, NotificationService notificationService) {
		this.i18nService = i18nService;
		this.notificationService = notificationService;
	}

	@Override
	public void createContactRequest(ContactForm form) throws InputValidationException {
		form.validate();
		String title = i18nService.getMessage("notification.contact.title", form.getLocale());
		String content = i18nService.getMessage("notification.contact.content",
				form.getLocale(), form.getName(), form.getEmail(), form.getMessage());
		notificationService.sendNotification(title, content);
	}
}

package com.revenat.iblog.application.service;

import com.revenat.iblog.application.domain.form.ContactForm;
import com.revenat.iblog.application.infra.exception.flow.InvalidParameterException;

public interface ContactService {

	public void createContactRequest(ContactForm form) throws InvalidParameterException;
}

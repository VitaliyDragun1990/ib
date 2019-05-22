package com.revenat.iblog.presentation.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.application.domain.form.ContactForm;
import com.revenat.iblog.application.infra.exception.flow.InvalidParameterException;
import com.revenat.iblog.application.infra.exception.flow.ValidationException;
import com.revenat.iblog.application.service.ContactService;
import com.revenat.iblog.presentation.controller.AbstractController;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Page;
import com.revenat.iblog.presentation.infra.config.Constants.URL;

public class ContactController extends AbstractController {
	private static final long serialVersionUID = -6579422407941195197L;
	
	private final ContactService contactService;
	
	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Boolean displayInfoMessage = shouldInfoMessageBeDisplayed(req);
		req.setAttribute(Attribute.DISPLAY_INFO_MESSAGE, displayInfoMessage);
		forwardToPage(Page.CONTACT, req, resp);
	}

	private Boolean shouldInfoMessageBeDisplayed(HttpServletRequest req) {
		Boolean displayInfoMessage = (Boolean) req.getSession().getAttribute(Attribute.DISPLAY_INFO_MESSAGE);
		if (displayInfoMessage == null) {
			displayInfoMessage = false;
		} else {
			req.getSession().removeAttribute(Attribute.DISPLAY_INFO_MESSAGE);
		}
		return displayInfoMessage;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ContactForm form = createForm(req, ContactForm.class);
			contactService.createContactRequest(form);
			req.getSession().setAttribute(Attribute.DISPLAY_INFO_MESSAGE, Boolean.TRUE);
			redirect(URL.CONTACT, resp);
		} catch (InvalidParameterException e) {
			throw new ValidationException("Validation should be done on client side: " + e.getMessage(), e);
		}
	}
}

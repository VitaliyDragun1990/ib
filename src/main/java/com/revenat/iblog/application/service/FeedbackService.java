package com.revenat.iblog.application.service;

import com.revenat.iblog.application.form.CommentForm;
import com.revenat.iblog.application.form.ContactForm;

public interface FeedbackService {
	
	void sendNewCommentNotification(CommentForm form);
	
	void sendContactRequestNotification(ContactForm form);

}

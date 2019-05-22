package com.revenat.iblog.application.service.impl;

import com.revenat.iblog.application.domain.entity.Account;
import com.revenat.iblog.application.domain.entity.Comment;
import com.revenat.iblog.application.domain.form.CommentForm;
import com.revenat.iblog.application.service.AuthenticationService;
import com.revenat.iblog.application.service.I18nService;
import com.revenat.iblog.application.service.NotificationService;
import com.revenat.iblog.persistence.repository.ArticleRepository;
import com.revenat.iblog.persistence.repository.CategoryRepository;
import com.revenat.iblog.persistence.repository.CommentRepository;

/**
 * This is development-like implementations of the {@link ArticleService}
 * interface. Main difference from production-like {@link ArticleServiceImpl}
 * implementations is that it's method
 * {@link this#addCommentToArticle(com.revenat.iblog.application.domain.form.CommentForm,
 * String)} doesn't actually persists newly added comment to underlying
 * datastore and such comment won't be visible to other users of the
 * application.
 * 
 * @author Vitaly Dragun
 *
 */
class DummyArticleService extends ArticleServiceImpl {

	public DummyArticleService(ArticleRepository articleRepo, CategoryRepository categoryRepo,
			CommentRepository commentRepo, NotificationService notificationService, I18nService i18nService,
			AuthenticationService authService) {
		super(articleRepo, categoryRepo, commentRepo, notificationService, i18nService, authService);
	}

	@Override
	public Comment addCommentToArticle(CommentForm form, String articleUri) {
		form.validate();
		Account a = authService.authenticate(form.getAuthToken());
		Comment c = new Comment();
		c.setArticleId(form.getArticleId());
		c.setContent(form.getContent());
		c.setAccount(a);
		
		sendNewCommentNotification(articleRepo.getById(form.getArticleId()), form.getContent(), articleUri,
				form.getLocale());
		return c;
	}
}

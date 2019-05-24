package com.revenat.iblog.application.service.impl;

import com.revenat.iblog.application.dto.CommentDTO;
import com.revenat.iblog.application.form.CommentForm;
import com.revenat.iblog.application.service.AccountService;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.application.service.AuthenticationService;
import com.revenat.iblog.application.service.FeedbackService;
import com.revenat.iblog.application.transform.Transformer;
import com.revenat.iblog.domain.entity.Comment;
import com.revenat.iblog.infrastructure.repository.ArticleRepository;
import com.revenat.iblog.infrastructure.repository.CategoryRepository;
import com.revenat.iblog.infrastructure.repository.CommentRepository;

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
			CommentRepository commentRepo, AuthenticationService authService, AccountService accountService,
			FeedbackService feedbackService, Transformer transformer) {
		super(articleRepo, categoryRepo, commentRepo, authService, accountService, feedbackService, transformer);
	}

	@Override
	public CommentDTO addCommentToArticle(CommentForm form) {
		form.validate();
		long accountId = authService.authenticate(form.getAuthToken());
		Comment c = new Comment();
		c.setArticleId(form.getArticleId());
		c.setContent(form.getContent());
		c.setAccountId(accountId);
		
		feedbackService.sendNewCommentNotification(form);
		return transform(c, CommentDTO.class, (e, dto) -> dto.setAccount(accountService.getById(e.getAccountId())));
	}
}

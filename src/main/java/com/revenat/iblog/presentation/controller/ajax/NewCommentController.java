package com.revenat.iblog.presentation.controller.ajax;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.application.domain.entity.Account;
import com.revenat.iblog.application.domain.entity.Comment;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.application.service.AuthenticationService;
import com.revenat.iblog.presentation.controller.AbstractController;
import com.revenat.iblog.presentation.form.CommentForm;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Fragment;

public class NewCommentController extends AbstractController {
	private static final long serialVersionUID = 1764993478783499111L;
	
	private final ArticleService articleService;
	private final AuthenticationService authService;

	public NewCommentController(ArticleService articleService, AuthenticationService authService) {
		this.articleService = articleService;
		this.authService = authService;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CommentForm form = createForm(req, CommentForm.class);
		form.validate();
		Account account = authService.authenticate(form.getAuthToken());
		Comment comment = 
				articleService.addComment(form.getArticleId(), form.getContent(), account);
		
		req.setAttribute(Attribute.COMMENTS, Collections.singletonList(comment));
		forwardToFragment(Fragment.COMMENTS, req, resp);
	}
}

package com.revenat.iblog.presentation.controller.ajax;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.application.domain.entity.Comment;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.presentation.controller.AbstractController;
import com.revenat.iblog.presentation.form.CommentForm;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Fragment;

public class NewCommentController extends AbstractController {
	private static final long serialVersionUID = 1764993478783499111L;
	
	private static final String CONTENT = "content";
	private static final String AUTH_TOKEN = "authToken";
	private static final String ARTICLE_ID = "articleId";

	
	private final ArticleService articleService;

	public NewCommentController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CommentForm form = getCommentForm(req);
		Comment comment = 
				articleService.addComment(form.getArticleId(), form.getContent(), form.getAuthToken());
		
		req.setAttribute(Attribute.COMMENTS, Collections.singletonList(comment));
		forwardToFragment(Fragment.COMMENTS, req, resp);
	}

	private CommentForm getCommentForm(HttpServletRequest req) {
		long articleId = Long.parseLong(req.getParameter(ARTICLE_ID));
		String authToken = req.getParameter(AUTH_TOKEN);
		String content = req.getParameter(CONTENT);
		return new CommentForm(articleId, content, authToken);
	}
}

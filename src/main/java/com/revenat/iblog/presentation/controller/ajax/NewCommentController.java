package com.revenat.iblog.presentation.controller.ajax;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.entity.Comment;
import com.revenat.iblog.application.domain.form.CommentForm;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.presentation.controller.AbstractController;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Fragment;

public class NewCommentController extends AbstractController {
	private static final long serialVersionUID = 1764993478783499111L;
	
	private final String applicationHost;
	private final ArticleService articleService;

	public NewCommentController(ArticleService articleService, String applicationHost) {
		this.articleService = articleService;
		this.applicationHost = applicationHost;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CommentForm form = createForm(req, CommentForm.class);
		Article article = articleService.findArticle(form.getArticleId());
		
		String articleUrl = getArticleUrl(article);
		Comment comment = 
				articleService.addCommentToArticle(form, articleUrl);
		
		req.setAttribute(Attribute.COMMENTS, Collections.singletonList(comment));
		forwardToFragment(Fragment.COMMENTS, req, resp);
	}

	private String getArticleUrl(Article article) {
		return applicationHost + getServletContext().getContextPath() + "/article/" + article.getId() + article.getUrl();
	}
}

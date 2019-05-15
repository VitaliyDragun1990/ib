package com.revenat.iblog.presentation.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.model.Items;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.presentation.controller.AbstractController;
import com.revenat.iblog.presentation.infra.config.Constants;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Page;

public class NewsController extends AbstractController {
	private static final long serialVersionUID = -819614170522907816L;
	
	private final ArticleService articleService;
	
	public NewsController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Items<Article> articles = articleService.listArticles(1, Constants.ATRILCES_PER_PAGE);
		req.setAttribute(Attribute.ARTICLES, articles.getItems());
		
		forwardToPage(Page.NEWS, req, resp);
	}
}

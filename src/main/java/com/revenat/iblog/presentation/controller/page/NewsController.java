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
import com.revenat.iblog.presentation.model.Pagination;

public class NewsController extends AbstractController {
	private static final long serialVersionUID = -819614170522907816L;
	
	private final ArticleService articleService;
	
	public NewsController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int page = getPageNumber(req);
		String requestUri = req.getRequestURI();
		Items<Article> articles = articleService.listArticles(page, Constants.ITEMS_PER_PAGE);
		Pagination pagination = new Pagination.Builder(requestUri+"?", page, articles.getCount()).build();
		
		req.setAttribute(Attribute.ARTICLES, articles.getItems());
		req.setAttribute(Attribute.PAGINATION, pagination);
		
		forwardToPage(Page.NEWS, req, resp);
	}
}

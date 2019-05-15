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
import com.revenat.iblog.presentation.infra.config.Constants.URL;

public class NewsByCategoryController extends AbstractController {
	private static final long serialVersionUID = -819614170522907816L;
	private static final int SUBSTRING_INDEX = URL.NEWS.length();
	
	private final ArticleService articleService;
	
	public NewsByCategoryController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryUrl = getSelectedCategoryUrl(req);
		Items<Article> articles = articleService.listArticlesByCategory(categoryUrl, 1, Constants.ATRILCES_PER_PAGE);
		req.setAttribute(Attribute.ARTICLES, articles.getItems());
		req.setAttribute(Attribute.SELECTED_CATEGORY_URL, categoryUrl);
		
		forwardToPage(Page.NEWS, req, resp);
	}

	private String getSelectedCategoryUrl(HttpServletRequest req) {
		return req.getRequestURI().substring(SUBSTRING_INDEX);
	}
}

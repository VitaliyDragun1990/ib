package com.revenat.iblog.presentation.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.model.Items;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.presentation.controller.AbstractController;
import com.revenat.iblog.presentation.infra.config.Constants;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Page;
import com.revenat.iblog.presentation.infra.config.Constants.URL;

public class SearchController extends AbstractController {
	private static final long serialVersionUID = -6579422407941195197L;
	
	private final ArticleService articleService;
	
	public SearchController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getParameter("query");
		String categoryUrl = req.getParameter("categoryUrl");
		if (StringUtils.isNotBlank(query)) {
			Items<Article> articles =
					articleService.listArticlesBySearchQuery(query, categoryUrl, 1, Constants.ATRILCES_PER_PAGE);
			req.setAttribute(Attribute.ARTICLES, articles.getItems());
			req.setAttribute(Attribute.SEARCH_QUERY, query);
			req.setAttribute(Attribute.ARTICLE_COUNT, articles.getCount());
			req.setAttribute(Attribute.SELECTED_CATEGORY_URL, categoryUrl);
			
			forwardToPage(Page.SEARCH, req, resp);
		} else {
			redirect(URL.NEWS, resp);
		}
	}
}

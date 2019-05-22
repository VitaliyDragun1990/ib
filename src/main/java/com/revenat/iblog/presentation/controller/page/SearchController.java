package com.revenat.iblog.presentation.controller.page;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.revenat.iblog.application.domain.entity.Article;
import com.revenat.iblog.application.domain.model.Items;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.application.service.CategoryService;
import com.revenat.iblog.presentation.controller.AbstractController;
import com.revenat.iblog.presentation.infra.config.Constants;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Page;
import com.revenat.iblog.presentation.infra.config.Constants.URL;
import com.revenat.iblog.presentation.model.Pagination;

public class SearchController extends AbstractController {
	private static final long serialVersionUID = -6579422407941195197L;
	private static final String CATEGORY_URL = "categoryUrl";
	private static final String QUERY = "query";

	
	private final ArticleService articleService;
	private final CategoryService categoryService;
	
	public SearchController(ArticleService articleService, CategoryService categoryService) {
		this.articleService = articleService;
		this.categoryService = categoryService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestUri = req.getRequestURI();
		int page = getPageNumber(req);
		String query = req.getParameter(QUERY);
		String categoryUrl = req.getParameter(CATEGORY_URL);
		
		if (StringUtils.isNotBlank(query)) {
			Items<Article> articles =
					articleService.listArticlesBySearchQuery(query, categoryUrl, page, Constants.ITEMS_PER_PAGE);
			
			String baseUrl = buildBaseURL(requestUri, query, categoryUrl);
			
			req.setAttribute(Attribute.ARTICLES, articles.getItems());
			req.setAttribute(Attribute.SEARCH_QUERY, query);
			req.setAttribute(Attribute.ARTICLE_COUNT, articles.getCount());
			req.setAttribute(Attribute.SELECTED_CATEGORY, categoryService.findByUrl(categoryUrl));
			req.setAttribute(Attribute.PAGINATION, new Pagination.Builder(baseUrl, page, articles.getCount()).build());
			
			forwardToPage(Page.SEARCH, req, resp);
		} else {
			redirect(URL.NEWS, resp);
		}
	}

	private String buildBaseURL(String requestUri, String query, String categoryUrl) throws UnsupportedEncodingException {
		String baseUrl = requestUri + "?query=" + query;
		if (StringUtils.isNoneEmpty(categoryUrl)) {
			baseUrl += "&categoryUrl=" + URLEncoder.encode(categoryUrl, "UTF-8");
		}
		return baseUrl+"&";
	}
}

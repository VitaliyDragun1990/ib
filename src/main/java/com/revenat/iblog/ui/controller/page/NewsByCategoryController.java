package com.revenat.iblog.ui.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.application.dto.ArticleDTO;
import com.revenat.iblog.application.model.Items;
import com.revenat.iblog.application.service.ArticleService;
import com.revenat.iblog.application.service.CategoryService;
import com.revenat.iblog.ui.config.Constants;
import com.revenat.iblog.ui.config.Constants.Attribute;
import com.revenat.iblog.ui.config.Constants.Page;
import com.revenat.iblog.ui.config.Constants.URL;
import com.revenat.iblog.ui.controller.AbstractController;
import com.revenat.iblog.ui.model.Pagination;

public class NewsByCategoryController extends AbstractController {
	private static final long serialVersionUID = -819614170522907816L;
	private static final int SUBSTRING_INDEX = URL.NEWS.length();
	
	private final ArticleService articleService;
	private final CategoryService categoryService;
	
	public NewsByCategoryController(ArticleService articleService, CategoryService categoryService) {
		this.articleService = articleService;
		this.categoryService = categoryService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryUrl = getSelectedCategoryUrl(req);
		int page = getPageNumber(req);
		String requestUri = req.getRequestURI();
		Items<ArticleDTO> articles = articleService.listArticlesByCategory(categoryUrl, page, Constants.ITEMS_PER_PAGE);
		Pagination pagination = new Pagination.Builder(requestUri+"?", page, articles.getCount()).build();
		
		req.setAttribute(Attribute.ARTICLES, articles.getItems());
		req.setAttribute(Attribute.SELECTED_CATEGORY, categoryService.findByUrl(categoryUrl));
		req.setAttribute(Attribute.PAGINATION, pagination);
		
		forwardToPage(Page.NEWS, req, resp);
	}

	private String getSelectedCategoryUrl(HttpServletRequest req) {
		return req.getRequestURI().substring(SUBSTRING_INDEX);
	}
}

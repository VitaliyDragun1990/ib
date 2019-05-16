package com.revenat.iblog.presentation.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revenat.iblog.application.domain.entity.Category;
import com.revenat.iblog.application.service.CategoryService;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.URL;

public class CategoriesLoaderFilter extends AbstractFilter {
	private final CategoryService categoryService;
	
	public CategoriesLoaderFilter(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Map<Integer, Category> categories;
		if (request.getRequestURI().startsWith(URL.SEARCH)) {
			String serachQuery = request.getParameter("query");
			categories = categoryService.getCategoriesWithMatchedArticleCount(serachQuery);
		} else {
			categories = categoryService.getCategoriesWithTotalArticleCount();
		}
		request.getServletContext().setAttribute(Attribute.CATEGORY_MAP, categories);
		
		chain.doFilter(request, response);
	}

}

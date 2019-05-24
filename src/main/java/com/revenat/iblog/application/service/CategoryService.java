package com.revenat.iblog.application.service;

import java.util.Map;

import com.revenat.iblog.domain.entity.Category;

public interface CategoryService {

	Category findByUrl(String categoryUrl);

	Map<Integer, Category> getCategoriesWithTotalArticleCount();

	Map<Integer, Category> getCategoriesWithMatchedArticleCount(String searchQuery);

}
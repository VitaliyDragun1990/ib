package com.revenat.iblog.application.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.revenat.iblog.application.domain.entity.Category;
import com.revenat.iblog.application.service.CategoryService;
import com.revenat.iblog.persistence.repository.CategoryRepository;

class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepo;

	public CategoryServiceImpl(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}

	@Override
	public Category findByUrl(String categoryUrl) {
		return categoryRepo.getByUrl(categoryUrl);
	}

	@Override
	public Map<Integer, Category> getCategoriesWithTotalArticleCount() {
		List<Category> categories = categoryRepo.getAll();
		return transform(categories);
	}
	
	@Override
	public Map<Integer, Category> getCategoriesWithMatchedArticleCount(String searchQuery) {
		List<Category> categories = categoryRepo.getByArticleSearchQuery(searchQuery);
		return transform(categories);
	}

	private Map<Integer, Category> transform(List<Category> categories) {
		Map<Integer, Category> map = new LinkedHashMap<>();
		for (Category category : categories) {
			map.put(category.getId(), category);
		}
		return map;
	}
}

package com.revenat.iblog.application.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.revenat.iblog.application.domain.entity.Category;
import com.revenat.iblog.application.infra.util.Checks;
import com.revenat.iblog.persistence.repository.CategoryRepository;

public class CategoryService {
	private final CategoryRepository categoryRepo;

	CategoryService(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}
	
	public Category findByUrl(String categoryUrl) {
		Category category = categoryRepo.getByUrl(categoryUrl);
//		Checks.checkResource(category, "There is no category for such url: %s", categoryUrl);
		return category;
	}

	public Map<Integer, Category> getCategoriesWithTotalArticleCount() {
		List<Category> categories = categoryRepo.getAll();
		return transform(categories);
	}
	
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

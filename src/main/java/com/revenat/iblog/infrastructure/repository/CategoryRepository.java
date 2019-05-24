package com.revenat.iblog.infrastructure.repository;

import java.util.List;

import com.revenat.iblog.domain.entity.Category;

public interface CategoryRepository {

	List<Category> getAll();
	
	Category getByUrl(String url);
	
	List<Category> getByArticleSearchQuery(String searchQuery);
}
